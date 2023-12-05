package zad1;

import java.util.Queue;

public class TowarAuditor implements Runnable {
    private final Queue<Towar> towary;

    public TowarAuditor(Queue<Towar> towary) {
        super();
        this.towary = towary;
    }

    @Override
    public void run() {
        int sum = 0;
        int count = 0;
        Towar towar;
        // use a while loop instead of a do-while loop
        while (!Main.readingDone || !towary.isEmpty()) {
            // no need to synchronize the queue
            towar = towary.poll();
            if (towar != null) {
                sum += towar.getWeight();
                if( ++count % 100 == 0){
                    System.out.println("policzono wage " + count + " towarów");
                }
            }
        }
        Main.sum = sum;
        System.out.println("Suma wag: " + sum);
    }

}