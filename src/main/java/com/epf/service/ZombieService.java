package com.epf.service;

import com.epf.model.Zombie;
import java.util.List;

public interface ZombieService {
    List<Zombie> getAllZombies();
    Zombie getZombieById(Long id);
    List<Zombie> getZombiesByMapId(Long mapId);
    Zombie createZombie(Zombie zombie);
    Zombie updateZombie(Zombie zombie);
    void deleteZombie(Long id);
}