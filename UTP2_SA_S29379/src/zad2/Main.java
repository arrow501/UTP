/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad2;


/*<-- niezbędne importy */

import java.util.*;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    // Lista destynacji: port_wylotu port_przylotu cena_EUR
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = dest.stream()
            .filter(s -> s.startsWith("WAW"))
            .map( s -> {
                String[] parts = s.split(" ");
                // (DEP, ARR, priceEUR) = s...
                double priceInPLN = Double.parseDouble(parts[2]) * ratePLNvsEUR;
                return "to " + parts[1] + " - price in PLN:\t" + (int)priceInPLN;
            })
            .collect(Collectors.toList());

    for (String r : result) System.out.println(r);
  }
}
