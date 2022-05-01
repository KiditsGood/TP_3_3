package com.vsu.sem6.tp.tp33.service.logic.impl;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.repository.RecipeRepository;
import com.vsu.sem6.tp.tp33.service.logic.RecipeService;
import com.vsu.sem6.tp.tp33.service.mapper.CycleAvoidingMappingContext;
import com.vsu.sem6.tp.tp33.service.mapper.RecipeMapper;
import com.vsu.sem6.tp.tp33.service.model.ImmutablePageDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.RecipeDto;
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
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }


    @Override
    public PageDto<RecipeDto> findAll(Integer pageNumber, Integer totalPages) {
        Page<RecipeDto> recipeDtos = recipeRepository
                .findAll(Pageable.ofSize(totalPages).withPage(pageNumber))
                .map(entity->recipeMapper.fromEntity(entity,new CycleAvoidingMappingContext()));

        return ImmutablePageDto.<RecipeDto>builder()
                .pageNumber(pageNumber)
                .totalPages(totalPages)
                .items(recipeDtos.getContent())
                .build();
    }

    @Override
    public RecipeDto create(RecipeDto recipeDto) {
        return Optional.ofNullable(recipeDto)
                .filter(dto -> dto.getId() == null)
                .map(recipeMapper::toEntity)
                .map(recipeRepository::save)
                .map(entity->recipeMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
    }

    @Override
    public RecipeDto update(RecipeDto recipeDto) {
        return Optional.ofNullable(recipeDto)
                .filter(dto -> recipeRepository.existsById(dto.getId()))
                .map(recipeMapper::toEntity)
                .map(recipeRepository::save)
                .map(entity->recipeMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
    }

    @Override
    public void deleteById(UUID id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
        } else {
            throw new ApiRequestException("Wrong id");
        }
    }

    @Override
    public RecipeDto findById(UUID id) {
        return recipeRepository
                .findById(id)
                .map(entity->recipeMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong id"));
    }
}
