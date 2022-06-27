package com.vsu.sem6.tp.tp33.service.logic.impl;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.entity.Product;
import com.vsu.sem6.tp.tp33.persistence.entity.ProductPhoto;
import com.vsu.sem6.tp.tp33.persistence.entity.Recipe;
import com.vsu.sem6.tp.tp33.persistence.entity.RecipePhoto;
import com.vsu.sem6.tp.tp33.persistence.repository.RecipePhotoRepository;
import com.vsu.sem6.tp.tp33.persistence.repository.RecipeRepository;
import com.vsu.sem6.tp.tp33.service.logic.RecipePhotoService;
import com.vsu.sem6.tp.tp33.service.mapper.RecipePhotoMapper;
import com.vsu.sem6.tp.tp33.service.model.RecipePhotoDto;
import com.vsu.sem6.tp.tp33.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@Transactional
public class RecipePhotoServiceImpl implements RecipePhotoService {
    private final RecipePhotoRepository recipePhotoRepository;
    private final RecipeRepository recipeRepository;
    private final RecipePhotoMapper recipePhotoMapper;

    @Autowired
    public RecipePhotoServiceImpl(RecipePhotoRepository recipePhotoRepository, RecipePhotoMapper recipePhotoMapper,RecipeRepository recipeRepository) {
        this.recipePhotoRepository = recipePhotoRepository;
        this.recipePhotoMapper = recipePhotoMapper;
        this.recipeRepository=recipeRepository;
    }


    @Override
    public RecipePhotoDto create(RecipePhotoDto photoDto) {
        String fileName = StringUtils.cleanPath(photoDto.getContent().getOriginalFilename());
        String uploadDir = "recipe-photos/" + photoDto.getRecipeId();
        String filePath = Paths.get(uploadDir).resolve(fileName).toString();

            try {
                FileUploadUtil.saveFile(uploadDir, fileName, photoDto.getContent());
                RecipePhoto nphoto=recipePhotoMapper.toEntity(photoDto);
                nphoto.setPhoto(filePath);
                RecipePhoto recipePhoto= recipePhotoRepository.save(nphoto);
                Recipe recipe= recipeRepository.findById(photoDto.getRecipeId()).get();
                recipe.setMainPhoto(recipePhoto.getId());
                recipeRepository.save(recipe);
                return recipePhotoMapper.fromEntity(recipePhoto);
            } catch (IOException e) {
                e.printStackTrace();

        }
        return photoDto;
    }

    @Override
    public void deleteById(UUID id) throws IOException {
        if (recipePhotoRepository.existsById(id)) {
            Path path=Paths.get(recipePhotoRepository.findById(id).get().getPhoto());
            recipePhotoRepository.deleteById(id);
            Files.delete(path);
        } else {
            throw new ApiRequestException("Wrong id");
        }
    }

    @Override
    public List<UUID> countByRecipeId(UUID id) {
        List<UUID> uuids=new ArrayList<>();
        List<RecipePhoto> photos = recipePhotoRepository.findByRecipeId(id);
        if (photos!=null){
            photos.forEach(photo->{
                uuids.add(photo.getId());
            });
        }
        return uuids;
    }

    @Override
    public RecipePhoto findById(UUID id) {
        return recipePhotoRepository
                .findById(id)
                .orElseThrow(() -> new ApiRequestException("Wrong id"));
    }
}
