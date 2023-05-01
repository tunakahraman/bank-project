import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) throws SQLException {


        Connection connection = null;
        DbHelper dbHelper = new DbHelper();

        try {
            connection = dbHelper.getConnection();
            System.out.println("Successfully connected to the DB!");
        } catch (SQLException exception) {
            dbHelper.showErrorMessage(exception);
        } finally {
            connection.close();
        }


        Scanner scan = new Scanner(System.in);
        CustomerManager customerManager = new CustomerManager();
        Login login = new Login();

        int choice;
        int num;


        do {
            System.out.println("----------Dell Bank---------- \n");
            System.out.println(" 1.Open a new account \n 2.Login \n 3.Exit ");
            System.out.println("Enter your choice: ");
            choice = scan.nextInt();

            switch (choice) {
                case 1:
                    customerManager.openAcc();

                    break;

                case 2:
                    if (login.Login()) {
                        do {

                            System.out.println("Welcome " + login.loginInput);
                            System.out.println("----------Dell Bank---------- \n");
                            System.out.println(" 1.Show account details \n 2.Deposit money \n 3.Withdraw money \n 4.Exit");
                            System.out.println("Enter your choice: ");
                            num = scan.nextInt();
                            switch (num) {
                                case 1:
                                    try {
                                        customerManager.accDetails();
                                    } catch (Exception exception) {
                                        System.out.println("Error: " + exception.getMessage());
                                    }

                                    break;

                                case 2:
                                    customerManager.deposit();

                                    break;

                                case 3:
                                    customerManager.withdraw();

                                    break;
                                case 4:
                                    System.out.println("See you soon!");
                                    break;

                            }

                        } while (num != 4);

                    } else {
                        System.out.println("Wrong credentials! Try again!");
                    }

            }

        } while (choice !=3);
    }

}
