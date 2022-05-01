package com.vsu.sem6.tp.tp33.controller.api;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.service.logic.RecipeService;
import com.vsu.sem6.tp.tp33.service.model.PageDto;

import com.vsu.sem6.tp.tp33.service.model.RecipeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recipes")
@CrossOrigin
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping()
    PageDto<RecipeDto> findAll(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer totalPages
    ) {
        return recipeService.findAll(pageNumber, totalPages);
    }
    @GetMapping(value = "/{recipe_id}")
    RecipeDto findById(@PathVariable(name = "recipe_id") String resipeId) {
        try {
            UUID uuid = UUID.fromString(resipeId);
            return recipeService.findById(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    RecipeDto create(@RequestBody RecipeDto recipeDto) {
        return recipeService.create(recipeDto);
    }


    @PutMapping
    RecipeDto update(@RequestBody RecipeDto recipeDto) {
        return recipeService.update(recipeDto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            recipeService.deleteById(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }
}
