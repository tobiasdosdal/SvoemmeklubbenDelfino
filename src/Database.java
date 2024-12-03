/**
 * Database klasse der håndterer alle databaseoperationer for systemet.
 * Bruger SQLite som database management system.
 * Implementerer connection pooling og prepared statements for sikker SQL håndtering.
 */
import java.sql.*;

public class Database {
    // Database connection URL - SQLite database gemmes i en lokal fil
    private static final String URL = "jdbc:sqlite:database.sqlite";
    // Connection object der holder den aktive database forbindelse
    private Connection connection = null;

    /**
     * Constructor - initialiserer databaseforbindelsen
     * Loader SQLite JDBC driver og opretter den første forbindelse
     */
    public Database() {
        try {
            // Load SQLite JDBC driver før vi kan oprette forbindelser
            Class.forName("org.sqlite.JDBC");
            createConnection();
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver ikke fundet!");
            e.printStackTrace();
        }
    }

    /**
     * Opretter en ny forbindelse til databasen
     * Kaldes automatisk af constructoren og kan også bruges til at genoprette tabte forbindelser
     */
    private void createConnection() {
        try {
            connection = DriverManager.getConnection(URL);
            //System.out.println("Forbindelse til database oprettet");
        } catch (SQLException e) {
            System.out.println("Kunne ikke oprette forbindelse til database");
            e.printStackTrace();
        }
    }

    /**
     * Lukker den aktive databaseforbindelse
     * Bør kaldes når programmet afsluttes for at frigive resourcer
     */
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database forbindelse lukket");
            }
        } catch (SQLException e) {
            System.out.println("Kunne ikke lukke forbindelsen til databasen");
            e.printStackTrace();
        }
    }

    /**
     * Udfører en SQL update kommando uden parametre
     * Bruges til INSERT, UPDATE, DELETE og CREATE operationer
     * @param sql SQL kommandoen der skal udføres
     */
    public void executeUpdate(String sql) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Fejl i executeUpdate");
            e.printStackTrace();
        }
    }

    /**
     * Udfører en SQL update kommando med parametre via prepared statement
     * Sikrer mod SQL injection ved at escape special karakterer
     * @param sql SQL kommandoen med ? placeholders
     * @param params Variable antal parametre der indsættes i SQL kommandoen
     */
    public void executeUpdate(String sql, Object... params) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            // Indsæt alle parametre i prepared statement
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Fejl i executeUpdate med prepared statement");
            e.printStackTrace();
        }
    }

    /**
     * Udfører en SQL select kommando uden parametre
     * @param sql SQL select kommandoen
     * @return ResultSet med de fundne data, eller null hvis der opstod en fejl
     */
    public ResultSet executeQuery(String sql) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Fejl i executeQuery");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Udfører en SQL select kommando med parametre via prepared statement
     * @param sql SQL select kommandoen med ? placeholders
     * @param params Variable antal parametre der indsættes i SQL kommandoen
     * @return ResultSet med de fundne data, eller null hvis der opstod en fejl
     */
    public ResultSet executeQuery(String sql, Object... params) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            // Indsæt alle parametre i prepared statement
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Fejl i executeQuery med prepared statement");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returnerer den aktive database forbindelse
     * Bruges hvis andre klasser har brug for direkte adgang til forbindelsen
     * @return Den aktive Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Tester om der er en aktiv og åben forbindelse til databasen
     * @return true hvis forbindelsen er aktiv, false hvis ikke
     */
    public boolean testConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.out.println("Fejl i testConnection");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Opretter de nødvendige database tabeller hvis de ikke allerede eksisterer
     * Inkluderer personer og medlemmer tabeller med relationer
     */
    public void createTables() {
        // SQL til oprettelse af personer tabel
        String createPersonerTable = """
            CREATE TABLE IF NOT EXISTS personer (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                navn TEXT NOT NULL,
                email TEXT,
                telefon TEXT,
                adresse TEXT
            )""";

        // SQL til oprettelse af medlemmer tabel med foreign key til personer
        String createMedlemmerTable = """
            CREATE TABLE IF NOT EXISTS medlemmer (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                person_id INTEGER,
                medlems_type TEXT,
                er_aktiv BOOLEAN,
                indmeldelses_dato DATE,
                FOREIGN KEY (person_id) REFERENCES personer(id)
            )""";

        // Udfør SQL kommandoerne
        executeUpdate(createPersonerTable);
        executeUpdate(createMedlemmerTable);
    }
}