/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;


public class Finder {
private String fname;
private final List<String> lines;

    public Finder(String fname) {
        lines = new ArrayList<>();
        this.fname = fname;
        try(BufferedReader br = new BufferedReader(new FileReader(fname))) {
            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch(IOException e) {
            System.out.println("Error while reading file");
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
    public int getIfCount() {
        int count = 0;
        // remove all fake ifs
        List<String> filteredLines = new ArrayList<>();

        boolean inComment = false;
        for(String line : lines) {
            // remove multiline comments
            if(line.contains("/*")) {
                if(line.contains("*/")) {
                    line = line.replaceAll("/\\*.*\\*/", "");
                } else {
                    inComment = true;
                    line = line.replaceAll("/\\*.*", "");
                }
            }
            if(inComment) {
                if(line.contains("*/")) {
                    inComment = false;
                    line = line.replaceAll(".*\\*/", "");
                } else {
                    continue;
                }
            }
            // remove single line comments
            else if ( line.contains("//")) {
                line = line.replaceAll("//.*", "");
            }
            //remove strings
            line = line.replaceAll("\".*\"", "");


            filteredLines.add(line);
        }

        // match all ifs than don't have a letter or digit or _ before them
        String regex = "\\bif\\s*\\(.*\\)\\s*";

        Pattern pattern = Pattern.compile(regex);
        for(String line : filteredLines) {
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()) {
                count++;
            }
        }

        return count;
    }
    public int getStringCount(String stringToFind ) {
        // this regular expression mathes all occurences of the stringToFind in the file
        String regex = stringToFind;
        Pattern pattern = Pattern.compile(regex);

        int count = 0;
        for(String line : lines) {
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()) {
                count++;
            }
        }

        return count;
    }
}
