package com.epf.dto;

import jakarta.validation.constraints.Positive;

public record MapDTO(
        @Positive(message = "Rows must be positive")
        int ligne,

        @Positive(message = "Columns must be positive")
        int colonne,

        String cheminImage
) {}