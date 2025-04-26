package com.epf.repository;

import com.epf.model.Zombie;
import java.util.List;
import java.util.Optional;

public interface ZombieRepository {
    List<Zombie> findAll();
    Optional<Zombie> findById(Long id);
    List<Zombie> findByMapId(Long mapId);
    Long create(Zombie zombie);
    void update(Zombie zombie);
    void delete(Long id);
}