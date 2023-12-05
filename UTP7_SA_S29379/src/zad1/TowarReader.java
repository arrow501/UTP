package zad1;

import java.io.*;
import java.util.Queue;

public class TowarReader implements Runnable {
    private static final File file = Main.FILE;
    private final Queue<Towar> towary;
    public TowarReader( Queue<Towar> towary) {
        super();
        this.towary = towary;
    }
    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" ");

                Towar towar = new Towar(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                if( ++count % 200 == 0){
                    System.out.println("utworzono " + count + " obiektów");
                }
                towary.add(towar);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main.readingDone = true;
    }
}
