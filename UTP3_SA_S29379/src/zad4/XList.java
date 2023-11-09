package zad4;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class XList<T> extends ArrayList<T> {

    // Konstruktory
    public XList() {
        super();
    }

    public XList(Collection<? extends T> c) {
        super(c);
    }

    public XList(T... args) {
        super(Arrays.asList(args));
    }
    // Konstruktor przyjmujący listę elementów typu T
    public XList(List<T> list) {
        super(list);
    }

    // Statyczne metody of
    public static <T> XList<T> of(Collection<? extends T> c) {
        return new XList<T>(c);
    }

    public static <T> XList<T> of(T... args) {
        return new XList<T>(args);
    }

    // Pomocnicze metody do tworzenia z napisów
    public static XList<String> charsOf(String s) {
        return new XList<String>(s.split(""));
    }

    public static XList<String> tokensOf(String s) {
        return tokensOf(s, "\\s+");
    }

    public static XList<String> tokensOf(String s, String sep) {
        return new XList<String>(s.split(sep));
    }

    // Metoda union - suma elementów
    public XList<T> union(Collection<? extends T> c) {
        XList<T> result = new XList<T>(this);
        result.addAll(c);
        return result;
    }
    // metoda union dla Array
    public XList<T> union(T[] array) {
        return union(Arrays.asList(array));
    }

    // Metoda diff - różnica elementów
    public XList<T> diff(Collection<? extends T> c) {
        XList<T> result = new XList<T>(this);
        result.removeAll(c);
        return result;
    }

    // Metoda unique - usuwa duplikaty
    public XList<T> unique() {
        return new XList<T>(new LinkedHashSet<T>(this));
    }


    // Metoda collect - zastosowanie funkcji do elementów
    public <R> XList<R> collect(Function<T, R> f) {
        return new XList<R>(this.stream().map(f).collect(Collectors.toList()));
    }

    // Metoda join - połączenie elementów w napis
    public String join() {
        return join("");
    }

    public String join(String sep) {
        return this.stream().map(Object::toString).collect(Collectors.joining(sep));
    }

    // Metoda forEachWithIndex - iteracja z dostępem do indeksów
    public void forEachWithIndex(BiConsumer<T, Integer> consumer) {
        for (int i = 0; i < this.size(); i++) {
            consumer.accept(this.get(i), i);
        }
    }

    // Metoda combine - kombinacje elementów
    public XList<XList<T>> combine() {
        XList<XList<T>> result = new XList<XList<T>>();
        if (this.isEmpty()) {
            result.add(new XList<T>());
            return result;
        }
        XList<T> first = (XList<T>) this.get(0);
        XList<XList<T>> rest = new XList<XList<T>>(this.subList(1, this.size())).combine();
        for (T t : first) {
            for (XList<T> list : rest) {
                XList<T> temp = new XList<T>();
                temp.add(t);
                temp.addAll(list);
                result.add(temp);
            }
        }
        return result;
    }

    // Interfejs funkcyjny do metody forEachWithIndex
    @FunctionalInterface
    interface BiConsumer<T, U> {
        void accept(T t, U u);
    }

}
