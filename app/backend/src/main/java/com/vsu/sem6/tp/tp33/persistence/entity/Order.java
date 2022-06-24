package com.vsu.sem6.tp.tp33.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private UUID id;
    private LocalDate date;
    private String status;
    private String payment;
    private String comment;
    private String address;
    private int totalCost;
    @ManyToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order",orphanRemoval=true)
    private List<ProductOrder> productOrders;


}
