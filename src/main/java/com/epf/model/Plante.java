package com.epf.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Plante {
    private Long idPlante;
    private String name;
    private String species;
    private int pointDeVie;
    private double attaqueParSeconde;
    private int degatAttaque;
    private int cout;
    private double soleilParSeconde;
    private Effet effet;
    private String cheminImage;

    public enum Effet {
        @JsonProperty("normal") NORMAL,
        @JsonProperty("slow low") SLOW_LOW,
        @JsonProperty("slow medium") SLOW_MEDIUM,
        @JsonProperty("slow stop") SLOW_STOP;
        public static Effet fromString(String value) {
            return Effet.valueOf(value.toUpperCase().replace(" ", "_"));
        }
    }

    // Constructors
    public Plante() {}

    public Plante(Long idPlante, String name, String species, int pointDeVie,
                  double attaqueParSeconde, int degatAttaque, int cout,
                  double soleilParSeconde, Effet effet, String cheminImage)
    {
        this.idPlante = idPlante;
        this.name = name;
        this.pointDeVie = pointDeVie;
        this.attaqueParSeconde = attaqueParSeconde;
        this.degatAttaque = degatAttaque;
        this.cout = cout;
        this.soleilParSeconde = soleilParSeconde;
        this.effet = effet;
        this.cheminImage = cheminImage;
    }

    // Getters and Setters
    public Long getIdPlante() { return idPlante; }
    public void setIdPlante(Long idPlante) { this.idPlante = idPlante; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public int getPointDeVie() { return pointDeVie; }
    public void setPointDeVie(int pointDeVie) { this.pointDeVie = pointDeVie; }

    public double getAttaqueParSeconde() { return attaqueParSeconde; }
    public void setAttaqueParSeconde(double attaqueParSeconde) { this.attaqueParSeconde = attaqueParSeconde; }

    public int getDegatAttaque() { return degatAttaque; }
    public void setDegatAttaque(int degatAttaque) { this.degatAttaque = degatAttaque; }

    public int getCout() { return cout; }
    public void setCout(int cout) { this.cout = cout; }

    public double getSoleilParSeconde() { return soleilParSeconde; }
    public void setSoleilParSeconde(double soleilParSeconde) { this.soleilParSeconde = soleilParSeconde; }

    public Effet getEffet() { return effet; }
    public void setEffet(Effet effet) { this.effet = effet; }

    public String getCheminImage() { return cheminImage; }
    public void setCheminImage(String cheminImage) { this.cheminImage = cheminImage; }
}