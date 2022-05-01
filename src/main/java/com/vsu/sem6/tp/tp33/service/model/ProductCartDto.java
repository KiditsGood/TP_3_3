package com.vsu.sem6.tp.tp33.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductCartDto {
    private ProductCartIdDto id;
    private int amount;
}
