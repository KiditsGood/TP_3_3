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
public class OrderDto {
    private UUID id;
    private LocalDate date;
    private String status;
    private String payment;
    private String comment;
    private String address;
    private UserDto user;
    private List<ProductOrderDto> productOrders;
}
