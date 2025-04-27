package com.epf.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void testDefaultConstructor() {
        Map map = new Map();
        assertNotNull(map);
        assertNull(map.getIdMap());
        assertEquals(0, map.getLigne());
        assertEquals(0, map.getColonne());
        assertNull(map.getCheminImage());
    }

    @Test
    void testParameterizedConstructor() {
        Map map = new Map(1L, 5, 10, "/test.png");
        
        assertEquals(1L, map.getIdMap());
        assertEquals(5, map.getLigne());
        assertEquals(10, map.getColonne());
        assertEquals("/test.png", map.getCheminImage());
    }

    @Test
    void testSettersAndGetters() {
        Map map = new Map();
        
        map.setIdMap(1L);
        map.setLigne(5);
        map.setColonne(10);
        map.setCheminImage("/test.png");

        assertEquals(1L, map.getIdMap());
        assertEquals(5, map.getLigne());
        assertEquals(10, map.getColonne());
        assertEquals("/test.png", map.getCheminImage());
    }
} 