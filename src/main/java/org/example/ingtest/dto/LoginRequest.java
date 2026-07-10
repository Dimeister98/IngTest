package org.example.ingtest.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String username,@NotBlank String password) {
}
