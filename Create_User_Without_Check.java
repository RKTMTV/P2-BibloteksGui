package SQL;

import java.sql.*;
import java.util.Scanner;


public class Create_user {

        public static void main(String[] args) {
            // Database placering
            String url = "jdbc:sqlite:D:\\skole\\JAVA\\Libary.db";

            // String til SQLite
            String sql = "INSERT INTO Users(Name, Username, Pass) VALUES(?,?,?)";

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
                    
             // While loop til Password match
                while (!Password.equals(PasswordMatch)) {
                    System.out.println("Adgangskoderne matcher ikke, prøv igen");
                    System.out.print("Indtast adgangskode: ");
                    Password = Input.nextLine();
                    System.out.print("Genindtast adgangskode: ");
                    PasswordMatch = Input.nextLine();
                }
                
                //Lukker Inputs
                Input.close();
                
                // Placering
                pstmt.setString(1, Navn);
                pstmt.setString(2, Brugernavn);
                pstmt.setString(3, Password);
                    
                
                pstmt.executeUpdate();
                System.out.println("Data er nu indsendt til SQL");
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

}