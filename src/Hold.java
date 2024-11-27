import java.util.ArrayList;

public class Hold {
    // attributer
    private String holdNavn; //opret to objekter en til junior og en til senior
    private String niveau;
    private int maxAntalDeltagere;
    // har valgt at tilføje senior vs junior træningstider for nemhedens skyld
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

    // to string på holdet


    public void addDeltager(Medlem medlem) { //der står Medlem medlem fordi Medlem står i stedet for dataypen (string, int doubble etc)
        //måske man skulle tilføje noget så inaktive ikke kan komme på holdet?
        if (holdliste.size() < maxAntalDeltagere) {
            holdliste.add(medlem);
            System.out.println("Medlem tilmeldt til holdet");
        } else {
            System.out.println("Der er ikke plads på holdet.");
        }
        // man bør heller ikke kunne tilføjes til holdet hvis man allerede er på holdet
        // man bør heller ikke kunne tilføjes hvis man er for ung/gammel til holdet (automatisk tilfføjer en til det hold der passer ens alder)
    }

    public void removeDeltager (Medlem medlem) {
        if (holdliste.contains(medlem)) {
            holdliste.remove(medlem);
            System.out.println(medlem + " er blevet fjernet fra holdet");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); //jeg bruger stringbuilder i stedet for '+' for at sparer på plads (+ et eller andet betyder java husker en masse strings forde strings er umitble/uændrebarelige)
        sb.append(holdNavn).append("\n").append("Holdets træning foregår:\n").append(traeningsTider).append("har maks ").append(maxAntalDeltagere).append(" deltagere.").append("Holdet består af").append(holdliste);
        return sb.toString();
    }

// HUSK at slette mainmetoden nedenfor når jeg har testet at klassen virker som den skal (vi kører med en stor main hvor alle klasserne bliver kørt)
    public static void main(String[] args) {
        ArrayList<String> traeningtider = new ArrayList<>();
        traeningtider.add("Tirsdag kl.16:00 - 18:00");
        traeningtider.add("Torsdag kl.17:00 - 19:00");


        //De to hold (objekter)
        Hold junior = new Hold("Juniorholdet", "Højt", 10, traeningtider);
        System.out.println(junior);
        Hold senior = new Hold("Seniorholdet", "Elite", 7, traeningtider);
        System.out.println(senior);


        Medlem medlem1 = new Medlem("Camilla", "CamillaKanin@gmail.com", "35422144", "Borups Allé 22, 5. th.", 123, 22, true);
        Medlem medlem2 = new Medlem("Tinke","UlvepigenTinke@gmail.com", "23456789", "Fernandosgade 5B", 233, 15, true);
        Medlem medlem3 = new Medlem("Majbrit","Majbritprivatmail@gmail.com", "24949389", "Sjællandsgade 233K", 303, 12, false);


    }
}

// addMember
// removeMedlem
//getHoldliste ------
//getHoldInfo
