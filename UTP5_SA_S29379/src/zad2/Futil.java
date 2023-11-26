package zad2;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
            BufferedWriter writer;
            {
                try {
                    writer = Files.newBufferedWriter(Paths.get(resultFileName), Charset.forName("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".txt")) {
                    BufferedReader reader = Files.newBufferedReader(file, Charset.forName("Cp1250"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                    reader.close();
                }
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (dir.toString().equals(dirName)) {
                    writer.close();
                }
                return FileVisitResult.CONTINUE;
            }
        };
        try {
            Files.walkFileTree(Paths.get(dirName), visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
