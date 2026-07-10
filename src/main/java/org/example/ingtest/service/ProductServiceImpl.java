package org.example.ingtest.service;

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
        var productList = StringUtils.hasText(name)
                ? productRepository.findProductsByName(name)
                : productRepository.findAll();
        return productList.stream().map(productMapper::toDto).toList();
    }

    @Transactional
    public ProductDto getProductById(UUID uuid) {
        var product = productRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(Product.class.getName(), uuid));
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        var product = productRepository.save(productMapper.toEntity(productDto));
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto patchProduct(UUID uuid, ProductPatchDto productPatchDto) {
        var product = productRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(Product.class.getName(), uuid));
        productMapper.applyPatch(product, productPatchDto);
        return productMapper.toDto(productRepository.save(product));
    }
}
