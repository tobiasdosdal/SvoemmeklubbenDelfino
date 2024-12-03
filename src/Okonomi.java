import java.sql.ResultSet;

public class Okonomi {
    // Kontingent satser
    private static final double JUNIOR_SATS = 1000.0;    // Under 18 år
    private static final double SENIOR_SATS = 1600.0;    // 18 år og over
    private static final double PASSIV_SATS = 500.0;     // Passivt medlemskab
    private static final double SENIOR_RABAT = 0.25;     // 25% rabat for over 60 år

    public static void beregnKontingent() {
        System.out.print("Indtast medlems-ID: ");
        int medlemId = Integer.parseInt(Main.scanner.nextLine());

        String sql = """
            SELECT p.*, m.alder, m.er_aktiv, m.medlemstype
            FROM person p
            JOIN medlem m ON p.id = m.person_id
            WHERE m.id = ?
            """;

        try {
            ResultSet rs = Main.db.executeQuery(sql, medlemId);
            if (rs.next()) {
                String navn = rs.getString("navn");
                int alder = rs.getInt("alder");
                boolean erAktiv = rs.getBoolean("er_aktiv");

                double kontingent = beregnKontingentForMedlem(alder, erAktiv);

                System.out.println("\nKontingentberegning for " + navn);
                System.out.println("Alder: " + alder);
                System.out.println("Status: " + (erAktiv ? "Aktiv" : "Passiv"));
                System.out.println("Årligt kontingent: " + kontingent + " kr.");
            } else {
                System.out.println("Medlem ikke fundet");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved kontingentberegning: " + e.getMessage());
        }
    }

    public static void beregnSamletKontingent() {
        String sql = """
        SELECT p.navn, m.alder, m.er_aktiv
        FROM person p
        JOIN medlem m ON p.id = m.person_id
        """;

        try {
            ResultSet rs = Main.db.executeQuery(sql);
            double totalKontingent = 0;
            int antalMedlemmer = 0;

            System.out.println("\n=== SAMLET KONTINGENTBEREGNING ===");
            while (rs.next()) {
                String navn = rs.getString("navn");
                int alder = rs.getInt("alder");
                boolean erAktiv = rs.getBoolean("er_aktiv");

                double kontingent = beregnKontingentForMedlem(alder, erAktiv);
                totalKontingent += kontingent;
                antalMedlemmer++;

                System.out.printf("%s (Alder: %d): %.2f kr\n", navn, alder, kontingent);
            }

            System.out.println("\nTotal for alle medlemmer:");
            System.out.printf("Antal medlemmer: %d\n", antalMedlemmer);
            System.out.printf("Samlet årligt kontingent: %.2f kr\n", totalKontingent);

        } catch (Exception e) {
            System.out.println("Fejl ved beregning af samlet kontingent: " + e.getMessage());
        }
    }

    private static double beregnKontingentForMedlem(int alder, boolean erAktiv) {
        if (!erAktiv) {
            return PASSIV_SATS;
        }

        if (alder < 18) {
            return JUNIOR_SATS;
        }

        double kontingent = SENIOR_SATS;
        if (alder >= 60) {
            kontingent = kontingent * (1 - SENIOR_RABAT);
        }

        return kontingent;
    }
}