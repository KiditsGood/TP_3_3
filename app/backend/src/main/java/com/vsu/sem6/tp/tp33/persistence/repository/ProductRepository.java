package com.vsu.sem6.tp.tp33.persistence.repository;

import com.vsu.sem6.tp.tp33.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}