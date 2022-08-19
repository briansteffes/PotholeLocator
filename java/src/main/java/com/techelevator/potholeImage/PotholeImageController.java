package com.techelevator.potholeImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("pothole-image")
public class PotholeImageController {

    private final PotholeImageService potholeImageService;

    @Autowired
    public PotholeImageController(PotholeImageService potholeImageService) {
        this.potholeImageService = potholeImageService;
    }

    @GetMapping
    public List<PotholeImage> getPothole() {
        return potholeImageService.getPotholeImages();
    }

    @PostMapping(
            path = "{potholeId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadPotholeImage(@PathVariable("potholeId") Long potholeId,
                                   @RequestParam("file")MultipartFile file) {
        potholeImageService.uploadPotholeImage(potholeId, file);
    }

    @GetMapping(path = "{potholeId}/image/download")
    public byte[] downloadPotholeImage(@PathVariable("potholeId") Long potholeId) {
        return potholeImageService.downloadPotholeImage(potholeId);
    }
}
