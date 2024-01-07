package zad1;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Class to handle travel data.
 */
public class TravelData {
    SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private List<Offer> offers;

    /**
     * Constructor for TravelData.
     *
     * @param dataDir Directory containing the data files.
     */
    public TravelData(File dataDir) {
        this.offers = DataReader.getOffers(dataDir);
    }

    /**
     * Returns a list of offer descriptions.
     *
     * @param localeString String representing the locale.
     * @param dateFormat String representing the date format.
     * @return List of offer descriptions.
     */
    public List<String> getOffersDescriptionsList(String localeString, String dateFormat) {
        Locale displayLocale = getDisplayLocale(localeString);
        SimpleDateFormat displayFormat = new SimpleDateFormat(dateFormat);
        ResourceBundle places = ResourceBundle.getBundle("Places", displayLocale);
        NumberFormat numberFormat = getNumberFormat(displayLocale);

        return offers.stream()
                .map(offer -> formatOffer(offer, displayFormat, places, numberFormat, displayLocale))
                .collect(Collectors.toList());
    }

    /**
     * Returns the display locale.
     *
     * @param localeString String representing the locale.
     * @return Locale for display.
     */
    private Locale getDisplayLocale(String localeString) {
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
    private NumberFormat getNumberFormat(Locale displayLocale) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(displayLocale);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat;
    }

    /**
     * Formats the offer.
     *
     * @param offer Offer to format.
     * @param displayFormat SimpleDateFormat for display.
     * @param places ResourceBundle containing places.
     * @param numberFormat NumberFormat for formatting.
     * @param displayLocale Locale for display.
     * @return Formatted offer string.
     */
    private String formatOffer(Offer offer, SimpleDateFormat displayFormat, ResourceBundle places, NumberFormat numberFormat, Locale displayLocale) {
        String dateFrom = formatDate(offer.dateFrom, displayFormat);
        String dateTo = formatDate(offer.dateTo, displayFormat);
        String countryName = getCountryName(offer, displayLocale);

        return String.format("%s %s %s %s %s %s",
                countryName,
                dateFrom,
                dateTo,
                places.getString(offer.place),
                numberFormat.format(offer.price),
                offer.currency);
    }

    /**
     * Formats the date.
     *
     * @param date Date to format.
     * @param displayFormat SimpleDateFormat for display.
     * @return Formatted date string.
     */
    private String formatDate(String date, SimpleDateFormat displayFormat) {
        try {
            return displayFormat.format(defaultDateFormat.parse(date));
        } catch (ParseException e) {
            return "Invalid date";
        }
    }

    /**
     * Returns the country name for the given offer and locale.
     *
     * @param offer Offer containing the country code.
     * @param displayLocale Locale for display.
     * @return Country name.
     */
    private String getCountryName(Offer offer, Locale displayLocale) {
        Locale country = new Locale("", offer.countryCode);
        return country.getDisplayCountry(displayLocale);
    }

    public Iterable<? extends Offer> getOffers() {
        return offers;
    }
}