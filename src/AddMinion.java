import java.sql.*;
import java.util.Scanner;

public class AddMinion {
    public static int towns(Connection connect,String name) throws SQLException{
        PreparedStatement statement = connect.prepareStatement("select id from towns where name=?");
        statement.setString(1,name);

        ResultSet result = statement.executeQuery();
        int id = 0;
        if(result.next()){
            id = result.getInt("id");
        }else{
            PreparedStatement insertTown = connect.prepareStatement("insert into towns(name) values(?)");
            insertTown.setString(1,name);
            insertTown.executeUpdate();
            ResultSet resultFromInsert = statement.executeQuery();
            resultFromInsert.next();
            id = resultFromInsert.getInt("id");
            System.out.println("Town " + name + " was added to the database.");
        }
        return id;
    }

    public static int villains(Connection connect,String name) throws SQLException{
        PreparedStatement statement = connect.prepareStatement("select id from villains where name=?");
        statement.setString(1,name);
        ResultSet result = statement.executeQuery();
        int id = 0;
        if(result.next()){
            id = result.getInt("id");
        }else{
            PreparedStatement insert = connect.prepareStatement("insert into villains(name,evilness_factor) values(?,?)");
            insert.setString(1,name);
            insert.setString(2,"evil");
            insert.executeUpdate();
            ResultSet resultFromInsert = statement.executeQuery();
            resultFromInsert.next();
            id = resultFromInsert.getInt("id");
            System.out.println("Villain " + name + " was added to database.");
        }

        return id;
    }
    public static void minionsInsert(Connection connect,String name,int years,String townName,String villainName) throws SQLException{
        PreparedStatement statement = connect.prepareStatement("insert into minions(name,age,town_id)" +
                " values(?, ?, ?)");
        statement.setString(1,name);
        statement.setInt(2,years);
        statement.setInt(3,towns(connect,townName));
        statement.executeUpdate();

        PreparedStatement checkMinionId = connect.prepareStatement("select id from minions where name = ?");
        checkMinionId.setString(1,name);
        ResultSet id = checkMinionId.executeQuery();
        id.next();
        int minionId = id.getInt("id");
        PreparedStatement minionVillain = connect.prepareStatement("insert into minions_villains values (?,?)");
        minionVillain.setInt(1,minionId);
        minionVillain.setInt(2,villains(connect,villainName));
        minionVillain.executeUpdate();
        System.out.println("Successfully added " + name + " to be minion of " + villainName);
    }


    public static void main(String[] args) throws SQLException {
        Scanner scn = new Scanner(System.in);
        String user = "root";
        String pass = "0144259040eE";
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", user, pass);
        String minion = scn.nextLine();
        String name = minion.split(" ")[1];
        int age =Integer.parseInt(minion.split(" ")[2]);
        String town = minion.split(" ")[3];
        String villain = scn.nextLine();
        String villainName = villain.split(" ")[1];
        minionsInsert(connect,name,age,town,villainName);
        connect.close();


    }
}
