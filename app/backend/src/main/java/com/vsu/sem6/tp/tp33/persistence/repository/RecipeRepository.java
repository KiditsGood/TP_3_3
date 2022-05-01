package com.vsu.sem6.tp.tp33.persistence.repository;

import com.vsu.sem6.tp.tp33.persistence.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
}
