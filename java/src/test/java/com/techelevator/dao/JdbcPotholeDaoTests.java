package com.techelevator.dao;

import com.techelevator.model.Image;
import com.techelevator.model.Pothole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcPotholeDaoTests extends BaseDaoTests {

    private JdbcPotholeDao sut;
    private static final Pothole POTHOLE_1 = new Pothole(1, "33.6609 °N, 95.5555° W", "Test 1", 1, 1, 1, 1, true, Timestamp.valueOf("2022-08-09 05:13:20"));
    private static final Pothole POTHOLE_2 = new Pothole(2, "34.6609 °N, 96.5555° W", "Test 2", 2, 2, 1, 1, true, Timestamp.valueOf("2022-08-09 05:13:21"));
    private static final Pothole POTHOLE_3 = new Pothole(3, "35.6609 °N, 97.5555° W", "Test 3", 3, 3, 2, 1, true, Timestamp.valueOf("2022-08-09 05:13:22"));
    private static final Pothole POTHOLE_4 = new Pothole(4, "36.6609 °N, 98.5555° W", "Test 4", 3, 4, 2, 1, false, Timestamp.valueOf("2022-08-09 05:13:23"));

    private Pothole testPothole;
    private Image testImage;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcPotholeDao(jdbcTemplate);
        testPothole = new Pothole(5, "37.6609 °N, 99.5555° W", "Test 5", 2, 5, 2, 1, false, Timestamp.valueOf(LocalDateTime.now()));
        testImage = new Image(5, "testImage5", ".jpg", new byte[10]);
    }

    @Test
    public void getPotholes_returns_list_of_all_potholes() {
        List<Pothole> potholesExpected = new ArrayList<>();
        potholesExpected.add(POTHOLE_1);
        potholesExpected.add(POTHOLE_2);
        potholesExpected.add(POTHOLE_3);
        potholesExpected.add(POTHOLE_4);

        List<Pothole> potholesActual = sut.getPotholes();
        Assert.assertEquals(potholesExpected.size(), potholesActual.size());
        assertPotholesMatch(POTHOLE_1, potholesActual.get(0));
        assertPotholesMatch(POTHOLE_4, potholesActual.get(3));
    }

    @Test
    public void getPotholeById_returns_correct_pothole() {
        Pothole pothole = sut.getPotholeById(1);
        assertPotholesMatch(POTHOLE_1, pothole);
    }

    @Test
    public void getPothole_returns_null_for_invalid_id() {
        Pothole pothole = sut.getPotholeById(13);
        Assert.assertNull(pothole);
    }

    @Test
    public void createPothole_returns_pothole_with_id_and_expected_values() {
        Pothole actualPothole = sut.createPothole(testPothole, testImage);
        assertPotholesMatch(testPothole, actualPothole);
    }

    private void assertPotholesMatch(Pothole expected, Pothole actual) {
        Assert.assertEquals(expected.getPotholeId(), actual.getPotholeId());
        Assert.assertEquals(expected.getPotholeLocation(), actual.getPotholeLocation());
        Assert.assertEquals(expected.getPotholeName(), actual.getPotholeName());
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getImageId(), actual.getImageId());
        Assert.assertEquals(expected.getCategoryId(), actual.getCategoryId());
        Assert.assertEquals(expected.getActive(), actual.getActive());
        Assert.assertEquals(expected.getStatusId(), actual.getStatusId());
        Assert.assertTrue(expected.getUploadTime().toLocalDateTime().compareTo(actual.getUploadTime().toLocalDateTime()) < 1);
    }
}
