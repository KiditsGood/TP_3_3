package com.vsu.sem6.tp.tp33.service.logic;


import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.RecipeDto;

import java.util.UUID;

public interface RecipeService {
    PageDto<RecipeDto> findAll(Integer pageNumber, Integer pageSize, String search,String type,String sortOrder);

    RecipeDto create(RecipeDto recipeDto);

    RecipeDto update(RecipeDto recipeDto);

    void deleteById(UUID id);

    RecipeDto findById(UUID id);
}
