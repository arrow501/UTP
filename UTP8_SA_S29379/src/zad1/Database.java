package zad1;

import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

public class Database {
    private Connection connection;
    private TravelData travelData;
    private Locale locale = Locale.getDefault();

    private JFrame frame;
    /**
     * Constructor for Database.
     *
     * @param url URL of the SQLite database.
     */
    public Database(String url) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);

            String sql = "CREATE TABLE IF NOT EXISTS offers (" +
                    "id INTEGER PRIMARY KEY," +
                    "locale TEXT," +
                    "countryCode TEXT," +
                    "dateFrom TEXT," +
                    "dateTo TEXT," +
                    "place TEXT," +
                    "price REAL," +
                    "currency TEXT," +
                    "UNIQUE(locale, countryCode, dateFrom, dateTo, place, price, currency)" +
                    ")";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create database", e);
        }
    }

    /**
     * Adds an offer to the database.
     *
     * @param offer Offer to add.
     */
    public void addOffer(Offer offer) {
        String sql = "INSERT OR IGNORE INTO offers(locale, countryCode, dateFrom, dateTo, place, price, currency) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, offer.locale.toString());
            pstmt.setString(2, offer.countryCode);
            pstmt.setString(3, offer.dateFrom);
            pstmt.setString(4, offer.dateTo);
            pstmt.setString(5, offer.place);
            pstmt.setDouble(6, offer.price);
            pstmt.setString(7, offer.currency);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Database(String url, TravelData travelData) {
        this(url);
        this.travelData = travelData;
    }

    public void create() {
        for (Offer offer : travelData.getOffers()) {
            addOffer(offer);
        }
    }


    public void testDatabase() {

        // Create a test offer
        Offer testOffer = new Offer("en_US\tUnited States\t2022-01-01\t2022-01-31\tlake\t1000.0\tUSD");

        // Check if the test offer already exists in the database
        String checkSql = "SELECT COUNT(*) FROM offers WHERE locale = ? AND countryCode = ? AND dateFrom = ? AND dateTo = ? AND place = ? AND price = ? AND currency = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(checkSql)) {
            pstmt.setString(1, testOffer.locale.toString());
            pstmt.setString(2, testOffer.countryCode);
            pstmt.setString(3, testOffer.dateFrom);
            pstmt.setString(4, testOffer.dateTo);
            pstmt.setString(5, testOffer.place);
            pstmt.setDouble(6, testOffer.price);
            pstmt.setString(7, testOffer.currency);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // The test offer already exists in the database, so we don't insert it
                System.out.println("The test offer already exists in the database.");
            } else {
                // The test offer does not exist in the database, so we insert it
                addOffer(testOffer);
                System.out.println("The test offer has been added to the database.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        // Retrieve the test offer from the database
        String sql = "SELECT * FROM offers WHERE place = 'lake'";

        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through the result set
            while (rs.next()) {
                System.out.println("no error");
                System.out.println(rs.getString("locale") + "\t" +
                        rs.getString("countryCode") + "\t" +
                        rs.getString("dateFrom") + "\t" +
                        rs.getString("dateTo") + "\t" +
                        rs.getString("place") + "\t" +
                        rs.getDouble("price") + "\t" +
                        rs.getString("currency"));
            }
        } catch (
                SQLException e) {
            System.out.println("error");
            System.out.println(e.getMessage());
        }
    }


    public void showGui() {
        if (frame == null) {
            frame = new JFrame("Offers");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            frame.getContentPane().removeAll();
        }

        // Load the ColumnNames resource bundle for the current locale
        ResourceBundle columnNamesBundle;
        try {
            columnNamesBundle = ResourceBundle.getBundle("ColumnNames", locale);
        } catch (MissingResourceException e) {
            locale = Locale.ENGLISH;
            columnNamesBundle = ResourceBundle.getBundle("ColumnNames", locale);
        }
        String[] columnNames = {
                columnNamesBundle.getString("Country"),
                columnNamesBundle.getString("DateFrom"),
                columnNamesBundle.getString("DateTo"),
                columnNamesBundle.getString("Place"),
                columnNamesBundle.getString("Price")
        };

        // Create a number format for the locale
        NumberFormat numberFormat = Localize.getNumberFormat(locale);
        // Create a date format for the locale
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        // Create a resource bundle for the locale
        ResourceBundle places = ResourceBundle.getBundle("Places", locale);

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Get the system locale

        String sql = "SELECT * FROM offers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {



            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(Localize.getCountryName(rs.getString("countryCode"), locale));
                row.add(Localize.formatDate(rs.getString("dateFrom"), (SimpleDateFormat) dateFormat));
                row.add(Localize.formatDate(rs.getString("dateTo"), (SimpleDateFormat) dateFormat));
                row.add(places.getString(rs.getString("place")));
                row.add(numberFormat.format(rs.getDouble("price")) + " " + rs.getString("currency"));
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.setJMenuBar(createLanguageBar());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
private JMenuBar createLanguageBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Language");
    menuBar.add(menu);

    JMenuItem menuItem = new JMenuItem("English");
    menuItem.addActionListener(e -> {
        locale = Locale.ENGLISH;
        frame.getContentPane().removeAll();
        frame.repaint();
        showGui();
    });
    menu.add(menuItem);

    menuItem = new JMenuItem("Polish");
    menuItem.addActionListener(e -> {
        locale = new Locale("pl", "PL");
        frame.getContentPane().removeAll();
        frame.repaint();
        showGui();
    });
    menu.add(menuItem);

    menuItem = new JMenuItem("German");
    menuItem.addActionListener(e -> {
        locale = Locale.GERMANY;
        frame.getContentPane().removeAll();
        frame.repaint();
        showGui();
    });
    menu.add(menuItem);

    return menuBar;
}

}
