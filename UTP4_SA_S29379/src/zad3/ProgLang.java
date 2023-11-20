package zad3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
public class ProgLang {
    private final LinkedHashMap<String, LinkedHashSet<String>> langsMap;
    private final LinkedHashMap<String, LinkedHashSet<String>> progsMap;

    /**
     * This constructor creates a ProgLang object from a file that contains information about languages and programmers
     *
     * @param nazwaPliku the name of the file that contains the data
     * @throws IOException if the file cannot be read
     * @since 4.2/3
     */
    public ProgLang(String nazwaPliku) throws IOException {
        langsMap = new LinkedHashMap<>();
        progsMap = new LinkedHashMap<>();

        try (FileInputStream fis = new FileInputStream(nazwaPliku);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\t");

                String lang = tokens[0]; // The first token is the language name

                // Create a set of programmers for this language
                LinkedHashSet<String> progs = new LinkedHashSet<>();

                for (int i = 1; i < tokens.length; i++) {
                    String prog = tokens[i];
                    progs.add(prog);

                    // Use the computeIfAbsent method to create a new set if the key does not exist
                    progsMap.computeIfAbsent(prog, k -> new LinkedHashSet<>()).add(lang);
                }
                // Update the langsMap with the language and the set of programmers
                langsMap.put(lang, progs);
            }
        }
    }


    public LinkedHashMap<String, LinkedHashSet<String>> getLangsMap() {
        return langsMap;
    }

    public LinkedHashMap<String, LinkedHashSet<String>> getProgsMap() {
        return progsMap;
    }

    /**
     * This method returns a LinkedHashMap of languages and programmers, sorted by the number of programmers in descending order
     *
     * @return a sorted LinkedHashMap of languages and programmers
     * @since 4.2/3
     */
    public LinkedHashMap<String, LinkedHashSet<String>> getLangsMapSortedByNumOfProgs() {
        return langsMap.entrySet().stream()
                .sorted(Comparator
                        .comparingInt((Map.Entry<String, LinkedHashSet<String>> e) -> e.getValue().size())
                        .reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    /**
     * This method returns a LinkedHashMap of programmers and languages, sorted by the number of languages in descending order and then by the name in ascending order
     *
     * @return a sorted LinkedHashMap of programmers and languages
     * @since 4.2/3
     */
    public LinkedHashMap<String, LinkedHashSet<String>> getProgsMapSortedByNumOfLangs() {
        return progsMap.entrySet().stream()
                .sorted(Comparator
                        .comparingInt((Map.Entry<String, LinkedHashSet<String>> e) -> e.getValue().size())
                        .reversed()
                        .thenComparing(Map.Entry::getKey)
                )
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    /**
     * This method returns a LinkedHashMap of programmers and languages, where the number of languages is greater than a given number
     *
     * @param n the minimum number of languages
     * @return a filtered LinkedHashMap of programmers and languages
     * @since 4.2/3
     */
    public LinkedHashMap<String, LinkedHashSet<String>> getProgsMapForNumOfLangsGreaterThan(int n) {
        return progsMap.entrySet().stream()
                .filter(e -> e.getValue().size() > n)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }


    /**
     * This method returns a LinkedHashMap of keys and values, sorted by the given comparator
     *
     * @param map the map to be sorted
     * @param comp the comparator that defines the order of the map entries
     * @return a sorted LinkedHashMap of keys and values
     * @since 4.2/4
     */
    public static <K,V> LinkedHashMap<K,V> sorted(Map<K,V> map, Comparator<Map.Entry<K,V>> comp ){

        return map.entrySet().stream()
                .sorted(comp)
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    /**
     * This method returns a LinkedHashMap of keys and values, filtered by the given predicate
     *
     * @param map the map to be filtered
     * @param filter the predicate that defines the condition of the map entries
     * @return a filtered LinkedHashMap of keys and values
     * @since 4.2/4
     */
    public static <K,V> LinkedHashMap<K,V> filtered(Map<K,V> map, Predicate<Map.Entry<K,V>> filter) {
        return map.entrySet().stream()
                .filter(filter)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }
}