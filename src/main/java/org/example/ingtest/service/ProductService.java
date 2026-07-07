package org.example.ingtest.service;


import org.example.ingtest.dto.ProductDto;
import org.example.ingtest.mapper.ProductMapper;
import org.example.ingtest.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public List<ProductDto> findProductsByName(String name) {
        var productList = name.isBlank() ?
                productRepository.findAll() : productRepository.findProductsByName(name);
        return productList.stream().map(ProductMapper::toDto).toList();
    }

    @Transactional
    public ProductDto getProductById(UUID uuid) {
        var product = productRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.toDto(product);
    }

    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        var product = productRepository.save(ProductMapper.toEntity(productDto));
        return ProductMapper.toDto(product);
    }
}
