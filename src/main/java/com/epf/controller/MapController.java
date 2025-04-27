package com.epf.controller;

import com.epf.dto.MapDTO;
import com.epf.dto.MapMapper;
import com.epf.model.Map;
import com.epf.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maps")
@Tag(name = "Map", description = "API de gestion des maps")
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @Operation(summary = "Récupérer toutes les maps", description = "Retourne la liste de toutes les maps disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des maps récupérée avec succès",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MapDTO.class))),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping
    public List<MapDTO> getAllMaps() {
        return mapService.getAllMaps().stream()
                .map(MapMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Récupérer une map par son ID", description = "Retourne une map spécifique en fonction de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Map trouvée",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MapDTO.class))),
        @ApiResponse(responseCode = "404", description = "Map non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping("/{id}")
    public MapDTO getMapById(
            @Parameter(description = "ID de la map à récupérer", required = true)
            @PathVariable Long id) {
        return MapMapper.toDTO(mapService.getMapById(id));
    }

    @Operation(summary = "Créer une nouvelle map", description = "Crée une nouvelle map et retourne les détails de la map créée")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Map créée avec succès",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MapDTO.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @PostMapping
    public ResponseEntity<MapDTO> createMap(
            @Parameter(description = "Données de la map à créer", required = true)
            @RequestBody MapDTO mapDTO) {
        Map map = MapMapper.toEntity(mapDTO);
        Map createdMap = mapService.createMap(map);
        MapDTO responseDto = MapMapper.toDTO(createdMap);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Mettre à jour une map", description = "Met à jour les informations d'une map existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Map mise à jour avec succès",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MapDTO.class))),
        @ApiResponse(responseCode = "404", description = "Map non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @PutMapping("/{id}")
    public MapDTO updateMap(
            @Parameter(description = "ID de la map à mettre à jour", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouvelles données de la map", required = true)
            @RequestBody MapDTO mapDTO) {
        Map map = MapMapper.toEntity(mapDTO);
        map.setIdMap(id);
        Map updatedMap = mapService.updateMap(map);
        return MapMapper.toDTO(updatedMap);
    }

    @Operation(summary = "Supprimer une map", description = "Supprime une map existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Map supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Map non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMap(
            @Parameter(description = "ID de la map à supprimer", required = true)
            @PathVariable Long id) {
        mapService.deleteMap(id);
    }
}