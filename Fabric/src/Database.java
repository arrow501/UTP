import java.util.HashMap;
import java.util.Map;

class Database{
    private Database(){}
    private static Map<Integer, String> pesels = Map.of(
            11111,"99010188888",
            22222,"99080877777",
            44444,"99040466666",
            55555,"99020255555"
    );

    public static void savePeselAndEska(String pesel, String eska){
        pesels = new HashMap<>(pesels);

        pesels.put(Integer.parseInt(eska.substring(1)), pesel);
    }

    public static String getPesel(String eska) {
        return pesels.get(Integer.valueOf(eska.substring(1)));
    }

    public static String nextStudentNumber() {
        return "s"+pesels.keySet().stream().max(Integer::compareTo).map(s->s+1).orElse(1);
    }


    public static void printContent() {
        System.out.println("_______________________");
        System.out.println("| Eska   | PESEL       |");
        pesels.forEach((k,v)-> System.out.printf("| %6s | %11s |%n","s"+k,v));
        System.out.println("_______________________");
    }
}