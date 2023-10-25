public class Fabric {
    public static void main(String[] args) {
        //Jak wyświetlić zawartość bazy danych?
        Database.printContent();

        //Jak pobrać PESEL istniejącego studenta?
        System.out.println(Database.getPesel("s11111"));

        //Jak wygenerować kolejny numer studenta?
        System.out.println(Database.nextStudentNumber());
        //Jeśli nie stworzymy studenta z taką eską, to numer nadal jest dostępny:
        System.out.println(Database.nextStudentNumber());

        //Po stworzeniu nowego studenta powinniśmy zapisać jego dane do bazy danych:
        Database.savePeselAndEska("84111192042","s55556");

        //Po zapisaniu studenta do bazy danych jest ona aktualizowana:
        Database.printContent();

        //A metoda nextStudentNumber() zwraca inną wartość niż poprzednio
        System.out.println(Database.nextStudentNumber());
    }
}

/**
 * Dokończ implementację klasy Student tak, aby możliwe było tworzenie studenta na podstawie
 *  - tylko numeru PESEL:
 *      - generowana jest nowa "eska" za pomocą metody Database.nextStudentNumber()
 *      - wygenerowana eska oraz numer pesel powinny być zapisane do bazy danych (Database.savePeselAndEska())
 *
 *  - tylko "eski" -> wtedy numer PESEL pobierany jest z bazy danych za pomocą metody Database.getPesel()
 *
 *  Nie powinien istnieć sposób na tworzenie studenta na podstawie PESELU oraz eski
 */
class Student {
    public Student(String eska){
        this.pesel = Database.getPesel(eska);
        if(pesel)
    }
    private String eska;
    private String pesel;
}