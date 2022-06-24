package com.vsu.sem6.tp.tp33.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private UUID id;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="recipe_id")
    private List<RecipePhoto> photos;
//    @ManyToOne
//    private User creator;
    private UUID creator;
    private String status;
    private UUID mainPhoto;
    @ManyToMany()
    private List<Product> products;
}
