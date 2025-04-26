// src/main/java/com/oxyl/coursepfback/controller/PlanteController.java
package com.epf.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.epf.dto.PlanteDTO;
import com.epf.dto.PlanteMapper;
import com.epf.model.Plante;
import com.epf.service.PlanteService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plantes")
public class PlanteController {

    private final PlanteService planteService;

    public PlanteController(PlanteService planteService) {
        this.planteService = planteService;
    }

    @GetMapping
    public List<PlanteDTO> getAllPlantes() {
        return planteService.getAllPlantes().stream()
                .map(PlanteMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public PlanteDTO getPlanteById(@PathVariable Long id) {
        return PlanteMapper.toDTO(planteService.getPlanteById(id));
    }

    @PostMapping
    public ResponseEntity<?> createPlante(@Valid @RequestBody PlanteDTO planteDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .collect(Collectors.toMap(
                                    FieldError::getField,
                                    FieldError::getDefaultMessage
                            ))
            );
        }

        Plante plante = PlanteMapper.toEntity(planteDTO);
        planteService.createPlante(plante);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlanteMapper.toDTO(plante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanteDTO> updatePlante(
            @PathVariable Long id,
            @RequestBody PlanteDTO planteDTO
    ) {
        Plante plante = PlanteMapper.toEntity(planteDTO);
        plante.setIdPlante(id);
        Plante updatedPlante = planteService.updatePlante(plante);
        return ResponseEntity.ok(PlanteMapper.toDTO(updatedPlante));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlante(@PathVariable Long id) {
        planteService.deletePlante(id);
    }
}