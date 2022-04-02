//package com.atob.shanna.init;
//
//import com.atob.shanna.entity.Knowledge;
//import com.atob.shanna.repository.KnowledgeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class Init implements CommandLineRunner {
//
//    @Autowired
//    private KnowledgeRepository knowledgeRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Knowledge k1 = new Knowledge(1L, "name1", "description1", new ArrayList<>());
//        Knowledge k2 = new Knowledge(2L, "name2", "description2", List.of(k1));
//        Knowledge k3 = new Knowledge(3L, "name3", "description3", List.of(k2));
//
//        knowledgeRepository.save(k1);
//        knowledgeRepository.save(k2);
//        knowledgeRepository.save(k3);
//    }
//}
