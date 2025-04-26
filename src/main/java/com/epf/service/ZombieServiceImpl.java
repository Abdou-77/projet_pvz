package com.epf.service;

import com.epf.exception.ResourceNotFoundException;
import com.epf.model.Zombie;
import com.epf.repository.MapRepository;
import com.epf.repository.ZombieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZombieServiceImpl implements ZombieService {
    private final ZombieRepository zombieRepository;
    private final MapRepository mapRepository;

    public ZombieServiceImpl(ZombieRepository zombieRepository, MapRepository mapRepository) {
        this.zombieRepository = zombieRepository;
        this.mapRepository = mapRepository;
    }

    @Override
    public List<Zombie> getAllZombies() {
        return zombieRepository.findAll();
    }

    @Override
    public Zombie getZombieById(Long id) {
        return zombieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zombie not found with id: " + id));
    }

    @Override
    public List<Zombie> getZombiesByMapId(Long mapId) {
        if (!mapRepository.existsById(mapId)) {
            throw new ResourceNotFoundException("Map not found with id: " + mapId);
        }
        return zombieRepository.findByMapId(mapId);
    }

    @Override
    public Zombie createZombie(Zombie zombie) {
        if (zombie.getIdMap() == null) {
            throw new IllegalArgumentException("idMap is required");
        }
        validateMapExists(zombie.getIdMap());
        Long generatedId = zombieRepository.create(zombie);
        zombie.setIdZombie(generatedId);
        return zombie;
    }

    @Override
    public Zombie updateZombie(Zombie zombie) {
        validateMapExists(zombie.getIdMap());
        zombieRepository.update(zombie);
        return zombie;
    }

    @Override
    public void deleteZombie(Long id) {
        zombieRepository.delete(id);
    }

    private void validateMapExists(Long mapId) {
        if (!mapRepository.existsById(mapId)) {
            throw new ResourceNotFoundException("Map not found with id: " + mapId);
        }
    }
}