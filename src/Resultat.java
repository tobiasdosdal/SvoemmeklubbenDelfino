import java.sql.ResultSet;
import java.util.Date;

/**
 * Klasse til håndtering af svømmeresultater.
 * Håndterer registrering, visning og sammenligning af svømmetider.
 */
public class Resultat {
    /** Svømmedisciplin (Butterfly/Crawl/Rygcrawl/Brystsvømning) */
    private String disciplin;

    /** Tiden i sekunder */
    private double tid;

    /** Dato for resultatet */
    private Date dato;

    /** Eventuelle kommentarer til resultatet */
    private String kommentar;

    /**
     * Opretter et nyt resultat
     * @param disciplin Svømmedisciplinen
     * @param tid Tiden i sekunder
     * @param dato Datoen for resultatet
     * @param kommentar Eventuelle kommentarer
     */
    public Resultat(String disciplin, double tid, Date dato, String kommentar) {
        this.disciplin = disciplin;
        this.tid = tid;
        this.dato = dato;
        this.kommentar = kommentar;
    }

    /**
     * Gemmer resultatet i databasen
     * @param medlemId ID på det medlem resultatet tilhører
     */
    public void gemResultat(int medlemId) {
        String sql = "INSERT INTO resultat (medlem_id, disciplin, tid, dato, kommentar) VALUES (?, ?, ?, ?, ?)";
        Main.db.executeUpdate(sql, medlemId, disciplin, tid, dato.toString(), kommentar);
    }

    /**
     * Viser de 5 hurtigste tider på tværs af alle discipliner
     */
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

    /**
     * Viser alle registrerede resultater sorteret efter dato
     */
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

    /**
     * Registrerer et nyt stævneresultat ved at indsamle information fra brugeren
     */
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

    /**
     * Opdaterer et eksisterende resultat med nye værdier
     * @param nyTid Den nye tid i sekunder
     * @param nyDato Den nye dato
     * @param nyKommentar Den nye kommentar
     */
    public void opdaterResultat(double nyTid, Date nyDato, String nyKommentar) {
        this.tid = nyTid;
        this.dato = nyDato;
        this.kommentar = nyKommentar;
    }

    /**
     * Returnerer en streng med al information om resultatet
     * @return String med resultatinformation
     */
    public String getResultatInfo() {
        return "Disciplin: " + disciplin +
                ", Tid: " + String.format("%.2f", tid) + " sek" +
                ", Dato: " + dato +
                ", Kommentar: " + kommentar;
    }

    /**
     * Sammenligner dette resultat med et andet resultat
     * @param andetResultat Resultatet der sammenlignes med
     * @return -1 hvis dette resultat er hurtigere, 1 hvis langsommere, 0 hvis ens
     */
    public int sammenlignTid(Resultat andetResultat) {
        if (this.tid < andetResultat.tid) {
            return -1;  // Dette resultat er hurtigere
        } else if (this.tid > andetResultat.tid) {
            return 1;   // Dette resultat er langsommere
        } else {
            return 0;   // Tiderne er ens
        }
    }

    /**
     * @return Svømmedisciplinen
     */
    public String getDisciplin() {
        return disciplin;
    }

    /**
     * @return Tiden i sekunder
     */
    public double getTid() {
        return tid;
    }

    /**
     * @return Datoen for resultatet
     */
    public Date getDato() {
        return dato;
    }
}