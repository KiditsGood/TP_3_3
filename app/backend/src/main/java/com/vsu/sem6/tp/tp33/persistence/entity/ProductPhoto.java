package com.vsu.sem6.tp.tp33.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product_photo")
public class ProductPhoto {
  @Id
  @GeneratedValue()
  @Column(name = "product_photo_id")
  private UUID id;

  private String photo;

  @Column(name = "product_id")
  private UUID productId;
}
