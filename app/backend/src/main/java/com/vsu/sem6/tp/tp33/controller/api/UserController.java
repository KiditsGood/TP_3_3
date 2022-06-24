package com.vsu.sem6.tp.tp33.controller.api;

import com.vsu.sem6.tp.tp33.config.jwt.JwtProvider;
import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.entity.User;
import com.vsu.sem6.tp.tp33.service.logic.UserService;
import com.vsu.sem6.tp.tp33.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService usersService;

    private JwtProvider jwtProvider;

    @Autowired
    public UserController(UserService userService,JwtProvider jwtProvider) {
        this.usersService = userService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping()
    PageDto<UserDto> findAll(@RequestParam(defaultValue = "0", name = "page_number")
                                     Integer pageNumber,
                             @RequestParam(defaultValue = "10", name = "page_size")
                                     Integer pageSize
    ) {
        return usersService.findAll(pageNumber, pageSize);
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
    @GetMapping(value = "/get_user")
    UserDto findById() {
        try {
            return usersService.findUserByToken();
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    UserDto create(@RequestBody RegistrationUserDto userDto) {
        return usersService.create(userDto);
    }


    @PutMapping
    UserDto update(@RequestBody UserDto userDto) {
        return usersService.update(userDto);
    }
    @PutMapping("/upd_pass")
    AuthResponse updatePass(@RequestBody AuthRequest request) {
        User userEntity = usersService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if(userEntity==null){
            throw new ApiRequestException("invalid credentials");
        }
        userEntity.setPassword(request.getNewPassword());
        usersService.updatePassword(userEntity);
        String token = jwtProvider.generateToken(userEntity.getEmail(),userEntity.getId());

        return new AuthResponse(token);
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
    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User userEntity = usersService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getEmail(),userEntity.getId());
        return new AuthResponse(token);
    }
}
