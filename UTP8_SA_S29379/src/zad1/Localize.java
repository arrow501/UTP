package zad1;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localize {

    static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Returns the display locale.
     *
     * @param localeString String representing the locale.
     * @return Locale for display.
     */
    public static Locale getDisplayLocale(String localeString) {
        String[] localeParts = localeString.split("_");
        if (localeParts.length > 1) {
            return new Locale(localeParts[0], localeParts[1]);
        } else {
            return new Locale(localeParts[0]);
        }
    }

    /**
     * Returns the number format for the given locale.
     *
     * @param displayLocale Locale for which to get the number format.
     * @return NumberFormat for the given locale.
     */
    public static NumberFormat getNumberFormat(Locale displayLocale) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(displayLocale);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat;
    }


    /**
     * Formats the date.
     *
     * @param date          Date to format.
     * @param displayFormat SimpleDateFormat for display.
     * @return Formatted date string.
     */
    public static String formatDate(String date, SimpleDateFormat displayFormat) {
        try {
            return displayFormat.format(defaultDateFormat.parse(date));
        } catch (ParseException e) {
            System.err.println("Invalid date: " + date);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the country name for the given offer and locale.
     *
     * @param offer         Offer containing the country code.
     * @param displayLocale Locale for display.
     * @return Country name.
     */
    public static String getCountryName(Offer offer, Locale displayLocale) {
        Locale country = new Locale("", offer.countryCode);
        return country.getDisplayCountry(displayLocale);
    }

    /**
     * Returns the country name for the given country code and locale.
     *
     * @param CountryCode   Country code.
     * @param displayLocale Locale for display.
     * @return Country name.
     */
    public static String getCountryName(String CountryCode, Locale displayLocale) {
        Locale country = new Locale("", CountryCode);
        return country.getDisplayCountry(displayLocale);
    }

}
