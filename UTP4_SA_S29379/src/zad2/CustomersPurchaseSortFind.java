/**
 * @author Święch Aleksander S29379
 */

package zad2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;


public class CustomersPurchaseSortFind {
    // A list of purchases
    private ArrayList<Purchase> purchases;

    // A constructor that initializes the list
    public CustomersPurchaseSortFind() {
        purchases = new ArrayList<>();
    }

    /**
     * Reads the data from a file and adds them to a private list for later use
     * @param fname the name of the file
     */
    public void readFile(String fname) {
        try (Stream<String> lines = Files.lines(Paths.get(fname))) {
            lines.map(line -> {
                String[] fields = line.split(";");
                return new Purchase(fields[0], fields[1], fields[2], Double.parseDouble(fields[3]), Double.parseDouble(fields[4]));
            }).forEach(purchases::add);
        } catch (IOException e) {
            System.out.println("File not found: " + fname);
        }
    }


    /**
     * Shows the data sorted by a given criterion
     * @param criterion the criterion to sort by, either "Nazwiska" or "Koszty"
     */
    public void showSortedBy(String criterion) {
        // Create a copy of the list
        ArrayList<Purchase> sorted = new ArrayList<>(purchases);
        // Sort the copy according to the criterion
        switch (criterion) {
            case "Nazwiska":
                // Sort by surname and then by id
                sorted.sort(Comparator.comparing(Purchase::getName)
                        .thenComparing(Purchase::getId));
                break;
            case "Koszty":
                // Sort by cost in descending order and then by id
                sorted.sort(Comparator.comparing(Purchase::getCost)
                        .reversed()
                        .thenComparing(Purchase::getId));
                break;
            default:
                // Invalid criterion
                System.out.println("Invalid criterion: " + criterion);
                return;
        }
        // Print the sorted data
        System.out.println(criterion);
        for (Purchase purchase : sorted) {
            System.out.print(purchase);
            if (criterion.equals("Koszty")) {
                System.out.print(" (koszt: " + purchase.getCost() + ")");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Shows the data for a given customer id
     * @param id the customer id
     */
    public void showPurchaseFor(String id) {
        // Print the customer id
        System.out.println("Klient " + id);
        // Loop through the list and find the matching purchases
        for (Purchase purchase : purchases) {
            // If the id matches, print the purchase
            if (purchase.getId().equals(id)) {
                System.out.println(purchase);
            }
        }
        System.out.println();
    }
}

