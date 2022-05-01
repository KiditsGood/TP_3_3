package com.vsu.sem6.tp.tp33.service.mapper;

import com.vsu.sem6.tp.tp33.persistence.entity.RecipePhoto;
import com.vsu.sem6.tp.tp33.service.model.RecipePhotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RecipePhotoMapper {
    @Mapping( target = "photo",ignore = true)
    RecipePhotoDto fromEntity(RecipePhoto photo);
    @Mapping( target = "photo",ignore = true)
    RecipePhoto toEntity(RecipePhotoDto photoDto);

}
