package org.example.ingtest.mapper;

import org.example.ingtest.dto.ProductDto;
import org.example.ingtest.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDto productDto) {
        var product = new Product();

        product.setDescription(productDto.description());
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setQuantity(productDto.quantity());

        return product;
    }

    public ProductDto toDto(Product product) {
        return new ProductDto(product.getUuid(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
    }
}
