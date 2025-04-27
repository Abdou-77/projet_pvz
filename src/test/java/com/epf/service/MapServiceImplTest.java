package com.epf.service;

import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Map;
import com.epf.repository.MapRepository;
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
class MapServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(MapServiceImplTest.class);

    @Mock
    private MapRepository mapRepository;

    @InjectMocks
    private MapServiceImpl mapService;

    private Map testMap;

    @BeforeEach
    void setUp() {
        logger.info("Setting up test environment");
        testMap = new Map(1L, 5, 5, "/test.png");
    }

    @Test
    void getAllMaps_ShouldReturnAllMaps() {
        logger.info("Testing getAllMaps method");
        List<Map> expectedMaps = Arrays.asList(testMap);
        when(mapRepository.findAll()).thenReturn(expectedMaps);

        List<Map> result = mapService.getAllMaps();

        assertEquals(expectedMaps, result);
        verify(mapRepository).findAll();
        logger.info("getAllMaps test completed successfully");
    }

    @Test
    void getMapById_WhenMapExists_ShouldReturnMap() {
        logger.info("Testing getMapById with existing map");
        when(mapRepository.findById(1L)).thenReturn(Optional.of(testMap));

        Map result = mapService.getMapById(1L);

        assertEquals(testMap, result);
        verify(mapRepository).findById(1L);
        logger.info("getMapById test with existing map completed successfully");
    }

    @Test
    void getMapById_WhenMapDoesNotExist_ShouldThrowException() {
        logger.info("Testing getMapById with non-existing map");
        when(mapRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> mapService.getMapById(1L));
        verify(mapRepository).findById(1L);
        logger.info("getMapById test with non-existing map completed successfully");
    }

    @Test
    void createMap_ShouldCreateAndReturnMap() {
        logger.info("Testing createMap method");
        doNothing().when(mapRepository).create(any(Map.class));

        Map result = mapService.createMap(testMap);

        assertEquals(testMap, result);
        verify(mapRepository).create(testMap);
        logger.info("createMap test completed successfully");
    }

    @Test
    void updateMap_ShouldUpdateAndReturnMap() {
        logger.info("Testing updateMap method");
        Map result = mapService.updateMap(testMap);

        assertEquals(testMap, result);
        verify(mapRepository).update(testMap);
        logger.info("updateMap test completed successfully");
    }

    @Test
    void deleteMap_ShouldDeleteMap() {
        logger.info("Testing deleteMap method");
        doNothing().when(mapRepository).delete(1L);

        assertDoesNotThrow(() -> mapService.deleteMap(1L));
        verify(mapRepository).delete(1L);
        logger.info("deleteMap test completed successfully");
    }
}