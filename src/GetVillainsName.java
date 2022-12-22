import java.sql.*;
import java.util.Scanner;

public class GetVillainsName {
    public static void main(String[] args) throws SQLException {
        Scanner scn = new Scanner(System.in);
        String user = "root";
        String pass = "0144259040eE";
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", user, pass);
        PreparedStatement statement = connect.prepareStatement("select villains.name,count(minions.id) as 'count'\n" +
                " from villains join minions_villains\n" +
                "on villains.id = minions_villains.villain_id\n" +
                "join minions on minions_villains.minion_id = minions.id\n" +
                "group by villains.id \n" +
                "having count >= 3\n" +
                "order by count desc;");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            String name = result.getString("name");
            int count = result.getInt("count");
            System.out.println(name + " " + count);
        }
        connect.close();
    }
}
