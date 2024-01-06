package zad1;

public class Offer{
    String locale;
    String country;
    String dateFrom;
    String dateTo;
    String place;
    String price;
    String currency;

    public Offer(String locale, String country, String dateFrom, String dateTo, String place, String price, String currency) {
        this.locale = locale;
        this.country = country;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.place = place;
        this.price = price;
        this.currency = currency;
    }

    public String toString() {
        return locale + "\t" + country + "\t" + dateFrom + "\t" + dateTo + "\t" + place + "\t" + price + "\t" + currency;
    }
}
