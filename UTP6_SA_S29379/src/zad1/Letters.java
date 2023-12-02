package zad1;

public class Letters {
    private final String letters;
    private final Thread[] threads;

    public Letters(String letters) {
        this.letters = letters;
        this.threads = new Thread[letters.length()];
        for (int i = 0; i < letters.length(); i++) {
            threads[i] = new Thread(new LetterRunnable(letters.charAt(i)));
            threads[i].setName("Thread " + letters.charAt(i));
        }
    }
    private static class LetterRunnable implements Runnable {
        private char letter;

        public LetterRunnable(char letter) {
            this.letter = letter;
        }

        @Override
        public void run() {
            while (true) {
                System.out.print(letter);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
    public Thread[] getThreads() {
        return threads;
    }
}
