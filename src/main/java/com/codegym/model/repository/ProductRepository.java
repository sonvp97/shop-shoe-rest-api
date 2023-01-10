package com.codegym.model.repository;

import com.codegym.model.dto.CategoryDto;
import com.codegym.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Iterable<Product> findAllByCategory(CategoryDto categoryDto);
    Page<Product> findAllByNameContaining(String name, Pageable pageable);
}
