package com.vsu.sem6.tp.tp33.service.logic.impl;

import com.vsu.sem6.tp.tp33.config.SecurityService;
import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.entity.Role;
import com.vsu.sem6.tp.tp33.persistence.entity.User;
import com.vsu.sem6.tp.tp33.persistence.repository.RoleRepository;
import com.vsu.sem6.tp.tp33.persistence.repository.UserRepository;
import com.vsu.sem6.tp.tp33.service.logic.UserService;
import com.vsu.sem6.tp.tp33.service.mapper.CycleAvoidingMappingContext;
import com.vsu.sem6.tp.tp33.service.mapper.UserMapper;
import com.vsu.sem6.tp.tp33.service.model.RegistrationUserDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
//
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final SecurityService securityService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.securityService = securityService;
    }


    @Override
    public PageDto<UserDto> findAll(Integer pageNumber, Integer totalPages) {
//        Page<UserDto> userDtos = userRepository
//                .findAll(Pageable.ofSize(totalPages).withPage(pageNumber))
//                .map(entity->userMapper.fromEntity(entity,new CycleAvoidingMappingContext()));
//
//        return ImmutablePageDto.<UserDto>builder()
//                .pageNumber(pageNumber)
//                .totalPages(totalPages)
//                .items(userDtos.getContent())
//                .build();
        return null;
    }

    @Override
    public UserDto create(RegistrationUserDto userDto) {
        Optional.ofNullable(userDto).orElseThrow(() -> new ApiRequestException("Wrong data"));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.regToEntity(userDto);
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRole(role);
        User entity = userRepository.save(user);
        return userMapper.fromEntity(entity, new CycleAvoidingMappingContext());


//                .map(userMapper::toEntity)
//                .map(userRepository::save)
//                .map(entity->userMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
//                .orElseThrow(() -> new ApiRequestException("Wrong data"));
    }

    @Override
    public UserDto update(UserDto userDto) {

        userDto = Optional.ofNullable(userDto)
                .filter(dto -> userRepository.existsById(dto.getId()))
                .filter(u -> securityService.checkAccess(u.getId()))
                .orElseThrow(() -> new ApiRequestException("access denied"));
        User user = userMapper.toEntity(userDto);
        user.setRole(userRepository.findById(userDto.getId()).get().getRole());
        return userMapper.fromEntity(userRepository.save(user), new CycleAvoidingMappingContext());

    }

    //Userdto get(){}
    @Override
    public void deleteById(UUID id) {
        if (!securityService.checkAccess(id)) {
            throw new ApiRequestException("access denied");
        }
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ApiRequestException("Wrong id");
        }
    }

    @Override
    public UserDto findById(UUID id) {
        if (!securityService.checkAccess(id)) {
            throw new ApiRequestException("access denied");
        }

        return userRepository.findById(id).map(entity -> userMapper.fromEntity(entity, new CycleAvoidingMappingContext())).orElseThrow(() -> new ApiRequestException("Wrong id"));
    }


    public User findByEmail(String email) {

        return userRepository.findUserByEmail(email);
    }

    public User findByEmailAndPassword(String email, String password) {
        User user = findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public UserDto findUserByToken() {
        return userRepository.findById(securityService.getUserId())
                .map(entity -> userMapper.fromEntity(entity, new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong id"));

    }

    @Override
    public void updatePassword(User user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
