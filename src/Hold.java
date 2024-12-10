import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Hold klassen repræsenterer et svømmehold i klubben.
 * Klassen håndterer holdadministration, herunder tilmelding af medlemmer,
 * administration af træningstider og holdoversigter.
 */
public class Hold {
    /** Unik identifikator for holdet */
    private int id;

    /** Holdets navn (junior eller senior) */
    private String holdNavn;

    /** Holdets niveau (f.eks. "Højt", "Elite") */
    private String niveau;

    /** Maksimalt antal deltagere på holdet */
    private int maxAntalDeltagere;

    /** Liste over holdets træningstider */
    private ArrayList<String> traeningsTider;

    /** Information om holdet */
    private String holdinfo;

    /** Liste over medlemmer på holdet */
    private ArrayList<Medlem> holdliste;

    /**
     * Opretter et nyt hold med de angivne parametre
     * @param holdNavn Holdets navn
     * @param niveau Holdets niveau
     * @param maxAntalDeltagere Maksimalt antal deltagere
     * @param traeningsTider Liste over træningstider
     */
    public Hold(String holdNavn, String niveau, int maxAntalDeltagere, ArrayList<String> traeningsTider) {
        this.holdNavn = holdNavn;
        this.niveau = niveau;
        this.maxAntalDeltagere = maxAntalDeltagere;
        this.traeningsTider = traeningsTider;
        this.holdliste = new ArrayList<>();
    }

    /**
     * @return Maksimalt antal deltagere på holdet
     */
    public int getMaxAntalDeltagere() {
        return maxAntalDeltagere;
    }

    /**
     * @return Holdets navn
     */
    public String getHoldNavn() {
        return holdNavn;
    }

    /**
     * @return Liste over holdets træningstider
     */
    public ArrayList<String> getTraeningsTider() {
        return traeningsTider;
    }

    /**
     * @return Liste over medlemmer på holdet
     */
    public ArrayList<Medlem> getHoldliste() {
        return holdliste;
    }

    /**
     * Returnerer en streng-repræsentation af holdet
     * @return String med holdets information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(holdNavn).append("\n")
                .append("Holdets træning foregår:\n")
                .append(traeningsTider)
                .append("har maks ")
                .append(maxAntalDeltagere)
                .append(" deltagere.")
                .append("Holdet består af")
                .append(holdliste);
        return sb.toString();
    }

    /**
     * @return Holdets information som string
     */
    public String getHoldinfo() {
        return this.toString();
    }

    /**
     * Tilføjer et medlem til holdet hvis der er plads og alderen passer til holdtypen
     * @param medlem Medlemmet der skal tilføjes
     */
    public void addDeltager(Medlem medlem) {
        if (medlem.getAlder() < 18) {
            if (holdliste.size() < maxAntalDeltagere && holdNavn.contains("Juniorholdet")) {
                holdliste.add(medlem);
                System.out.println(medlem.getName() + " er tilmeldt til Juniorholdet");
            } else {
                System.out.println("Der er ikke plads på Juniorholdet.");
            }
        } else {
            if (holdliste.size() < maxAntalDeltagere && holdNavn.contains("Seniorholdet")) {
                holdliste.add(medlem);
                System.out.println(medlem.getName() + " er tilmeldt til Seniorholdet");
            } else {
                System.out.println("Der er ikke plads på Seniorholdet.");
            }
        }
    }

    /**
     * Fjerner et medlem fra holdet
     * @param medlem Medlemmet der skal fjernes
     */
    public void removeDeltager(Medlem medlem) {
        if (medlem.getAlder() < 18 && holdNavn.contains("Juniorholdet") && holdliste.contains(medlem)) {
            holdliste.remove(medlem);
            System.out.println(medlem.getName() + " er blevet fjernet fra Juniorholdet");
        } else if (holdNavn.contains("Seniorholdet") && holdliste.contains(medlem)) {
            holdliste.remove(medlem);
            System.out.println(medlem.getName() + " er blevet fjernet fra Seniorholdet");
        } else {
            System.out.println("Du har forsøgt at fjerne en person fra deltagerlisten som ikke var på nogen af holdene");
        }
    }

