import java.util.ArrayList;

public class Hold {
    // attributer
    private String holdNavn; //opret to objekter en til junior og en til senior
    private String niveau;
    private int maxAntalDeltagere;
    // se note
    private ArrayList<String> traeningsTider;
    // tilføjelse
    private String holdinfo;
    private ArrayList<Medlem> holdliste;
    //private ArrayList <Medlem> medlemArrayList; // ????????

    // construktor
    public Hold(String holdNavn, String niveau, int maxAntalDeltagere, ArrayList<String> traeningsTider) {
        this.holdNavn = holdNavn;
        this.niveau = niveau;
        this.maxAntalDeltagere = maxAntalDeltagere;
        this.traeningsTider = traeningsTider; // forskel på denne??
        this.holdliste = new ArrayList<>(); // og denne??
    }

    public int getMaxAntalDeltagere() {
        return maxAntalDeltagere;
    }

    public String getHoldNavn() {
        return holdNavn;
    }

    public ArrayList<String> getTraeningsTider() {
        return traeningsTider;
    }

    public ArrayList<Medlem> getHoldliste() {
        return holdliste;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); //jeg bruger stringbuilder i stedet for '+' for at sparer på plads (+ et eller andet betyder java husker en masse strings forde strings er umitble/uændrebarelige)
        sb.append(holdNavn).append("\n").append("Holdets træning foregår:\n").append(traeningsTider).append("har maks ").append(maxAntalDeltagere).append(" deltagere.").append("Holdet består af").append(holdliste);
        return sb.toString();
    }

    public String getHoldinfo() {
        return this.toString();
    }


    public void addDeltager(Medlem medlem) { //der står 'Medlem medlem' fordi Medlem står i stedet for dataypen (string, int doubble etc)
        //måske man skulle tilføje noget så inaktive ikke kan komme på holdet?
        if (medlem.getAlder() < 18) {
            if (holdliste.size() < maxAntalDeltagere && holdNavn.contains("Juniorholdet")) {
                holdliste.add(medlem);
                System.out.println(medlem.getName() + " er tilmeldt til Juniorholdet"); //medlem har adgang til Persons navneattribut fordi medlem nedarver fra person (super navn og alt det der)
            } else {
                System.out.println("Der er ikke plads på Juniorholdet.");
            }
            // man bør heller ikke kunne tilføjes til holdet hvis man allerede er på holdet
            // man bør heller ikke kunne tilføjes hvis man er for ung/gammel til holdet (automatisk tilfføjer en til det hold der passer ens alder)
        } else {
            if (holdliste.size() < maxAntalDeltagere && holdNavn.contains("Seniorholdet")) {
                holdliste.add(medlem);
                System.out.println(medlem.getName() + " er tilmeldt til Seniorholdet");
            } else {
                System.out.println("Der er ikke plads på Seniorholdet.");
            }
        }
    }

    // ret til man bør kun kunne komme på et hold hvis man passer ind så behøver ikke det med alder
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


    //tester
// HUSK at slette mainmetoden nedenfor når jeg har testet at klassen virker som den skal (vi kører med en stor main hvor alle klasserne bliver kørt)
    public static void main(String[] args) {
        ArrayList<String> traeningtider = new ArrayList<>();
        traeningtider.add("Tirsdag kl.16:00 - 18:00");
        traeningtider.add("Torsdag kl.17:00 - 19:00");


        //De to hold (objekter)
        Hold junior = new Hold("Juniorholdet", "Højt", 10, traeningtider);
        //System.out.println(junior + "\n");
        Hold senior = new Hold("Seniorholdet", "Elite", 7, traeningtider);
        //System.out.println(senior + "\n");


        Medlem medlem1 = new Medlem("Camilla", "CamillaKanin@gmail.com", "35422144", "Borups Allé 22, 5. th.", 123, 22, true);
        Medlem medlem2 = new Medlem("Tinke", "UlvepigenTinke@gmail.com", "23456789", "Fernandosgade 5B", 233, 15, true);
        Medlem medlem3 = new Medlem("Majbrit", "Majbritprivatmail@gmail.com", "24949389", "Sjællandsgade 233K", 303, 12, false);

        System.out.println("Senior holdinfo: " + senior.getHoldinfo() + "\n");
        System.out.println("Junior holdinfo: " + junior.getHoldinfo() + "\n");

        System.out.println("Junior holdliste: " + junior.getHoldliste() + "\n");

        System.out.println("forsøger at tilføje medlem 1,2 og 3 til juniorholdet - obs medlem 1 er 22år");
        junior.addDeltager(medlem1);
        junior.addDeltager(medlem2);
        junior.addDeltager(medlem3);

        System.out.println("Junior holdliste: " + junior.getHoldliste());
        System.out.println("Senior holdliste: " + senior.getHoldliste());

        System.out.println("forsøger at tilføje medlem1 til seniorholdet i stedet");
        senior.addDeltager(medlem1);

        System.out.println("senior holdliste: " + senior.getHoldliste());

        System.out.println("Junior holdliste: " + junior.getHoldliste());
        System.out.println("fjerner medlem 2 fra juniorholdet");
        junior.removeDeltager(medlem2);

        System.out.println(junior.getHoldliste());



    }
}



/**
 // Note:
 // Der er to måder at initialisere en ArrayList på:
 // 1. Initialisering direkte i attributten (fx "private ArrayList<String> traeningsTider = new ArrayList<>();").
 //    - Fordel: Listen er altid klar (tom), så vi undgår null-pointer fejl.
 //    - Ulempe: Mindre fleksibelt, hvis vi vil tildele en specifik liste ved oprettelse.
 //
 // 2. Initialisering i konstruktøren (fx "this.traeningsTider = traeningsTider;").
 //    - Fordel: Vi kan give en liste som input, når objektet oprettes.
 //    - Ulempe: Vi skal sikre os, at input ikke er null. Hvis null gives, skal vi håndtere det (fx lave en tom liste).
 //
 // Ofte kombinerer man de to tilgange ved at sikre, at konstruktøren laver en tom liste, hvis input er null.

 */