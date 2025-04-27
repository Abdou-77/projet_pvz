package com.epf.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZombieTest {

    @Test
    void testDefaultConstructor() {
        Zombie zombie = new Zombie();
        assertNotNull(zombie);
        assertNull(zombie.getIdZombie());
        assertNull(zombie.getNom());
        assertEquals(0, zombie.getPointDeVie());
        assertEquals(0.0, zombie.getAttaqueParSeconde());
        assertEquals(0, zombie.getDegatAttaque());
        assertEquals(0.0, zombie.getVitesseDeDeplacement());
        assertNull(zombie.getCheminImage());
        assertNull(zombie.getIdMap());
    }

    @Test
    void testParameterizedConstructor() {
        Zombie zombie = new Zombie(1L, "Test Zombie", 100, 2.0, 50, 0.5, "/test.png", 1L);
        
        assertEquals(1L, zombie.getIdZombie());
        assertEquals("Test Zombie", zombie.getNom());
        assertEquals(100, zombie.getPointDeVie());
        assertEquals(2.0, zombie.getAttaqueParSeconde());
        assertEquals(50, zombie.getDegatAttaque());
        assertEquals(0.5, zombie.getVitesseDeDeplacement());
        assertEquals("/test.png", zombie.getCheminImage());
        assertEquals(1L, zombie.getIdMap());
    }

    @Test
    void testParameterizedConstructorWithNullValues() {
        Zombie zombie = new Zombie(1L, null, null, null, null, null, null, 1L);
        
        assertEquals(1L, zombie.getIdZombie());
        assertEquals("Default Zombie", zombie.getNom());
        assertEquals(100, zombie.getPointDeVie());
        assertEquals(1.0, zombie.getAttaqueParSeconde());
        assertEquals(15, zombie.getDegatAttaque());
        assertEquals(0.5, zombie.getVitesseDeDeplacement());
        assertEquals("/default_zombie.png", zombie.getCheminImage());
        assertEquals(1L, zombie.getIdMap());
    }

    @Test
    void testSettersAndGetters() {
        Zombie zombie = new Zombie();
        
        zombie.setIdZombie(1L);
        zombie.setNom("Test Zombie");
        zombie.setPointDeVie(100);
        zombie.setAttaqueParSeconde(2.0);
        zombie.setDegatAttaque(50);
        zombie.setVitesseDeDeplacement(0.5);
        zombie.setCheminImage("/test.png");
        zombie.setIdMap(1L);

        assertEquals(1L, zombie.getIdZombie());
        assertEquals("Test Zombie", zombie.getNom());
        assertEquals(100, zombie.getPointDeVie());
        assertEquals(2.0, zombie.getAttaqueParSeconde());
        assertEquals(50, zombie.getDegatAttaque());
        assertEquals(0.5, zombie.getVitesseDeDeplacement());
        assertEquals("/test.png", zombie.getCheminImage());
        assertEquals(1L, zombie.getIdMap());
    }
} 