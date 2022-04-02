package com.atob.shanna.service.impl;

import com.atob.shanna.service.KnowledgeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class KnowledgeServiceImpl implements KnowledgeService {

    @Transactional
    @Override
    public void parse() {

    }
}
