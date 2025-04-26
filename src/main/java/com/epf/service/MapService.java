package com.epf.service;

import com.epf.model.Map;
import java.util.List;
import java.util.Optional;

public interface MapService {
    List<Map> getAllMaps();
    Map getMapById(Long id);
    Map createMap(Map map);
    Map updateMap(Map map);
    void deleteMap(Long id);
}