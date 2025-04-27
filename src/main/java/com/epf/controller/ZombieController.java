package com.epf.controller;

import com.epf.dto.ZombieDTO;
import com.epf.dto.ZombieMapper;
import com.epf.model.Zombie;
import com.epf.service.ZombieService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/zombies")
@Tag(name = "Zombie", description = "API de gestion des zombies")
public class ZombieController {

    private final ZombieService zombieService;

    public ZombieController(ZombieService zombieService) {
        this.zombieService = zombieService;
    }

    @Operation(summary = "Récupérer tous les zombies", description = "Retourne la liste de tous les zombies disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des zombies récupérée avec succès",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ZombieDTO.class))),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping
    public List<ZombieDTO> getAllZombies() {
        return zombieService.getAllZombies().stream()
                .map(ZombieMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Récupérer un zombie par son ID", description = "Retourne un zombie spécifique en fonction de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Zombie trouvé",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ZombieDTO.class))),
        @ApiResponse(responseCode = "404", description = "Zombie non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping("/{id}")
    public ZombieDTO getZombieById(
            @Parameter(description = "ID du zombie à récupérer", required = true)
            @PathVariable("id") Long id) {
        return ZombieMapper.toDTO(zombieService.getZombieById(id));
    }

    @Operation(summary = "Récupérer les zombies d'une map", description = "Retourne la liste des zombies associés à une map spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des zombies récupérée avec succès",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ZombieDTO.class))),
        @ApiResponse(responseCode = "404", description = "Map non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping("/map/{mapId}")
    public List<ZombieDTO> getZombiesByMap(
            @Parameter(description = "ID de la map", required = true)
            @PathVariable("mapId") Long mapId) {
        return zombieService.getZombiesByMapId(mapId).stream()
                .map(ZombieMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Créer un nouveau zombie", description = "Crée un nouveau zombie et retourne les détails du zombie créé")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Zombie créé avec succès",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ZombieDTO.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Map non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @PostMapping
    public ResponseEntity<ZombieDTO> createZombie(
            @Parameter(description = "Données du zombie à créer", required = true)
            @Valid @RequestBody ZombieDTO zombieDTO) {
        Zombie zombie = ZombieMapper.toEntity(zombieDTO);
        Zombie createdZombie = zombieService.createZombie(zombie);
        ZombieDTO responseDto = ZombieMapper.toDTO(createdZombie);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdZombie.getIdZombie())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @Operation(summary = "Mettre à jour un zombie", description = "Met à jour les informations d'un zombie existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Zombie mis à jour avec succès",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ZombieDTO.class))),
        @ApiResponse(responseCode = "404", description = "Zombie ou Map non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @PutMapping("/{id}")
    public ZombieDTO updateZombie(
            @Parameter(description = "ID du zombie à mettre à jour", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouvelles données du zombie", required = true)
            @RequestBody ZombieDTO zombieDTO) {
        Zombie zombie = ZombieMapper.toEntity(zombieDTO);
        zombie.setIdZombie(id);
        Zombie updatedZombie = zombieService.updateZombie(zombie);
        return ZombieMapper.toDTO(updatedZombie);
    }

    @Operation(summary = "Supprimer un zombie", description = "Supprime un zombie existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Zombie supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Zombie non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZombie(
            @Parameter(description = "ID du zombie à supprimer", required = true)
            @PathVariable Long id) {
        zombieService.deleteZombie(id);
    }
}