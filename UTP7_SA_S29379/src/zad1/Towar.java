package zad1;

public class Towar {
    private int id;
    private int weight;

    public Towar(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Towar{" +
            "id=" + id +
            ", weight=" + weight +
            '}';
    }
}
