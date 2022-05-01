package com.vsu.sem6.tp.tp33.service.logic;


import com.vsu.sem6.tp.tp33.persistence.entity.RecipePhoto;
import com.vsu.sem6.tp.tp33.service.model.RecipePhotoDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface RecipePhotoService {
    RecipePhotoDto create(RecipePhotoDto photoDto);

    void deleteById(UUID id) throws IOException;

    List<UUID> countByRecipeId(UUID id);

    RecipePhoto findById(UUID id);
}
