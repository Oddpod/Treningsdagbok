package Project;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;;

public class Treningsdagbokprog {

    private String url = "jdbc:mysql://localhost:3306/treningsdagbokdb";
    private String user = "root";
    private String password = "AVGvisualstudio123?";

    public static void main(String[] args) {
        Treningsdagbokprog dagbok = new Treningsdagbokprog();
        /*try {
             dagbok.regOvelse();
        } catch (Exception e) {
            System.out.println("Exception thrown:" + e);
        }
        try{
            dagbok.visOkt(2);
            dagbok.visOkt(1);
        }catch (Exception e){
            System.out.println("Exception thrown:" + e);
        }*/
        try {
            String ovelser = dagbok.getOvelser();
            int id = dagbok.regTreningsokt();
            System.out.println("Øvelser som ligger inne: " + ovelser);
            dagbok.ØvelseTilØvelseriøkt(id, ovelser);
        }catch (Exception e){
            System.out.println("Exception thrown:" + e);
        }
    }

  //  public int getØktid(){
    //    return int øktid = myStmt.executeUpdate("select count(distinct øktid) from øvelseiøkt;") + 1;
    //}

    public void ØvelseTilØvelseriøkt (int id, String ovelser){
      Scanner sc = new Scanner(System.in);
        System.out.println("Skriv inn navnet på alle øvelsene du vil ha i økten");
        sc.useDelimiter(", ");
        String line = sc.nextLine();
        String[] lineArray = line.split(", ");
        for (String ovelse : lineArray) {
            try {
                if( !ovelser.contains(ovelse)){
                    Treningsdagbokprog tdb = new Treningsdagbokprog();
                    System.out.println(ovelse + " ligger ikke inne i databasen og må derfor opprettes");
                    tdb.regOvelse();
                }
                Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
                Statement myStat = (Statement) myConn.createStatement();
                String sql = " insert into øvelseriøkt "
                        + "(øktid, øvelsesnavn)"
                        + "values( '"+id+"', '"+ovelse+"')";
                myStat.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("Exception thrown" + e);
            }
        }
    }

    public int regTreningsokt(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Oppretter Treningsøkt..");
        System.out.println("Skriv inn datotid, varighet, personlig form, notat, prestasjon");
        sc.useDelimiter(", ");
        String line = sc.nextLine();
        String[] lineArray = line.split(", ");
        try {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement myStat = (Statement) myConn.createStatement();
            /*String sql = "insert into treningsøkt "
                    + "( datotid, varighet, personlig_form, notat, prestasjon )"
                    + " values('" + lineArray[0] + "', '" + lineArray[1] + "', '" + lineArray[2] + "', '"
                    + lineArray[3] + "', '" + lineArray[4] + "')";
            myStat.executeUpdate(sql);
            */
            String key = "Select Max(idtreningsøkt) from treningsøkt";
            ResultSet myRsi = myStat.executeQuery(key);
            while (myRsi.next()){
                System.out.println(myRsi.getString("Max(idtreningsøkt)"));
                return Integer.parseInt(myRsi.getString("Max(idtreningsøkt)"));
            }
        } catch (Exception e){
            System.out.println("Exception thrown" + e);
        }
        sc.close();
        return -1;
    }
// Registrere ny øvelse
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
                    + "(navn, type, beskrivelse, mål, inne)"
                    + "values('"+lineArray[0]+"', '"+lineArray[1]+"', '"+lineArray[2]+"', '"+lineArray[3]+"', '"+inne+"')";
            myStat.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
        sc.close();


    }
    public void visOkt(int øktid){
        try {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement myStmt = (Statement) myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select * from treningsøkt where "+øktid+" = idtreningsøkt");
            while (myRs.next()) {
                System.out.println(myRs.getString("datotid") + ", " + myRs.getString("varighet") + ", " + myRs.getString("personlig_form")
                        + ", " + myRs.getString("notat") + ", " + myRs.getString("prestasjon") + ", " + myRs.getString("sett"));
            }
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
    }

    public void visLogg(){
        try {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement myStmt = (Statement) myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select datotid, notat from treningsøkt");
            while (myRs.next()){
                System.out.println(myRs.getString("datotid") + ", " + myRs.getString("notat"));
            }
        }catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
    }

    public String getOvelser() {
        String ovelser = "";
        try {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement myStmt = (Statement) myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT  navn from øvelse");
            while (myRs.next()) {
                ovelser = ovelser + myRs.getString("navn") + " ";
            }
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
        return ovelser;
    }

    public void regStyrkeKond(){

    }
    public void regUthold(String øvelsesnavn){
         Scanner sc = new Scanner(System.in);
         System.out.println("Lengde?");
         String line = sc.nextLine();
         try {
             Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
             Statement myStmt = (Statement) myConn.createStatement();
             String update = " insert into utholdenhet "
                     + "(lengde, øvelsesnavn)"
                     + "values( '"+line+"', '"+øvelsesnavn+"')";
             myStmt.executeUpdate(update);
         }catch (SQLException e) {
             System.out.println("Exception thrown" + e);
         }
    }
}
