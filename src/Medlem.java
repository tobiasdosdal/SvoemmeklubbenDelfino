import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Medlem extends Person {
    private int medlemsId;
    private int alder;
    private boolean erAktiv;
    private Date indmeldelsesdato;

    public Medlem(String navn, String email, String telefon, String adresse,
                  int medlemsId, int alder, boolean erAktiv) {
        super(navn, email, telefon, adresse);
        this.medlemsId = medlemsId;
        this.alder = alder;
        this.erAktiv = erAktiv;
        this.indmeldelsesdato = new Date(); // Sætter indmeldelsesdato til nu
    }

    // Getters og setters for Medlem-specifikke felter
    public int getMedlemsId() {
        return medlemsId;
    }

    public int getAlder() {
        return alder;
    }

    public boolean isErAktiv() {
        return erAktiv;
    }

    public Date getIndmeldelsesdato() {
        return indmeldelsesdato;
    }

    public void setAlder(int alder) {
        this.alder = alder;
    }

    public void setErAktiv(boolean erAktiv) {
        this.erAktiv = erAktiv;
    }

    // Override toString for at inkludere medlems-specifik information
    @Override
    public String toString() {
        return String.format("Navn: %s, Email: %s, Telefon: %s, Adresse: %s, MedlemsID: %d, Alder: %d, Aktiv: %b",
                name, email, telefon, adresse, medlemsId, alder, erAktiv);
    }

    // Metode til at oprette hardcodede medlemmer
    public static List<Medlem> opretMedlemmer() {
        List<Medlem> medlemmer = new ArrayList<>();
        medlemmer.add(new Medlem("Mads Mikkelsen", "mads@mikkelsen.dk", "12345678", "Frederiksberg Allé 10, 1820 Frederiksberg", 1, 57, true));
        medlemmer.add(new Medlem("Sofie Gråbøl", "sofie@gråbøl.dk", "87654321", "Østerbro 5, 2100 København Ø", 2, 55, true));
        medlemmer.add(new Medlem("Lars Mikkelsen", "lars@mikkelsen.dk", "11223344", "Nørrebrogade 20, 2200 København N", 3, 59, false));
        medlemmer.add(new Medlem("Birgitte Hjort Sørensen", "birgitte@hjortsorensen.dk", "44332211", "Amagerbrogade 15, 2300 København S", 4, 41, true));
        medlemmer.add(new Medlem("Nikolaj Coster-Waldau", "nikolaj@costerwaldau.dk", "55667788", "Valby Langgade 30, 2500 Valby", 5, 53, true));
        medlemmer.add(new Medlem("Sidse Babett Knudsen", "sidse@babettknudsen.dk", "77889900", "Christianshavn 25, 1400 København K", 6, 54, false));
        medlemmer.add(new Medlem("Casper Christensen", "casper@christensen.dk", "88990011", "Hellerupvej 45, 2900 Hellerup", 7, 54, true));
        medlemmer.add(new Medlem("Nikolaj Lie Kaas", "nikolaj@liekaas.dk", "99001122", "Gentofte Parkvej 15, 2820 Gentofte", 8, 51, true));
        medlemmer.add(new Medlem("Trine Dyrholm", "trine@dyrholm.dk", "11002233", "Vesterbrogade 8, 1620 København V", 9, 52, false));
        medlemmer.add(new Medlem("Rasmus Bjerg", "rasmus@bjerg.dk", "22003344", "Islands Brygge 10, 2300 København S", 10, 47, true));
        return medlemmer;
    }

    // Main metode til at teste og vise medlemmerne
    public static void main(String[] args) {
        List<Medlem> medlemmer = opretMedlemmer(); // Kalder metoden for at oprette medlemmer
        for (Medlem medlem : medlemmer) {
            System.out.println(medlem); // Printer hvert medlem
        }
    }
}