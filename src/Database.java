import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:database.sqlite";
    private Connection connection = null;

    // Constructor
    public Database() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            createConnection();
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver ikke fundet!");
            e.printStackTrace();
        }
    }

    // Opret forbindelse til databasen
    private void createConnection() {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("Forbindelse til database oprettet");
        } catch (SQLException e) {
            System.out.println("Kunne ikke oprette forbindelse til database");
            e.printStackTrace();
        }
    }

    // Luk forbindelse til databasen
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

    // Udfør en SQL forespørgsel der ikke returnerer data (CREATE, INSERT, UPDATE, DELETE)
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

    // Udfør en SQL forespørgsel med prepared statement
    public void executeUpdate(String sql, Object... params) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
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

    // Udfør en SQL forespørgsel der returnerer data (SELECT)
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

    // Udfør en SQL forespørgsel med prepared statement der returnerer data
    public ResultSet executeQuery(String sql, Object... params) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
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

    // Get connection (bruges hvis du har brug for forbindelsen i andre klasser)
    public Connection getConnection() {
        return connection;
    }

    // Test forbindelsen
    public boolean testConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.out.println("Fejl i testConnection");
            e.printStackTrace();
            return false;
        }
    }

    // Opret tabeller hvis de ikke eksisterer
    public void createTables() {
        // Personer tabel
        String createPersonerTable = """
            CREATE TABLE IF NOT EXISTS personer (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                navn TEXT NOT NULL,
                email TEXT,
                telefon TEXT,
                adresse TEXT
            )""";

        // Medlemmer tabel
        String createMedlemmerTable = """
            CREATE TABLE IF NOT EXISTS medlemmer (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                person_id INTEGER,
                medlems_type TEXT,
                er_aktiv BOOLEAN,
                indmeldelses_dato DATE,
                FOREIGN KEY (person_id) REFERENCES personer(id)
            )""";

        executeUpdate(createPersonerTable);
        executeUpdate(createMedlemmerTable);
    }
}