package zad1;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class representing an offer.
 */
public class Offer {
    public final Locale locale;
    public final String countryCode;
    public final String dateFrom;
    public final String dateTo;
    public final String place;
    public final double price;
    public final String currency;

    /**
     * Constructor for Offer.
     *
     * @param offerString String representing the offer.
     */
    public Offer(String offerString) {
        String[] data = offerString.split("\t");

        if(data.length != 7) {
            throw new IllegalArgumentException("Invalid offer: " + offerString);
        }

        this.locale = getLocale(data[0]);
        this.countryCode = getCountryCode(data[1], this.locale);
        this.dateFrom = data[2];
        this.dateTo = data[3];
        this.place = getPlace(data[4], this.locale);
        this.price = getPrice(data[5], this.locale);
        this.currency = data[6];
    }

    /**
     * Returns the locale.
     *
     * @param localeString String representing the locale.
     * @return Locale.
     */
    private Locale getLocale(String localeString) {
        String[] localeParts = localeString.split("_");
        if (localeParts.length > 1) {
            return new Locale(localeParts[0], localeParts[1]);
        } else {
            return new Locale(localeParts[0]);
        }
    }

    /**
     * Returns the country code.
     *
     * @param countryName Name of the country.
     * @param locale Locale.
     * @return Country code.
     */
    private String getCountryCode(String countryName, Locale locale) {
        Locale[] locales = Locale.getAvailableLocales();
        Locale countryLocale = Arrays.stream(locales)
                .filter(l -> l.getDisplayCountry(locale).equals(countryName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such country: " + countryName));
        return countryLocale.getCountry();
    }

    /**
     * Returns the place.
     *
     * @param placeName Name of the place.
     * @param locale Locale.
     * @return Place.
     */
    private String getPlace(String placeName, Locale locale) {
        ResourceBundle places = ResourceBundle.getBundle("Places", locale);
        return places.keySet().stream()
                .filter(k -> places.getString(k).equals(placeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such place: " + placeName));
    }

    /**
     * Returns the price.
     *
     * @param priceString String representing the price.
     * @param locale Locale.
     * @return Price.
     */
    private double getPrice(String priceString, Locale locale) {
        NumberFormat format = NumberFormat.getInstance(locale);
        try {
            Number number = format.parse(priceString);
            return number.doubleValue();
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid price: " + priceString, e);
        }
    }

    /**
     * Returns a string representation of the offer.
     *
     * @return String representation of the offer.
     */
    @Override
    public String toString() {
        return locale + "\t" + countryCode + "\t" + dateFrom + "\t" + dateTo + "\t" + place + "\t" + price + "\t" + currency;
    }
}