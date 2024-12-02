import java.sql.ResultSet;
import java.time.LocalDate;

public abstract class Person {
    protected String name;
    protected String email;
    protected String telefon;
    protected String adresse;

    public Person(String name, String email, String telefon, String adresse) {
        this.name = name;
        this.email = email;
        this.telefon = telefon;
        this.adresse = adresse;
    }

    public static void opretPerson() {
        System.out.println("\nOPRET NY PERSON");
        System.out.print("Navn: ");
        String navn = Main.scanner.nextLine();
        System.out.print("Email: ");
        String email = Main.scanner.nextLine();
        System.out.print("Telefon: ");
        String telefon = Main.scanner.nextLine();
        System.out.print("Adresse: ");
        String adresse = Main.scanner.nextLine();

        String insertPersonSQL = "INSERT INTO person (navn, email, telefon, adresse) VALUES (?, ?, ?, ?)";
        Main.db.executeUpdate(insertPersonSQL, navn, email, telefon, adresse);

        ResultSet rs = Main.db.executeQuery("SELECT last_insert_rowid() as id");
        try {
            if (rs.next()) {
                int personId = rs.getInt("id");
                System.out.println("\n1. Motionist");
                System.out.println("2. Konkurrencesvømmer");
                System.out.println("3. Træner");
                System.out.print("Vælg type (1-3): ");

                switch (Main.scanner.nextLine()) {
                    case "1" -> opretMedlem(personId, false);
                    case "2" -> opretMedlem(personId, true);
                    case "3" -> traener.opretTraener(personId);
                    default -> System.out.println("Ugyldigt valg");
                }
            }
        } catch (Exception e) {
            System.out.println("Fejl ved oprettelse: " + e.getMessage());
        }
    }

    private static void opretMedlem(int personId, boolean erKonkurrence) {
        System.out.print("Alder: ");
        int alder = Integer.parseInt(Main.scanner.nextLine());
        String medlemstype = erKonkurrence ? "KONKURRENCE" : "MOTIONIST";
        String sql = "INSERT INTO medlem (person_id, alder, indmeldelsesdato, medlemstype) VALUES (?, ?, ?, ?)";
        Main.db.executeUpdate(sql, personId, alder, LocalDate.now().toString(), medlemstype);

        if (erKonkurrence) {
            try {
                ResultSet rs = Main.db.executeQuery("SELECT last_insert_rowid() as id");
                if (rs.next()) {
                    // Bestem hold automatisk baseret på alder
                    String hold = (alder < 18) ? "JUNIOR" : "SENIOR";
                    sql = "INSERT INTO konkurrencesvoemmer (medlem_id, hold) VALUES (?, ?)";
                    Main.db.executeUpdate(sql, rs.getInt("id"), hold);
                    System.out.println("Konkurrencesvømmer oprettet på " + hold + " holdet");
                }
            } catch (Exception e) {
                System.out.println("Fejl ved oprettelse af konkurrencesvømmer");
            }
        }
    }

    public static void visPersoner() {
        String sql = "SELECT p.*, m.medlemstype, m.alder, t.certificeringer FROM person p " +
                "LEFT JOIN medlem m ON p.id = m.person_id " +
                "LEFT JOIN traener t ON p.id = t.person_id";

        ResultSet rs = Main.db.executeQuery(sql);
        try {
            while (rs.next()) {
                System.out.println("\nID: " + rs.getInt("id"));
                System.out.println("Navn: " + rs.getString("navn"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Telefon: " + rs.getString("telefon"));
                if (rs.getString("medlemstype") != null) {
                    System.out.println("Type: " + rs.getString("medlemstype"));
                    System.out.println("Alder: " + rs.getInt("alder"));
                }
                if (rs.getString("certificeringer") != null) {
                    System.out.println("Type: TRAENER");
                    System.out.println("Certificeringer: " + rs.getString("certificeringer"));
                }
                System.out.println("---------------");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved visning af personer: " + e.getMessage());
        }
    }
}