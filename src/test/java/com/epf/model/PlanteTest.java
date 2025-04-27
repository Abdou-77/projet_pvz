package com.epf.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlanteTest {

    @Test
    void testDefaultConstructor() {
        Plante plante = new Plante();
        assertNotNull(plante);
        assertNull(plante.getIdPlante());
        assertNull(plante.getnom());
        assertEquals(0, plante.getPointDeVie());
        assertEquals(0.0, plante.getAttaqueParSeconde());
        assertEquals(0, plante.getDegatAttaque());
        assertEquals(0, plante.getCout());
        assertEquals(0.0, plante.getSoleilParSeconde());
        assertNull(plante.getEffet());
        assertNull(plante.getCheminImage());
    }

    @Test
    void testParameterizedConstructor() {
        Plante plante = new Plante(1L, "Test Plant", 100, 2.0, 50, 25, 1.5, Plante.Effet.NORMAL, "/test.png");
        
        assertEquals(1L, plante.getIdPlante());
        assertEquals("Test Plant", plante.getnom());
        assertEquals(100, plante.getPointDeVie());
        assertEquals(2.0, plante.getAttaqueParSeconde());
        assertEquals(50, plante.getDegatAttaque());
        assertEquals(25, plante.getCout());
        assertEquals(1.5, plante.getSoleilParSeconde());
        assertEquals(Plante.Effet.NORMAL, plante.getEffet());
        assertEquals("/test.png", plante.getCheminImage());
    }

    @Test
    void testSettersAndGetters() {
        Plante plante = new Plante();
        
        plante.setIdPlante(1L);
        plante.setnom("Test Plant");
        plante.setPointDeVie(100);
        plante.setAttaqueParSeconde(2.0);
        plante.setDegatAttaque(50);
        plante.setCout(25);
        plante.setSoleilParSeconde(1.5);
        plante.setEffet(Plante.Effet.NORMAL);
        plante.setCheminImage("/test.png");

        assertEquals(1L, plante.getIdPlante());
        assertEquals("Test Plant", plante.getnom());
        assertEquals(100, plante.getPointDeVie());
        assertEquals(2.0, plante.getAttaqueParSeconde());
        assertEquals(50, plante.getDegatAttaque());
        assertEquals(25, plante.getCout());
        assertEquals(1.5, plante.getSoleilParSeconde());
        assertEquals(Plante.Effet.NORMAL, plante.getEffet());
        assertEquals("/test.png", plante.getCheminImage());
    }

    @Test
    void testEffetEnum() {
        assertEquals(Plante.Effet.NORMAL, Plante.Effet.fromString("normal"));
        assertEquals(Plante.Effet.SLOW_LOW, Plante.Effet.fromString("slow low"));
        assertEquals(Plante.Effet.SLOW_MEDIUM, Plante.Effet.fromString("slow medium"));
        assertEquals(Plante.Effet.SLOW_STOP, Plante.Effet.fromString("slow stop"));
    }
} 