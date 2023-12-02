package zad2;

public class StringTask implements Runnable {
    public enum State {
        CREATED,
        RUNNING,
        ABORTED,
        READY,
    }

    private final String word;
    private final int repeats;

    private State state;
    private String result;
// Q: should i use volatile?
// A: Yes, because it is used by multiple threads.
    private Thread running;

    public StringTask(String word, int repeats) {
        this.state = State.CREATED;

        this.word = word;
        this.repeats = repeats;
        this.result = "";

    }

    @Override
    public void run() {
        state = State.RUNNING;
        try {
            for (int i = 0; i < repeats; i++) {
                if (Thread.interrupted())
                    throw new InterruptedException();
                result = result + word;
            }
            state = State.READY;
        } catch (InterruptedException e) {
            state = State.ABORTED;
        }
    }


    public String getResult() {
        return result;
    }

    public State getState() {
        return state;

    }

    public void start() {
        running = new Thread(this);
        running.start();
    }

    public void abort() throws InterruptedException {
        if (running != null) {
            running.interrupt();
        }
    }

    public boolean isDone() {
        return state == State.ABORTED || state == State.READY;
    }

    public String getWord() {
        return word;
    }

}