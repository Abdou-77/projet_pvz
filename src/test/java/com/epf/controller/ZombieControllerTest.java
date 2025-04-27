package com.epf.controller;

import com.epf.dto.ZombieDTO;
import com.epf.dto.ZombieMapper;
import com.epf.model.Zombie;
import com.epf.service.ZombieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZombieControllerTest {

    @Mock
    private ZombieService zombieService;

    @InjectMocks
    private ZombieController zombieController;

    private Zombie testZombie;
    private ZombieDTO testZombieDTO;

    @BeforeEach
    void setUp() {
        testZombie = new Zombie(1L, "Test Zombie", 100, 2.0, 50, 0.5, "/test.png", 1L);
        testZombieDTO = ZombieMapper.toDTO(testZombie);
    }

    @Test
    void getAllZombies_ShouldReturnListOfZombieDTO() {
        List<Zombie> zombies = Arrays.asList(testZombie);
        when(zombieService.getAllZombies()).thenReturn(zombies);

        List<ZombieDTO> result = zombieController.getAllZombies();

        assertEquals(1, result.size());
        assertEquals(testZombieDTO, result.get(0));
        verify(zombieService).getAllZombies();
    }

    @Test
    void getZombieById_ShouldReturnZombieDTO() {
        when(zombieService.getZombieById(1L)).thenReturn(testZombie);

        ZombieDTO result = zombieController.getZombieById(1L);

        assertEquals(testZombieDTO, result);
        verify(zombieService).getZombieById(1L);
    }

    @Test
    void getZombiesByMap_ShouldReturnListOfZombieDTO() {
        List<Zombie> zombies = Arrays.asList(testZombie);
        when(zombieService.getZombiesByMapId(1L)).thenReturn(zombies);

        List<ZombieDTO> result = zombieController.getZombiesByMap(1L);

        assertEquals(1, result.size());
        assertEquals(testZombieDTO, result.get(0));
        verify(zombieService).getZombiesByMapId(1L);
    }

    @Test
    void createZombie_ShouldReturnCreatedZombieDTO() {
        when(zombieService.createZombie(any(Zombie.class))).thenReturn(testZombie);

        ResponseEntity<ZombieDTO> response = zombieController.createZombie(testZombieDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testZombieDTO, response.getBody());
        verify(zombieService).createZombie(any(Zombie.class));
    }

    @Test
    void updateZombie_ShouldReturnUpdatedZombieDTO() {
        when(zombieService.updateZombie(any(Zombie.class))).thenReturn(testZombie);

        ZombieDTO result = zombieController.updateZombie(1L, testZombieDTO);

        assertEquals(testZombieDTO, result);
        verify(zombieService).updateZombie(any(Zombie.class));
    }

    @Test
    void deleteZombie_ShouldReturnNoContent() {
        doNothing().when(zombieService).deleteZombie(1L);

        assertDoesNotThrow(() -> zombieController.deleteZombie(1L));
        verify(zombieService).deleteZombie(1L);
    }
} 