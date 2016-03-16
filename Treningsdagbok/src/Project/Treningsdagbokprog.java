package Project;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;;

public class Treningsdagbokprog {

    private String url = "jdbc:mysql://localhost:3306/treningsdagbokdb";
    private String user = "root";
    private String password = "AVGvisualstudio123?";

    public static void main(String[] args) {
        Treningsdagbokprog dagbok = new Treningsdagbokprog();
        try{
            dagbok.regOvelse();
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


    public void regForhold(int idTreningsøkt, String date) {
        boolean inne = false;
        boolean ute = false;
        ResultSet myRs2;
        idTreningsøkt = 1;
        String sql3 = "SELECT øktid, øvelsesnavn FROM øvelseriøkt " +
                "WHERE '"+idTreningsøkt+"' = øktId";
        ResultSet myRs = startConnectiontoDatabaseAndQuery(sql3);
        try {
            while (myRs.next()) {
                System.out.println("hei");
                String ovelse = myRs.getString("øvelsesnavn");

                myRs2 = startConnectiontoDatabaseAndQuery("SELECT inne from øvelse " +
                        "where '"+ovelse+"' = navn");
                myRs2.next();
                int UteEllerInne = myRs2.getInt("inne");
                if(ute == false) {
                    ute = (UteEllerInne == 0) ? true : false;
                }
                if (inne == false) {
                    inne = (UteEllerInne == 0) ? false : true;
                }
                if((inne && ute) == true) {
                    break;
                }
            }
            if (inne) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Skriv inn luftforhold og ventilasjon");
                sc.useDelimiter(", ");
                String line = sc.nextLine();
                String[] lineArray = line.split(", ");
                String sql = "insert into inneforhold"
                        + "( dato, luft, ventilasjon, idtreningsøkt)"
                        + "values('" + date + "', '"+lineArray[0]+"','"+lineArray[1]+"', '"+idTreningsøkt+"')";
                startConnectiontoDatabaseAndUpdate(sql);
            }
            if (ute) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Skriv inn vær og temperatur");
                String line = sc.nextLine();
                String[] lineArray = line.split(", ");
                String sql = "insert into utendørsforhold"
                        + "( dato, vær, temperatur, idtreningsøkt)"
                        + "values('" + date + "', '"+lineArray[0]+"','"+lineArray[1]+"', '"+idTreningsøkt+"')";
                startConnectiontoDatabaseAndUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
    }

    public void regTreningsokt(){
        int id = 0;
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
                id = Integer.parseInt(myRsi.getString("Max(idtreningsøkt)"));
                //System.out.println(this.id);
            }
        } catch (Exception e){
            System.out.println("Exception thrown" + e);
        }
        regForhold(id, lineArray[0]);
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
        String sql = " insert into øvelse "
                + "(navn, type, beskrivelse, mål, inne)"
                + "values('"+lineArray[0]+"', '"+lineArray[1]+"', '"+lineArray[2]+"', '"+lineArray[3]+"', '"+inne+"')";
        startConnectiontoDatabaseAndUpdate(sql);
        addToGroup(lineArray[0], lineArray[1]);
    }

    public void addGroup(String gruppe) {
        String sql = "insert into undergruppe"
                + "(gruppenavn)"
                + "values('"+gruppe+"')";
        startConnectiontoDatabaseAndUpdate(sql);
    }

    public void addToGroup(String ovelse, String type) {
        System.out.println("Skriv hvilken gruppe øvelsen hører til");
        Scanner sc = new Scanner(System.in);
        String gruppe = sc.nextLine();
        String grupper = "";

        try {
            ResultSet myRs = startConnectiontoDatabaseAndQuery("SELECT gruppenavn from undergruppe");
            while (myRs.next()) {
                grupper += myRs.getString("navn") + " ";
            }
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
        if (!grupper.contains(gruppe)) {
            addGroup(gruppe);
        }

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
                    + "(øvelsesnavn, bragd, sett)"
                    + "values ('" + lineArray[0] + "', '" + lineArray[1] + "', '" + lineArray[2] + "')";
            mystat.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
        MålVsBragd(Integer.parseInt(lineArray[1]), lineArray[0]);
    }

    public void MålVsBragd(int bragd, String øvelse ) {
        String sql = "select mål, måltype from øvelse where navn = '"+øvelse+"'";
        try {
            ResultSet result = startConnectiontoDatabaseAndQuery(sql);
            while(result.next()) {
                int mål = result.getInt("mål");
                System.out.println(mål);
                System.out.println(result.getBoolean("måltype"));
                boolean måltype = result.getBoolean("måltype");
                if (måltype) {
                    if (mål <= bragd) {
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Du har nådd målet for " + øvelse + " på tide å registrere et nytt mål");
                        System.out.println("Skriv inn nytt mål i tid/kg/meter");
                        String line = sc.nextLine();
                        String sql2 = "update øvelse set mål='" + Integer.parseInt(line) + "' where navn ='" + øvelse + "'";
                        startConnectiontoDatabaseAndUpdate(sql2);
                    }
                } else {
                    if (mål >= bragd) {
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Du har nådd målet for" + øvelse + "på tide å registrere et nytt mål");
                        String line = sc.nextLine();
                        System.out.println("Skriv inn nytt mål i tid/kg/meter");
                        String sql2 = "uppdate øvelse set mål='" + Integer.parseInt(line) + "' where navn ='" + øvelse + "'";
                        startConnectiontoDatabaseAndUpdate(sql2);
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
    }
    public void visOkt(int øktid){
        try {
            String key = "select * from treningsøkt where " + øktid + " = idtreningsøkt order by datotid";
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
    public void visAlleØkter(){
        try {
            ResultSet myRs = startConnectiontoDatabaseAndQuery("select * from treningsøkt order by datotid");
            while (myRs.next()){
                System.out.println(myRs.getString("datotid") + " " + myRs.getString("varighet") + " " + myRs.getString("personlig_form")
                + " " + myRs.getString("notat") + " " + myRs.getString("prestasjon") + " " + myRs.getString("sett"));
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
            runUpdate(id);

        }catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
    }
    // sletter øvelser som blir skrevet inn
    public void runUpdate(int id){
        Scanner sca = new Scanner(System.in);
        System.out.println("Hvilke øvelser ønsker du å fjerne?( eks: Løp, Styrke, etc...");
        sca.useDelimiter(", ");
        String line = sca.nextLine();
        String[] lineArray = line.split(", ");
        for(String ovelse: lineArray){
            startConnectiontoDatabaseAndUpdate("Delete from øvelseriøkt where '"+ovelse+"'= øvelsesnavn");
        }
        System.out.println("Vil du legge inn flere øvelser (1/0 - ja/nei)");
        int svar = Integer.parseInt(sca.nextLine());
        if(svar == 1){
            String ovelser = getOvelser();
            System.out.println("hei");
            ØvelseTilØvelseriøkt(id, ovelser);
        }
    }

}
