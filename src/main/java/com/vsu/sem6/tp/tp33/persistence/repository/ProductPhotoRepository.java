package com.vsu.sem6.tp.tp33.persistence.repository;

import com.vsu.sem6.tp.tp33.persistence.entity.Product;
import com.vsu.sem6.tp.tp33.persistence.entity.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, UUID> {
    List<ProductPhoto> findByProductId(UUID id);
    boolean existsByPhoto(String photo);
}