    /**
     * Opretter et nyt hold i databasen med brugerinput
     */
    public static void opretHold() {
        try {
            System.out.println("\nOPRET NYT HOLD");
            System.out.print("Holdnavn: ");
            String holdNavn = Main.scanner.nextLine();

            System.out.print("Niveau: ");
            String niveau = Main.scanner.nextLine();

            System.out.print("Max antal deltagere: ");
            int maxAntal = Integer.parseInt(Main.scanner.nextLine());

            String sql = "INSERT INTO hold (hold_navn, niveau, max_antal_deltagere) VALUES (?, ?, ?)";
            Main.db.executeUpdate(sql, holdNavn, niveau, maxAntal);

            ResultSet rs = Main.db.executeQuery("SELECT last_insert_rowid() as id");
            if (rs.next()) {
                int holdId = rs.getInt("id");
                System.out.println("Indtast træningstider (tom linje når færdig):");
                while (true) {
                    String tid = Main.scanner.nextLine();
                    if (tid.isEmpty()) break;

                    sql = "INSERT INTO traenings_tider (hold_id, tid) VALUES (?, ?)";
                    Main.db.executeUpdate(sql, holdId, tid);
                }
            }
            System.out.println("Hold oprettet succesfuldt!");
        } catch (Exception e) {
            System.out.println("Fejl ved oprettelse af hold: " + e.getMessage());
        }
    }

    /**
     * Viser en oversigt over alle hold med deres information
     */
    public static void visHold() {
        String sql = """
            SELECT h.*, GROUP_CONCAT(t.tid) as tider,
                   COUNT(DISTINCT hm.medlem_id) as antal_medlemmer
            FROM hold h
            LEFT JOIN traenings_tider t ON h.id = t.hold_id
            LEFT JOIN hold_medlem hm ON h.id = hm.hold_id
            GROUP BY h.id
            """;

        try {
            ResultSet rs = Main.db.executeQuery(sql);
            System.out.println("\nHoldoversigt:");
            System.out.println("----------------------------------------");

            while (rs.next()) {
                System.out.printf("Hold: %s\n", rs.getString("hold_navn"));
                System.out.printf("Niveau: %s\n", rs.getString("niveau"));
                System.out.printf("Max antal: %d\n", rs.getInt("max_antal_deltagere"));
                System.out.printf("Nuværende antal: %d\n", rs.getInt("antal_medlemmer"));
                System.out.printf("Træningstider: %s\n", rs.getString("tider"));
                System.out.println("----------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved visning af hold: " + e.getMessage());
        }
    }

    /**
     * Tilmelder et medlem til et specifikt hold hvis der er plads
     */
    public static void tilmeldMedlemTilHold() {
        try {
            System.out.print("\nIndtast medlem-ID: ");
            int medlemId = Integer.parseInt(Main.scanner.nextLine());

            System.out.print("Indtast hold-ID: ");
            int holdId = Integer.parseInt(Main.scanner.nextLine());

            String checkSql = """
                SELECT h.max_antal_deltagere, COUNT(hm.medlem_id) as antal_medlemmer
                FROM hold h
                LEFT JOIN hold_medlem hm ON h.id = hm.hold_id
                WHERE h.id = ?
                GROUP BY h.id
                """;

            ResultSet rs = Main.db.executeQuery(checkSql, holdId);
            if (rs.next()) {
                int maxAntal = rs.getInt("max_antal_deltagere");
                int nuværendeAntal = rs.getInt("antal_medlemmer");

                if (nuværendeAntal < maxAntal) {
                    String sql = "INSERT INTO hold_medlem (hold_id, medlem_id) VALUES (?, ?)";
                    Main.db.executeUpdate(sql, holdId, medlemId);
                    System.out.println("Medlem tilmeldt holdet!");
                } else {
                    System.out.println("Holdet er fuldt!");
                }
            }
        } catch (Exception e) {
            System.out.println("Fejl ved tilmelding til hold: " + e.getMessage());
        }
    }
}