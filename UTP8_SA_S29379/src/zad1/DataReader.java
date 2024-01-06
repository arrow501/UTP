package zad1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {


    static List<Offer> loadData(File dataDir){
        File[] files = dataDir.listFiles();
        List<Offer> offers = new ArrayList<>();

        if(files == null) throw new IllegalArgumentException("No files in directory: " + dataDir.getAbsolutePath());

        for (File file : files) {
            if (file.isFile()) {
                System.out.println(file.getName());
                try {
                    offers.addAll(readFile(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return offers;
    }

    static List<Offer> readFile(File file) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        List<Offer> offers = br.lines().map(line -> {
            String[] data = line.split("\t");
            return new Offer(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
        }).collect(Collectors.toList());


        return offers;
    }

}
