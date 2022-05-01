package com.vsu.sem6.tp.tp33.controller.api;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.entity.RecipePhoto;
import com.vsu.sem6.tp.tp33.service.logic.RecipePhotoService;
import com.vsu.sem6.tp.tp33.service.model.RecipePhotoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipe_photos")
@CrossOrigin

public class RecipePhotoController {
    private final RecipePhotoService recipePhotoService;

    @Autowired
    public RecipePhotoController(RecipePhotoService recipePhotoService) {
        this.recipePhotoService = recipePhotoService;
    }

    @GetMapping(value = "/{id}",produces = "image/jpeg")
    ResponseEntity<Resource> findById(@PathVariable(name = "id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            RecipePhoto photo=recipePhotoService.findById(uuid);
            Path path= Paths.get(photo.getPhoto());
            Resource file = new UrlResource(path.toUri());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("File-name",photo.getId().toString());
            responseHeaders.set("Access-Control-Expose-Headers","File-name");

            return new ResponseEntity(file,responseHeaders, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/recipe/{recipe_id}")
    List<UUID> findByRecipeId(
            @PathVariable(name = "recipe_id") String resipeId
    ) {
        try {
            UUID uuid = UUID.fromString(resipeId);
            return recipePhotoService.countByRecipeId( uuid);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    RecipePhotoDto create(@ModelAttribute RecipePhotoDto photoDto) {
        return recipePhotoService.create(photoDto);
    }


    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            recipePhotoService.deleteById(uuid);
        } catch (IllegalArgumentException | IOException e) {
            throw new ApiRequestException("Wrong id");
        }
    }
}
