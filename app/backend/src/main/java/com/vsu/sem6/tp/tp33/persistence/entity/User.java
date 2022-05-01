package com.vsu.sem6.tp.tp33.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Table(name = "users")
@Entity

public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthday;
    private String email;
    private String phoneNumber;
    @ManyToMany
    private List<Recipe> favouriteRecipes;
    @ManyToMany
    private List<Product> favouriteProducts;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",orphanRemoval=true)
    private List<ProductCart> productCarts;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
