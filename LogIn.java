package SQL;

import java.sql.*;
import java.util.Scanner;

public class Log_In {
    // Database placering
    private static final String url = "jdbc:sqlite:D:\\skole\\JAVA\\Libary.db";

    public static void main(String[] args) {
        
        //Det en scanner
        Scanner Input = new Scanner(System.in);

        // Bruger inputs
        System.out.print("Indtast Brugernavn: ");
        String Brugernavn = Input.nextLine();
        System.out.print("Indtast Adgangkode: ");
        String Adgangskode = Input.nextLine();

        // Validere om login passer
        if (validateLogin(Brugernavn, Adgangskode)) {
            System.out.println("Du er nu logget ind!!");
        } else {
            System.out.println("Forkert Brugernavn og/eller Adgangskode.");
        }

        Input.close();
    }

    private static boolean validateLogin(String Brugernavn, String Adgangskode) {
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE username = ? AND Pass = ?")) {
            preparedStatement.setString(1, Brugernavn); // Tager Brugernavne fra databasen til matchup
            preparedStatement.setString(2, Adgangskode); // Tager adgangskoder fra databasen til matchup
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Controller om Brugernavn og Adgangskode matcher med databasen
            }
        } catch (SQLException e) { //Error Messeage!!!!!!!
            System.out.println("Fejl, tjek connection: " + e.getMessage());
            return false;
        }
    }
}