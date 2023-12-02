/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad1;


public class Main {

  public static void main(String[] args) throws InterruptedException {
    Letters letters = new Letters("ABCD");
    for (Thread t : letters.getThreads()) System.out.println(t.getName());
    // Q: What is a thread name for?
    // A: It is used to identify the thread.
    // Q: Should i set the name or let it set automatically?
    // A: It is better to set it manually, because it is easier to identify the thread.
    /*<- tu uruchomić
         wszystkie kody w wątkach 
     */
    for (Thread t : letters.getThreads()) t.start();

    Thread.sleep(5000);

    /*<- tu trzeba zapisać
       fragment, który kończy działanie kodów, wypisujących litery 
    */
    for (Thread t : letters.getThreads()) t.interrupt();

    System.out.println("\nProgram skończył działanie");
  }

}
