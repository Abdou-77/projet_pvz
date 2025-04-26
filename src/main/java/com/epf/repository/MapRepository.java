package com.epf.repository;

import com.epf.model.Map;
import java.util.List;
import java.util.Optional;

public interface MapRepository {
    List<Map> findAll();
    Optional<Map> findById(Long id);
    Long create(Map map);
    void update(Map map);
    void delete(Long id);


    boolean existsById(Long mapId);
}