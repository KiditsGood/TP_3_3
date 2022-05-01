package com.vsu.sem6.tp.tp33.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "recipe_photo")
public class RecipePhoto {
    @Id
    @GeneratedValue()
    @Column(name = "recipe_photo_id")
    private UUID id;

    private String photo;

    @Column(name = "recipe_id")
    private UUID recipeId;
}
