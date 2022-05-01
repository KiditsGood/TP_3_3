package com.vsu.sem6.tp.tp33.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class RecipeDto {
    private UUID id;
    private String name;
    private String description;
    //private List<RecipePhotoDto> photos;
    private UserDto creator;
    private String status;
    private List<ProductDto> products;
}
