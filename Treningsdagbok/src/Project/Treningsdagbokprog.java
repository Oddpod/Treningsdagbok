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
    int id = 0;

    public static void main(String[] args) {
        Treningsdagbokprog dagbok = new Treningsdagbokprog();
        try{
            dagbok.repeatOkt();
        } catch (Exception e) {
            System.out.println("Exception thrown:" + e);
        }
        /*try {
            dagbok.regOvelse();
        } catch (Exception e) {
            System.out.println("Exception thrown:" + e);
        }
         try{

            dagbok.regTreningsokt();
        } catch (Exception e){
            System.out.println("Exception thrown:" + e);
        }
        try{
            dagbok.visOkt(2);
            dagbok.visOkt(1);
        }catch (Exception e){
            System.out.println("Exception thrown:" + e);
        }*/
        /*try {
            String ovelser = dagbok.getOvelser();
            System.out.println("Øvelser som ligger inne: " + ovelser);
            dagbok.ØvelseTilØvelseriøkt(1, ovelser);
        }catch (Exception e){
            System.out.println("Exception thrown:" + e);
        } */
    }


    public void startConnectiontoDatabaseAndUpdate(String sql) {
        try {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement myStat = (Statement) myConn.createStatement();
            myStat.executeUpdate(sql);
        }  catch (SQLException e) {
                System.out.println("Exception thrown " + e);
        }
    }

    public ResultSet startConnectiontoDatabaseAndQuery(String sql) {
        try {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement myStat = (Statement) myConn.createStatement();
            ResultSet myRsi = myStat.executeQuery(sql);
            return myRsi;
        }  catch (SQLException e) {
            System.out.println("Exception thrown " + e);
        }
        return null;
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

            if( !ovelser.contains(ovelse)){
                System.out.println(ovelse + " ligger ikke inne i databasen og må derfor opprettes");
                try {
                    regOvelse();
                } catch (SQLException e) {
                    System.out.println("Exception thrown" + e);
                }
            }
            String sql = " insert into øvelseriøkt "
                    + "(øktid, øvelsesnavn)"
                    + "values( '"+id+"', '"+ovelse+"')";
            startConnectiontoDatabaseAndUpdate(sql);
        }
    }


    public void regForhold() {
        Scanner sc = new Scanner(System.in);

    }

    public void regTreningsokt(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Oppretter Treningsøkt..");
        System.out.println("Skriv inn datotid, varighet, personlig form, notat, prestasjon");
        sc.useDelimiter(", ");
        String line = sc.nextLine();
        String[] lineArray = line.split(", ");
        try {
            /*String sql = "insert into treningsøkt "
                    + "( datotid, varighet, personlig_form, notat, prestasjon )"
                    + " values('" + lineArray[0] + "', '" + lineArray[1] + "', '" + lineArray[2] + "', '"
                    + lineArray[3] + "', '" + lineArray[4] + "')";
            myStat.executeUpdate(sql);
            */
            String key = "Select Max(idtreningsøkt) from treningsøkt";
            ResultSet myRsi = startConnectiontoDatabaseAndQuery(key);
            while (myRsi.next()){
                System.out.println(myRsi.getString("Max(idtreningsøkt)"));
                this.id = Integer.parseInt(myRsi.getString("Max(idtreningsøkt)"));
                System.out.println(this.id);
            }
        } catch (Exception e){
            System.out.println("Exception thrown" + e);
        }

            sc.close();
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
        System.out.println("Hei");
        String sql = " insert into øvelse "
                + "(navn, type, beskrivelse, mål, inne)"
                + "values('"+lineArray[0]+"', '"+lineArray[1]+"', '"+lineArray[2]+"', '"+lineArray[3]+"', '"+inne+"')";
        sc.close();
        startConnectiontoDatabaseAndUpdate(sql);


    }

    public void regResultat() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Skriv inn øvelsesnavn, bragd, antallsett");
        sc.useDelimiter(",");
        String line = sc.nextLine();
        String[] lineArray= line.split(", ");
        try {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement mystat = (Statement) myConn.createStatement();
            String sql = " insert into resultat "
                    + "(øvelsesnavn, bragd, resultatid, antallsett)"
                    + "values ('" + lineArray[0] + "', '" + lineArray[1] + "', '" + lineArray[3] + "')";
            mystat.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
        sc.close();
    }

    public void MålVsBragd(){
        
    }
    public void visOkt(int øktid){

        try {
            String key = "select * from treningsøkt where " + øktid + " = idtreningsøkt";
            ResultSet myRs = startConnectiontoDatabaseAndQuery(key);
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
            ResultSet myRs = startConnectiontoDatabaseAndQuery("select datotid, notat from treningsøkt order by datotid");
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
            ResultSet myRs = startConnectiontoDatabaseAndQuery("SELECT navn from øvelse");
            while (myRs.next()) {
                ovelser = ovelser + myRs.getString("navn") + " ";
            }
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
        return ovelser;
    }

    public void repeatOkt(){
            String ovelser= "";
        try{
            ResultSet myRsi = startConnectiontoDatabaseAndQuery("select datotid, notat from treningsøkt");
            while (myRsi.next()){
                System.out.println(myRsi.getString("datotid") + ", " + myRsi.getString("notat"));
            };
            Scanner sc = new Scanner(System.in);
            System.out.println("Velg økt du vil bruke som mal(1, 2, 3 osv...)");
            int id = Integer.parseInt(sc.nextLine());
            ResultSet myRs = startConnectiontoDatabaseAndQuery("SELECT * from treningsøkt inner join øvelseriøkt on " +
                    "treningsøkt.idtreningsøkt = øvelseriøkt.øktid" +
                    " where "+id+"=treningsøkt.idtreningsøkt");
            while(myRs.next()){
                ovelser += myRs.getString("øvelsesnavn") + " ";
            }
            System.out.println(ovelser);
            System.out.println("Hvilke øvelser ønsker du å bytte ut?( 1, 2, 3 osv...");
        }catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
    }
}
