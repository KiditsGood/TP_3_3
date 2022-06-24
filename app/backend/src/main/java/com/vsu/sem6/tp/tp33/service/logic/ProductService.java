package com.vsu.sem6.tp.tp33.service.logic;

import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.ProductDto;

import java.util.UUID;

public interface ProductService {
    PageDto<ProductDto> findAll(Integer pageNumber, Integer pageSize, String search,String type,String sortOrder);

    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    void deleteById(UUID id);

    ProductDto findById(UUID id);
}
