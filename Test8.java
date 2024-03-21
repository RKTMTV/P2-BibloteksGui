package dbTest1;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Test9 {

    private Connection conn() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\nikol\\Desktop\\2. SEM CCT\\2. Objektorienterede Programmering - Java\\100. P2 Projekt\\Test1\\src\\javaDatabase2.db");
        return conn;
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        Test9 test = new Test9();
        Connection conn = null;

        try {
            while (true) {
                System.out.print("Welcome to the library administrator menu:\n\n"
                        + "Press 0: To close the menu.\n"
                        + "Press 1: To insert data.\n"
                        + "Press 2: To update data.\n"
                        + "Press 3: To delete data.\n\n"
                        + "Enter number here:");

                int UI1 = scanner.nextInt();
                scanner.nextLine();

                switch (UI1) {
                    case 1:
                        try {
                            conn = test.conn();
                            test.insertData(conn);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            if (conn != null) {
                                try {
                                    conn.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    case 2:
                        try {
                            conn = test.conn();
                            test.updateData(conn);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            if (conn != null) {
                                try {
                                    conn.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    case 3:
                        try {
                            conn = test.conn();
                            test.printData(conn);
                            test.deleteData(conn);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            if (conn != null) {
                                try {
                                    conn.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    case 0:
                        System.out.print("System Closing...");
                        System.exit(0);
                    default:
                        System.out.println("\n\tERROR: Invalid input.\n");
                }
            }
        } catch (InputMismatchException a) {
            System.out.println("\n\tERROR: Invalid input.\n");
            menu();
        } finally {
            scanner.close();
        }
    }

    public void insertData(Connection connection) {
    	@SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter author name or press 'm' to return to menu: ");
        String authorName = scanner.nextLine();

        if (authorName.equalsIgnoreCase("m")) {
            menu();
        }

        if (authorName.isEmpty()) {
            System.out.println("\n\tERROR: Author name cannot be empty.\n\n");
            insertData(connection);
        }

        try {
            System.out.print("\nEnter author age: ");
            int authorAge = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after reading authorAge

            System.out.print("\nEnter book title (can include numbers and characters): ");
            String bookTitle = scanner.nextLine();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO testDatabase (authorName, authorAge, bookTitle) VALUES (?, ?, ?)");
            statement.setString(1, authorName);
            statement.setInt(2, authorAge);
            statement.setString(3, bookTitle);
            statement.executeUpdate();

            System.out.println("\nData inserted successfully.\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateData(Connection connection) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose a specific column to update: "
                + "\n\n\tauthorID\tauthorName\tauthorAge\tbookTitle\n\n"
                + "Press 'm' to return to menu.\n\n"
                + "Write column name here: ");

        String columnName = scanner.nextLine();

        switch (columnName) {
        	case "authorID":
            case "authorName":
            case "authorAge":
            case "bookTitle":
                try {
                    System.out.print("\nList of " + columnName + "'s to update: \n\n");
                    printData(connection);

                    System.out.println("\nEnter authorID of the record to update: ");
                    int authorID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("\nEnter new " + columnName + ": ");
                    String newValue = scanner.nextLine();

                    if (!newValue.isEmpty()) {
                        PreparedStatement statement = connection.prepareStatement("UPDATE testDatabase SET " + columnName + " = ? WHERE authorID = ?");
                        statement.setString(1, newValue);
                        statement.setInt(2, authorID);

                        int rowsUpdated = statement.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.print("\nData updated successfully.\n\n");
                        } else {
                            System.out.print("\n\tERROR: Data update failed.\n\n");
                        }
                    } else {
                        System.out.println("\n\tERROR: Invalid input."
                                + "\n\tReturning to menu...\n");
                        menu();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "m":
            	menu();
            
            default:
                System.out.println("\n\tERROR: Invalid input.\n");
                updateData(connection);
        }
    }

    public void deleteData(Connection connection) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter author ID to delete or press 'm' to return to menu: ");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("m")) {
            menu();
            return;
        }

        try {
            int authorID = Integer.parseInt(input);

            PreparedStatement statement = connection.prepareStatement("DELETE FROM testDatabase WHERE authorID = ?");
            statement.setInt(1, authorID);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("\nData deleted successfully.\n");
            } else {
                System.out.println("\n\tERROR: Invalid input.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n\tERROR: Invalid author ID.\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printData(Connection connection) {
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM testDatabase");

            System.out.println("AuthorID\tAuthorName\tAuthorAge\tBookTitle");
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String authorName = rs.getString("authorName");
                int authorAge = rs.getInt("authorAge");
                String bookTitle = rs.getString("bookTitle");
                System.out.println(authorID + "\t\t" + authorName + "\t\t" + authorAge + "\t\t" + bookTitle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        menu();
    }
}
