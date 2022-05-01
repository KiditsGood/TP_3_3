package com.vsu.sem6.tp.tp33.service.mapper;

import com.vsu.sem6.tp.tp33.persistence.entity.ProductPhoto;
import com.vsu.sem6.tp.tp33.service.model.ProductPhotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductPhotoMapper {
    @Mapping( target = "photo",ignore = true)
    ProductPhotoDto fromEntity(ProductPhoto photo);
    @Mapping( target = "photo",ignore = true)
    ProductPhoto toEntity(ProductPhotoDto photoDto);

}
