package org.example.ingtest.repository;

import org.example.ingtest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findProductsByNameIgnoreCase(String name);
}
