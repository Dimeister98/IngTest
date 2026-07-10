package org.example.ingtest.controller;

import jakarta.validation.Valid;
import org.example.ingtest.dto.ProductDto;
import org.example.ingtest.dto.ProductPatchDto;
import org.example.ingtest.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProductsByName(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(productService.findProductsByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> patchProduct(@PathVariable UUID id, @Valid @RequestBody ProductPatchDto dto) {
        return ResponseEntity.ok(productService.patchProduct(id, dto));
    }
}
