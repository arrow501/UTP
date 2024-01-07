import java.util.*;
import java.net.*;
import java.io.*;

public class Main {
    static String locale;
    public static void main(String[] args) throws IOException {
        String[] data = "pl_PL\tWłochy\t2015-07-10\t2015-07-30\tmorze\t4000,10\tPLN".split("\t");

        String thisLocale = data[0];
        System.out.println(thisLocale);
        // Get the list of all available locales
        Locale[] locales = Locale.getAvailableLocales();

        // Find the locale that has the same display country as data[1] in the language of this.locale
        Locale countryLocale = Arrays.stream(locales)
                .filter(l -> l.getDisplayCountry(new Locale(thisLocale)).equals(data[1]))
                .findFirst()
                .orElse(new Locale(""));

        System.out.println(countryLocale + ";");
        System.out.println(countryLocale.getDisplayCountry(new Locale(thisLocale)));
        System.out.println(countryLocale.getDisplayCountry(Locale.ENGLISH));


//        // Find the locale that has the same display country as data[1] in the language of this.locale
//        Locale countryLocale = Arrays.stream(locales)
//                .filter(l -> l.getDisplayCountry(new Locale(this.locale)).equals(data[1]))
//                .findFirst()
//                .orElse(new Locale(""));
//
    }
}