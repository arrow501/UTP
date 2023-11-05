/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad2;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*<--
 *  niezbędne importy
 */
public class Main {
  public static void main(String[] args) {
    /*<--
     *  definicja operacji w postaci lambda-wyrażeń:
     *  - flines - zwraca listę wierszy z pliku tekstowego
     *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
     *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
     *  - sum - zwraca sumę elmentów listy liczb całkowitych
     */
    // flines - returns a list of lines from a text file
    Function<String, List<String>> flines = filename -> {
      List<String> lines = new ArrayList<>();
      try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
          lines.add(line);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return lines;
    };

// join - joins a list of strings into one string
    Function<List<String>, String> join = list -> String.join(" ", list);

// collectInts - returns a list of integers contained in a string
    Function<String, List<Integer>> collectInts = s -> Arrays.stream(s.split("\\D+")) // split by non-digit characters
            .filter(str -> !str.isEmpty()) // filter out empty strings
            .map(Integer::parseInt) // parse each string as an integer
            .collect(Collectors.toList()); // collect the integers into a list

// sum - returns the sum of a list of integers
    Function<List<Integer>, Integer> sum = list -> list.stream().reduce(0, Integer::sum);


    String fname = System.getProperty("user.home") + "/LamComFile.txt"; 
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);  
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

  }
}
