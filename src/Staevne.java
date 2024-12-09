import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Staevne {
    private int id;
    private String navn;
    private Date dato;
    private String lokation;
    private String[] discipliner;
    private boolean erAabenForTilmelding;
    private ArrayList<String> deltagere;
    private List<String> resultater;

    public Staevne(String navn, Date dato, String lokation, String[] discipliner, boolean erAabenForTilmelding) {
        this.navn = navn;
        this.dato = dato;
        this.lokation = lokation;
        this.discipliner = discipliner;
        this.erAabenForTilmelding = erAabenForTilmelding;
        this.deltagere = new ArrayList<>();
        this.resultater = new ArrayList<>();
    }

    public static void opretStaevne() {
        try {
            System.out.println("\nOPRET NYT STAEVNE");
            System.out.print("Navn: ");
            String navn = Main.scanner.nextLine();

            System.out.print("Lokation: ");
            String lokation = Main.scanner.nextLine();

            System.out.print("Discipliner (adskilt med komma): ");
            String disciplinerInput = Main.scanner.nextLine();
            String[] discipliner = disciplinerInput.split(",");

            String sql = "INSERT INTO staevne (navn, dato, lokation, discipliner, er_aaben) VALUES (?, ?, ?, ?, ?)";
            Main.db.executeUpdate(sql, navn, new Date().toString(), lokation, String.join(",", discipliner), true);

            System.out.println("Stævne oprettet succesfuldt!");
        } catch (Exception e) {
            System.out.println("Fejl ved oprettelse af stævne: " + e.getMessage());
        }
    }

    public static void tilmeldStaevne() {
        try {
            System.out.print("\nIndtast medlems-ID: ");
            int medlemId = Integer.parseInt(Main.scanner.nextLine());

            System.out.print("Indtast stævne-ID: ");
            int staevneId = Integer.parseInt(Main.scanner.nextLine());

            // Check om stævnet er åbent for tilmelding
            String checkSql = "SELECT er_aaben FROM staevne WHERE id = ?";
            ResultSet rs = Main.db.executeQuery(checkSql, staevneId);

            if (rs.next() && rs.getBoolean("er_aaben")) {
                String sql = "INSERT INTO staevne_deltagere (staevne_id, medlem_id) VALUES (?, ?)";
                Main.db.executeUpdate(sql, staevneId, medlemId);
                System.out.println("Tilmelding gennemført!");
            } else {
                System.out.println("Stævnet er ikke åbent for tilmelding.");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved tilmelding: " + e.getMessage());
        }
    }

    public static void visAktiveStaevner() {
        String sql = """
            SELECT s.*, COUNT(sd.medlem_id) as antal_deltagere 
            FROM staevne s 
            LEFT JOIN staevne_deltagere sd ON s.id = sd.staevne_id 
            WHERE s.dato >= DATE('now') 
            GROUP BY s.id
            """;

        try {
            ResultSet rs = Main.db.executeQuery(sql);
            System.out.println("\nAktive stævner:");
            System.out.println("----------------------------------------");

            while (rs.next()) {
                System.out.printf("ID: %d\n", rs.getInt("id"));
                System.out.printf("Navn: %s\n", rs.getString("navn"));
                System.out.printf("Dato: %s\n", rs.getString("dato"));
                System.out.printf("Lokation: %s\n", rs.getString("lokation"));
                System.out.printf("Discipliner: %s\n", rs.getString("discipliner"));
                System.out.printf("Antal deltagere: %d\n", rs.getInt("antal_deltagere"));
                System.out.printf("Åben for tilmelding: %s\n", rs.getBoolean("er_aaben") ? "Ja" : "Nej");
                System.out.println("----------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved visning af stævner: " + e.getMessage());
        }
    }

    // Hjælpemetoder til at hente data fra databasen
    public List<String> getStaevneResultater() {
        List<String> resultater = new ArrayList<>();
        String sql = """
            SELECT p.navn, r.disciplin, r.tid, r.placering 
            FROM staevne_resultater r 
            JOIN medlem m ON r.medlem_id = m.id 
            JOIN person p ON m.person_id = p.id 
            WHERE r.staevne_id = ?
            """;

        try {
            ResultSet rs = Main.db.executeQuery(sql, this.id);
            while (rs.next()) {
                String resultat = String.format("Deltager: %s, Disciplin: %s, Tid: %s, Placering: %d",
                        rs.getString("navn"),
                        rs.getString("disciplin"),
                        rs.getString("tid"),
                        rs.getInt("placering"));
                resultater.add(resultat);
            }
        } catch (Exception e) {
            System.out.println("Fejl ved hentning af resultater: " + e.getMessage());
        }
        return resultater;
    }

    public String getStaevneInfo() {
        return "Navn: " + navn +
                ", Dato: " + dato +
                ", Lokation: " + lokation +
                ", Discipliner: " + String.join(", ", discipliner) +
                ", Antal Deltagere: " + getAntalDeltagere() +
                ", Åben for Tilmelding: " + erAabenForTilmelding;
    }

    public int getAntalDeltagere() {
        String sql = "SELECT COUNT(*) as antal FROM staevne_deltagere WHERE staevne_id = ?";
        try {
            ResultSet rs = Main.db.executeQuery(sql, this.id);
            if (rs.next()) {
                return rs.getInt("antal");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved optælling af deltagere: " + e.getMessage());
        }
        return 0;
    }
}