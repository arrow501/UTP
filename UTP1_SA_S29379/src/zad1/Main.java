/**
 *
 *  @author Święch Aleksander S29379
 *
 */
package  zad1;

import java.util.*;

public class Main {
  public Main() {
    List<Integer> src1 = Arrays.asList(1, 7, 9, 11, 12);
    System.out.println(test1(src1));

    List<String> src2 = Arrays.asList("a", "zzzz", "vvvvvvv" );
    System.out.println(test2(src2));
  }

  public List<Integer> test1(List<Integer> src) {
    // Definicja selektora bez lambda-wyrażeń; nazwa zmiennej sel
    Selector<Integer> sel = new Selector<Integer>() {
      @Override
      public boolean select(Integer arg) {
        return arg < 10;
      }
    };

    // Definicja mappera bez lambda-wyrażeń; nazwa zmiennej map
    Mapper<Integer, Integer> map = new Mapper<Integer, Integer>() {
      @Override
      public Integer map(Integer arg) {
        return arg + 10;
      }
    };

    // Zwrot wyniku uzyskanego przez wywołanie statycznej metody klasy ListCreator
    return ListCreator.collectFrom(src).when(sel).mapEvery(map);
  }

  public List<Integer> test2(List<String> src) {
    // Definicja selektora bez lambda-wyrażeń; nazwa zmiennej sel
    Selector<String> sel = new Selector<String>() {
      @Override
      public boolean select(String arg) {
        return arg.length() > 3;
      }
    };

    // Definicja mappera bez lambda-wyrażeń; nazwa zmiennej map
    Mapper<String, Integer> map = new Mapper<String, Integer>() {
      @Override
      public Integer map(String arg) {
        return arg.length() + 10;
      }
    };

    // Zwrot wyniku uzyskanego przez wywołanie statycznej metody klasy ListCreator:
    return ListCreator.collectFrom(src).when(sel).mapEvery(map);
  }

  public static void main(String[] args) {
    new Main();
  }
}