package com.techelevator.potholeImage;

import com.amazonaws.services.s3.model.Bucket;
import com.techelevator.bucket.BucketName;
import com.techelevator.fileStore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class PotholeImageService implements PotholeImageDao {

    private final JdbcTemplate jdbcTemplate;
    private final FileStore fileStore;

    @Autowired
    public PotholeImageService(JdbcTemplate jdbcTemplate, FileStore fileStore) {
        this.jdbcTemplate = jdbcTemplate;
        this.fileStore = fileStore;
    }

    public List<PotholeImage> getPotholeImages() {
        List <PotholeImage> potholeImages = new ArrayList<>();
        String sql = "select * from pothole_images";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            PotholeImage potholeImage = mapRowToPotholeImage(results);
            potholeImages.add(potholeImage);
        }
        return potholeImages;
    }

    public void updatePotholeImage(PotholeImage potholeImage, Long potholeImageId) {
        String sql = "update pothole_images " +
                        "set pothole_id = ?, image_link = ? where pothole_image_id = ?;";
        jdbcTemplate.update(sql,
                potholeImage.getPothole_id(),
                potholeImage.getPotholeImageLink(),
                potholeImageId);
    }



    public void uploadPotholeImage(Long potholeId, MultipartFile file) {
        // 1. Check if image is not empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }

        // 2. If file is an image
        if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image (jpeg, png, gif) [ " + file.getContentType() + " ]");
        }

        // 3. Is the pothole present in the images table?
        PotholeImage potholeImage = getPotholeOrThrow(potholeId);

        // 4. Grab some metadata from file if any
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        // 5. Store the image in s3
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), potholeImage.getPothole_id());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            potholeImage.setPotholeImageLink(filename);
            // update the database
            updatePotholeImage(potholeImage, potholeImage.getId());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private PotholeImage mapRowToPotholeImage(SqlRowSet rs) {
        PotholeImage potholeImage = new PotholeImage();
        potholeImage.setId(rs.getLong("pothole_image_id"));
        potholeImage.setPothole_id(rs.getLong("pothole_id"));
        potholeImage.setPotholeImageLink(rs.getString("image_link"));
        return potholeImage;
    }

    private PotholeImage getPotholeOrThrow(Long potholeId) {
        return getPotholeImages()
                .stream()
                .filter(potholeImage -> potholeImage.getPothole_id().equals(potholeId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Pothole %s not found", potholeId)));
    }
}
