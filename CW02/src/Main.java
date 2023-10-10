public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

class Human {
    final private String name;

    public Human(String name) {
        this.name = name;

    }

    @Override
    public String toString() {
        return "Human(" + name + ")";
    }

    public String getName() {
        return name;
    }
}

class HumanCage {
    private Human cagedHuman;

    public Human getHuman() {
        return cagedHuman;
    }

    public Human takeHuman() {
        Human h = getHuman();
        cagedHuman = null;
        return h;
    }

    @Override
    public String toString() {
        return "HumanCage{" +
                "cagedHuman=" + cagedHuman +
                '}';
    }
}