package zad1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class TowarGen {
    // file: ../towary.txt
    public static final File FILE = Main.FILE;

    public static final int LINES = 10_000_000;

    public static int sum = 0;
    public static void main(String[] args) throws IOException {

        BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(FILE));

        for (int i = 0; i < LINES; i++) {
            int weight = (int) (Math.random() * 1000);
            sum += weight;
            writer.write(i + " " + weight);
            writer.newLine();
        }
        writer.close();

        System.out.println("File generated.");

        // print the head 5 lines or all lines if there are less than 5
        BufferedReader reader = new BufferedReader(new java.io.FileReader(FILE));

        // if there are less than 5 lines, print them all
        if (LINES < 5) {
            for (int i = 0; i < LINES; i++) {
                System.out.println(reader.readLine());
            }
            reader.close();
            return;
        }

        // print the first 5 lines
        for (int i = 0; i < 5; i++) {
            System.out.println(reader.readLine());
        }
        System.out.println("... and " + (LINES - 5) + " more lines.");
        reader.close();

        System.out.println("Sum of weights: " + sum);

    }
}
