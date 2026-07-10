package org.example.ingtest.service;

import org.example.ingtest.dto.ProductDto;
import org.example.ingtest.dto.ProductPatchDto;
import org.example.ingtest.exception.ResourceNotFoundException;
import org.example.ingtest.mapper.ProductMapper;
import org.example.ingtest.model.Product;
import org.example.ingtest.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private static final String PRODUCT_NAME_1 = "Product1";
    private static final String PRODUCT_NAME_2 = "Product2";
    private static final String DESC_1 = "Desc1";
    private static final String DESC_2 = "Desc2";
    private static final UUID ID_1 = UUID.randomUUID();
    private static final UUID ID_2 = UUID.randomUUID();
    private static final UUID ID_3 = UUID.randomUUID();
    private Product product;
    private Product product2;
    private List<Product> entityList;

    private static Product product(UUID id, String name, String desc, BigDecimal price, int quantity) {
        return new Product(id, name, desc, price, quantity);
    }

    private static ProductDto dto(UUID id, String name, String desc, BigDecimal price, int quantity) {
        return new ProductDto(id, name, desc, price, quantity);
    }

    @BeforeEach
    void add() {
        product = product(ID_1, PRODUCT_NAME_1, DESC_1, BigDecimal.valueOf(10.99), 10);
        product2 = product(ID_2, PRODUCT_NAME_2, DESC_2, BigDecimal.valueOf(5.99), 5);
        entityList = List.of(product, product2);
    }

    @Test
    void findProductsByNameWithNoInputReturnsAllProducts() {
        var dto = dto(ID_1, PRODUCT_NAME_1, DESC_1, BigDecimal.valueOf(10.99), 10);
        var dto2 = dto(ID_2, PRODUCT_NAME_2, DESC_2, BigDecimal.valueOf(5.99), 5);
        var dtoList = List.of(dto, dto2);

        when(productRepository.findAll()).thenReturn(entityList);
        when(productMapper.toDto(product)).thenReturn(dto);
        when(productMapper.toDto(product2)).thenReturn(dto2);

        var result = productService.findProductsByName("");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).isEqualTo(dtoList);
    }

    @Test
    void findProductsByNameReturnsCorrectProduct() {
        var dto = dto(ID_1, PRODUCT_NAME_1, DESC_1, BigDecimal.valueOf(10.99), 10);

        when(productRepository.findProductsByNameIgnoreCase(PRODUCT_NAME_1)).thenReturn(Collections.singletonList(product));
        when(productMapper.toDto(product)).thenReturn(dto);

        var result = productService.findProductsByName(PRODUCT_NAME_1);
        assertThat(result).isEqualTo(Collections.singletonList(dto));
    }

    @Test
    void getProductByIdReturnsCorrectProduct() {
        var dto = dto(ID_1, PRODUCT_NAME_1, DESC_1, BigDecimal.valueOf(10.99), 10);

        when(productRepository.findById(ID_1)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(dto);

        assertThat(productService.getProductById(ID_1)).isEqualTo(dto);
    }

    @Test
    void getProductByIdWhichDoesntExistThrowsException() {
        when(productRepository.findById(ID_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(ID_1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(String.valueOf(ID_1));
        verifyNoInteractions(productMapper);
    }

    @Test
    void addProductSavesEntityAndReturnsDto() {
        var input = dto(null, "new", "desc", new BigDecimal(1), 1);
        var entity = product(null, "new", "desc", new BigDecimal(1), 1);
        var saved = product(ID_3, "new", "desc", new BigDecimal(1), 1);
        var dto = dto(ID_3, "new", "desc", new BigDecimal(1), 1);

        when(productMapper.toEntity(input)).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(saved);
        when(productMapper.toDto(saved)).thenReturn(dto);

        assertThat(productService.addProduct(input)).isEqualTo(dto);
        verify(productRepository).save(entity);
    }

    @Test
    void patchProductWhenPresentUpdatesEntity() {
        var product3 = product(ID_3, PRODUCT_NAME_1, DESC_1, BigDecimal.valueOf(10.99), 10);
        var patch = new ProductPatchDto("UpdatedName", "new", new BigDecimal(10), 4);
        var dto3 = dto(ID_3, PRODUCT_NAME_1, DESC_1, BigDecimal.valueOf(10.99), 10);
        when(productRepository.findById(ID_3)).thenReturn(Optional.of(product3));
        when(productRepository.save(product3)).thenReturn(product3);
        when(productMapper.toDto(product3)).thenReturn(dto3);

        var result = productService.patchProduct(ID_3, patch);

        assertThat(result).isEqualTo(dto3);
        verify(productMapper).applyPatch(product3, patch);
        verify(productRepository).save(product3);
    }

    @Test
    void patchProductWhenMissingThrowsException() {
        var patch = new ProductPatchDto("UpdatedName", "new", new BigDecimal(10), 4);
        when(productRepository.findById(ID_3)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.patchProduct(ID_3, patch))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(ID_3.toString());
        verify(productRepository, never()).save(any());
        verifyNoInteractions(productMapper);
    }
}
