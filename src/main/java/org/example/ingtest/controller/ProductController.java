package org.example.ingtest.controller;

import org.example.ingtest.dto.ProductDto;
import org.example.ingtest.mapper.ProductMapper;
import org.example.ingtest.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getProductsByName(@RequestParam(required = false) String name) {
        return productService.findProductsByName(name).stream()
                .map(ProductMapper::toDto)
                .toList();
    }
}
