package com.vsu.sem6.tp.tp33.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "product_order")
public class ProductOrder {
    @EmbeddedId
    @GeneratedValue
    @Column(name = "product_order_id")
    private ProductOrderId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;
    private double price;
    private int amount;
}
