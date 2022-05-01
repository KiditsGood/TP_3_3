package com.vsu.sem6.tp.tp33.service.logic.impl;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.repository.UserRepository;
import com.vsu.sem6.tp.tp33.service.logic.UserService;
import com.vsu.sem6.tp.tp33.service.mapper.CycleAvoidingMappingContext;
import com.vsu.sem6.tp.tp33.service.mapper.UserMapper;
import com.vsu.sem6.tp.tp33.service.model.ImmutablePageDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public PageDto<UserDto> findAll(Integer pageNumber, Integer totalPages) {
        Page<UserDto> userDtos = userRepository
                .findAll(Pageable.ofSize(totalPages).withPage(pageNumber))
                .map(entity->userMapper.fromEntity(entity,new CycleAvoidingMappingContext()));

        return ImmutablePageDto.<UserDto>builder()
                .pageNumber(pageNumber)
                .totalPages(totalPages)
                .items(userDtos.getContent())
                .build();
    }

    @Override
    public UserDto create(UserDto userDto) {
        return Optional.ofNullable(userDto)
                .filter(dto -> dto.getId() == null)
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(entity->userMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
    }

    @Override
    public UserDto update(UserDto userDto) {
        return Optional.ofNullable(userDto)
                .filter(dto -> userRepository.existsById(dto.getId()))
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(entity->userMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
    }

    @Override
    public void deleteById(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ApiRequestException("Wrong id");
        }
    }

    @Override
    public UserDto findById(UUID id) {
        return userRepository
                .findById(id)
                .map(entity->userMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong id"));
    }
}
