package com.atob.shanna.service.impl;

import com.atob.shanna.dto.KnowledgeDto;
import com.atob.shanna.entity.Knowledge;
import com.atob.shanna.repository.KnowledgeRepository;
import com.atob.shanna.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public KnowledgeServiceImpl(KnowledgeRepository knowledgeRepository) {
        this.knowledgeRepository = knowledgeRepository;
    }

    @Transactional
    @Override
    public KnowledgeDto parse(final String text) {
        List<String> dots = Arrays.asList(text.split(":"));

        String lDots = dots.get(0);
        String rDots = dots.get(1);

        String iBrackets = lDots
                .replace("[", "")
                .replace("]", "");

        List<KnowledgeDto> referencedBy = new ArrayList<>();
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

        return new KnowledgeDto(name, body, referencedBy);
    }

    @Override
    public Knowledge getById(Long id) {
        return knowledgeRepository.getById(id);
    }

    @Transactional
    @Override
    public List<KnowledgeDto> save(String text) {
        knowledgeRepository.save(dtoToEntity(this.parse(text)));
        return knowledgeRepository.findAll().stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<KnowledgeDto> getAll() {
        return knowledgeRepository.findAll().stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    private KnowledgeDto entityToDto(Knowledge knowledge){
            KnowledgeDto dto = new KnowledgeDto();
            dto.setId(knowledge.getId());
            dto.setName(knowledge.getName());
            dto.setDescription(knowledge.getDescription());
            List<KnowledgeDto> dtoPointedBy = new ArrayList<>();
            if(knowledge.getPointedBy() != null){
                knowledge.getPointedBy().forEach(k ->
                        dtoPointedBy.add(new KnowledgeDto(k.getId(),
                                k.getName(), k.getDescription(), new LinkedList<>())));
            }
            dto.setPointedBy(dtoPointedBy);
            return dto;
    }

    private Knowledge dtoToEntity(KnowledgeDto dto){
        Knowledge knowledge = new Knowledge();
        knowledge.setName(dto.getName());
        knowledge.setDescription(dto.getDescription());
        if(dto.getPointedBy() != null) {
            dto.getPointedBy().forEach(k ->
                    knowledge.getPointedBy().add(new Knowledge(k.getName(), k.getDescription())));
        }
        return knowledge;
    }
}
