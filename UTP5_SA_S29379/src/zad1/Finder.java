/**
 * @author Święch Aleksander S29379
 */

package zad1;
// Why compiler says that package com.sun.source.tree does not exist?
// Because it is not included in the JDK. It is part of the Java Compiler API.

// https://docs.oracle.com/javase/8/docs/jdk/api/javac/tree/com/sun/source/tree/package-summary.html

// How to make the compiler happy?
// Add the following dependency to the pom.xml file:
// <dependency>
//   <groupId>com.sun</groupId>
//   <artifactId>tools</artifactId>
//   <version>1.8.0</version>
//   <scope>system</scope>
//   <systemPath>${java.home}/../lib/tools.jar</systemPath>
// </dependency>


// Klasa Finder wczytuje plik i wyszukuje instrukcje if i napisy wariant

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.IfTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreeScanner;
import com.sun.source.util.Trees;

import javax.lang.model.element.Element;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Finder {

    private final List<String> lines;
    private final String filename;

    public Finder(String filename) throws Exception {
        this.filename = filename;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        System.out.println("Liczba wierszy w pliku: " + lines.size());
    }

    // Define a scanner class that extends the TreeScanner
    // This class will visit each node in the AST and perform some action
    private static class IfScanner extends TreeScanner<Void, Integer> {
        // Override the visitIf method for if statements
        // This method will be called whenever an if statement is encountered in the AST
        @Override
        public Void visitIf(IfTree node, Integer count) {
            // Increment the count by one
            count++;
            // Visit the children nodes of the if statement
            return super.visitIf(node, count);
        }
    }

    // Metoda, która zwraca liczbę instrukcji if w pliku
    public int getIfCount() throws Exception {
        // Get the system compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // Get the standard file manager
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        // Get the Java file object from the file name
        Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjects("Test.java");
        // Get the compilation task from the compiler
        JavacTask task = (JavacTask) compiler.getTask(null, fileManager, null, null, null, fileObjects);
        // Parse the source file and get the compilation unit
        Iterable<? extends CompilationUnitTree> units = task.parse();
        // Get the first compilation unit (assuming there is only one source file)
        CompilationUnitTree unit = units.iterator().next();
        // Get the trees utility class
        Trees trees = Trees.instance(task);
        // Create an IfScanner instance
        IfScanner scanner = new IfScanner();
        // Initialize the count to zero
        Integer count = 0;
        // Scan the compilation unit with the scanner and the count
        scanner.scan(trees.getPath((Element) unit), count);
        // Print the result
        System.out.println("The number of if statements in Test.java is: " + count);
        return count;
    }

    // Metoda, która zwraca liczbę wystąpień podanego napisu w pliku
    public int getStringCount(String s) {
        return 0;
    }

}
