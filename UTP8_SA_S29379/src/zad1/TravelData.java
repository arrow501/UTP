package zad1;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/*
lokalizacje_kontrahenta kraj  date_wyjazdu  date_powrotu miejsce cene symbol_waluty

gdzie:
lokalizacja - napis,  oznaczający język_kraj (np. pl_PL, en_US; tak jak zwraca to metoda toString() z klasy Locale)
kraj - nazwa kraju w języku kontrahenta,
daty - (wyjazdu, powrotu) daty w formacie RRRR-MM-DD (np. 2015-12-31),
miejsce - jedno z: [morze, jezioro, góry] - w języku kontrahenta,
cena - liczba w formacie liczb, używanym w kraji kontrahenta,
symbol_waluty = PLN, USD itp.

 */
public class TravelData {
    private List<Offer> offers;
    public TravelData(File dataDir) {
        this.offers = DataReader.loadData(dataDir);
    }

    public List<String> getOffersDescriptionsList(String loc, String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);



        List<String> offerDescriptions = offers.stream().map(Offer::toString).collect(Collectors.toList());

        return offerDescriptions;
    }
}
