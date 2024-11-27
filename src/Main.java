import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Database db = new Database();

    public static void main(String[] args) {
        if (db.testConnection()) {
            System.out.println("Database forbindelse oprettet succesfuldt!");
            boolean kørProgram = true;

            while (kørProgram) {
                System.out.println("\n=== SVØMMEKLUBBEN DELFINEN ===");
                System.out.println("1. Opret person");
                System.out.println("2. Vis personer");
                System.out.println("3. Afslut");
                System.out.print("Vælg: ");

                String valg = scanner.nextLine();

                switch (valg) {
                    case "1" -> opretPerson();
                    case "2" -> visPersoner();
                    case "3" -> {
                        kørProgram = false;
                        db.closeConnection();
                    }
                }
            }
        }
    }

    private static void opretPerson() {
        // Opret person
        System.out.println("\nOPRET NY PERSON");
        System.out.print("Navn: ");
        String navn = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Telefon: ");
        String telefon = scanner.nextLine();

        System.out.print("Adresse: ");
        String adresse = scanner.nextLine();

        // Indsæt i person tabel
        String insertPersonSQL = "INSERT INTO person (navn, email, telefon, adresse) VALUES (?, ?, ?, ?)";
        db.executeUpdate(insertPersonSQL, navn, email, telefon, adresse);

        // Hent det oprettede person ID
        ResultSet rs = db.executeQuery("SELECT last_insert_rowid() as id");
        try {
            if (rs.next()) {
                int personId = rs.getInt("id");

                System.out.println("\nVælg type:");
                System.out.println("1. Motionist");
                System.out.println("2. Konkurrencesvømmer");
                System.out.println("3. Træner");
                System.out.print("Vælg (1-3): ");

                switch (scanner.nextLine()) {
                    case "1", "2" -> opretMedlem(personId, scanner.nextLine().equals("2"));
                    case "3" -> opretTræner(personId);
                    default -> System.out.println("Ugyldigt valg");
                }
            }
        } catch (Exception e) {
            System.out.println("Fejl ved oprettelse: " + e.getMessage());
        }
    }

    private static void opretMedlem(int personId, boolean erKonkurrence) {
        System.out.print("Alder: ");
        int alder = Integer.parseInt(scanner.nextLine());

        String medlemstype = erKonkurrence ? "KONKURRENCE" : "MOTIONIST";
        String insertMedlemSQL = "INSERT INTO medlem (person_id, alder, indmeldelsesdato, medlemstype) VALUES (?, ?, ?, ?)";
        db.executeUpdate(insertMedlemSQL, personId, alder, LocalDate.now().toString(), medlemstype);

        // Hvis det er en konkurrencesvømmer
        if (erKonkurrence) {
            ResultSet rs = db.executeQuery("SELECT last_insert_rowid() as id");
            try {
                if (rs.next()) {
                    int medlemId = rs.getInt("id");
                    System.out.print("Hold (Junior/Senior): ");
                    String hold = scanner.nextLine().toUpperCase();
                    String insertKonkurrenceSQL = "INSERT INTO konkurrencesvoemmer (medlem_id, hold) VALUES (?, ?)";
                    db.executeUpdate(insertKonkurrenceSQL, medlemId, hold);
                }
            } catch (Exception e) {
                System.out.println("Fejl ved oprettelse af konkurrencesvømmer");
            }
        }
    }

    private static void opretTræner(int personId) {
        System.out.print("Certificeringer: ");
        String cert = scanner.nextLine();
        String insertTrænerSQL = "INSERT INTO traener (person_id, certificeringer) VALUES (?, ?)";
        db.executeUpdate(insertTrænerSQL, personId, cert);
    }

    private static void visPersoner() {
        String sql = """
            SELECT p.*, m.medlemstype, m.alder, t.certificeringer 
            FROM person p 
            LEFT JOIN medlem m ON p.id = m.person_id 
            LEFT JOIN traener t ON p.id = t.person_id
            """;

        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs.next()) {
                System.out.println("\nID: " + rs.getInt("id"));
                System.out.println("Navn: " + rs.getString("navn"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Telefon: " + rs.getString("telefon"));

                String medlemstype = rs.getString("medlemstype");
                if (medlemstype != null) {
                    System.out.println("Type: " + medlemstype);
                    System.out.println("Alder: " + rs.getInt("alder"));
                }

                String cert = rs.getString("certificeringer");
                if (cert != null) {
                    System.out.println("Type: TRÆNER");
                    System.out.println("Certificeringer: " + cert);
                }
                System.out.println("---------------");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved visning af personer: " + e.getMessage());
        }
    }
}