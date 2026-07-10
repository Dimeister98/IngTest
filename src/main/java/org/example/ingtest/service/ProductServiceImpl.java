package org.example.ingtest.service;

import lombok.extern.slf4j.Slf4j;
import org.example.ingtest.dto.ProductDto;
import org.example.ingtest.dto.ProductPatchDto;
import org.example.ingtest.exception.ResourceNotFoundException;
import org.example.ingtest.mapper.ProductMapper;
import org.example.ingtest.model.Product;
import org.example.ingtest.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public List<ProductDto> findProductsByName(String name) {
        log.debug("Finding products by name {}", name);
        var productList = StringUtils.hasText(name)
                ? productRepository.findProductsByName(name)
                : productRepository.findAll();
        return productList.stream().map(productMapper::toDto).toList();
    }

    @Transactional
    public ProductDto getProductById(UUID uuid) {
        log.debug("Get products by id {}", uuid);
        var product = productRepository.findById(uuid)
                .orElseThrow(() -> {
                    log.info("Failed to find product with id {}", uuid);
                    return new ResourceNotFoundException(Product.class.getName(), uuid);
                });
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        log.debug("Creating new product {}", productDto);
        var product = productRepository.save(productMapper.toEntity(productDto));
        log.debug("Product created {}", product);
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto patchProduct(UUID uuid, ProductPatchDto productPatchDto) {
        var product = productRepository.findById(uuid)
                .orElseThrow(() -> {
                    log.info("Failed to update product with id {}", uuid);
                    return new ResourceNotFoundException(Product.class.getName(), uuid);
                });
        productMapper.applyPatch(product, productPatchDto);
        return productMapper.toDto(productRepository.save(product));
    }
}
