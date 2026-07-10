package org.example.ingtest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductPatchDto(
        @NotBlank String name,
        String description,
        @Positive BigDecimal price,
        @PositiveOrZero Integer quantity) {

}
