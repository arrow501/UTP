package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class represents a database for storing and retrieving travel offers.
 * It provides methods for creating the database, adding offers to the database,
 * and displaying the offers in a GUI.
 */
public class Database {
    private Connection connection;
    private final TravelData travelData;
    private Locale locale = Locale.getDefault();
    private JFrame frame;


    /**
     * Constructs a new Database object.
     *
     * @param url         the URL of the database
     * @param travelData  the travel data to be stored in the database
     */
    public Database(String url, TravelData travelData) {
        this.travelData = travelData;
        initializeDatabase(url);
    }

    /**
     * Initializes the database by creating a new table if it does not exist.
     *
     * @param url the URL of the database
     */
    private void initializeDatabase(String url) {
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
        } catch (ClassNotFoundException e) {
            System.err.println("The SQLite JDBC driver was not found.");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("An error occurred while executing the SQL statement.");
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Message: " + e.getMessage());
            System.err.println("Vendor Error Code: " + e.getErrorCode());
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds all offers from the travel data to the database.
     */
    public void create() {
        for (Offer offer : travelData.getOffers()) {
            addOfferToDatabase(offer);
        }
    }

    /**
     * Adds a single offer to the database.
     *
     * @param offer the offer to be added
     */
    private void addOfferToDatabase(Offer offer) {
        String sql = "INSERT OR IGNORE INTO offers(locale, countryCode, dateFrom, dateTo, place, price, currency) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, offer.locale.toString());
            statement.setString(2, offer.countryCode);
            statement.setString(3, offer.dateFrom);
            statement.setString(4, offer.dateTo);
            statement.setString(5, offer.place);
            statement.setDouble(6, offer.price);
            statement.setString(7, offer.currency);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to add offer to database: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays the GUI for viewing the offers in the database.
     */
    public void showGui() {
        if (frame == null) {
            frame = new JFrame("Offers");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            frame.getContentPane().removeAll();
        }

        createGui();
    }

    /**
     * Creates the GUI for viewing the offers in the database.
     */
    private void createGui() {
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

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        String sql = "SELECT * FROM offers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            NumberFormat numberFormat = Localize.getNumberFormat(locale);
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
            ResourceBundle places = ResourceBundle.getBundle("Places", locale);

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
            // Log the error message and SQL state
            System.err.println("SQL error message: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());

            // Display a user-friendly message
            JOptionPane.showMessageDialog(frame, "An error occurred while retrieving data from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);

            // Exit the program
            throw new RuntimeException(e);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.setJMenuBar(createLanguageBar());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates a language bar for changing the display language of the GUI.
     *
     * @return the language bar
     */
    private JMenuBar createLanguageBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Language");
        menuBar.add(menu);

        createMenuItem(menu, "English", Locale.ENGLISH);
        createMenuItem(menu, "Polish", new Locale("pl", "PL"));
        createMenuItem(menu, "German", Locale.GERMANY);

        return menuBar;
    }

    /**
     * Creates a menu item for a specific language.
     *
     * @param menu     the menu to which the item will be added
     * @param language the language of the menu item
     * @param locale   the locale corresponding to the language
     */
    private void createMenuItem(JMenu menu, String language, Locale locale) {
        JMenuItem menuItem = new JMenuItem(language);
        menuItem.addActionListener(e -> handleLocaleChange(locale));
        menu.add(menuItem);
    }

    /**
     * Handles a change in the display language.
     *
     * @param newLocale the new locale
     */
    private void handleLocaleChange(Locale newLocale) {
        this.locale = newLocale;
        frame.getContentPane().removeAll();
        frame.repaint();
        createGui();
    }
}