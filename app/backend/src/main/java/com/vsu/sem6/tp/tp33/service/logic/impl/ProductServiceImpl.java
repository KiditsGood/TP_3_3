package com.vsu.sem6.tp.tp33.service.logic.impl;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.repository.ProductRepository;
import com.vsu.sem6.tp.tp33.service.logic.ProductService;
import com.vsu.sem6.tp.tp33.service.mapper.ProductMapper;
import com.vsu.sem6.tp.tp33.service.model.ImmutablePageDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
@Service
@Validated
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public PageDto<ProductDto> findAll(Integer pageNumber, Integer totalPages) {
        Page<ProductDto> productDtos = productRepository
                .findAll(Pageable.ofSize(totalPages).withPage(pageNumber))
                .map(productMapper::fromEntity);

        return ImmutablePageDto.<ProductDto>builder()
                .pageNumber(pageNumber)
                .totalPages(totalPages)
                .items(productDtos.getContent())
                .build();
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        return Optional.ofNullable(productDto)
                .filter(dto -> dto.getId() == null)
                .map(productMapper::toEntity)
                .map(productRepository::save)
                .map(productMapper::fromEntity)
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        return Optional.ofNullable(productDto)
                .filter(dto -> productRepository.existsById(dto.getId()))
                .map(productMapper::toEntity)
                .map(productRepository::save)
                .map(productMapper::fromEntity)
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
    }

    @Override
    public void deleteById(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ApiRequestException("Wrong id");
        }
    }

    @Override
    public ProductDto findById(UUID id) {
        return productRepository
                .findById(id)
                .map(productMapper::fromEntity)
                .orElseThrow(() -> new ApiRequestException("Wrong id"));
    }
}
