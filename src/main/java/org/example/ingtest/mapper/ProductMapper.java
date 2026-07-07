package org.example.ingtest.mapper;

import org.example.ingtest.dto.ProductDto;
import org.example.ingtest.dto.ProductPatchDto;
import org.example.ingtest.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "uuid", ignore = true)
    Product toEntity(ProductDto productDto);

    @Mapping(source = "uuid", target = "id")
    ProductDto toDto(Product product);

    @Mapping(target = "uuid", ignore = true)
    void applyPatch(@MappingTarget Product product, ProductPatchDto productPatchDto);
}
