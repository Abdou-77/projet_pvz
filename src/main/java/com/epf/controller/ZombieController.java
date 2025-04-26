package com.epf.controller;

import com.epf.dto.ZombieDTO;
import com.epf.dto.ZombieMapper;
import com.epf.model.Zombie;
import com.epf.service.ZombieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/zombies")
public class ZombieController {

    private final ZombieService zombieService;

    public ZombieController(ZombieService zombieService) {
        this.zombieService = zombieService;
    }

    @GetMapping
    public List<ZombieDTO> getAllZombies() {
        return zombieService.getAllZombies().stream()
                .map(ZombieMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ZombieDTO getZombieById(@PathVariable("id") Long id) {
        return ZombieMapper.toDTO(zombieService.getZombieById(id));
    }

    @GetMapping("/map/{mapId}")
    public List<ZombieDTO> getZombiesByMap(@PathVariable("mapId") Long mapId) {
        return zombieService.getZombiesByMapId(mapId).stream()
                .map(ZombieMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ZombieDTO> createZombie(@Valid @RequestBody ZombieDTO zombieDTO) {
        Zombie zombie = ZombieMapper.toEntity(zombieDTO);
        Zombie createdZombie = zombieService.createZombie(zombie); // Correct method
        ZombieDTO responseDto = ZombieMapper.toDTO(createdZombie);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdZombie.getIdZombie())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }


    @PutMapping("/{id}")
    public ZombieDTO updateZombie(@PathVariable("id") Long id,
                                  @Valid @RequestBody ZombieDTO zombieDTO) {
        Zombie zombie = ZombieMapper.toEntity(zombieDTO);
        zombie.setIdZombie(id);
        return ZombieMapper.toDTO(zombieService.updateZombie(zombie));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZombie(@PathVariable("id") Long id) {
        zombieService.deleteZombie(id);
    }
}