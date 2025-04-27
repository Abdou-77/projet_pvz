package com.epf.repository;

import com.epf.model.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZombieRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ZombieRepositoryImpl zombieRepository;

    private Zombie testZombie;

    @BeforeEach
    void setUp() {
        testZombie = new Zombie();
        testZombie.setIdZombie(1L);
        testZombie.setNom("Test Zombie");
        testZombie.setPointDeVie(150);
        testZombie.setAttaqueParSeconde(1.0);
        testZombie.setDegatAttaque(30);
        testZombie.setVitesseDeDeplacement(0.5);
        testZombie.setCheminImage("/images/test.png");
        testZombie.setIdMap(1L);
    }

    @Test
    void findAll_ShouldReturnAllZombies() {
        // Arrange
        List<Zombie> expectedZombies = Arrays.asList(testZombie);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedZombies);

        // Act
        List<Zombie> result = zombieRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(expectedZombies.size(), result.size());
        assertEquals(testZombie.getNom(), result.get(0).getNom());
        verify(jdbcTemplate).query(eq("SELECT * FROM Zombie"), any(RowMapper.class));
    }

    @Test
    void findById_WhenZombieExists_ShouldReturnZombie() {
        // Arrange
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList(testZombie));

        // Act
        Optional<Zombie> result = zombieRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testZombie.getNom(), result.get().getNom());
        verify(jdbcTemplate).query(eq("SELECT * FROM Zombie WHERE id_Zombie = ?"), any(RowMapper.class), eq(1L));
    }

    @Test
    void findById_WhenZombieDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList());

        // Act
        Optional<Zombie> result = zombieRepository.findById(1L);

        // Assert
        assertTrue(result.isEmpty());
        verify(jdbcTemplate).query(eq("SELECT * FROM Zombie WHERE id_Zombie = ?"), any(RowMapper.class), eq(1L));
    }

    @Test
    void findByMapId_ShouldReturnZombiesForMap() {
        // Arrange
        List<Zombie> expectedZombies = Arrays.asList(testZombie);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(expectedZombies);

        // Act
        List<Zombie> result = zombieRepository.findByMapId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(expectedZombies.size(), result.size());
        assertEquals(testZombie.getNom(), result.get(0).getNom());
        verify(jdbcTemplate).query(eq("SELECT * FROM Zombie WHERE id_map = ?"), any(RowMapper.class), eq(1L));
    }

    @Test
    void create_ShouldReturnGeneratedId() {
        // Arrange
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        when(jdbcTemplate.update(any(), any(GeneratedKeyHolder.class))).thenAnswer(invocation -> {
            keyHolder.getKeyList().add(java.util.Collections.singletonMap("id_Zombie", 1L));
            return 1;
        });

        // Act
        Long id = zombieRepository.create(testZombie);

        // Assert
        assertEquals(1L, id);
        verify(jdbcTemplate).update(any(), any(GeneratedKeyHolder.class));
    }

    @Test
    void update_ShouldUpdateZombie() {
        // Act
        zombieRepository.update(testZombie);

        // Assert
        verify(jdbcTemplate).update(
            eq("UPDATE Zombie SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, vitesse_de_deplacement = ?, chemin_image = ?, id_map = ? WHERE id_Zombie = ?"),
            eq(testZombie.getNom()),
            eq(testZombie.getPointDeVie()),
            eq(testZombie.getAttaqueParSeconde()),
            eq(testZombie.getDegatAttaque()),
            eq(testZombie.getVitesseDeDeplacement()),
            eq(testZombie.getCheminImage()),
            eq(testZombie.getIdMap()),
            eq(testZombie.getIdZombie())
        );
    }

    @Test
    void delete_ShouldDeleteZombie() {
        // Act
        zombieRepository.delete(1L);

        // Assert
        verify(jdbcTemplate).update(eq("DELETE FROM Zombie WHERE id_Zombie = ?"), eq(1L));
    }
} 