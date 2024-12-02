import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Database db = new Database();

    public static void main(String[] args) {
        if (db.testConnection()) {
            System.out.println("Database forbindelse oprettet succesfuldt!");
            boolean korProgram = true;

            while (korProgram) {
                System.out.println("\n=== SVOMMEKLUBBEN DELFINEN ===");
                visHovedMenu();
                String valg = scanner.nextLine();

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

    private static void visHovedMenu() {
        System.out.println("1. Medlemsadministration");
        System.out.println("2. Traeneradministration");
        System.out.println("3. Hold og aktiviteter");
        System.out.println("4. Konkurrence og resultater");
        System.out.println("5. Okonomi");
        System.out.println("6. Afslut");
        System.out.print("Vaelg (1-6): ");
    }

    private static void medlemsAdministrationMenu() {
        while (true) {
            System.out.println("\n=== MEDLEMSADMINISTRATION ===");
            System.out.println("1. Opret nyt medlem");
            System.out.println("2. Rediger medlem (ikke kodet)");
            System.out.println("3. Vis alle medlemmer");
            System.out.println("4. Slet medlem (ikke kodet)");
            System.out.println("5. Kontaktinformation (ikke kodet)1");
            System.out.println("6. Tilbage til hovedmenu");
            System.out.print("Vaelg (1-6): ");

            String valg = scanner.nextLine();
            if (valg.equals("6")) break;

            switch (valg) {
                case "1" -> Person.opretPerson();
                //case "2" -> Person.redigerPerson();
                case "3" -> Person.visPersoner();
                //case "4" -> Person.sletPerson();
                //case "5" -> Person.visKontaktinfo();
                default -> System.out.println("Ugyldigt valg");
            }
        }
    }

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
            if (valg.equals("5")) break;

            switch (valg) {
                case "1" -> traener.opretTraener();
                //case "2" -> traener.tildelHold();
                //case "3" -> traener.visTraenere();
                //case "4" -> traener.administrerCertificeringer();
                default -> System.out.println("Ugyldigt valg");
            }
        }
    }

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
            if (valg.equals("6")) break;

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
            if (valg.equals("6")) break;

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
                //case "1" -> Okonomi.beregnKontingent();
                //case "2" -> Okonomi.registrerBetaling();
                //case "3" -> Okonomi.visRestancer();
                //case "4" -> Okonomi.visKontingentsatser();
                //case "5" -> Okonomi.visOkonomioversigt();
                default -> System.out.println("Ugyldigt valg");
            }
        }
    }
}