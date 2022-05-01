package com.vsu.sem6.tp.tp33.service.mapper;

import com.vsu.sem6.tp.tp33.persistence.entity.Product;
import com.vsu.sem6.tp.tp33.service.model.ProductDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    ProductDto fromEntity(Product product);

    Product toEntity(ProductDto dto);
}
