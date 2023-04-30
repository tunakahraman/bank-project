import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

public class CustomerManager {

    Customer customer = new Customer();
    Scanner scan = new Scanner(System.in);
    DbHelper dbHelper = new DbHelper();
    Connection connection = null;
    PreparedStatement preStatement = null;
    ResultSet resultset;
    Login login = new Login();



    public void openAcc() throws SQLException {
        System.out.println("-----Welcome to our bank!-----" + "\n" + "\n" + "Please enter your name:");
        String nameInput = scan.nextLine();
        customer.setName(nameInput);
        System.out.println("Please enter your surname:");
        String surNameInput = scan.nextLine();
        customer.setSurName(surNameInput);
        System.out.println("Please enter your citizen id:");
        int idInput = scan.nextInt();
        customer.setId(idInput);
        System.out.println("Please enter your password:");
        scan.nextLine();
        String passwordInput = scan.nextLine();
        customer.setPassword(passwordInput);


        try {
            connection = dbHelper.getConnection();
            preStatement = connection.prepareStatement("insert into user (name, surName, id, password) values(?,?,?,?)");
            preStatement.setString(1, nameInput.toUpperCase());
            preStatement.setString(2, surNameInput.toUpperCase());
            preStatement.setInt(3, idInput);
            preStatement.setString(4, passwordInput);
            preStatement.executeUpdate();
        } catch (SQLException exception) {
            dbHelper.showErrorMessage(exception);
        } finally {
            connection.close();
        }
        System.out.println("Account created successfully! \n");
    }

    public void accDetails() throws SQLException, ClassNotFoundException {
        System.out.println(login.id(login.dataBaseId));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = dbHelper.getConnection();
            preStatement = connection.prepareStatement("select id, name, surName, balance from user where id= ? ");
            preStatement.setInt(1, login.id(login.dataBaseId));
            resultset = preStatement.executeQuery();

            while (resultset.next()) {
                System.out.println("Citizen Id: " + resultset.getInt("id"));
                System.out.println("Account holder: " + resultset.getString("name").toUpperCase() + " "
                        + resultset.getString("surName").toUpperCase());
                System.out.println("Balance: " + resultset.getDouble("balance" + " TL \n"));
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }

    public void withdraw() throws SQLException {
        System.out.println("Current balance: " + customer.getBalance());
        System.out.println("Please enter the amount that you want to withdraw:");
        double amount = scan.nextDouble();
        double balance = customer.getBalance();
        if (balance >= amount) {
            try {
                balance -= amount;
                customer.setBalance(balance);
                connection = dbHelper.getConnection();
                preStatement = connection.prepareStatement("update user set balance =" + balance + "  where id = ? ");
                preStatement.setDouble(1, customer.getId());
                preStatement.executeUpdate();
                System.out.println("You withdraw " + amount + " TL" + "\n");
                System.out.println("New balance: " + balance + " TL \n");
            } catch (SQLException exception) {
                dbHelper.showErrorMessage(exception);
            } finally {
                connection.close();
            }

        } else {
            System.out.println("Insufficient balance! \n");
        }
    }

    public void deposit() throws SQLException {
        System.out.println("Please enter the amount that you want to deposit: ");
        double amount = scan.nextDouble();
        double balance = customer.getBalance();
        try {
            balance += amount;
            customer.setBalance(balance);
            connection = dbHelper.getConnection();
            preStatement = connection.prepareStatement("update user set balance =" + balance + "  where id = ? ");
            preStatement.setDouble(1, customer.getId());
            preStatement.executeUpdate();
            System.out.println("Deposit is successful!" + "\n");
            System.out.println("New balance:" + balance + " TL \n");
        } catch (SQLException exception) {
            dbHelper.showErrorMessage(exception);
        } finally {
            connection.close();
        }

    }


}
