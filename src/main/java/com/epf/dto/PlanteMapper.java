package com.epf.dto;

import com.epf.model.Plante;

public class PlanteMapper {
    public static PlanteDTO toDTO(Plante plante) {
        return new PlanteDTO(
                plante.getIdPlante(),
                plante.getnom(),
                plante.getPointDeVie(),
                plante.getAttaqueParSeconde(),
                plante.getDegatAttaque(),
                plante.getCout(),
                plante.getSoleilParSeconde(),
                plante.getEffet() != null ? plante.getEffet().name() : null,
                plante.getCheminImage()
        );
    }

    public static Plante toEntity(PlanteDTO dto) {
        Plante plante = new Plante();
        plante.setnom(dto.nom() != null ? dto.nom() : "Default Plant");
        plante.setPointDeVie(dto.pointDeVie() != null ? dto.pointDeVie() : 100);
        plante.setAttaqueParSeconde(dto.attaqueParSeconde() != null ? dto.attaqueParSeconde() : 1.0);
        plante.setDegatAttaque(dto.degatAttaque() != null ? dto.degatAttaque() : 10);
        plante.setCout(dto.cout() != null ? dto.cout() : 50);
        plante.setSoleilParSeconde(dto.soleilParSeconde() != null ? dto.soleilParSeconde() : 0.0);
        plante.setEffet(dto.effet() != null ? Plante.Effet.fromString(dto.effet()) : null);
        plante.setCheminImage(dto.cheminImage() != null ? dto.cheminImage() : "/default.png");
        return plante;
    }
}