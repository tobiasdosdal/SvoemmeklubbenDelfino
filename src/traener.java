import java.sql.ResultSet;

public class traener {
    public static void opretTraener() {
        // Opret person først
        System.out.println("\nOPRET NY TRAENER");
        System.out.print("Navn: ");
        String navn = Main.scanner.nextLine();

        System.out.print("Email: ");
        String email = Main.scanner.nextLine();

        System.out.print("Telefon: ");
        String telefon = Main.scanner.nextLine();

        System.out.print("Adresse: ");
        String adresse = Main.scanner.nextLine();

        // Indsæt i person tabel først
        String insertPersonSQL = "INSERT INTO person (navn, email, telefon, adresse) VALUES (?, ?, ?, ?)";
        Main.db.executeUpdate(insertPersonSQL, navn, email, telefon, adresse);

        // Hent det oprettede person ID
        ResultSet rs = Main.db.executeQuery("SELECT last_insert_rowid() as id");
        try {
            if (rs.next()) {
                int personId = rs.getInt("id");

                // Spørg om certificeringer og opret træner
                System.out.print("Certificeringer: ");
                String cert = Main.scanner.nextLine();
                String insertTraenerSQL = "INSERT INTO traener (person_id, certificeringer) VALUES (?, ?)";
                Main.db.executeUpdate(insertTraenerSQL, personId, cert);

                System.out.println("Traener oprettet succesfuldt!");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved oprettelse af traener: " + e.getMessage());
        }
    }

    public static void opretTraener(int personId) {
        System.out.print("Certificeringer: ");
        String cert = Main.scanner.nextLine();
        String insertTraenerSQL = "INSERT INTO traener (person_id, certificeringer) VALUES (?, ?)";
        Main.db.executeUpdate(insertTraenerSQL, personId, cert);
        System.out.println("Traener oprettet succesfuldt!");
    }

    // Tilføj de andre metoder der kaldes i Main
    public static void tildelHold() {
        System.out.println("Under udvikling");
    }

    public static void visTraenere() {
        System.out.println("Under udvikling");
    }

    public static void administrerCertificeringer() {
        System.out.println("Under udvikling");
    }
}