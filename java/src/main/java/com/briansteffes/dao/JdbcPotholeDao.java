package com.briansteffes.dao;

import com.briansteffes.model.Category;
import com.briansteffes.model.Pothole;
import com.briansteffes.model.PotholeDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

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
        String sql = "SELECT lat, long, pothole_name, account_id, image_id, category_id, status_id, active, " +
                "upload_time FROM potholes;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            potholes.add(mapRowToPothole(results));
        }
        return potholes;
    }

    @Override
    public List<PotholeDTO> getPotholeDTOs() {
        List<PotholeDTO> potholeDTOs = new ArrayList<>();
        String sql = "SELECT p.pothole_id, p.lat, p.long, p.pothole_name, u.username, i.image_data, c.category_desc," +
                "s.status_desc, p.active, p.upload_time FROM potholes p " +
                "JOIN user_accounts a ON p.account_id = a.account_id " +
                "JOIN users u ON a.user_id = u.user_id " +
                "JOIN images i ON p.image_id = i.image_id " +
                "JOIN statuses s ON p.status_id = s.status_id " +
                "JOIN categories c ON p.category_id = c.category_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            potholeDTOs.add(mapRowToPotholeDTO(results));
        }
        return potholeDTOs;
    }

//    @Override
//    public List<Pothole> getPotholesPublic() {
//        List<Pothole> potholes = new ArrayList<>();
//        String sql =
//            "SELECT pothole_id, lat, long, pothole_name, image_id, category_id FROM potholes;";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
//        while (results.next()) {
//            potholes.add(mapRowToPotholePublic(results));
//        }
//        return potholes;
//    }

    @Override
    public List<Pothole> getPotholesByUsername(String username) {
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT * FROM potholes p " +
            "JOIN user_accounts ua ON p.account_id = ua.account_id " +
            "JOIN users u ON u.user_id = ua.user_id " +
            "WHERE u.username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
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
    public void createPothole(Pothole pothole) {
        // TODO: Implement this
        /*
        String sql = "INSERT INTO images(image_name, image_type, image_data) " +
                "VALUES(?, ?, ?) RETURNING image_id;";

        Integer imageId = jdbcTemplate.queryForObject(sql, Integer.class, image.getImageName(), image.getImageType(),
                image.getImageData());
         */

//        String sqlTwo = "INSERT INTO potholes(lat, long, pothole_name, account_id, " +
//            "image_id, category_id, status_id, active) " +
//            "VALUES(?, ?, ?, ?, ?, ?, ?, ?) RETURNING pothole_id;";
//
//        Integer potholeId =
//            jdbcTemplate.queryForObject(sqlTwo, Integer.class, pothole.getPotholeLat(),
//                pothole.getPotholeLong(), pothole.getPotholeName(), pothole.getAccountId(), pothole.getImageId(), pothole.getCategoryId(),
//                pothole.getStatusId(), pothole.getActive());

        String sqlTwo = "INSERT INTO potholes(lat, long, pothole_name, account_id, category_id) " +
                "VALUES(?, ?, ?, ?, ?) RETURNING pothole_id;";

        Integer potholeId =
                jdbcTemplate.queryForObject(sqlTwo, Integer.class, pothole.getPotholeLat(),
                        pothole.getPotholeLong(), pothole.getPotholeName(), pothole.getAccountId(),
                        pothole.getCategoryId());

        System.out.println(potholeId);
    }

    // Fix this method
    @Override
    public void updatePothole(PotholeDTO pothole) {

        Pothole potholeToUpdate = new Pothole();
        potholeToUpdate.setPotholeId(pothole.getPotholeId());
        potholeToUpdate.setPotholeLat(pothole.getPotholeLat());
        potholeToUpdate.setPotholeLong(pothole.getPotholeLong());
        potholeToUpdate.setPotholeName(pothole.getPotholeName());
        potholeToUpdate.setActive(pothole.getActive());

        if (pothole.getStatus().equals("Reported")) {
            potholeToUpdate.setStatusId(1);
        } else if (pothole.getStatus().equals("Inspected")) {
            potholeToUpdate.setStatusId(2);
        } else if (pothole.getStatus().equals("Repaired")) {
            potholeToUpdate.setStatusId(3);
        } else {
            potholeToUpdate.setStatusId(1);
        }

        if (pothole.getCategory().equals("Major")) {
            potholeToUpdate.setCategoryId(1);
        } else if (pothole.getCategory().equals("Minor")) {
            potholeToUpdate.setCategoryId(2);
        } else {
            potholeToUpdate.setCategoryId(1);
        }

        String sql = "UPDATE potholes " +
            "SET lat = ?, long = ?, pothole_name = ?, status_id = ?, category_id = ?, " +
                "active = ? WHERE pothole_id = ?;";

        jdbcTemplate.update(sql, potholeToUpdate.getPotholeLat(), potholeToUpdate.getPotholeLong(),
                potholeToUpdate.getPotholeName(), potholeToUpdate.getStatusId(),
                potholeToUpdate.getCategoryId(), pothole.getActive(), potholeToUpdate.getPotholeId());
    }

//    @Override
//    public void updatePothole(Pothole pothole) {
//        String sql = "UPDATE potholes " +
//                "SET lat = ?, long = ?, pothole_name = ?, account_id = ?, image_id = ?, " +
//                "category_id = ?, status_id = ?, active = ? " +
//                "WHERE pothole_id = ?;";
//
//        jdbcTemplate.update(sql, pothole.getPotholeLat(), pothole.getPotholeLong(), pothole.getPotholeName(), pothole.getAccountId(),
//                pothole.getImageId(), pothole.getCategoryId(), pothole.getStatusId(),
//                pothole.getActive(),
//                pothole.getPotholeId());
//    }

    @Override
    public Pothole markForDelete(Pothole pothole) {
        String sql = "UPDATE potholes " +
            "SET lat = ?, long = ?, pothole_name = ?, account_id = ?, image_id = ?, " +
            "category_id = ?, status_id = ?, active = ? " +
            "WHERE pothole_id = ?;";

        jdbcTemplate.update(sql, pothole.getPotholeLat(), pothole.getPotholeLong(), pothole.getPotholeName(), pothole.getAccountId(),
            pothole.getImageId(), pothole.getCategoryId(), pothole.getStatusId(),
            pothole.getActive(),
            pothole.getPotholeId());

        return getPotholeById(pothole.getPotholeId());
    }

    @Override
    public void deletePothole(int potholeId) {
        String sql = "DELETE FROM potholes WHERE pothole_id = ?;";
        jdbcTemplate.update(sql, potholeId);
    }

    private Pothole mapRowToPothole(SqlRowSet sqlRowSet) {
        Pothole pothole = new Pothole();

        pothole.setPotholeId(sqlRowSet.getInt("pothole_id"));
        pothole.setPotholeLat(sqlRowSet.getBigDecimal("lat"));
        pothole.setPotholeLong(sqlRowSet.getBigDecimal("long"));
        pothole.setPotholeName(sqlRowSet.getString("pothole_name"));
        pothole.setAccountId(sqlRowSet.getInt("account_id"));
        pothole.setImageId(sqlRowSet.getInt("image_id"));
        pothole.setCategoryId(sqlRowSet.getInt("category_id"));
        pothole.setStatusId(sqlRowSet.getInt("status_id"));
        pothole.setActive(sqlRowSet.getBoolean("active"));
        pothole.setUploadTime(sqlRowSet.getTimestamp("upload_time"));
//        pothole.setUploadTime(Timestamp.valueOf(sqlRowSet.getString("upload_time")));

        return pothole;
    }

    private PotholeDTO mapRowToPotholeDTO(SqlRowSet sqlRowSet) {
        PotholeDTO potholeDTO = new PotholeDTO();

        potholeDTO.setPotholeId(sqlRowSet.getInt("pothole_id"));
        potholeDTO.setPotholeLat(sqlRowSet.getBigDecimal("lat"));
        potholeDTO.setPotholeLong(sqlRowSet.getBigDecimal("long"));
        potholeDTO.setPotholeName(sqlRowSet.getString("pothole_name"));
        potholeDTO.setUsername(sqlRowSet.getString("username"));
        potholeDTO.setImageData(SerializationUtils.serialize(sqlRowSet.getObject("image_data")));
        potholeDTO.setCategory(sqlRowSet.getString("category_desc"));
        potholeDTO.setStatus(sqlRowSet.getString("status_desc"));
        potholeDTO.setActive(sqlRowSet.getBoolean("active"));
        potholeDTO.setUploadTime(sqlRowSet.getTimestamp("upload_time"));

        return potholeDTO;
    }

//    private Pothole mapRowToPotholePublic(SqlRowSet sqlRowSet) {
//        Pothole pothole = new Pothole();
//
//        pothole.setPotholeId(sqlRowSet.getInt("pothole_id"));
//        pothole.setPotholeLat(sqlRowSet.getBigDecimal("lat"));
//        pothole.setPotholeLong(sqlRowSet.getBigDecimal("long"));
//        pothole.setPotholeName(sqlRowSet.getString("pothole_name"));
//        pothole.setImageId(sqlRowSet.getInt("image_id"));
//        pothole.setCategoryId(sqlRowSet.getInt("category_id"));
//
//        return pothole;
//    }

}
