// Import af nødvendige biblioteker for database, dato og scanner funktionalitet
import java.util.Scanner;

/**
 * Hovedklassen for Svømmeklubben Delfinens administrationssystem.
 * Håndterer alle primære menuer og programflow.
 */
public class Main {
    // Globale objekter for input og database
    public static Scanner scanner = new Scanner(System.in);
    public static Database db = new Database();

    /**
     * Main metoden - programmets startpunkt
     * Initialiserer databaseforbindelse og kører hovedmenuen
     */
    public static void main(String[] args) {
        // Test af databaseforbindelse
        if (db.testConnection()) {
            System.out.println("Database forbindelse oprettet succesfuldt!");
            boolean korProgram = true;

            // Hovedløkke for programmet
            while (korProgram) {
                System.out.println("\n=== SVOMMEKLUBBEN DELFINEN ===");
                visHovedMenu();
                String valg = scanner.nextLine();

                // Switch til håndtering af brugerens menuvalg
                switch (valg) {
                    case "1" -> medlemsAdministrationMenu();
                    case "2" -> traenerAdministrationMenu();
                    case "3" -> holdOgAktiviteterMenu();
                    case "4" -> konkurrenceOgResultaterMenu();
                    case "5" -> okonomiMenu();
                    case "6" -> {
                        korProgram = false;
                        db.closeConnection();
                        System.out.println("Programmet afsluttes. Farvel!");
                    }
                    default -> System.out.println("Ugyldigt valg. Prov igen.");
                }
            }
        }
    }

    /**
     * Viser hovedmenuen med alle tilgængelige muligheder
     */
    private static void visHovedMenu() {
        System.out.println("1. Medlemsadministration");
        System.out.println("2. Traeneradministration");
        System.out.println("3. Hold og aktiviteter");
        System.out.println("4. Konkurrence og resultater");
        System.out.println("5. Okonomi");
        System.out.println("6. Afslut");
        System.out.print("Vaelg (1-6): ");
    }

    /**
     * Undermenu for medlemsadministration
     * Håndterer CRUD-operationer for medlemmer
     */
    private static void medlemsAdministrationMenu() {
        while (true) {
            System.out.println("\n=== MEDLEMSADMINISTRATION ===");
            System.out.println("1. Opret nyt medlem");
            System.out.println("2. Rediger medlem (ikke kodet)");
            System.out.println("3. Vis alle medlemmer");
            System.out.println("4. Slet medlem (ikke kodet)");
            System.out.println("5. Kontaktinformation (ikke kodet)");
            System.out.println("6. Tilbage til hovedmenu");
            System.out.print("Vaelg (1-6): ");

            String valg = scanner.nextLine();
            if (valg.equals("6")) break;  // Returnér til hovedmenu

            // Håndtering af brugervalg
            switch (valg) {
                case "1" -> Person.opretPerson();
                //case "2" -> Person.redigerPerson();  // Ikke implementeret endnu
                case "3" -> Person.visPersoner();
                //case "4" -> Person.sletPerson();     // Ikke implementeret endnu
                //case "5" -> Person.visKontaktinfo(); // Ikke implementeret endnu
                default -> System.out.println("Ugyldigt valg");
            }
        }
    }

    /**
     * Undermenu for træneradministration
     * Håndterer træner-relaterede operationer
     */
    private static void traenerAdministrationMenu() {
        while (true) {
            System.out.println("\n=== TRAENERADMINISTRATION ===");
            System.out.println("1. Opret traener");
            System.out.println("2. Tildel traener til hold");
            System.out.println("3. Vis traeneroversigt");
            System.out.println("4. Certificeringer");
            System.out.println("5. Tilbage til hovedmenu");
            System.out.print("Vaelg (1-5): ");

            String valg = scanner.nextLine();
            if (valg.equals("5")) break;  // Returnér til hovedmenu

            // Håndtering af brugervalg
            switch (valg) {
                case "1" -> traener.opretTraener();
                //case "2" -> traener.tildelHold();           // Ikke implementeret endnu
                //case "3" -> traener.visTraenere();         // Ikke implementeret endnu
                //case "4" -> traener.administrerCertificeringer(); // Ikke implementeret endnu
                default -> System.out.println("Ugyldigt valg");
            }
        }
    }

