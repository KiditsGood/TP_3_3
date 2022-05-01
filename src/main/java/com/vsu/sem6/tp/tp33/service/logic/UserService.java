package com.vsu.sem6.tp.tp33.service.logic;

import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.UserDto;

import java.util.UUID;

public interface UserService {
    PageDto<UserDto> findAll(Integer pageNumber, Integer totalPages);

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void deleteById(UUID id);

    UserDto findById(UUID id);
}
