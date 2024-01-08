package zad1;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Class to handle travel data.
 */
public class TravelData {
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
        Locale displayLocale = Localize.getDisplayLocale(localeString);
        SimpleDateFormat displayFormat = new SimpleDateFormat(dateFormat);
        ResourceBundle places = ResourceBundle.getBundle("Places", displayLocale);
        NumberFormat numberFormat = Localize.getNumberFormat(displayLocale);

        return offers.stream()
                .map(offer -> formatOffer(offer, displayFormat, places, numberFormat, displayLocale))
                .collect(Collectors.toList());
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
    public static String formatOffer(Offer offer, SimpleDateFormat displayFormat, ResourceBundle places, NumberFormat numberFormat, Locale displayLocale) {
        String dateFrom = Localize.formatDate(offer.dateFrom, displayFormat);
        String dateTo = Localize.formatDate(offer.dateTo, displayFormat);
        String countryName = Localize.getCountryName(offer, displayLocale);

        return String.format("%s %s %s %s %s %s",
                countryName,
                dateFrom,
                dateTo,
                places.getString(offer.place),
                numberFormat.format(offer.price),
                offer.currency);
    }
    public Iterable<? extends Offer> getOffers() {
        return offers;
    }
}