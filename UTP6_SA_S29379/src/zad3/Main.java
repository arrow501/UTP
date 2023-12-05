/**
 * @author Święch Aleksander S29379
 */

package zad3;


import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.*;
import javax.swing.*;

public class Main {
    private final static BufferedReader bufferedReader;

    static {
        try {
            // Create a file reader and wrap it in a buffered reader
            bufferedReader = new BufferedReader(new FileReader("src/zad3/Advent_day1.txt"));
        } catch (IOException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        // Create an executor service to run the tasks
        ExecutorService executor = Executors.newCachedThreadPool();

        // Create a list model to store the futures
        DefaultListModel<Future<Integer>> listModel = new DefaultListModel<>();

        // Create a list to display the futures
        JList<Future<Integer>> list = new JList<>(listModel);
        // Create a renderer to display the futures nicely
        list.setCellRenderer(new FutureCellRenderer());


        // Create a scroll pane to wrap the list
        JScrollPane scrollPane = new JScrollPane(list);

        // Create a frame to hold the scroll pane
        JFrame frame = new JFrame("Trebuchet calibration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(300, 800);


        // Submit some tasks to the executor and add the futures to the list model
        SwingUtilities.invokeLater(() -> {
            String line;
            try {
                // Read a line from the buffered reader (this is a blocking operation)
                while ((line = bufferedReader.readLine()) != null) {
                    String finalLine = line;
                    Future<Integer> future = executor.submit(() -> {
                        System.out.println(finalLine);
                        // search for first and last digit
                        Integer first = null;
                        Integer last = null;

                        for (int i = 0; i < finalLine.length(); i++) {
                            if (Character.isDigit(finalLine.charAt(i)) && first == null) {
                                first = Character.getNumericValue(finalLine.charAt(i));
                                if (last != null) {
                                    break;
                                }
                            }
                            if (Character.isDigit(finalLine.charAt(finalLine.length() - i - 1)) && last == null) {
                                last = Character.getNumericValue(finalLine.charAt(finalLine.length() - i - 1));
                                if (first != null) {
                                    break;
                                }
                            }
                            // sleep for 50 ms
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                System.out.println("Interrupted");
                            }
                        }

                        if (first == null || last == null) {
                            throw new ExecutionException("No digits found", null);
                        }
                        // calculate the result
                        return first * 10 + last;
                    });
                    // Add the future to the list model
                    listModel.addElement(future);
                }
                // Close the buffered reader and the file reader
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("Error reading file");
            }

            // Create a button to cancel the selected future because it's in the requirements
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> {
                // Get the selected future
                Future<Integer> future = list.getSelectedValue();
                if (future != null && !future.isDone()) {
                    future.cancel(true);
                }
            });
            frame.add(cancelButton, BorderLayout.SOUTH);


            // Create a button to show the popup and add an action listener to it
            JButton showPopupButton = getPopUpButton(listModel, frame);
            // Add the buttons to a panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(cancelButton);
            buttonPanel.add(showPopupButton);

            // Add the panel to the frame
            frame.add(buttonPanel, BorderLayout.SOUTH);
        });
        // Create a Timer object with 50 milliseconds interval and an ActionListener lambda
        Timer timer = new Timer(50, e -> frame.repaint());
        // Start the timer
        timer.start();

    }

    private static JButton getPopUpButton(DefaultListModel<Future<Integer>> listModel, JFrame frame) {
        JButton showPopupButton = new JButton("Show popup");
        showPopupButton.addActionListener(e -> {
            // Create a variable to store the total sum of the futures
            int totalSum = 0;

            // Loop through the list model and get the value of each future
            for (int i = 0; i < listModel.size(); i++) {
                Future<Integer> future = listModel.get(i);
                try {
                    // If the future is done and not cancelled, add its value to the total sum
                    if (future.isDone() && !future.isCancelled()) {
                        totalSum += future.get();
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    // Handle the exception
                    System.out.println("Error getting future value");
                }
            }
            // Call the showMessageDialog() method with the total sum as the message
            JOptionPane.showMessageDialog(frame, "The total sum is " + totalSum, "Popup", JOptionPane.INFORMATION_MESSAGE);
        });
        return showPopupButton;
    }

    private static class FutureCellRenderer implements ListCellRenderer<Future<Integer>> {

        private final JLabel label;

        public FutureCellRenderer() {
            label = new JLabel();
            label.setOpaque(true);
        }

        @Override
        public JComponent getListCellRendererComponent(JList<? extends Future<Integer>> list, Future<Integer> value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value.isDone()) {
                try {
                    label.setText(value.get().toString());
                } catch (InterruptedException | ExecutionException e) {
                    label.setText("Error");
                } catch (CancellationException e) {
                    label.setText("Cancelled");
                }
            } else {
                label.setText("Waiting...");
            }
            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            } else {
                label.setBackground(list.getBackground());
                label.setForeground(list.getForeground());
            }
            return label;
        }

    }
}
