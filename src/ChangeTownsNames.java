import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.Scanner;

public class ChangeTownsNames {
    public static void main(String[] args) throws SQLException {
        Scanner scn = new Scanner(System.in);
        String user = "root";
        String pass = "0144259040eE";
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", user, pass);
        PreparedStatement statement = connect.prepareStatement("update towns set name = upper(name) where country = ?");
        String country = scn.nextLine();
        statement.setString(1, country);
        statement.executeUpdate();
        List<String> towns = new ArrayList<>();
        if(statement.executeUpdate()!=0) {
            PreparedStatement afterUpdate = connect.prepareStatement("select name from towns where country = ?");
            afterUpdate.setString(1, country);
            ResultSet result = afterUpdate.executeQuery();
            while (result.next()) {
                towns.add(result.getString("name"));

            }
            System.out.println(towns.size() + " town names were affected.");
            System.out.println(towns);
        }else{
            System.out.println("No towns were affected.");
        }
        connect.close();

    }
}
