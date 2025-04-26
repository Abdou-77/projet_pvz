package com.epf.dto;

import com.epf.model.Zombie;

public class ZombieMapper {
    public static Zombie toEntity(ZombieDTO dto) {
        Zombie zombie = new Zombie();
        zombie.setIdZombie(dto.getIdZombie());
        zombie.setNom(dto.getNom());
        zombie.setPointDeVie(dto.getPointDeVie());
        zombie.setAttaqueParSeconde(dto.getAttaqueParSeconde());
        zombie.setDegatAttaque(dto.getDegatAttaque());
        zombie.setVitesseDeDeplacement(dto.getVitesseDeDeplacement());
        zombie.setCheminImage(dto.getCheminImage());
        zombie.setIdMap(dto.getIdMap()); // Map idMap from DTO to entity
        return zombie;
    }

    public static ZombieDTO toDTO(Zombie zombie) {
        ZombieDTO dto = new ZombieDTO();
        dto.setIdZombie(zombie.getIdZombie());
        dto.setNom(zombie.getNom());
        dto.setPointDeVie(zombie.getPointDeVie());
        dto.setAttaqueParSeconde(zombie.getAttaqueParSeconde());
        dto.setDegatAttaque(zombie.getDegatAttaque());
        dto.setVitesseDeDeplacement(zombie.getVitesseDeDeplacement());
        dto.setCheminImage(zombie.getCheminImage());
        dto.setIdMap(zombie.getIdMap());
        return dto;
    }
}