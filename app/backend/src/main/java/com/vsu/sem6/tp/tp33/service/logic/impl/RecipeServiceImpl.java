package com.vsu.sem6.tp.tp33.service.logic.impl;

import com.google.common.base.Joiner;
import com.vsu.sem6.tp.tp33.config.SecurityService;
import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.entity.Recipe;
import com.vsu.sem6.tp.tp33.persistence.repository.RecipeRepository;
import com.vsu.sem6.tp.tp33.persistence.specification.SearchOperation;
import com.vsu.sem6.tp.tp33.persistence.specification.recipe.RecipeSpecificationBuilder;
import com.vsu.sem6.tp.tp33.service.logic.RecipeService;
import com.vsu.sem6.tp.tp33.service.mapper.CycleAvoidingMappingContext;
import com.vsu.sem6.tp.tp33.service.mapper.RecipeMapper;
import com.vsu.sem6.tp.tp33.service.model.ImmutablePageDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.RecipeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final SecurityService securityService;
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper,SecurityService securityService) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.securityService=securityService;
    }


    @Override
    public PageDto<RecipeDto> findAll(Integer pageNumber, Integer pageSize, String search) {
        RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder();
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

        Specification<Recipe> spec = builder.build();
        Page<RecipeDto> productDtos = recipeRepository
                .findAll(
                        spec,
                        PageRequest.of(pageNumber,pageSize)
                )
                .map(entity->recipeMapper.fromEntity(entity,new CycleAvoidingMappingContext()));

        return ImmutablePageDto.<RecipeDto>builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(productDtos.getTotalElements())
                .items(productDtos.getContent())
                .build();
    }

    @Override
    public RecipeDto create(RecipeDto recipeDto) {
        recipeDto=Optional.ofNullable(recipeDto)
                .filter(dto -> dto.getId() == null)
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
        recipeDto.setCreator(securityService.getUserId());
        Recipe recipe=recipeRepository.save( recipeMapper.toEntity(recipeDto));
        return recipeMapper.fromEntity(recipe,new CycleAvoidingMappingContext());

    }

    @Override
    public RecipeDto update(RecipeDto recipeDto) {


        return Optional.ofNullable(recipeDto)
                .filter(dto -> recipeRepository.existsById(dto.getId()))
                .filter(r->securityService.checkAccess(r.getCreator()))
                .map(recipeMapper::toEntity)
                .map(recipeRepository::save)
                .map(entity->recipeMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("access denied"));
    }

    @Override
    public void deleteById(UUID id) {

        if (recipeRepository.existsById(id)) {
            if (!securityService.checkAccess(recipeRepository.getById(id).getCreator())){
                throw new ApiRequestException("access denied");
            }
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
