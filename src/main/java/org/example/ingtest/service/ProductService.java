package org.example.ingtest.service;

import jakarta.transaction.Transactional;
import org.example.ingtest.model.Product;
import org.example.ingtest.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public List<Product> findProductsByName(String name) {
        return name.isBlank() ?
                productRepository.findAll() : productRepository.findProductsByName(name);
    }
}
