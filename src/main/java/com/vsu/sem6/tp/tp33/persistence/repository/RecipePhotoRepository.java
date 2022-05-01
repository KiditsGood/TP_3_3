package com.vsu.sem6.tp.tp33.persistence.repository;

import com.vsu.sem6.tp.tp33.persistence.entity.RecipePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipePhotoRepository extends JpaRepository<RecipePhoto, UUID> {
    List<RecipePhoto> findByRecipeId(UUID id);
    boolean existsByPhoto(String photo);
}
