package zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public class ListCreator<T> {
  private List<T> list;

  private ListCreator(List<T> list) {
    this.list = list;
  }

  public static <T> ListCreator<T> collectFrom(List<T> list) {
    return new ListCreator<>(list);
  }

  public ListCreator<T> when(Predicate<T> condition) {
    List<T> filteredList = new ArrayList <> ();
    for (T element : list) {
      if (condition.test (element)) {
        filteredList.add (element);
      }
    }
    return new ListCreator <> (filteredList);
  }

  public <R> List<R> mapEvery(Function<T,R> transformer) {
    List<R> transformedList = new ArrayList <> ();
    for (T element : list) {
      transformedList.add (transformer.apply (element));
    }
    return transformedList;
  }
}

