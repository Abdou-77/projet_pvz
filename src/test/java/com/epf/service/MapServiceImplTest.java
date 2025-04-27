package com.epf.service;

import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Map;
import com.epf.repository.MapRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MapServiceImplTest {

    private MapRepository mapRepository; // Mocked repository
    private MapServiceImpl mapService;  // Service we are testing

    @Before
    public void setUp() {
        // Create mock objects and set up the service dependency
        mapRepository = Mockito.mock(MapRepository.class);
        mapService = new MapServiceImpl(mapRepository);
    }

    /**
     * Test case: getMapById() should return a Map when the ID exists in the repository.
     */
    @Test
    public void testGetMapById_ReturnsMap_WhenIdExists() {
        // Arrange
        Long mapId = 1L;
        Map map = new Map(mapId, 10, 20, "path/to/image");

        // Mock repository behavior
        when(mapRepository.findById(mapId)).thenReturn(Optional.of(map));

        // Act
        Map result = mapService.getMapById(mapId);

        // Assert
        assertNotNull(result); // Ensure result is not null
        assertEquals(mapId, result.getIdMap());
        assertEquals(map.getLigne(), result.getLigne());
        assertEquals(map.getColonne(), result.getColonne());
        assertEquals(map.getCheminImage(), result.getCheminImage());

        // Verify mocked method was called
        verify(mapRepository, times(1)).findById(mapId);
    }

    /**
     * Test case: getMapById() should throw a ResourceNotFoundException when the ID doesn't exist.
     */
    @Test
    public void testGetMapById_ThrowsException_WhenIdDoesNotExist() {
        // Arrange
        Long mapId = 1L;

        // Mock repository behavior
        when(mapRepository.findById(mapId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> mapService.getMapById(mapId)
        );
        assertEquals("Map not found with id: " + mapId, exception.getMessage());

        // Verify mocked method was called
        verify(mapRepository, times(1)).findById(mapId);
    }
}