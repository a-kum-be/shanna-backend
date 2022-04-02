package com.atob.shanna.parser;

import java.util.Set;

public record KnowledgeDTO(String name, String body, Set<KnowledgeDTO> basedOn) {};

public static List<String> terminals = new ArrayList<String>();
public static List<String> nonTerminals = new ArrayList<String>();
public static Map<String, List<List<String>>> rules = new TreeMap<>();

public Parser() {
        terminals = List.of("[", "]", ":", "");
        nonTerminals = List.of("S", "A", "B", "A_L", "A_R", "A_PRIME", "A_DASH", "L", "B_DOT", "B_DASH");
        rules = getRules();
        }
//    public static void main(String[] args) {
//        System.out.println(
//                new Parser()
////                        .letters()
////                        .cyk(List.of("[regex]:ala (bala)"))
//                        .get("[regex]: ala {bala} qla {gala}")
//        );
//    }

public boolean cyk(final List<String> text) {
        int length = text.size();

        List<List<Set<String>>> t = getTable(length);

        for (int j = 0; j < length; ++j) {

        int finalJ = j;
        rules.forEach((lhs, rule) -> {
        rule.forEach(rhs -> {
        if (rhs.size() == 1 &&
        rhs.get(0).equals(text.get(finalJ)))
        {
        t.get(finalJ).get(finalJ).add(lhs);
        }
        });
        });

        for (int i = j; i > -1; --i) {
        for (int k = i; k < j + 1; ++k) {

        int finalI = i;
        int finalK = k;
        int finalJ1 = j;
        rules.forEach((lhs, rule) -> {
        rule.forEach(rhs -> {
        if (rhs.size() == 2 &&
        t.get(finalI).get(finalK).contains(rhs.get(0)) &&
        t.get(finalK +1).get(finalJ1).contains(rhs.get(1)))
        {
        t.get(finalI).get(finalJ1).add(lhs);
        }
        });
        });

        }
        }
        }

        return t.get(0).get(length-1).size() > 0;
        }

private List<List<Set<String>>> getTable(int length) {
        List<List<Set<String>>> table = new ArrayList<>();

        for (int i = 0; i < length; ++i) {
        table.add(new ArrayList<>(length));
        for (int j = 0; j < length; ++j) {
        table.get(i).add(new HashSet<>());
        }
        }

        return table;
        }

public String getName(String text) {
        String name = "";
        String rest = "";
        return null;
        }

private Map<String, List<List<String>>> getRules() {
        Map<String, List<List<String>>> rules = new HashMap<>();

        rules.putIfAbsent("S", List.of(
        List.of("A", "B")
        ));
        rules.putIfAbsent("A", List.of(
        List.of("A_L", "A_PRIME")
        ));
        rules.putIfAbsent("A_L", List.of(
        List.of("["))
        );
        rules.putIfAbsent("A_R", List.of(
        List.of("]"))
        );
        rules.putIfAbsent("A_PRIME", List.of(
        List.of("A_DASH", "A_R")
        ));
        rules.putIfAbsent("A_DASH", List.of(
        List.of("L", "A_DASH"),
        List.of(""))
        );
        rules.putIfAbsent("B", List.of(
        List.of("B_DOT", "B_DASH")
        ));
        rules.putIfAbsent("B_DOT", List.of(
        List.of(":")
        ));
        rules.putIfAbsent("B_DASH", List.of(
        List.of("L", "B_DASH"),
        List.of("")
        ));
        rules.putIfAbsent("L", List.of(
        letters()
        ));

        return rules;
        }

private List<String> letters() {
        List<String> res = new ArrayList<>();

        for(int c = 'a'; c <= 'z'; ++c) {
        res.add(Character.toString(c));
        res.add(Character.toString(Character.toUpperCase(c)));
        }

        String symbols = ".,><?!(){}+-@#$%^&*= ";
        List<String> listSymbols = symbols
        .chars()
        .mapToObj(e -> (char) e)
        .map(c -> Character.toString(c))
        .toList();

        res.addAll(listSymbols);
        return res;
        }

        }



