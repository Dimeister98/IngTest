package org.example.ingtest.service;

import org.example.ingtest.dto.ProductDto;
import org.example.ingtest.dto.ProductPatchDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductDto> findProductsByName(String name);

    ProductDto getProductById(UUID uuid);

    ProductDto addProduct(ProductDto productDto);

    ProductDto patchProduct(UUID uuid, ProductPatchDto productPatchDto);
}
