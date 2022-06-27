package com.vsu.sem6.tp.tp33.service.logic;

import com.vsu.sem6.tp.tp33.persistence.entity.User;
import com.vsu.sem6.tp.tp33.service.model.RegistrationUserDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.UserDto;

import java.util.UUID;

public interface UserService {
    PageDto<UserDto> findAll(Integer pageNumber, Integer pageSize);

    UserDto create(RegistrationUserDto userDto);

    UserDto update(UserDto userDto);

    void deleteById(UUID id);

    UserDto findById(UUID id);

    User findByEmailAndPassword(String email, String password);

    UserDto findUserByToken();
    void updatePassword(User user);
}
