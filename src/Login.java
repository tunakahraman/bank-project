import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {

    Customer customer = new Customer();
    Scanner scan = new Scanner(System.in);
    DbHelper dbHelper = new DbHelper();
    Connection connection = null;
    PreparedStatement preStatement = null;

    ResultSet resultset;

    int dataBaseId;
    int loginInput;
    String passwordInput;


    public int id(int dataBaseId) {
        this.dataBaseId = dataBaseId;
        return dataBaseId;
    }

    public boolean Login() throws SQLException {
        boolean flag = false;


        System.out.println("Welcome!" + "\n" + "Please enter your citizen Id: ");
        loginInput = scan.nextInt();
        System.out.println("Please enter your password: ");
        scan.nextLine();
        passwordInput = scan.nextLine();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = dbHelper.getConnection();
            preStatement = connection.prepareStatement("select id, password from user where id =? and password =?");
            preStatement.setInt(1, loginInput);
            preStatement.setString(2, passwordInput);
            resultset = preStatement.executeQuery();
            while(resultset.next()) {
                 dataBaseId = resultset.getInt("id");
                 String dataBasePassword = resultset.getString("password");
                if(dataBaseId == loginInput && passwordInput.equals(dataBasePassword)) {
                    flag = true;
                }
                else {
                    flag = false;
                }
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return flag;
    }





}
