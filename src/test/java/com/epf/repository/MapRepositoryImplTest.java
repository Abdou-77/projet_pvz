package com.epf.repository;

import com.epf.model.Map;
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
class MapRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MapRepositoryImpl mapRepository;

    private Map testMap;

    @BeforeEach
    void setUp() {
        testMap = new Map(1L, 5, 9, "/images/test.png");
    }

    @Test
    void findAll_ShouldReturnAllMaps() {
        // Arrange
        List<Map> expectedMaps = Arrays.asList(testMap);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedMaps);

        // Act
        List<Map> result = mapRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(expectedMaps.size(), result.size());
        assertEquals(testMap.getLigne(), result.get(0).getLigne());
        assertEquals(testMap.getColonne(), result.get(0).getColonne());
        verify(jdbcTemplate).query(eq("SELECT * FROM Map"), any(RowMapper.class));
    }

    @Test
    void findById_WhenMapExists_ShouldReturnMap() {
        // Arrange
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList(testMap));

        // Act
        Optional<Map> result = mapRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testMap.getLigne(), result.get().getLigne());
        assertEquals(testMap.getColonne(), result.get().getColonne());
        verify(jdbcTemplate).query(eq("SELECT * FROM Map WHERE id_map = ?"), any(RowMapper.class), eq(1L));
    }

    @Test
    void findById_WhenMapDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList());

        // Act
        Optional<Map> result = mapRepository.findById(1L);

        // Assert
        assertTrue(result.isEmpty());
        verify(jdbcTemplate).query(eq("SELECT * FROM Map WHERE id_map = ?"), any(RowMapper.class), eq(1L));
    }

    @Test
    void create_ShouldReturnGeneratedId() {
        // Arrange
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        when(jdbcTemplate.update(any(), any(GeneratedKeyHolder.class))).thenAnswer(invocation -> {
            keyHolder.getKeyList().add(java.util.Collections.singletonMap("id_map", 1L));
            return 1;
        });

        // Act
        Long id = mapRepository.create(testMap);

        // Assert
        assertEquals(1L, id);
        verify(jdbcTemplate).update(any(), any(GeneratedKeyHolder.class));
    }

    @Test
    void update_ShouldUpdateMap() {
        // Act
        mapRepository.update(testMap);

        // Assert
        verify(jdbcTemplate).update(
            eq("UPDATE Map SET ligne = ?, colonne = ?, chemin_image = ? WHERE id_map = ?"),
            eq(testMap.getLigne()),
            eq(testMap.getColonne()),
            eq(testMap.getCheminImage()),
            eq(testMap.getIdMap())
        );
    }

    @Test
    void delete_ShouldDeleteMap() {
        // Act
        mapRepository.delete(1L);

        // Assert
        verify(jdbcTemplate).update(eq("DELETE FROM Map WHERE id_map = ?"), eq(1L));
    }

    @Test
    void existsById_WhenMapExists_ShouldReturnTrue() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyLong()))
                .thenReturn(1);

        // Act
        boolean result = mapRepository.existsById(1L);

        // Assert
        assertTrue(result);
        verify(jdbcTemplate).queryForObject(eq("SELECT COUNT(*) FROM Map WHERE id_map = ?"), eq(Integer.class), eq(1L));
    }

    @Test
    void existsById_WhenMapDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyLong()))
                .thenReturn(0);

        // Act
        boolean result = mapRepository.existsById(1L);

        // Assert
        assertFalse(result);
        verify(jdbcTemplate).queryForObject(eq("SELECT COUNT(*) FROM Map WHERE id_map = ?"), eq(Integer.class), eq(1L));
    }
} 