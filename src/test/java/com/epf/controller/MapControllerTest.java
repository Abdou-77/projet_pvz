package com.epf.controller;

import com.epf.dto.MapDTO;
import com.epf.dto.MapMapper;
import com.epf.model.Map;
import com.epf.service.MapService;
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
class MapControllerTest {

    @Mock
    private MapService mapService;

    @InjectMocks
    private MapController mapController;

    private Map testMap;
    private MapDTO testMapDTO;

    @BeforeEach
    void setUp() {
        testMap = new Map(1L, 5, 10, "/test.png");
        testMapDTO = MapMapper.toDTO(testMap);
    }

    @Test
    void getAllMaps_ShouldReturnListOfMapDTO() {
        List<Map> maps = Arrays.asList(testMap);
        when(mapService.getAllMaps()).thenReturn(maps);

        List<MapDTO> result = mapController.getAllMaps();

        assertEquals(1, result.size());
        assertEquals(testMapDTO, result.get(0));
        verify(mapService).getAllMaps();
    }

    @Test
    void getMapById_ShouldReturnMapDTO() {
        when(mapService.getMapById(1L)).thenReturn(testMap);

        MapDTO result = mapController.getMapById(1L);

        assertEquals(testMapDTO, result);
        verify(mapService).getMapById(1L);
    }

    @Test
    void createMap_ShouldReturnCreatedMapDTO() {
        when(mapService.createMap(any(Map.class))).thenReturn(testMap);

        ResponseEntity<MapDTO> response = mapController.createMap(testMapDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testMapDTO, response.getBody());
        verify(mapService).createMap(any(Map.class));
    }

    @Test
    void updateMap_ShouldReturnUpdatedMapDTO() {
        when(mapService.updateMap(any(Map.class))).thenReturn(testMap);

        MapDTO result = mapController.updateMap(1L, testMapDTO);

        assertEquals(testMapDTO, result);
        verify(mapService).updateMap(any(Map.class));
    }

    @Test
    void deleteMap_ShouldReturnNoContent() {
        doNothing().when(mapService).deleteMap(1L);

        assertDoesNotThrow(() -> mapController.deleteMap(1L));
        verify(mapService).deleteMap(1L);
    }
} 