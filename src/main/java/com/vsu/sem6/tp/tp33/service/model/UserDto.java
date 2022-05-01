package com.vsu.sem6.tp.tp33.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthday;
    private String email;
    private String phoneNumber;
    private List<RecipeDto> favouriteRecipes;
    private List<ProductDto> favouriteProducts;
    private List<ProductCartDto> productCarts;
    private List<OrderDto> orders;
}
