// src/main/java/com/oxyl/coursepfback/controller/PlanteController.java
package com.epf.controller;

import com.epf.dto.PlanteDTO;
import com.epf.dto.PlanteMapper;
import com.epf.model.Plante;
import com.epf.service.PlanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plantes")
@Tag(name = "Plante", description = "API de gestion des plantes")
public class PlanteController {

    private final PlanteService planteService;

    public PlanteController(PlanteService planteService) {
        this.planteService = planteService;
    }

    @Operation(summary = "Récupérer toutes les plantes", description = "Retourne la liste de toutes les plantes disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des plantes récupérée avec succès",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PlanteDTO.class))),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping
    public List<PlanteDTO> getAllPlantes() {
        return planteService.getAllPlantes().stream()
                .map(PlanteMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Récupérer une plante par son ID", description = "Retourne une plante spécifique en fonction de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plante trouvée",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PlanteDTO.class))),
        @ApiResponse(responseCode = "404", description = "Plante non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping("/{id}")
    public PlanteDTO getPlanteById(
            @Parameter(description = "ID de la plante à récupérer", required = true)
            @PathVariable Long id) {
        return PlanteMapper.toDTO(planteService.getPlanteById(id));
    }

    @Operation(summary = "Créer une nouvelle plante", description = "Crée une nouvelle plante et retourne les détails de la plante créée")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Plante créée avec succès",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PlanteDTO.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @PostMapping
    public ResponseEntity<?> createPlante(
            @Parameter(description = "Données de la plante à créer", required = true)
            @Valid @RequestBody PlanteDTO planteDTO,
            BindingResult result) {
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
        Plante createdPlante = planteService.createPlante(plante);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlanteMapper.toDTO(createdPlante));
    }

    @Operation(summary = "Mettre à jour une plante", description = "Met à jour les informations d'une plante existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plante mise à jour avec succès",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PlanteDTO.class))),
        @ApiResponse(responseCode = "404", description = "Plante non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlanteDTO> updatePlante(
            @Parameter(description = "ID de la plante à mettre à jour", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouvelles données de la plante", required = true)
            @RequestBody PlanteDTO planteDTO) {
        Plante plante = PlanteMapper.toEntity(planteDTO);
        plante.setIdPlante(id);
        Plante updatedPlante = planteService.updatePlante(plante);
        return ResponseEntity.ok(PlanteMapper.toDTO(updatedPlante));
    }

    @Operation(summary = "Supprimer une plante", description = "Supprime une plante existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Plante supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Plante non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlante(
            @Parameter(description = "ID de la plante à supprimer", required = true)
            @PathVariable Long id) {
        planteService.deletePlante(id);
    }
}