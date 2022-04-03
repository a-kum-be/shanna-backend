package com.atob.shanna.controller;

import com.atob.shanna.dto.KnowledgeResponseDto;
import com.atob.shanna.dto.KnowledgeRequestDto;
import com.atob.shanna.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
@CrossOrigin(origins = "*")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    @Autowired
    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @GetMapping("/getAll")
    public List<KnowledgeResponseDto> getAll(){
        return knowledgeService.getAll();
    }

    @PostMapping("/post")
    public List<KnowledgeResponseDto> create(@Valid @RequestBody KnowledgeRequestDto dto){
        return knowledgeService.save(dto.getText());
    }

    @PatchMapping("/{id}/updateDescription")
    public List<KnowledgeResponseDto> updateDescription(@PathVariable Long id, @RequestBody String description){
        return knowledgeService.updateDescription(id, description);
    }
}
