package com.atob.shanna.parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public KnowledgeDTO get(final String text) {
        List<String> dots = Arrays.stream(text.split(":")).toList();

        String lDots = dots.get(0);
        String rDots = dots.get(1);

        String iBrackets = lDots
                .replace("[", "")
                .replace("]", "");

        Set<KnowledgeDTO> basedOn = new HashSet<>();
        Matcher m = Pattern.compile("\\{(.*?)\\}").matcher(rDots);

        while(m.find()) {
            System.out.println(m.group(1));
            basedOn.add(new KnowledgeDTO(m.group(1), "", new HashSet<>()));
        }

        String body = rDots.trim();
        String name = iBrackets.trim();

        return new KnowledgeDTO(name, body, basedOn);
    }

