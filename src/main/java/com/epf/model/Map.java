package com.epf.model;

import jakarta.validation.constraints.Positive;

public class Map {
    private Long idMap;

    @Positive(message = "Number of rows must be positive")
    private int ligne;

    @Positive(message = "Number of columns must be positive")
    private int colonne;

    private String cheminImage;

    // Constructors
    public Map() {
    }

    public Map(Long idMap, int ligne, int colonne, String cheminImage) {
        this.idMap = idMap;
        this.ligne = ligne;
        this.colonne = colonne;
        this.cheminImage = cheminImage;
    }

    // Getters & Setters
    public Long getIdMap() {
        return idMap;
    }

    public void setIdMap(Long idMap) {
        this.idMap = idMap;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public String getCheminImage() {
        return cheminImage;
    }

    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }
}