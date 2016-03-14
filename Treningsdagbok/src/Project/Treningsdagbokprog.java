package Project;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;;

public class Treningsdagbokprog {

    private String url = "jdbc:mysql://localhost:3306/treningsdagbokdb";
    private String user = "root";
    private String password = "Julaften1!";

    public static void main(String[] args) {
        Treningsdagbokprog dagbok = new Treningsdagbokprog();
        try {
            dagbok.regOvelse();
        } catch (Exception e) {
            System.out.println("Exception thrown:" + e);
        }
       // try{
         //   int meh = dagbok.newKey("Treningsdagbok");
        // System.out.println(meh);
        //} catch (Exception e){
        //  System.out.println("meh");
        // }
    }

  //  public int getØktid(){
    //    return int øktid = myStmt.executeUpdate("select count(distinct øktid) from øvelseiøkt;") + 1;
    //}

    //public void ØvelseTilØvelseriøkt (String Øktnavn){
    //  Scanner sc = new Scanner(system.in);
      //  System.out.println("Velg ")
    //}

    public void regTreningsokt(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Oppretter Treningsøkt..");
        System.out.println("");
        sc.close();
    }

    public void regOvelse() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Skriv inn Navn, type, beskrivelse, mål, inne?");
        sc.useDelimiter(",");
        String line = sc.nextLine();
        String[] lineArray = line.split(", ");
        System.out.println(lineArray[0]);
        int inne;
        if (lineArray[4] == "inne") {
            inne = 1;
        } else {
            inne = 0;
        }

        try {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement myStat = (Statement) myConn.createStatement();
            String sql = " insert into øvelse "
                    + "(navn, type, beskrivelse, mål, inne, øktid, treningsid)"
                    + "values('"+lineArray[0]+"', '"+lineArray[1]+"', '"+lineArray[2]+"', '"+lineArray[3]+"', '"+inne+"', '1', '1')";
            myStat.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
        sc.close();
    }
}
