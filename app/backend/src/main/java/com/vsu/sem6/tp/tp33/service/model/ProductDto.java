package com.vsu.sem6.tp.tp33.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.UUID;
@Getter
@Setter
@ToString
public class ProductDto {
    private UUID id;
    private String name;
    private double price;
    private String description;
    private String category;
    private String status;
    private UUID mainPhoto;
//    private List<ProductPhotoDto> photos;
}
