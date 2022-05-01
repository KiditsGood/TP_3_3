package com.vsu.sem6.tp.tp33.service.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class ProductOrderIdDto {
    private UUID orderId;

    private UUID productId;
}
