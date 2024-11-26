public class Kontingent {
    // Konstant priser
    private static final int JUNIOR_AKTIV_PRIS = 1000;
    private static final int SENIOR_AKTIV_PRIS = 1600;
    private static final int PASSIV_PRIS = 500;
    private static final double SENIOR_RABAT = 0.25; // 25% rabat

    /**
     * Beregner det årlige kontingent for et medlem baseret på alder og aktivitetsstatus.
     *
     * @param alder          Medlemmets alder
     * @param erAktivtMedlem Om medlemmet er aktivt eller passivt
     * @return Det beregnede kontingent
     */
    public static int beregnKontingent(int alder, boolean erAktivtMedlem) {
        if (!erAktivtMedlem) {
            return PASSIV_PRIS; // Passivt medlemskab
        }

        if (alder < 18) {
            return JUNIOR_AKTIV_PRIS; // Juniorpris for aktive medlemmer
        } else if (alder >= 60) {
            // Seniorpris med rabat for medlemmer over 60 år
            return (int) (SENIOR_AKTIV_PRIS * (1 - SENIOR_RABAT));
        } else {
            return SENIOR_AKTIV_PRIS; // Normal seniorpris
        }
    }
}

//test public class Main {
//    public static void main(String[] args) {
//        // Test cases
//        System.out.println("Junior aktiv (16 år): " + Kontingent.beregnKontingent(16, true)); // 1000
//        System.out.println("Senior aktiv (65 år): " + Kontingent.beregnKontingent(65, true)); // 1200
//        System.out.println("Senior aktiv (40 år): " + Kontingent.beregnKontingent(40, true)); // 1600
//        System.out.println("Passivt medlemskab (30 år): " + Kontingent.beregnKontingent(30, false)); // 500
//    }
//}