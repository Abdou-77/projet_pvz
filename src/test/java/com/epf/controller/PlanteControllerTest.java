package com.epf.controller;

import com.epf.dto.PlanteDTO;
import com.epf.dto.PlanteMapper;
import com.epf.model.Plante;
import com.epf.service.PlanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanteControllerTest {

    @Mock
    private PlanteService planteService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private PlanteController planteController;

    private Plante testPlante;
    private PlanteDTO testPlanteDTO;

    @BeforeEach
    void setUp() {
        testPlante = new Plante(1L, "Test Plant", 100, 2.0, 50, 25, 1.5, Plante.Effet.NORMAL, "/test.png");
        testPlanteDTO = PlanteMapper.toDTO(testPlante);
    }

    @Test
    void getAllPlantes_ShouldReturnListOfPlanteDTO() {
        List<Plante> plantes = Arrays.asList(testPlante);
        when(planteService.getAllPlantes()).thenReturn(plantes);

        List<PlanteDTO> result = planteController.getAllPlantes();

        assertEquals(1, result.size());
        assertEquals(testPlanteDTO, result.get(0));
        verify(planteService).getAllPlantes();
    }

    @Test
    void getPlanteById_ShouldReturnPlanteDTO() {
        when(planteService.getPlanteById(1L)).thenReturn(testPlante);

        PlanteDTO result = planteController.getPlanteById(1L);

        assertEquals(testPlanteDTO, result);
        verify(planteService).getPlanteById(1L);
    }

    @Test
    void createPlante_WhenValid_ShouldReturnCreatedPlanteDTO() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(planteService.createPlante(any(Plante.class))).thenReturn(testPlante);

        ResponseEntity<?> response = planteController.createPlante(testPlanteDTO, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testPlanteDTO, response.getBody());
        verify(planteService).createPlante(any(Plante.class));
    }

    @Test
    void createPlante_WhenInvalid_ShouldReturnBadRequest() {
        when(bindingResult.hasErrors()).thenReturn(true);
        FieldError fieldError = new FieldError("planteDTO", "nom", "Name is required");
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError));

        ResponseEntity<?> response = planteController.createPlante(testPlanteDTO, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        verify(planteService, never()).createPlante(any(Plante.class));
    }

    @Test
    void updatePlante_ShouldReturnUpdatedPlanteDTO() {
        when(planteService.updatePlante(any(Plante.class))).thenReturn(testPlante);

        ResponseEntity<PlanteDTO> response = planteController.updatePlante(1L, testPlanteDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPlanteDTO, response.getBody());
        verify(planteService).updatePlante(any(Plante.class));
    }

    @Test
    void deletePlante_ShouldReturnNoContent() {
        doNothing().when(planteService).deletePlante(1L);

        assertDoesNotThrow(() -> planteController.deletePlante(1L));
        verify(planteService).deletePlante(1L);
    }
} 