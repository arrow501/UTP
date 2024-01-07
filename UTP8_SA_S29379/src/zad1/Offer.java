package zad1;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class Offer {
    Locale locale;
    String country;
    String dateFrom;
    String dateTo;
    String place;
    Integer price;
    String currency;

    public Offer(String OfferString) {
        String[] data = OfferString.split("\t");



        String[] localeParts = data[0].split("_");
        if (localeParts.length > 1) {
            this.locale = new Locale(localeParts[0], localeParts[1]);
        } else {
            this.locale = new Locale(localeParts[0]);
        }
//        System.out.println(locale);


        // Get the list of all available locales
        Locale[] locales = Locale.getAvailableLocales();

        // Find the locale that has the same display country as data[1] in the language of this.locale
        Locale countryLocale = Arrays.stream(locales)
                .filter(l -> l.getDisplayCountry(this.locale).equals(data[1]))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such country: " + data[1]));

        // Get the English name of the country
        this.country = countryLocale.getDisplayCountry(Locale.ENGLISH);

        this.dateFrom = data[2];
        this.dateTo = data[3];


        ResourceBundle places = ResourceBundle.getBundle("Places", this.locale);
        // Get the key for the place
        // data[4] is value of the place in the language of this.locale
        this.place = places.keySet().stream()
                .filter(k -> places.getString(k).equals(data[4]))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such place: " + data[4]));


        NumberFormat format = NumberFormat.getInstance(this.locale);
        try {
            Number number = format.parse(data[5]);
            this.price = (int) (number.doubleValue() * 100);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.currency = data[6];
    }

    public String toString() {
        return locale + "\t" + country + "\t" + dateFrom + "\t" + dateTo + "\t" + place + "\t" + price + "\t" + currency;
    }
}
