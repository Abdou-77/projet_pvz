
package com.epf.service;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Plante;
import com.epf.repository.PlanteRepository;

@Service
public class PlanteServiceImpl implements PlanteService {

    private final PlanteRepository planteRepository;

    public PlanteServiceImpl(PlanteRepository planteRepository) {
        this.planteRepository = planteRepository;
    }

    @Override
    public List<Plante> getAllPlantes() {
        return planteRepository.findAll();
    }

    @Override
    public Plante getPlanteById(Long id) {
        return planteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plante not found with id: " + id));
    }

    @Override
    public Plante createPlante(Plante plante) {
        planteRepository.create(plante);
        return plante; // Return the created plante for controller response
    }

    @Override
    public Plante updatePlante(Plante plante) {
        if(plante.getIdPlante() == null || !planteExists(plante.getIdPlante())) {
            throw new ResourceNotFoundException("Plante not found for update");
        }
        planteRepository.update(plante);
        return plante;
    }

    @Override
    public void deletePlante(Long id) {
        try {
            planteRepository.delete(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Plante not found with id: " + id);
        }
    }
    private Plante.Effet convertStringToEffet(String effet) {
        return Plante.Effet.fromString(effet); // Use the new case-insensitive method
    }
    private boolean planteExists(Long id) {
        return planteRepository.findById(id).isPresent();
    }
}