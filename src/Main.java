public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello World!");


        // Opret test medlem
        Medlem testMedlem = new Medlem(
                "Hans Hansen",          // navn
                "hans@email.dk",        // email
                "12345678",            // telefon
                "Testvej 1, 2100 KBH", // adresse
                1,                     // medlemsId
                25,                    // alder
                true                   // erAktiv
        );

        // Test før opdatering
        System.out.println("FØR OPDATERING:");
        System.out.println(testMedlem.getKontaktinfo());

        // Opdater information
        System.out.println("\nOPDATERER INFORMATION:");
        testMedlem.opdaterKontaktinfo();

        // Vis efter opdatering
        System.out.println("\nEFTER OPDATERING:");
        System.out.println(testMedlem.getKontaktinfo());
    }
}