package zad1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {


    static List<Offer> getOffers (File dataDir){
        File[] files = dataDir.listFiles();
        List<Offer> offers = new ArrayList<>();

        if(files == null) throw new IllegalArgumentException("No files in directory: " + dataDir.getAbsolutePath());

        for (File file : files) {
            if (file.isFile()) {
                System.out.println(file.getName());
                try {
                    offers.addAll(readFile(file));
                } catch (FileNotFoundException e) {
                    // it should never happen
                    e.printStackTrace();
                }
            } else if (file.isDirectory()) {
                offers.addAll(getOffers(file));
            }
        }
        return offers;
    }

    static List<Offer> readFile(File file) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        List<Offer> offers = br.lines().map(Offer::new).collect(Collectors.toList());

        return offers;
    }

}
