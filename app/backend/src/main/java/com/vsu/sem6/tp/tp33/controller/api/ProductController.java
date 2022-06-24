package com.vsu.sem6.tp.tp33.controller.api;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.service.logic.ProductService;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@CrossOrigin

public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    PageDto<ProductDto> findAll(@RequestParam(defaultValue = "0", name = "page_number")
                                        Integer pageNumber,
                                @RequestParam(defaultValue = "10", name = "page_size")
                                        Integer pageSize,
                                @RequestParam(defaultValue = " ",name = "search") String search
    ) {
        return productService.findAll(pageNumber, pageSize,search);
    }
    @GetMapping(value = "/{product_id}")
    ProductDto findById(@PathVariable(name = "product_id") String productId) {
        try {
            UUID uuid = UUID.fromString(productId);
            return productService.findById(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ProductDto create(@RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }


    @PutMapping
    ProductDto update(@RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            productService.deleteById(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }
}
