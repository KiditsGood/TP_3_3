package com.vsu.sem6.tp.tp33.service.mapper;

import com.vsu.sem6.tp.tp33.persistence.entity.Recipe;
import com.vsu.sem6.tp.tp33.service.model.RecipeDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(uses = {ProductMapper.class})
public interface RecipeMapper {
    RecipeDto fromEntity(Recipe recipe,@Context CycleAvoidingMappingContext context);

    Recipe toEntity(RecipeDto dto);
}
