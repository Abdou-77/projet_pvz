package com.epf.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epf.model.Plante;
import com.epf.model.Plante.Effet;

@Repository
public class PlanteRepositoryImpl implements PlanteRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT * FROM Plante";
    private static final String SELECT_BY_ID = "SELECT * FROM Plante WHERE id_plante = ?";
    private static final String INSERT = """
        INSERT INTO Plante (nom, point_de_vie, attaque_par_seconde, 
        degat_attaque, cout, soleil_par_seconde, effet, chemin_image)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
    private static final String UPDATE = """
        UPDATE Plante SET nom = ?, point_de_vie = ?, 
        attaque_par_seconde = ?, degat_attaque = ?, cout = ?, 
        soleil_par_seconde = ?, effet = ?, chemin_image = ?
        WHERE id_plante = ?
        """;
    private static final String DELETE = "DELETE FROM Plante WHERE id_plante = ?";

    public PlanteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Plante> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRow);
    }

    @Override
    public Optional<Plante> findById(Long id) {
        return jdbcTemplate.query(SELECT_BY_ID, this::mapRow, id)
                .stream()
                .findFirst();
    }

    @Override
    public void create(Plante plante) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, plante.getnom());
            ps.setInt(2, plante.getPointDeVie());
            ps.setDouble(3, plante.getAttaqueParSeconde());
            ps.setInt(4, plante.getDegatAttaque());
            ps.setInt(5, plante.getCout());
            ps.setDouble(6, plante.getSoleilParSeconde());
            ps.setString(7, convertEffetToDBFormat(plante.getEffet()));
            ps.setString(8, plante.getCheminImage());
            return ps;
        }, keyHolder);

        plante.setIdPlante(keyHolder.getKey().longValue());
    }

    @Override
    public void update(Plante plante) {
        jdbcTemplate.update(UPDATE,
                plante.getnom(),
                plante.getPointDeVie(),
                plante.getAttaqueParSeconde(),
                plante.getDegatAttaque(),
                plante.getCout(),
                plante.getSoleilParSeconde(),
                convertEffetToDBFormat(plante.getEffet()),
                plante.getCheminImage(),
                plante.getIdPlante()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    private Plante mapRow(ResultSet rs, int rowNum) throws SQLException {
        Plante plante = new Plante();
        plante.setIdPlante(rs.getLong("id_plante"));
        plante.setnom(rs.getString("nom"));
        plante.setPointDeVie(rs.getInt("point_de_vie"));
        plante.setAttaqueParSeconde(rs.getDouble("attaque_par_seconde"));
        plante.setDegatAttaque(rs.getInt("degat_attaque"));
        plante.setCout(rs.getInt("cout"));
        plante.setSoleilParSeconde(rs.getDouble("soleil_par_seconde"));
        plante.setEffet(convertStringToEffet(rs.getString("effet")));
        plante.setCheminImage(rs.getString("chemin_image"));
        return plante;
    }

    private Effet convertStringToEffet(String effet) {
        return Effet.valueOf(effet.toUpperCase().replace(" ", "_"));
    }
    private String convertEffetToDBFormat(Effet effet) {
        if (effet == null) {
            return "NORMAL";
        }
        return effet.name().toLowerCase().replace("_", " ");
    }
    }