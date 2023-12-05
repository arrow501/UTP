/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad1;


import java.io.File;
import java.util.List;
import java.util.Queue;

public class Main {

  public static final File FILE = new File("../towary.txt");
  volatile static Boolean readingDone = false;
  volatile static int sum = 0;

  public static void main(String[] args) {
    Queue<Towar> towary = new java.util.concurrent.ConcurrentLinkedQueue<>();

    Thread A = new Thread(new TowarReader(towary));
    Thread B = new Thread(new TowarAuditor(towary));

    A.start();
    B.start();
  }
}
