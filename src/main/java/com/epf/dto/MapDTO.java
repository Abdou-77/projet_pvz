package com.epf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;

public record MapDTO(
        @JsonProperty("id_map")
        Long idMap,
        @Positive(message = "Rows must be positive")
        int ligne,
        @Positive(message = "Columns must be positive")
        int colonne,
        @JsonProperty("chemin_image")
        String cheminImage
) {}