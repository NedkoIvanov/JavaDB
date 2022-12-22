import java.sql.*;
import java.util.Scanner;

public class IncreaseAge {
    public static void main(String[] args) throws SQLException {
        Scanner scn = new Scanner(System.in);
        String user = "root";
        String pass = "0144259040eE";
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", user, pass);
        String[] id = scn.nextLine().split(" ");
        for(int i=0;i<id.length;i++) {
            try {
                connect.setAutoCommit(false);
                PreparedStatement updateName = connect.prepareStatement("update minions set name = " +
                        " concat(upper(substring(name,1,1)),lower(substring(name,2))) where id = ?");
                updateName.setInt(1, Integer.parseInt(id[i]));
                updateName.executeUpdate();
                PreparedStatement increaseAge = connect.prepareStatement("update minions set age = age + 1 where id = ?;");
                increaseAge.setInt(1,Integer.parseInt(id[i]));
                increaseAge.executeUpdate();
                connect.commit();
            }catch(SQLException e){
                connect.rollback();
            }
        }
        PreparedStatement statement = connect.prepareStatement("select name,age from minions;");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            System.out.println(result.getString("name") + " " + result.getInt("age"));
        }


        connect.close();



    }
}
