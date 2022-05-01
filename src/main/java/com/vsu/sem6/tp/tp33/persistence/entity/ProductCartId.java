package com.vsu.sem6.tp.tp33.persistence.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProductCartId implements Serializable {
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "product_id")
    private UUID productId;
}
