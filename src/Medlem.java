import java.util.Date;

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
        return super.toString() +
                String.format(", MedlemsID: %d, Alder: %d, Aktiv: %b",
                        medlemsId, alder, erAktiv);
    }
}