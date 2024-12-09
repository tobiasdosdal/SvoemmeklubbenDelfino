import java.sql.ResultSet;
import java.util.Date;

public class Resultat {
    // Attributter
    private String disciplin;
    private double tid; // I sekunder
    private Date dato;
    private String kommentar;

    // Konstruktør
    public Resultat(String disciplin, double tid, Date dato, String kommentar) {
        this.disciplin = disciplin;
        this.tid = tid;
        this.dato = dato;
        this.kommentar = kommentar;
    }

    // Metode til at gemme resultat i database
    public void gemResultat(int medlemId) {
        String sql = "INSERT INTO resultat (medlem_id, disciplin, tid, dato, kommentar) VALUES (?, ?, ?, ?, ?)";
        Main.db.executeUpdate(sql, medlemId, disciplin, tid, dato.toString(), kommentar);
    }

    public static void visTop5() {
        String sql = """
        SELECT p.navn, r.disciplin, r.tid, r.dato
        FROM resultat r
        JOIN medlem m ON r.medlem_id = m.person_id
        JOIN person p ON m.person_id = p.id
        ORDER BY r.tid ASC
        LIMIT 5
        """;

        try {
            ResultSet rs = Main.db.executeQuery(sql);
            System.out.println("\nTop 5 hurtigste tider:");
            System.out.println("----------------------------------------");

            while (rs.next()) {
                System.out.printf("Svømmer: %s\n", rs.getString("navn"));
                System.out.printf("Disciplin: %s\n", rs.getString("disciplin"));
                System.out.printf("Tid: %.2f sekunder\n", rs.getDouble("tid"));
                System.out.printf("Dato: %s\n", rs.getString("dato"));
                System.out.println("----------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved visning af top 5: " + e.getMessage());
        }
    }

    public static void visAlleResultater() {
        String sql = """
        SELECT p.navn, r.disciplin, r.tid, r.dato, r.kommentar
        FROM resultat r
        JOIN medlem m ON r.medlem_id = m.person_id
        JOIN person p ON m.person_id = p.id
        ORDER BY r.dato DESC
        """;

        try {
            ResultSet rs = Main.db.executeQuery(sql);
            System.out.println("\nOversigt over alle resultater:");
            System.out.println("----------------------------------------");

            while (rs.next()) {
                System.out.printf("Svømmer: %s\n", rs.getString("navn"));
                System.out.printf("Disciplin: %s\n", rs.getString("disciplin"));
                System.out.printf("Tid: %.2f sekunder\n", rs.getDouble("tid"));
                System.out.printf("Dato: %s\n", rs.getString("dato"));
                System.out.printf("Kommentar: %s\n", rs.getString("kommentar"));
                System.out.println("----------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved visning af resultater: " + e.getMessage());
        }
    }


    public static void registrerStaevneResultat() {
        try {
            System.out.print("\nIndtast medlems-ID: ");
            int medlemId = Integer.parseInt(Main.scanner.nextLine());

            System.out.print("Indtast disciplin (Butterfly/Crawl/Rygcrawl/Brystsvømning): ");
            String disciplin = Main.scanner.nextLine();

            System.out.print("Indtast tid i sekunder (f.eks. 32.5): ");
            double tid = Double.parseDouble(Main.scanner.nextLine());

            System.out.print("Tilføj kommentar: ");
            String kommentar = Main.scanner.nextLine();

            Resultat nytResultat = new Resultat(disciplin, tid, new Date(), kommentar);
            nytResultat.gemResultat(medlemId);

            System.out.println("Resultat registreret succesfuldt!");

        } catch (NumberFormatException e) {
            System.out.println("Fejl: Indtast venligst et gyldigt tal.");
        } catch (Exception e) {
            System.out.println("Fejl ved registrering af resultat: " + e.getMessage());
        }
    }


    // Metode til at opdatere resultatet
    public void opdaterResultat(double nyTid, Date nyDato, String nyKommentar) {
        this.tid = nyTid;
        this.dato = nyDato;
        this.kommentar = nyKommentar;
    }

    // Metode til at hente resultatinformation som string
    public String getResultatInfo() {
        return "Disciplin: " + disciplin +
                ", Tid: " + String.format("%.2f", tid) + " sek" +
                ", Dato: " + dato +
                ", Kommentar: " + kommentar;
    }

    // Metode til at sammenligne tider
    public int sammenlignTid(Resultat andetResultat) {
        if (this.tid < andetResultat.tid) {
            return -1;  // Dette resultat er hurtigere
        } else if (this.tid > andetResultat.tid) {
            return 1;   // Dette resultat er langsommere
        } else {
            return 0;   // Tiderne er ens
        }
    }

    // Getters
    public String getDisciplin() {
        return disciplin;
    }

    public double getTid() {
        return tid;
    }

    public Date getDato() {
        return dato;
    }
}