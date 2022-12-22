import java.sql.*;
import java.util.Scanner;

public class MinionNames {
    public static void printMinions(Connection connect,int id) throws SQLException{
        PreparedStatement minions = connect.prepareStatement("select minions.name,minions.age " +
                        "from villains join minions_villains " +
                        "on villains.id = minions_villains.villain_id " +
                        "join minions " +
                        "on minions_villains.minion_id = minions.id " +
                        "where villains.id = ?");
        minions.setInt(1,id);
        ResultSet result = minions.executeQuery();
        int count = 0;
        while(result.next()){
            count++;
            System.out.println(count + ". " + result.getString("name") + " " + result.getInt("age"));
        }

    }
    public static void main(String[] args) throws SQLException {
        Scanner scn = new Scanner(System.in);
        String user = "root";
        String pass = "0144259040eE";
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", user, pass);
        PreparedStatement statement = connect.prepareStatement("select name from villains where id=?");
        int id = Integer.parseInt(scn.nextLine());
        statement.setInt(1,id);
        ResultSet result = statement.executeQuery();
        if(result.next()){
            System.out.println("Villain: " + result.getString("name"));
            printMinions(connect,id);
        }else{
            System.out.println("Wrong id!");
        }
        connect.close();

    }
}
