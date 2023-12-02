/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // Create an executor service to run the tasks
        ExecutorService executor = Executors.newCachedThreadPool();

        // Create a list model to store the futures
        DefaultListModel<Future<Integer>> listModel = new DefaultListModel<>();

        // Create a list to display the futures
        JList<Future<Integer>> list = new JList<>(listModel);

        // Create a scroll pane to wrap the list
        JScrollPane scrollPane = new JScrollPane(list);

        // Create a frame to hold the scroll pane
        JFrame frame = new JFrame("Future List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);

        // Submit some tasks to the executor and add the futures to the list model
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < 10; i++) {
                Future<Integer> future = executor.submit(() -> {
                    // Simulate some computation
                    Thread.sleep(1000);
                    return (int) (Math.random() * 100);
                });
                listModel.addElement(future);
            }
        });
    }
}
