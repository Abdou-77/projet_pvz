package com.epf.repository;

import com.epf.model.Zombie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ZombieRepositoryImpl implements ZombieRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_Zombie = """
        INSERT INTO Zombie (nom, point_de_vie, attaque_par_seconde, 
        degat_attaque, vitesse_de_deplacement, chemin_image, id_map)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    private static final String UPDATE_Zombie = """
        UPDATE Zombie SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?,
        degat_attaque = ?, vitesse_de_deplacement = ?, chemin_image = ?, id_map = ?
        WHERE id_Zombie = ?
        """;

    private static final String DELETE_Zombie = "DELETE FROM Zombie WHERE id_Zombie = ?";
    private static final String FIND_ALL = "SELECT * FROM Zombie";
    private static final String FIND_BY_ID = "SELECT * FROM Zombie WHERE id_Zombie = ?";
    private static final String FIND_BY_MAP_ID = "SELECT * FROM Zombie WHERE id_map = ?";

    public ZombieRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Zombie> findAll() {
        return jdbcTemplate.query(FIND_ALL, this::mapRow);
    }

    @Override
    public Optional<Zombie> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID, this::mapRow, id)
                .stream()
                .findFirst();
    }

    @Override
    public List<Zombie> findByMapId(Long mapId) {
        return jdbcTemplate.query(FIND_BY_MAP_ID, this::mapRow, mapId);
    }

    @Override
    public Long create(Zombie Zombie) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_Zombie, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Zombie.getNom());
            ps.setInt(2, Zombie.getPointDeVie());
            ps.setDouble(3, Zombie.getAttaqueParSeconde());
            ps.setInt(4, Zombie.getDegatAttaque());
            ps.setDouble(5, Zombie.getVitesseDeDeplacement());
            ps.setString(6, Zombie.getCheminImage());
            ps.setLong(7, Zombie.getIdMap());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Zombie Zombie) {
        jdbcTemplate.update(UPDATE_Zombie,
                Zombie.getNom(),
                Zombie.getPointDeVie(),
                Zombie.getAttaqueParSeconde(),
                Zombie.getDegatAttaque(),
                Zombie.getVitesseDeDeplacement(),
                Zombie.getCheminImage(),
                Zombie.getIdMap(),
                Zombie.getIdZombie()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_Zombie, id);
    }

    private Zombie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Zombie Zombie = new Zombie();
        Zombie.setIdZombie(rs.getLong("id_Zombie"));
        Zombie.setNom(rs.getString("nom"));
        Zombie.setPointDeVie(rs.getInt("point_de_vie"));
        Zombie.setAttaqueParSeconde(rs.getDouble("attaque_par_seconde"));
        Zombie.setDegatAttaque(rs.getInt("degat_attaque"));
        Zombie.setVitesseDeDeplacement(rs.getDouble("vitesse_de_deplacement"));
        Zombie.setCheminImage(rs.getString("chemin_image"));
        Zombie.setIdMap(rs.getLong("id_map"));
        return Zombie;
    }
}