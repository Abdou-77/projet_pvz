package com.epf.service;

import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Zombie;
import com.epf.repository.MapRepository;
import com.epf.repository.ZombieRepository;
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
class ZombieServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(ZombieServiceImplTest.class);

    @Mock
    private ZombieRepository zombieRepository;

    @Mock
    private MapRepository mapRepository;

    @InjectMocks
    private ZombieServiceImpl zombieService;

    private Zombie testZombie;

    @BeforeEach
    void setUp() {
        logger.info("Setting up test environment");
        testZombie = new Zombie(1L, "Test Zombie", 100, 2.0, 50, 0.5, "/test.png", 1L);
    }

    @Test
    void getAllZombies_ShouldReturnAllZombies() {
        logger.info("Testing getAllZombies method");
        List<Zombie> expectedZombies = Arrays.asList(testZombie);
        when(zombieRepository.findAll()).thenReturn(expectedZombies);

        List<Zombie> result = zombieService.getAllZombies();

        assertEquals(expectedZombies, result);
        verify(zombieRepository).findAll();
        logger.info("getAllZombies test completed successfully");
    }

    @Test
    void getZombieById_WhenZombieExists_ShouldReturnZombie() {
        logger.info("Testing getZombieById with existing zombie");
        when(zombieRepository.findById(1L)).thenReturn(Optional.of(testZombie));

        Zombie result = zombieService.getZombieById(1L);

        assertEquals(testZombie, result);
        verify(zombieRepository).findById(1L);
        logger.info("getZombieById test with existing zombie completed successfully");
    }

    @Test
    void getZombieById_WhenZombieDoesNotExist_ShouldThrowException() {
        logger.info("Testing getZombieById with non-existing zombie");
        when(zombieRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> zombieService.getZombieById(1L));
        verify(zombieRepository).findById(1L);
        logger.info("getZombieById test with non-existing zombie completed successfully");
    }

    @Test
    void getZombiesByMapId_WhenMapExists_ShouldReturnZombies() {
        logger.info("Testing getZombiesByMapId with existing map");
        List<Zombie> expectedZombies = Arrays.asList(testZombie);
        when(mapRepository.existsById(1L)).thenReturn(true);
        when(zombieRepository.findByMapId(1L)).thenReturn(expectedZombies);

        List<Zombie> result = zombieService.getZombiesByMapId(1L);

        assertEquals(expectedZombies, result);
        verify(mapRepository).existsById(1L);
        verify(zombieRepository).findByMapId(1L);
        logger.info("getZombiesByMapId test with existing map completed successfully");
    }

    @Test
    void getZombiesByMapId_WhenMapDoesNotExist_ShouldThrowException() {
        logger.info("Testing getZombiesByMapId with non-existing map");
        when(mapRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> zombieService.getZombiesByMapId(1L));
        verify(mapRepository).existsById(1L);
        verify(zombieRepository, never()).findByMapId(any());
        logger.info("getZombiesByMapId test with non-existing map completed successfully");
    }

    @Test
    void createZombie_WhenMapExists_ShouldCreateAndReturnZombie() {
        logger.info("Testing createZombie with existing map");
        when(mapRepository.existsById(1L)).thenReturn(true);
        doNothing().when(zombieRepository).create(any(Zombie.class));

        Zombie result = zombieService.createZombie(testZombie);

        assertEquals(testZombie, result);
        verify(mapRepository).existsById(1L);
        verify(zombieRepository).create(testZombie);
        logger.info("createZombie test with existing map completed successfully");
    }

    @Test
    void createZombie_WhenMapDoesNotExist_ShouldThrowException() {
        logger.info("Testing createZombie with non-existing map");
        when(mapRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> zombieService.createZombie(testZombie));
        verify(mapRepository).existsById(1L);
        verify(zombieRepository, never()).create(any(Zombie.class));
        logger.info("createZombie test with non-existing map completed successfully");
    }

    @Test
    void createZombie_WhenIdMapIsNull_ShouldThrowException() {
        logger.info("Testing createZombie with null map ID");
        testZombie.setIdMap(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> zombieService.createZombie(testZombie));
        verify(mapRepository, never()).existsById(any());
        verify(zombieRepository, never()).create(any(Zombie.class));
        logger.info("createZombie test with null map ID completed successfully");
    }

    @Test
    void updateZombie_WhenMapExists_ShouldUpdateAndReturnZombie() {
        logger.info("Testing updateZombie with existing map");
        when(mapRepository.existsById(1L)).thenReturn(true);

        Zombie result = zombieService.updateZombie(testZombie);

        assertEquals(testZombie, result);
        verify(mapRepository).existsById(1L);
        verify(zombieRepository).update(testZombie);
        logger.info("updateZombie test with existing map completed successfully");
    }

    @Test
    void updateZombie_WhenMapDoesNotExist_ShouldThrowException() {
        logger.info("Testing updateZombie with non-existing map");
        when(mapRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> zombieService.updateZombie(testZombie));
        verify(mapRepository).existsById(1L);
        verify(zombieRepository, never()).update(any(Zombie.class));
        logger.info("updateZombie test with non-existing map completed successfully");
    }

    @Test
    void deleteZombie_ShouldDeleteZombie() {
        logger.info("Testing deleteZombie method");
        doNothing().when(zombieRepository).delete(1L);

        assertDoesNotThrow(() -> zombieService.deleteZombie(1L));
        verify(zombieRepository).delete(1L);
        logger.info("deleteZombie test completed successfully");
    }
} 