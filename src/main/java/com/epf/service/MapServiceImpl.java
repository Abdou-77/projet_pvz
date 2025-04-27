package com.epf.service;

import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Map;
import com.epf.repository.MapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MapServiceImpl implements MapService {
    private static final Logger logger = LoggerFactory.getLogger(MapServiceImpl.class);
    private final MapRepository mapRepository;

    public MapServiceImpl(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    @Override
    public List<Map> getAllMaps() {
        logger.info("Retrieving all maps");
        try {
            List<Map> maps = mapRepository.findAll();
            logger.info("Successfully retrieved {} maps", maps.size());
            return maps;
        } catch (Exception e) {
            logger.error("Error retrieving all maps", e);
            throw new RuntimeException("Failed to retrieve maps", e);
        }
    }

    @Override
    public Map getMapById(Long id) {
        logger.info("Retrieving map with id: {}", id);
        try {
            return mapRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Map not found with id: {}", id);
                        return new ResourceNotFoundException("Map not found with id: " + id);
                    });
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error retrieving map with id: {}", id, e);
            throw new RuntimeException("Failed to retrieve map", e);
        }
    }

    @Override
    public Map createMap(Map map) {
        logger.info("Creating new map");
        try {
            Long id = mapRepository.create(map);
            map.setIdMap(id);
            logger.info("Successfully created map with id: {}", id);
            return map;
        } catch (Exception e) {
            logger.error("Error creating map", e);
            throw new RuntimeException("Failed to create map", e);
        }
    }

    @Override
    public Map updateMap(Map map) {
        logger.info("Updating map with id: {}", map.getIdMap());
        try {
            if (!mapExists(map.getIdMap())) {
                logger.warn("Map not found for update with id: {}", map.getIdMap());
                throw new ResourceNotFoundException("Map not found with id: " + map.getIdMap());
            }
            mapRepository.update(map);
            logger.info("Successfully updated map with id: {}", map.getIdMap());
            return map;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating map with id: {}", map.getIdMap(), e);
            throw new RuntimeException("Failed to update map", e);
        }
    }

    @Override
    public void deleteMap(Long id) {
        logger.info("Deleting map with id: {}", id);
        try {
            if (!mapExists(id)) {
                logger.warn("Map not found for deletion with id: {}", id);
                throw new ResourceNotFoundException("Map not found with id: " + id);
            }
            mapRepository.delete(id);
            logger.info("Successfully deleted map with id: {}", id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting map with id: {}", id, e);
            throw new RuntimeException("Failed to delete map", e);
        }
    }

    private boolean mapExists(Long id) {
        return mapRepository.findById(id).isPresent();
    }
}