package com.epf.repository;

import com.epf.model.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class MapRepositoryImpl implements MapRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL = "SELECT * FROM Map";
    private static final String FIND_BY_ID = "SELECT * FROM Map WHERE id_map = ?";
    private static final String CREATE = "INSERT INTO Map (ligne, colonne, chemin_image) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE Map SET ligne = ?, colonne = ?, chemin_image = ? WHERE id_map = ?";
    private static final String DELETE = "DELETE FROM Map WHERE id_map = ?";

    public MapRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map> findAll() {
        return jdbcTemplate.query(FIND_ALL, (rs, rowNum) ->
                new Map(
                        rs.getLong("id_map"),
                        rs.getInt("ligne"),
                        rs.getInt("colonne"),
                        rs.getString("chemin_image")
                ));
    }

    @Override
    public Optional<Map> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID,
                (rs, rowNum) -> new Map(
                        rs.getLong("id_map"),
                        rs.getInt("ligne"),
                        rs.getInt("colonne"),
                        rs.getString("chemin_image")
                ), id).stream().findFirst();
    }

    @Override
    public Long create(Map map) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, map.getLigne());
            ps.setInt(2, map.getColonne());
            ps.setString(3, map.getCheminImage());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Map map) {
        jdbcTemplate.update(UPDATE,
                map.getLigne(),
                map.getColonne(),
                map.getCheminImage(),
                map.getIdMap());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM Map WHERE id_map = ?";

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count != null && count > 0;
    }
}