package com.epf.model;

public class Zombie {
    private Long idZombie;
    private String nom;
    private int pointDeVie;
    private double attaqueParSeconde;
    private int degatAttaque;
    private double vitesseDeDeplacement;
    private String cheminImage;
    private Long idMap; // No default value

    public Zombie() {} // Empty constructor

    public Zombie(Long idZombie, String nom, Integer pointDeVie, Double attaqueParSeconde,
                  Integer degatAttaque, Double vitesseDeDeplacement, String cheminImage, Long idMap) {
        this.idZombie = idZombie;
        this.nom = nom != null && !nom.isBlank() ? nom : "Default Zombie";
        this.pointDeVie = pointDeVie != null ? pointDeVie : 100;
        this.attaqueParSeconde = attaqueParSeconde != null ? attaqueParSeconde : 1.0;
        this.degatAttaque = degatAttaque != null ? degatAttaque : 15;
        this.vitesseDeDeplacement = vitesseDeDeplacement != null ? vitesseDeDeplacement : 0.5;
        this.cheminImage = cheminImage != null ? cheminImage : "/default_zombie.png";
        this.idMap = idMap; // No default value
    }

    // Getters and Setters
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