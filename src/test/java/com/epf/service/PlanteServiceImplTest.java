package com.epf.service;

import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Plante;
import com.epf.repository.PlanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanteServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(PlanteServiceImplTest.class);

    @Mock
    private PlanteRepository planteRepository;

    @InjectMocks
    private PlanteServiceImpl planteService;

    private Plante testPlante;

    @BeforeEach
    void setUp() {
        logger.info("Setting up test environment");
        testPlante = new Plante(1L, "Test Plant", 100, 2.0, 50, 25, 1.5, Plante.Effet.NORMAL, "/test.png");
    }

    @Test
    void getAllPlantes_ShouldReturnAllPlantes() {
        logger.info("Testing getAllPlantes method");
        List<Plante> expectedPlantes = Arrays.asList(testPlante);
        when(planteRepository.findAll()).thenReturn(expectedPlantes);

        List<Plante> result = planteService.getAllPlantes();

        assertEquals(expectedPlantes, result);
        verify(planteRepository).findAll();
        logger.info("getAllPlantes test completed successfully");
    }

    @Test
    void getPlanteById_WhenPlanteExists_ShouldReturnPlante() {
        logger.info("Testing getPlanteById with existing plante");
        when(planteRepository.findById(1L)).thenReturn(Optional.of(testPlante));

        Plante result = planteService.getPlanteById(1L);

        assertEquals(testPlante, result);
        verify(planteRepository).findById(1L);
        logger.info("getPlanteById test with existing plante completed successfully");
    }

    @Test
    void getPlanteById_WhenPlanteDoesNotExist_ShouldThrowException() {
        logger.info("Testing getPlanteById with non-existing plante");
        when(planteRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> planteService.getPlanteById(1L));
        verify(planteRepository).findById(1L);
        logger.info("getPlanteById test with non-existing plante completed successfully");
    }

    @Test
    void createPlante_ShouldCreateAndReturnPlante() {
        logger.info("Testing createPlante method");
        doNothing().when(planteRepository).create(any(Plante.class));

        Plante result = planteService.createPlante(testPlante);

        assertEquals(testPlante, result);
        verify(planteRepository).create(testPlante);
        logger.info("createPlante test completed successfully");
    }

    @Test
    void updatePlante_WhenPlanteExists_ShouldUpdateAndReturnPlante() {
        logger.info("Testing updatePlante with existing plante");
        when(planteRepository.findById(1L)).thenReturn(Optional.of(testPlante));

        Plante result = planteService.updatePlante(testPlante);

        assertEquals(testPlante, result);
        verify(planteRepository).update(testPlante);
        logger.info("updatePlante test with existing plante completed successfully");
    }

    @Test
    void updatePlante_WhenPlanteDoesNotExist_ShouldThrowException() {
        logger.info("Testing updatePlante with non-existing plante");
        when(planteRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> planteService.updatePlante(testPlante));
        verify(planteRepository, never()).update(any(Plante.class));
        logger.info("updatePlante test with non-existing plante completed successfully");
    }

    @Test
    void deletePlante_WhenPlanteExists_ShouldDeletePlante() {
        logger.info("Testing deletePlante with existing plante");
        doNothing().when(planteRepository).delete(1L);

        assertDoesNotThrow(() -> planteService.deletePlante(1L));
        verify(planteRepository).delete(1L);
        logger.info("deletePlante test with existing plante completed successfully");
    }

    @Test
    void deletePlante_WhenPlanteDoesNotExist_ShouldThrowException() {
        logger.info("Testing deletePlante with non-existing plante");
        doThrow(new ResourceNotFoundException("Plante not found")).when(planteRepository).delete(1L);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> planteService.deletePlante(1L));
        verify(planteRepository).delete(1L);
        logger.info("deletePlante test with non-existing plante completed successfully");
    }
}
