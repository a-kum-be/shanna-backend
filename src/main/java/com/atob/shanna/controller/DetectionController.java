package com.atob.shanna.controller;

import com.atob.shanna.service.DetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("api/detect")
@CrossOrigin(origins = "*")
public class DetectionController {

    private final DetectionService detectionService;

    @Autowired
    public DetectionController(DetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @PostMapping("/post")
    public String detect(@RequestParam("resource") MultipartFile image) throws IOException {
        InputStream is = image.getInputStream();

        return detectionService.detect(is);
    }
}
