package com.epf.repository;

import com.epf.model.Plante;
import com.epf.model.Plante.Effet;
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
class PlanteRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PlanteRepositoryImpl planteRepository;

    private Plante testPlante;

    @BeforeEach
    void setUp() {
        testPlante = new Plante();
        testPlante.setIdPlante(1L);
        testPlante.setnom("Test Plante");
        testPlante.setPointDeVie(100);
        testPlante.setAttaqueParSeconde(1.5);
        testPlante.setDegatAttaque(20);
        testPlante.setCout(50);
        testPlante.setSoleilParSeconde(1.0);
        testPlante.setEffet(Effet.NORMAL);
        testPlante.setCheminImage("/images/test.png");
    }

    @Test
    void findAll_ShouldReturnAllPlantes() {
        // Arrange
        List<Plante> expectedPlantes = Arrays.asList(testPlante);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedPlantes);

        // Act
        List<Plante> result = planteRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(expectedPlantes.size(), result.size());
        assertEquals(testPlante.getnom(), result.get(0).getnom());
        verify(jdbcTemplate).query(eq("SELECT * FROM Plante"), any(RowMapper.class));
    }

    @Test
    void findById_WhenPlanteExists_ShouldReturnPlante() {
        // Arrange
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList(testPlante));

        // Act
        Optional<Plante> result = planteRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testPlante.getnom(), result.get().getnom());
        verify(jdbcTemplate).query(eq("SELECT * FROM Plante WHERE id_plante = ?"), any(RowMapper.class), eq(1L));
    }

    @Test
    void findById_WhenPlanteDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList());

        // Act
        Optional<Plante> result = planteRepository.findById(1L);

        // Assert
        assertTrue(result.isEmpty());
        verify(jdbcTemplate).query(eq("SELECT * FROM Plante WHERE id_plante = ?"), any(RowMapper.class), eq(1L));
    }

    @Test
    void create_ShouldSetGeneratedId() {
        // Arrange
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        when(jdbcTemplate.update(any(), any(GeneratedKeyHolder.class))).thenAnswer(invocation -> {
            keyHolder.getKeyList().add(java.util.Collections.singletonMap("id_plante", 1L));
            return 1;
        });

        // Act
        planteRepository.create(testPlante);

        // Assert
        assertEquals(1L, testPlante.getIdPlante());
        verify(jdbcTemplate).update(any(), any(GeneratedKeyHolder.class));
    }

    @Test
    void update_ShouldUpdatePlante() {
        // Act
        planteRepository.update(testPlante);

        // Assert
        verify(jdbcTemplate).update(
            eq("UPDATE Plante SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, cout = ?, soleil_par_seconde = ?, effet = ?, chemin_image = ? WHERE id_plante = ?"),
            eq(testPlante.getnom()),
            eq(testPlante.getPointDeVie()),
            eq(testPlante.getAttaqueParSeconde()),
            eq(testPlante.getDegatAttaque()),
            eq(testPlante.getCout()),
            eq(testPlante.getSoleilParSeconde()),
            eq("normal"),
            eq(testPlante.getCheminImage()),
            eq(testPlante.getIdPlante())
        );
    }

    @Test
    void delete_ShouldDeletePlante() {
        // Act
        planteRepository.delete(1L);

        // Assert
        verify(jdbcTemplate).update(eq("DELETE FROM Plante WHERE id_plante = ?"), eq(1L));
    }
} 