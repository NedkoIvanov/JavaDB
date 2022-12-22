import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrintAllNames {
    public static void main(String[] args) throws SQLException {
        Scanner scn = new Scanner(System.in);
        String user = "root";
        String pass = "0144259040eE";
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",user,pass);
        PreparedStatement statement = connect.prepareStatement("Select name from minions");
        List<String> minions = new ArrayList<>();
        ResultSet result = statement.executeQuery();
        while(result.next()){
            minions.add(result.getString("name"));
        }

        int first = 0;
        int last = minions.size()-1;
        while(first<last){
            System.out.print(minions.get(first) + " " + minions.get(last) + " ");
            first++;
            last--;
        }


        connect.close();

    }
}
