package com.epf.dto;

import com.epf.model.Plante;
import jakarta.validation.constraints.*;

public record PlanteDTO(
        Long id_plante,
        String name,          // No validation
        String species,       // No validation
        Integer pointDeVie,   // Nullable
        Double attaqueParSeconde,
        Integer degatAttaque,
        Integer cout,
        Double soleilParSeconde,
        String effet,        // Use String instead of Enum
        String cheminImage
)  {
        // Add default values for nullable fields
        public PlanteDTO {
                if (name == null) name = "Default Plant";
                if (species == null) species = "Default Species";
                if (pointDeVie == null) pointDeVie = 100;
                if (attaqueParSeconde == null) attaqueParSeconde = 1.0;
                if (degatAttaque == null) degatAttaque = 10;
                if (cout == null) cout = 50;
                if (soleilParSeconde == null) soleilParSeconde = 0.0;
                if (cheminImage == null) cheminImage = "/default.png";
        }
}