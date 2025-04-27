package com.epf.service;

import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Plante;
import com.epf.repository.PlanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanteServiceImplTest {

    @Mock
    private PlanteRepository planteRepository;

    @InjectMocks
    private PlanteServiceImpl planteService;

    private Plante samplePlante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        samplePlante = new Plante();
        samplePlante.setIdPlante(1L);
        samplePlante.setnom("Tournesol");
        samplePlante.setPointDeVie(100);
        samplePlante.setAttaqueParSeconde(0.0);
        samplePlante.setDegatAttaque(0);
        samplePlante.setCout(50);
        samplePlante.setSoleilParSeconde(1.0);
        samplePlante.setEffet(Plante.Effet.NORMAL);
        samplePlante.setCheminImage("/images/sunflower.png");
    }

    @Test
    void getAllPlantes() {
        List<Plante> plantes = Arrays.asList(samplePlante);
        when(planteRepository.findAll()).thenReturn(plantes);

        List<Plante> result = planteService.getAllPlantes();

        assertEquals(1, result.size());
        assertEquals("Tournesol", result.get(0).getnom());
        verify(planteRepository, times(1)).findAll();
    }

    @Test
    void getPlanteById() {
        when(planteRepository.findById(1L)).thenReturn(Optional.of(samplePlante));

        Plante result = planteService.getPlanteById(1L);

        assertEquals("Tournesol", result.getnom());
        verify(planteRepository, times(1)).findById(1L);
    }

    @Test
    void getPlanteById_NotFound() {
        when(planteRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            planteService.getPlanteById(999L);
        });
        verify(planteRepository, times(1)).findById(999L);
    }

    @Test
    void createPlante() {
        doNothing().when(planteRepository).create(samplePlante);

        planteService.createPlante(samplePlante);

        verify(planteRepository, times(1)).create(samplePlante);
    }

    @Test
    void updatePlante() {
        when(planteRepository.findById(1L)).thenReturn(Optional.of(samplePlante));
        doNothing().when(planteRepository).update(samplePlante);

        planteService.updatePlante(samplePlante);

        verify(planteRepository, times(1)).update(samplePlante);
    }

    @Test
    void updatePlante_NotFound() {
        samplePlante.setIdPlante(999L);
        when(planteRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            planteService.updatePlante(samplePlante);
        });
        verify(planteRepository, times(1)).findById(999L);
        verify(planteRepository, never()).update(any(Plante.class));
    }

    @Test
    void deletePlante() {
        doNothing().when(planteRepository).delete(1L);

        planteService.deletePlante(1L);

        verify(planteRepository, times(1)).delete(1L);
    }

    @Test
    void deletePlante_NotFound() {
        doThrow(new EmptyResultDataAccessException(1)).when(planteRepository).delete(999L);

        assertThrows(ResourceNotFoundException.class, () -> {
            planteService.deletePlante(999L);
        });
        verify(planteRepository, times(1)).delete(999L);
    }
}
