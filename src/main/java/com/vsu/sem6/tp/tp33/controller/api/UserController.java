package com.vsu.sem6.tp.tp33.controller.api;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.service.logic.UserService;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService usersService;

    @Autowired
    public UserController(UserService userService) {
        this.usersService = userService;
    }

    @GetMapping()
    PageDto<UserDto> findAll(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer totalPages
    ) {
        return usersService.findAll(pageNumber, totalPages);
    }
    @GetMapping(value = "/{user_id}")
    UserDto findById(@PathVariable(name = "user_id") String userId) {
        try {
            UUID uuid = UUID.fromString(userId);
            return usersService.findById(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    UserDto create(@RequestBody UserDto userDto) {
        return usersService.create(userDto);
    }


    @PutMapping
    UserDto update(@RequestBody UserDto userDto) {
        return usersService.update(userDto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            usersService.deleteById(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }
}
