package com.techelevator.dao;

import com.techelevator.model.Category;
import com.techelevator.model.Image;
import com.techelevator.model.Pothole;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPotholeDao implements PotholeDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPotholeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Pothole> getPotholes() {
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT * FROM potholes;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            potholes.add(mapRowToPothole(results));
        }
        return potholes;
    }

    @Override
    public List<Pothole> getPotholesByUser(User user) {
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT * FROM potholes p " +
                "JOIN user_accounts ua ON p.account_id = ua.account_id " +
                "JOIN users u ON u.user_id = ua.user_id " +
                "WHERE u.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
        while (results.next()) {
            potholes.add(mapRowToPothole(results));
        }
        return potholes;
    }

    @Override
    public Pothole getPotholeById(int potholeId) {
        Pothole pothole = null;
        String sql = "SELECT * FROM potholes WHERE pothole_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, potholeId);
        if (results.next()) {
            pothole = mapRowToPothole(results);
        }
        return pothole;
    }

    @Override
    public Pothole getPotholeByName(String potholeName) {
        Pothole pothole = null;
        String sql = "SELECT * FROM potholes WHERE pothole_name = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, potholeName);
        if (results.next()) {
            pothole = mapRowToPothole(results);
        }
        return pothole;
    }

    @Override
    public List<Pothole> getPotholesByCategory(Category category) {
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT * FROM potholes p WHERE category_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, category.getCategoryId());
        while (results.next()) {
            potholes.add(mapRowToPothole(results));
        }
        return potholes;
    }

    @Override
    public Pothole getPotholeByLocation(String location) {
        Pothole pothole = null;
        String sql = "SELECT * FROM potholes WHERE pothole_location = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, location);
        if (results.next()) {
            pothole = mapRowToPothole(results);
        }
        return pothole;
    }

    @Override
    public Pothole createPothole(Pothole pothole, Image image) {
        String sql = "INSERT INTO images(image_name, image_type, image_data) " +
                "VALUES(?, ?, ?) RETURNING image_id;";

        Integer imageId = jdbcTemplate.queryForObject(sql, Integer.class, image.getImageName(), image.getImageType(),
                image.getImageData());

        String sqlTwo = "INSERT INTO potholes(pothole_location, pothole_name, account_id, " +
                "image_id, category_id, active) " +
                "VALUES(?, ?, ?, ?, ?, ?) RETURNING pothole_id;";

        Integer potholeId = jdbcTemplate.queryForObject(sqlTwo, Integer.class, pothole.getPotholeLocation(),
                pothole.getPotholeName(), pothole.getAccountId(), imageId, pothole.getCategoryId(),
                pothole.getActive());

        return getPotholeById(potholeId);
    }

    @Override
    public Pothole updatePothole(Pothole pothole) {
        String sql = "UPDATE potholes " +
                "SET pothole_location = ?, pothole_name = ?, account_id = ?, image_id = ?, category_id = ?, active = ?" +
                "WHERE pothole_id = ?;";

        jdbcTemplate.update(sql, pothole.getPotholeLocation(), pothole.getPotholeName(), pothole.getAccountId(),
                pothole.getImageId(), pothole.getCategoryId(), pothole.getActive(), pothole.getPotholeId());

        return getPotholeById(pothole.getPotholeId());
    }

    @Override
    public void deletePothole(int potholeId) {
        String sql = "DELETE FROM potholes WHERE pothole_id = ?;";
        jdbcTemplate.update(sql);
    }

    private Pothole mapRowToPothole(SqlRowSet sqlRowSet) {
        Pothole pothole = new Pothole();

        pothole.setPotholeId(sqlRowSet.getInt("pothole_id"));
        pothole.setPotholeLocation(sqlRowSet.getString("pothole_location"));
        pothole.setPotholeName(sqlRowSet.getString("pothole_name"));
        pothole.setAccountId(sqlRowSet.getInt("account_id"));
        pothole.setImageId(sqlRowSet.getInt("image_id"));
        pothole.setCategoryId(sqlRowSet.getInt("category_id"));
        pothole.setActive(sqlRowSet.getBoolean("active"));
        // TODO: CHECK ON THIS METHOD
        pothole.setUploadTime(Timestamp.valueOf(sqlRowSet.getString("upload_time")));

        return pothole;
    }

}
