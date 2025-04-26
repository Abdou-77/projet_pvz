package com.epf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class ZombieDTO {
    private Long idZombie;

    @NotBlank(message = "Name is required")
    private String nom;

    @Positive(message = "Health must be positive")
    private int pointDeVie;

    @PositiveOrZero(message = "Attack speed cannot be negative")
    private double attaqueParSeconde;

    @Positive(message = "Attack damage must be positive")
    private int degatAttaque;

    @PositiveOrZero(message = "Movement speed cannot be negative")
    private double vitesseDeDeplacement;

    private String cheminImage;

    @NotNull(message = "Map ID is required")
    @JsonProperty("idMap")
    private Long idMap;

    // Constructor with default values
    public ZombieDTO() {
        this.nom = "Default Zombie";
        this.pointDeVie = 100;         // Default health
        this.attaqueParSeconde = 1.0;  // Default attack speed
        this.degatAttaque = 15;        // Default damage
        this.vitesseDeDeplacement = 0.5; // Default speed
        this.cheminImage = "/default_zombie.png";
        this.idMap = 1L;               // Default map ID (ensure this exists in DB)
    }

    // Getters and setters remain the same...
    public Long getIdZombie() { return idZombie; }
    public void setIdZombie(Long idZombie) { this.idZombie = idZombie; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getPointDeVie() { return pointDeVie; }
    public void setPointDeVie(int pointDeVie) { this.pointDeVie = pointDeVie; }

    public double getAttaqueParSeconde() { return attaqueParSeconde; }
    public void setAttaqueParSeconde(double attaqueParSeconde) { this.attaqueParSeconde = attaqueParSeconde; }

    public int getDegatAttaque() { return degatAttaque; }
    public void setDegatAttaque(int degatAttaque) { this.degatAttaque = degatAttaque; }

    public double getVitesseDeDeplacement() { return vitesseDeDeplacement; }
    public void setVitesseDeDeplacement(double vitesseDeDeplacement) { this.vitesseDeDeplacement = vitesseDeDeplacement; }

    public String getCheminImage() { return cheminImage; }
    public void setCheminImage(String cheminImage) { this.cheminImage = cheminImage; }

    public Long getIdMap() { return idMap; }
    public void setIdMap(Long idMap) { this.idMap = idMap; }
}