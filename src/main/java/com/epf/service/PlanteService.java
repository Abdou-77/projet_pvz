package com.epf.service;

import com.epf.model.Plante;

import java.util.List;
public interface PlanteService {
    List<Plante> getAllPlantes();
    Plante getPlanteById(Long id);
    Plante createPlante(Plante plante);
    Plante updatePlante(Plante plante);
    void deletePlante(Long id);
}