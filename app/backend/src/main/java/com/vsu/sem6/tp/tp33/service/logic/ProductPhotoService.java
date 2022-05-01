package com.vsu.sem6.tp.tp33.service.logic;

import com.vsu.sem6.tp.tp33.persistence.entity.ProductPhoto;
import com.vsu.sem6.tp.tp33.service.model.ProductPhotoDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductPhotoService {
    ProductPhotoDto create(ProductPhotoDto photoDto);

    void deleteById(UUID id) throws IOException;

    List<UUID> countByProductId(UUID id);

    ProductPhoto findById(UUID id);
}
