package zad1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to handle reading of data files.
 */
public class DataReader {

    /**
     * Returns a list of offers from the given directory.
     *
     * @param dataDir Directory containing the data files.
     * @return List of offers.
     */
    public static List<Offer> getOffers(File dataDir) {
        List<Offer> offers = new ArrayList<>();
        File[] files = getFilesFromDirectory(dataDir);

        for (File file : files) {
            if (file.isFile()) {
                offers.addAll(readOffersFromFile(file));
            } else if (file.isDirectory()) {
                offers.addAll(getOffers(file));
            }
        }
        return offers;
    }

    /**
     * Returns an array of files from the given directory.
     *
     * @param directory Directory to get files from.
     * @return Array of files.
     */
    private static File[] getFilesFromDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IllegalArgumentException("No files in directory: " + directory.getAbsolutePath());
        }
        return files;
    }

    /**
     * Returns a list of offers from the given file.
     *
     * @param file File to read offers from.
     * @return List of offers.
     */
    private static List<Offer> readOffersFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines().map(Offer::new).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + file.getAbsolutePath(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + file.getAbsolutePath(), e);
        }
    }
}