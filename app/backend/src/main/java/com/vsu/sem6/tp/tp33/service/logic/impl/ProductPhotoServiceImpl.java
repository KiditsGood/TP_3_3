package com.vsu.sem6.tp.tp33.service.logic.impl;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.entity.Product;
import com.vsu.sem6.tp.tp33.persistence.entity.ProductPhoto;
import com.vsu.sem6.tp.tp33.persistence.repository.ProductPhotoRepository;
import com.vsu.sem6.tp.tp33.persistence.repository.ProductRepository;
import com.vsu.sem6.tp.tp33.service.logic.ProductPhotoService;
import com.vsu.sem6.tp.tp33.service.mapper.ProductPhotoMapper;
import com.vsu.sem6.tp.tp33.service.model.ProductPhotoDto;
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
public class ProductPhotoServiceImpl implements ProductPhotoService {
    private final ProductPhotoRepository productPhotoRepository;
    private final ProductRepository productRepository;
    private final ProductPhotoMapper productPhotoMapper;

    @Autowired
    public ProductPhotoServiceImpl(ProductPhotoRepository productPhotoRepository, ProductPhotoMapper productPhotoMapper,ProductRepository productRepository) {
        this.productPhotoRepository = productPhotoRepository;
        this.productPhotoMapper = productPhotoMapper;
        this.productRepository=productRepository;
    }

    @Override
    public ProductPhotoDto create(ProductPhotoDto photoDto) {
        String fileName = StringUtils.cleanPath(photoDto.getContent().getOriginalFilename());
        String uploadDir = "product-photos/" + photoDto.getProductId();
        String filePath = Paths.get(uploadDir).resolve(fileName).toString();

            try {
                FileUploadUtil.saveFile(uploadDir, fileName, photoDto.getContent());
                ProductPhoto nphoto=productPhotoMapper.toEntity(photoDto);
                nphoto.setPhoto(filePath);
                ProductPhoto productPhoto= productPhotoRepository.save(nphoto);
                Product product= productRepository.findById(photoDto.getProductId()).get();
                product.setMainPhoto(productPhoto.getId());
                productRepository.save(product);
                return productPhotoMapper.fromEntity(productPhoto);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return photoDto;
    }

    @Override
    public void deleteById(UUID id) throws IOException {

        if (productPhotoRepository.existsById(id)) {
            Path path=Paths.get(productPhotoRepository.findById(id).get().getPhoto());
            productPhotoRepository.deleteById(id);
            Files.delete(path);
        } else {
            throw new ApiRequestException("Wrong id");
        }
    }

    @Override
    public List<UUID> countByProductId(UUID id) {
        List<UUID> uuids=new ArrayList<>();
        List<ProductPhoto> photos = productPhotoRepository.findByProductId(id);
        if (photos!=null){
            photos.forEach(photo->{
                uuids.add(photo.getId());
            });
        }
        return uuids;
    }

    @Override
    public ProductPhoto findById(UUID id) {
        return productPhotoRepository
                .findById(id)
                .orElseThrow(() -> new ApiRequestException("Wrong id"));
    }

}
