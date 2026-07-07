package org.example.ingtest.dto;

import java.math.BigDecimal;

public record ProductPatchDto(String name, String description, BigDecimal price, Integer quantity) {

}
