package SQL;

import java.sql.*;
import java.util.Scanner;

public class Create_user_check {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        // Database placering
        String url = "jdbc:sqlite:C:/Users/cbkri/Documents/JAVA/Libary.db";

        // String til indsæt
        String insertSql = "INSERT INTO Users(Name, Username, Pass) VALUES(?,?,?)";
        // String til undersøge
        String selectSql = "SELECT * FROM Users WHERE Username = ?";

        //Forbindelse
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            // Det er en scanner
            Scanner Input = new Scanner(System.in);

            // Bruger Inputs
            System.out.print("Intast dit fornavn og efternavn: ");
            String Navn = Input.nextLine();
            System.out.print("Indtast brugernavn: ");
            String Brugernavn = Input.nextLine();
            
            System.out.print("Indtast adgangskode: ");
            String Password = Input.nextLine();
            System.out.print("Genindtast adgangskode: ");
            String PasswordMatch = Input.nextLine();

            // Matcher Adgangskoden
            while (!Password.equals(PasswordMatch)) {
                System.out.println("Adgangskoderne matcher ikke, prøv igen");
                System.out.print("Indtast adgangskode: ");
                Password = Input.nextLine();
                System.out.print("Genindtast adgangskode: ");
                PasswordMatch = Input.nextLine();
            }

            // Undersøger om bruger allerede eksister
            selectStmt.setString(1, Brugernavn);
            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Brugernavnet eksisterer allerede.");
                return; //Stopper programmet
            }

            // Lukker Inputs
            Input.close();

            // Placering
            insertStmt.setString(1, Navn);
            insertStmt.setString(2, Brugernavn);
            insertStmt.setString(3, Password);

            insertStmt.executeUpdate();
            System.out.println("Data er nu indsendt til SQL");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}