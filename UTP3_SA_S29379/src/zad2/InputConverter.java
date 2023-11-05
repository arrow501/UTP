package zad2;


import java.util.function.Function;

public class InputConverter<T> {
    private T data; // pole przechowujące dane wejściowe

    public InputConverter(T data) { // konstruktor przyjmujący dane wejściowe
        this.data = data;
    }

    public <R> R convertBy(Function... functions) { // metoda przyjmująca dowolną liczbę funkcji
        Object result = data; // zmienna przechowująca wynik, początkowo ustawiona na dane wejściowe
        for (Function f : functions) { // pętla po wszystkich funkcjach
            result = f.apply(result); // wywołanie funkcji na aktualnym wyniku i zapisanie nowego wyniku
        }
        return (R) result; // zwrócenie wyniku po rzutowaniu na odpowiedni typ
    }
}
