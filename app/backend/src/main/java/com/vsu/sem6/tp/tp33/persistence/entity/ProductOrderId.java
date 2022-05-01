package com.vsu.sem6.tp.tp33.persistence.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProductOrderId implements Serializable {

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "product_id")
    private UUID productId;
}
