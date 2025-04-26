package com.epf.service;

import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Map;
import com.epf.repository.MapRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    private final MapRepository mapRepository;

    public MapServiceImpl(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    @Override
    public List<Map> getAllMaps() {
        return mapRepository.findAll();
    }

    @Override
    public Map getMapById(Long id) {
        return mapRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Map not found with id: " + id));
    }

    @Override
    public Map createMap(Map map) {
        Long id = mapRepository.create(map);
        map.setIdMap(id);
        return map;
    }

    @Override
    public Map updateMap(Map map) {
        mapRepository.update(map);
        return map;
    }

    @Override
    public void deleteMap(Long id) {
        mapRepository.delete(id);
    }
}