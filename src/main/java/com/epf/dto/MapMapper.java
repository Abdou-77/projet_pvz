package com.epf.dto;

import com.epf.model.Map;

public class MapMapper {
    public static MapDTO toDTO(Map map) {
        return new MapDTO(
                map.getLigne(),
                map.getColonne(),
                map.getCheminImage()
        );
    }

    public static Map toEntity(MapDTO mapDTO) {
        Map map = new Map();
        map.setLigne(mapDTO.ligne());
        map.setColonne(mapDTO.colonne());
        map.setCheminImage(mapDTO.cheminImage());
        return map;
    }
}