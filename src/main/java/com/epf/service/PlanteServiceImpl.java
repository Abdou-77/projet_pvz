package com.epf.service;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Plante;
import com.epf.repository.PlanteRepository;

@Service
public class PlanteServiceImpl implements PlanteService {
    private static final Logger logger = LoggerFactory.getLogger(PlanteServiceImpl.class);
    private final PlanteRepository planteRepository;

    public PlanteServiceImpl(PlanteRepository planteRepository) {
        this.planteRepository = planteRepository;
    }

    @Override
    public List<Plante> getAllPlantes() {
        logger.info("Retrieving all plantes");
        try {
            List<Plante> plantes = planteRepository.findAll();
            logger.info("Successfully retrieved {} plantes", plantes.size());
            return plantes;
        } catch (Exception e) {
            logger.error("Error retrieving all plantes", e);
            throw new RuntimeException("Failed to retrieve plantes", e);
        }
    }

    @Override
    public Plante getPlanteById(Long id) {
        logger.info("Retrieving plante with id: {}", id);
        try {
            return planteRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Plante not found with id: {}", id);
                        return new ResourceNotFoundException("Plante not found with id: " + id);
                    });
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error retrieving plante with id: {}", id, e);
            throw new RuntimeException("Failed to retrieve plante", e);
        }
    }

    @Override
    public Plante createPlante(Plante plante) {
        logger.info("Creating new plante");
        try {
            if (plante == null) {
                logger.warn("Attempted to create null plante");
                throw new IllegalArgumentException("Plante cannot be null");
            }
            planteRepository.create(plante);
            logger.info("Successfully created plante with id: {}", plante.getIdPlante());
            return plante;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error creating plante", e);
            throw new RuntimeException("Failed to create plante", e);
        }
    }

    @Override
    public Plante updatePlante(Plante plante) {
        logger.info("Updating plante with id: {}", plante.getIdPlante());
        try {
            if (plante == null || plante.getIdPlante() == null) {
                logger.warn("Attempted to update null plante or plante with null ID");
                throw new IllegalArgumentException("Plante and its ID cannot be null");
            }
            if (!planteExists(plante.getIdPlante())) {
                logger.warn("Plante not found for update with id: {}", plante.getIdPlante());
                throw new ResourceNotFoundException("Plante not found with id: " + plante.getIdPlante());
            }
            planteRepository.update(plante);
            logger.info("Successfully updated plante with id: {}", plante.getIdPlante());
            return plante;
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating plante with id: {}", plante.getIdPlante(), e);
            throw new RuntimeException("Failed to update plante", e);
        }
    }

    @Override
    public void deletePlante(Long id) {
        logger.info("Deleting plante with id: {}", id);
        try {
            if (id == null) {
                logger.warn("Attempted to delete plante with null ID");
                throw new IllegalArgumentException("Plante ID cannot be null");
            }
            if (!planteExists(id)) {
                logger.warn("Plante not found for deletion with id: {}", id);
                throw new ResourceNotFoundException("Plante not found with id: " + id);
            }
            planteRepository.delete(id);
            logger.info("Successfully deleted plante with id: {}", id);
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            throw e;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Plante not found for deletion with id: {}", id);
            throw new ResourceNotFoundException("Plante not found with id: " + id);
        } catch (Exception e) {
            logger.error("Error deleting plante with id: {}", id, e);
            throw new RuntimeException("Failed to delete plante", e);
        }
    }

    private Plante.Effet convertStringToEffet(String effet) {
        return Plante.Effet.fromString(effet); // Use the new case-insensitive method
    }

    private boolean planteExists(Long id) {
        return planteRepository.findById(id).isPresent();
    }
}