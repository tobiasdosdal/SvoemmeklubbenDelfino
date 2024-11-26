import java.util.Scanner;  // Import the Scanner class


public abstract class Person {
    protected String name;
    protected String email;
    protected String telefon;
    protected String adresse;

    public Person(String name, String email, String telefon, String adresse) {
        this.name = name;
        this.email = email;
        this.telefon = telefon;
        this.adresse = adresse;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getAdresse() {
        return adresse;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Implementering af opdaterKontaktinfo med Scanner
    public void opdaterKontaktinfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nOpdater kontaktinformation for " + name);
        System.out.println("Tryk ENTER uden at skrive noget for at beholde den nuværende værdi");

        System.out.print("Email (" + email + "): ");
        String nyEmail = scanner.nextLine();
        if (!nyEmail.isEmpty()) {
            this.email = nyEmail;
        }

        System.out.print("Telefon (" + telefon + "): ");
        String nyTelefon = scanner.nextLine();
        if (!nyTelefon.isEmpty()) {
            this.telefon = nyTelefon;
        }

        System.out.print("Adresse (" + adresse + "): ");
        String nyAdresse = scanner.nextLine();
        if (!nyAdresse.isEmpty()) {
            this.adresse = nyAdresse;
        }

        System.out.println("Kontaktinformation er opdateret!");
    }

    // Implementering af getKontaktinfo
    public String getKontaktinfo() {
        return String.format("""
            Kontaktinformation:
            Navn: %s
            Email: %s
            Telefon: %s
            Adresse: %s
            """, name, email, telefon, adresse);
    }

    // toString metode til at printe person information
    @Override
    public String toString() {
        return String.format("Navn: %s, Email: %s, Telefon: %s, Adresse: %s",
                name, email, telefon, adresse);
    }
}