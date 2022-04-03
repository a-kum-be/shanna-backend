package com.atob.shanna.controller;

import com.atob.shanna.dto.KnowledgeResponseDto;
import com.atob.shanna.service.DetectionService;
import com.atob.shanna.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/detect")
@CrossOrigin(origins = "*")
public class DetectionController {

    private final KnowledgeService knowledgeService;

    @Autowired
    public DetectionController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @PostMapping("/post")
    public List<KnowledgeResponseDto> detect(@RequestParam("resource") MultipartFile image) throws IOException {
        return knowledgeService.bulkSave(image);
    }
}
