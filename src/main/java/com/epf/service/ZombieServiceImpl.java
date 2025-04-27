package com.epf.service;

import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Zombie;
import com.epf.repository.MapRepository;
import com.epf.repository.ZombieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZombieServiceImpl implements ZombieService {
    private static final Logger logger = LoggerFactory.getLogger(ZombieServiceImpl.class);
    private final ZombieRepository zombieRepository;
    private final MapRepository mapRepository;

    public ZombieServiceImpl(ZombieRepository zombieRepository, MapRepository mapRepository) {
        this.zombieRepository = zombieRepository;
        this.mapRepository = mapRepository;
    }

    @Override
    public List<Zombie> getAllZombies() {
        logger.info("Retrieving all zombies");
        try {
            List<Zombie> zombies = zombieRepository.findAll();
            logger.info("Successfully retrieved {} zombies", zombies.size());
            return zombies;
        } catch (Exception e) {
            logger.error("Error retrieving all zombies", e);
            throw new RuntimeException("Failed to retrieve zombies", e);
        }
    }

    @Override
    public Zombie getZombieById(Long id) {
        logger.info("Retrieving zombie with id: {}", id);
        try {
            return zombieRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Zombie not found with id: {}", id);
                        return new ResourceNotFoundException("Zombie not found with id: " + id);
                    });
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error retrieving zombie with id: {}", id, e);
            throw new RuntimeException("Failed to retrieve zombie", e);
        }
    }

    @Override
    public List<Zombie> getZombiesByMapId(Long mapId) {
        logger.info("Retrieving zombies for map with id: {}", mapId);
        try {
            validateMapExists(mapId);
            List<Zombie> zombies = zombieRepository.findByMapId(mapId);
            logger.info("Successfully retrieved {} zombies for map with id: {}", zombies.size(), mapId);
            return zombies;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error retrieving zombies for map with id: {}", mapId, e);
            throw new RuntimeException("Failed to retrieve zombies for map", e);
        }
    }

    @Override
    public Zombie createZombie(Zombie zombie) {
        logger.info("Creating new zombie");
        try {
            if (zombie.getIdMap() == null) {
                logger.warn("Attempted to create zombie with null map ID");
                throw new IllegalArgumentException("idMap is required");
            }
            validateMapExists(zombie.getIdMap());
            Long generatedId = zombieRepository.create(zombie);
            zombie.setIdZombie(generatedId);
            logger.info("Successfully created zombie with id: {}", generatedId);
            return zombie;
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error creating zombie", e);
            throw new RuntimeException("Failed to create zombie", e);
        }
    }

    @Override
    public Zombie updateZombie(Zombie zombie) {
        logger.info("Updating zombie with id: {}", zombie.getIdZombie());
        try {
            validateMapExists(zombie.getIdMap());
            zombieRepository.update(zombie);
            logger.info("Successfully updated zombie with id: {}", zombie.getIdZombie());
            return zombie;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating zombie with id: {}", zombie.getIdZombie(), e);
            throw new RuntimeException("Failed to update zombie", e);
        }
    }

    @Override
    public void deleteZombie(Long id) {
        logger.info("Deleting zombie with id: {}", id);
        try {
            zombieRepository.delete(id);
            logger.info("Successfully deleted zombie with id: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting zombie with id: {}", id, e);
            throw new RuntimeException("Failed to delete zombie", e);
        }
    }

    private void validateMapExists(Long mapId) {
        if (!mapRepository.existsById(mapId)) {
            logger.warn("Map not found with id: {}", mapId);
            throw new ResourceNotFoundException("Map not found with id: " + mapId);
        }
    }
}