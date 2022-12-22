import java.sql.*;
import java.util.Scanner;

public class RemoveVillain {
    public static void delete(Connection connect,int id) throws SQLException{
        connect.setAutoCommit(false);
        try{
            //delete records from mapping table
            PreparedStatement deleteMapping = connect.prepareStatement("delete from minions_villains where villain_id = ?");
            deleteMapping.setInt(1,id);
            deleteMapping.executeUpdate();
            //delete records from villains table
            PreparedStatement deleteVillain  = connect.prepareStatement("delete from villains where id in(" +
                    " select minion_id from minions_villains where villain_id = ?)");
            deleteVillain.setInt(1,id);
            deleteVillain.executeUpdate();
            connect.commit();
        }catch(SQLException exc){
            connect.rollback();
        }
    }

    public static void main(String[] args) throws SQLException {
        Scanner scn = new Scanner(System.in);
        String user = "root";
        String pass = "0144259040eE";
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", user, pass);
        int id = Integer.parseInt(scn.nextLine());
        PreparedStatement statement = connect.prepareStatement("select name from villains where id = ?");
        statement.setInt(1,id);
        ResultSet result = statement.executeQuery();
        if(result.next()){
            PreparedStatement countMinions = connect.prepareStatement("\n" +
                    "select count(minion_id) as 'count' from minions_villains where villain_id = ?;");
            countMinions.setInt(1,id);
            ResultSet minionCount =countMinions.executeQuery();
            minionCount.next();
            delete(connect,id);
            System.out.println(result.getString("name") + " was deleted.");
            System.out.println(minionCount.getInt("count") + " minions released.");

        }else{
            System.out.println("No such villain found");
        }


        connect.close();
    }
}
