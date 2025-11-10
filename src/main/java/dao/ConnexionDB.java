package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
    private static final String URL =
        "jdbc:sqlserver://localhost:1433;"
      + "databaseName=BibliothequeDB;"
      + "encrypt=true;"
      + "trustServerCertificate=true;";
    
    private static final String USERNAME = "Ibrah"; // ton login SQL Server
    private static final String PASSWORD = "Fourera6991@"; // ton mot de passe

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connexion réussie à SQL Server !");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC introuvable : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
        return connection;
    }
}
