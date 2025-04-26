package com.epf.controller;

import com.epf.dto.MapDTO;
import com.epf.dto.MapMapper;
import com.epf.model.Map;
import com.epf.service.MapService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maps")
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public List<MapDTO> getAllMaps() {
        return mapService.getAllMaps().stream()
                .map(MapMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public MapDTO getMapById(@PathVariable("id") Long id) {
        return MapMapper.toDTO(mapService.getMapById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MapDTO createMap(@Valid @RequestBody MapDTO mapDTO) {
        Map map = MapMapper.toEntity(mapDTO);
        return MapMapper.toDTO(mapService.createMap(map));
    }

    @PutMapping("/{id}")
    public MapDTO updateMap(@PathVariable("id") Long id,
                            @Valid @RequestBody MapDTO mapDTO) {
        Map map = MapMapper.toEntity(mapDTO);
        map.setIdMap(id);
        return MapMapper.toDTO(mapService.updateMap(map));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMap(@PathVariable("id") Long id) {
        mapService.deleteMap(id);
    }
}