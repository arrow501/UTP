/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad1;

import java.util.*;

// Klasa ListCreator z parametrem typu T
public class ListCreator<T> {
    private List<T> list;

    private ListCreator(List<T> list) {
        this.list = list;
    }

    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }

    public ListCreator<T> when(Selector<T> selector) {
        // Nowa lista do przechowywania wybranych elementów
        List<T> newList = new ArrayList<>();
        // Pętla po wszystkich elementach listy
        for (T element : list) {
            if (selector.select(element))  newList.add(element);
        }
        // Zwróć nową instancję klasy ListCreator z nową listą
        return new ListCreator<>(newList);
    }

    // Metoda mapEvery, która przyjmuje mapper jako argument i przekształca każdy element listy za pomocą mappera
    public <R> List<R> mapEvery(Mapper<T, R> mapper) {
        List<R> newList = new ArrayList<>();
        for (T element : list) {
            newList.add(mapper.map(element));
        }
        return newList;
    }
}