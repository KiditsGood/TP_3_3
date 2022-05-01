package com.vsu.sem6.tp.tp33.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductCartIdDto {
    private UUID userId;
    private UUID productId;
}
