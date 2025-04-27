package com.epf.dto;

import com.epf.model.Plante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record PlanteDTO(
        Long id_plante,
        String nom,
        @JsonProperty("point_de_vie")
        Integer pointDeVie,
        @JsonProperty("attaque_par_seconde")
        Double attaqueParSeconde,
        @JsonProperty("degat_attaque")
        Integer degatAttaque,
        @JsonProperty("cout")
        Integer cout,
        @JsonProperty("soleil_par_seconde")
        Double soleilParSeconde,
        @JsonProperty("effet")
        String effet,
        @JsonProperty("chemin_image")
        String cheminImage
)  {

        public PlanteDTO {
                if (nom == null) nom = "Default Plant";
                if (pointDeVie == null) pointDeVie = 100;
                if (attaqueParSeconde == null) attaqueParSeconde = 1.0;
                if (degatAttaque == null) degatAttaque = 10;
                if (cout == null) cout = 50;
                if (soleilParSeconde == null) soleilParSeconde = 0.0;
                if (cheminImage == null) cheminImage = "/default.png";
        }
}