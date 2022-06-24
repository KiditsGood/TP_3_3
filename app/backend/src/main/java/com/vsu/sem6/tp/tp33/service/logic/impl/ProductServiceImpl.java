package com.vsu.sem6.tp.tp33.service.logic.impl;

import com.google.common.base.Joiner;
import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.entity.Product;
import com.vsu.sem6.tp.tp33.persistence.repository.ProductRepository;
import com.vsu.sem6.tp.tp33.persistence.specification.SearchOperation;

import com.vsu.sem6.tp.tp33.persistence.specification.product.ProductSpecificationBuilder;
import com.vsu.sem6.tp.tp33.service.logic.ProductService;
import com.vsu.sem6.tp.tp33.service.mapper.CycleAvoidingMappingContext;
import com.vsu.sem6.tp.tp33.service.mapper.ProductMapper;
import com.vsu.sem6.tp.tp33.service.model.ImmutablePageDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public PageDto<ProductDto> findAll(Integer pageNumber, Integer pageSize, String search) {
        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
        String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        //(\w+?)|([А-я]+)|(\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2})|(.{8}-.{4}-.{4}-.{4}-.{12})

        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?|[А-я]+|\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}|.{8}-.{4}-.{4}-.{4}-.{12})(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
            System.out.println(matcher.group(5));

            builder.with(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(4),
                    matcher.group(3),
                    matcher.group(5));
        }

        Specification<Product> spec = builder.build();
        Page<ProductDto> productDtos = productRepository
                .findAll(
                        spec,
                        PageRequest.of(pageNumber,pageSize)
                )
                .map(entity->productMapper.fromEntity(entity));

        return ImmutablePageDto.<ProductDto>builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(productDtos.getTotalElements())
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
