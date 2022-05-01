package com.vsu.sem6.tp.tp33.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "product_cart")
public class ProductCart {
    @EmbeddedId
    @GeneratedValue
    @Column(name = "product_cart_id")
    private ProductCartId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
    private int amount;
}
