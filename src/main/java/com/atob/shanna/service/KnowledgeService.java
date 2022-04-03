package com.atob.shanna.service;

import com.atob.shanna.dto.KnowledgeResponseDto;
import com.atob.shanna.entity.Knowledge;

import java.util.List;

public interface KnowledgeService {

    KnowledgeResponseDto parse(String text);

    Knowledge getById(Long id);

    List<KnowledgeResponseDto> save(String text);

    List<KnowledgeResponseDto> getAll();

    List<KnowledgeResponseDto> updateDescription(Long id, String newDescription);
}
