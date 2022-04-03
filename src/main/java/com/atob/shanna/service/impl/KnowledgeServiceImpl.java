package com.atob.shanna.service.impl;

import com.atob.shanna.dto.KnowledgeResponseDto;
import com.atob.shanna.entity.Knowledge;
import com.atob.shanna.exception.ResourceNotFoundException;
import com.atob.shanna.repository.KnowledgeRepository;
import com.atob.shanna.service.DetectionService;
import com.atob.shanna.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class KnowledgeServiceImpl implements KnowledgeService {

    private final KnowledgeRepository knowledgeRepository;
    private final DetectionService detectionService;

    @Autowired
    public KnowledgeServiceImpl(KnowledgeRepository knowledgeRepository, DetectionService detectionService) {
        this.knowledgeRepository = knowledgeRepository;
        this.detectionService = detectionService;
    }

    @Override
    public KnowledgeResponseDto parse(String text) {
        List<String> dots = Arrays.asList(text.split(":"));

        String lDots = dots.get(0);
        String rDots = dots.get(1);

        String iBrackets = lDots
                .replace("[", "")
                .replace("]", "");

        List<KnowledgeResponseDto> referencedBy = new ArrayList<>();
        Matcher m = Pattern.compile("\\{(.*?)\\}").matcher(rDots);
        while (m.find()) {
            System.out.println(m.group(1));

                Knowledge kn = new Knowledge();
                kn.setName(m.group(1));
                kn.setDescription("");
                kn.setPointedBy(new ArrayList<>());

                referencedBy.add(entityToDto(kn));
        }
        String body = rDots.trim();
        String name = iBrackets.trim();
        return new KnowledgeResponseDto(name, body, referencedBy);
    }

    @Override
    public Knowledge getById(Long id) {
        return knowledgeRepository.getById(id);
    }

    @Transactional
    @Override
    public List<KnowledgeResponseDto> save(String text) {
        KnowledgeResponseDto kDto = this.parse(text);
        List<Knowledge> parents = new ArrayList<>();
        for (KnowledgeResponseDto parent : kDto.pointedBy) {
            if (!knowledgeRepository.existsByName(parent.name)) {
                Knowledge p = knowledgeRepository.save(dtoToEntity(parent));
                parents.add(p);
            } else {
                Knowledge p = knowledgeRepository.findByName(parent.name).get();
                parents.add(p);
            }
        }
        Knowledge toSave;
        if(knowledgeRepository.existsByName(kDto.getName())) {
            toSave = knowledgeRepository.findByName(kDto.getName()).get();
            toSave.setDescription(text);

        } else {
            toSave = dtoToEntity(kDto);
        }
        toSave.pointedBy = parents;
        knowledgeRepository.save(toSave);

        return fetchAll();
    }

    @Override
    public List<KnowledgeResponseDto> getAll() {
        return fetchAll();
    }

    @Override
    @Transactional
    public List<KnowledgeResponseDto> updateDescription(Long id, String newDescription) {
        Knowledge knowledge = knowledgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found."));
        knowledge.setDescription(newDescription);
        knowledgeRepository.save(knowledge);
        return fetchAll();
    }

    @Transactional
    @Override
    public List<KnowledgeResponseDto> bulkSave(MultipartFile file) throws IOException {
        List<String> text = detectionService.detect(file.getInputStream());

        for(String def: text){
            if(!def.equals("")){
                this.save(def);
            }
        }
        return this.fetchAll();
    }

    private List<KnowledgeResponseDto> fetchAll() {
        return knowledgeRepository.findAll().stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    private KnowledgeResponseDto entityToDto(Knowledge knowledge){
            KnowledgeResponseDto dto = new KnowledgeResponseDto();
            dto.setId(knowledge.getId());
            dto.setName(knowledge.getName());
            dto.setDescription(knowledge.getDescription());
            List<KnowledgeResponseDto> dtoPointedBy = new ArrayList<>();
            if(knowledge.getPointedBy() != null){
                knowledge.getPointedBy().forEach(k ->
                        dtoPointedBy.add(new KnowledgeResponseDto(k.getId(),
                                k.getName(), k.getDescription(), new LinkedList<>())));
            }
            dto.setPointedBy(dtoPointedBy);
            return dto;
    }

    private Knowledge dtoToEntity(KnowledgeResponseDto dto){
        Knowledge knowledge = new Knowledge();
        knowledge.setName(dto.getName());
        knowledge.setDescription(dto.getDescription());
        dto.getPointedBy().forEach(k ->
                knowledge.getPointedBy().add(new Knowledge(k.getName(), k.getDescription())));
        return knowledge;
    }
}
