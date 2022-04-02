package com.atob.shanna.service;

import com.atob.shanna.dto.KnowledgeDto;
import com.atob.shanna.entity.Knowledge;

import java.util.List;

public interface KnowledgeService {

    KnowledgeDto parse(final String text);

    Knowledge getById(Long id);

    List<KnowledgeDto> save(String text);

    List<KnowledgeDto> getAll();
}
