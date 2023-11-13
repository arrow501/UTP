/**
 * @author Święch Aleksander S29379
 */

package zad1;


import java.util.List;

import java.io.*;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * A class that represents a collection of words and their anagrams.
 */
public class Anagrams {

    private final List<String> words; // the list of words from the file

    /**
     * Constructs an Anagrams object from a file name.
     * @param fileName the name of the file that contains the words
     * @throws FileNotFoundException if the file is not found
     */
    public Anagrams(String fileName) throws FileNotFoundException {
        words = new ArrayList<>();
        Scanner scan = new Scanner(new File(fileName));
        while (scan.hasNext()) {
            words.add(scan.next());
        }
        scan.close();
    }

    /**
     * Returns a list of lists of words that are anagrams of each other, sorted by the number of words in descending order, and then by the first word in alphabetical order.
     * @return the list of lists of anagrams
     */
    public List<List<String>> getSortedByAnQty() {
        List<List<String>> anagrams = new ArrayList<>();
        Set<String> processed = new HashSet<>();
        for (String word : words) {
            if (processed.contains(word)) continue;

            List<String> group = getAnagramsOf(word);
            anagrams.add(group);
            processed.addAll(group);
        }
        anagrams.sort(Comparator.comparingInt((ToIntFunction<List>) List::size)
                .reversed()
                .thenComparing(list -> ((List<String>) list)
                .get(0)));
        return anagrams;
    }

    /**
     * Returns a string that represents the list of anagrams for a given word, or null if the word is not in the list.
     * @param word the word to find the anagrams for
     * @return the string representation of the anagrams
     */
    public String getAnagramsFor(String word) {
        String result = word + ": ";
        if (words.contains(word)) {
            List<String> anagrams = getAnagramsOf(word);
            result += anagrams;
        } else {
            result += "null";
        }
        return result;
    }

    // Sorts the letters of a word
    private String sort(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    // Returns a list of words that are anagrams of a given word
    private List<String> getAnagramsOf(String word) {
        String sortedWord = sort(word);
        return words.stream()
                .filter(other -> !other.equals(word) && sort(other).equals(sortedWord))
                .collect(Collectors.toList());
    }

}
