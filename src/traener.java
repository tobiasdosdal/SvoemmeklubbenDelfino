import java.sql.ResultSet;

/**
 * Klasse til håndtering af trænere i svømmeklubben.
 * Indeholder funktionalitet til oprettelse og administration af trænere.
 */
public class traener {
    /**
     * Opretter en ny træner i systemet ved at indsamle personlige oplysninger
     * og træner-specifikke detaljer fra brugeren.
     * Opretter først en person-record og derefter en tilknyttet træner-record.
     */
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

    /**
     * Opretter en træner-record for en eksisterende person i systemet.
     *
     * @param personId ID på den eksisterende person som skal oprettes som træner
     */
    public static void opretTraener(int personId) {
        System.out.print("Certificeringer: ");
        String cert = Main.scanner.nextLine();
        String insertTraenerSQL = "INSERT INTO traener (person_id, certificeringer) VALUES (?, ?)";
        Main.db.executeUpdate(insertTraenerSQL, personId, cert);
        System.out.println("Traener oprettet succesfuldt!");
    }

    /**
     * Tildeler svømmere til trænerens hold.
     * Bemærk: Denne metode er under udvikling.
     */
    public static void tildelHold() {
        System.out.println("Under udvikling");
    }

    /**
     * Viser en oversigt over alle trænere i systemet.
     * Bemærk: Denne metode er under udvikling.
     */
    public static void visTraenere() {
        System.out.println("Under udvikling");
    }

    /**
     * Håndterer administration af træneres certificeringer.
     * Bemærk: Denne metode er under udvikling.
     */
    public static void administrerCertificeringer() {
        System.out.println("Under udvikling");
    }
}