package com.vsu.sem6.tp.tp33.controller.api;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.entity.ProductPhoto;
import com.vsu.sem6.tp.tp33.service.logic.ProductPhotoService;
import com.vsu.sem6.tp.tp33.service.model.ProductPhotoDto;
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
@RequestMapping("/product_photos")
@CrossOrigin

public class ProductPhotoController {
    private final ProductPhotoService productPhotoService;

    @Autowired
    public ProductPhotoController(ProductPhotoService productPhotoService) {
        this.productPhotoService = productPhotoService;
    }

    @GetMapping(value = "/{id}",produces = "image/jpeg")
    ResponseEntity<Resource> findById(@PathVariable(name = "id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            ProductPhoto photo=productPhotoService.findById(uuid);
            Path path= Paths.get(photo.getPhoto());
            Resource file = new UrlResource(path.toUri());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("File-name",photo.getId().toString());
            responseHeaders.set("Access-Control-Expose-Headers","File-name");

            return new ResponseEntity(file,responseHeaders,HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/products/{product_id}")
    List<UUID> findByProductId(
            @PathVariable(name = "product_id") String productId
    ) {
        try {
            UUID uuid = UUID.fromString(productId);
            return productPhotoService.countByProductId( uuid);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    ProductPhotoDto create(@ModelAttribute ProductPhotoDto photoDto) {
        return productPhotoService.create(photoDto);
    }


    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            productPhotoService.deleteById(uuid);
        } catch (IllegalArgumentException | IOException e) {
            throw new ApiRequestException("Wrong id");
        }
    }
}
