package com.atob.shanna.controller;

import com.atob.shanna.dto.KnowledgeDto;
import com.atob.shanna.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    @Autowired
    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @GetMapping("/getAll")
    public List<KnowledgeDto> getAll(){
        return knowledgeService.getAll();
    }

    @PostMapping("/post")
    public List<KnowledgeDto> create(String text){
        return knowledgeService.save(text);
    }
}