    /**
     * Undermenu for hold og aktiviteter
     * Håndterer forskellige holdtyper og tilmeldinger
     */
    private static void holdOgAktiviteterMenu() {
        while (true) {
            System.out.println("\n=== HOLD OG AKTIVITETER ===");
            System.out.println("1. Juniorhold");
            System.out.println("2. Seniorhold");
            System.out.println("3. Motionisthold");
            System.out.println("4. Holdtilmelding");
            System.out.println("5. Vis holdoversigt");
            System.out.println("6. Tilbage til hovedmenu");
            System.out.print("Vaelg (1-6): ");

            String valg = scanner.nextLine();
            if (valg.equals("6")) break;  // Returnér til hovedmenu

            // Alle funktioner er endnu ikke implementeret
            switch (valg) {
                //case "1" -> Hold.administrerJuniorhold();
                //case "2" -> Hold.administrerSeniorhold();
                //case "3" -> Hold.administrerMotionisthold();
                //case "4" -> Hold.tilmeldHold();
                //case "5" -> Hold.visHoldoversigt();
                default -> System.out.println("Ugyldigt valg");
            }
        }
    }

    /**
     * Undermenu for konkurrence og resultater
     * Håndterer stævner, resultater og konkurrencesvømmere
     */
    private static void konkurrenceOgResultaterMenu() {
        while (true) {
            System.out.println("\n=== KONKURRENCE OG RESULTATER ===");
            System.out.println("1. Registrer konkurrencesvommer");
            System.out.println("2. Tilmeld staevne");
            System.out.println("3. Registrer staevneresultater");
            System.out.println("4. Vis top 5 svommere");
            System.out.println("5. Staevneoversigt");
            System.out.println("6. Tilbage til hovedmenu");
            System.out.print("Vaelg (1-6): ");

            String valg = scanner.nextLine();
            if (valg.equals("6")) break;  // Returnér til hovedmenu

            // Alle funktioner er endnu ikke implementeret
            switch (valg) {
                //case "1" -> Konkurrence.registrerKonkurrencesvommer();
                //case "2" -> Konkurrence.tilmeldStaevne();
                //case "3" -> Konkurrence.registrerResultater();
                //case "4" -> Konkurrence.visTop5();
                //case "5" -> Konkurrence.visStaevner();
                default -> System.out.println("Ugyldigt valg");
            }
        }
    }

    /**
     * Undermenu for økonomi
     * Håndterer kontingenter, betalinger og økonomisk oversigt
     */
    private static void okonomiMenu() {
        while (true) {
            System.out.println("\n=== OKONOMI ===");
            System.out.println("1. Kontingentberegning");
            System.out.println("2. Registrer betaling");
            System.out.println("3. Vis restancer");
            System.out.println("4. Kontingentsatser");
            System.out.println("5. Okonomioversigt");
            System.out.println("6. Tilbage til hovedmenu");
            System.out.print("Vaelg (1-6): ");

            String valg = scanner.nextLine();
            if (valg.equals("6")) break;

            switch (valg) {
                case "1" -> Okonomi.beregnKontingent();
                case "2" -> {
                    System.out.println("Under udvikling - Registrer betaling");
                    // Okonomi.registrerBetaling();
                }
                case "3" -> {
                    System.out.println("Under udvikling - Vis restancer");
                    // Okonomi.visRestancer();
                }
                case "4" -> {
                    System.out.println("\nKontingentsatser:");
                    System.out.println("Junior (under 18 år): 1000 kr/år");
                    System.out.println("Senior (18+ år): 1600 kr/år");
                    System.out.println("Senior (60+ år): 1200 kr/år (25% rabat)");
                    System.out.println("Passivt medlemskab: 500 kr/år");
                }
                case "5" -> Okonomi.beregnSamletKontingent();
                default -> System.out.println("Ugyldigt valg");
            }
        }
    }
}