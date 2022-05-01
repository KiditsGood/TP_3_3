package com.vsu.sem6.tp.tp33.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private UUID id;
    private String name;
    private double price;
    private String description;
    private String category;
    private String status;
    private UUID mainPhoto;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private List<ProductPhoto> photos;
}
