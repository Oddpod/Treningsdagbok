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


    public void startConnectiontoDatabaseAndUpdate(String sql) throws SQLException {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement myStat = (Statement) myConn.createStatement();
            myStat.executeUpdate(sql);
    }

    public ResultSet startConnectiontoDatabaseAndQuery(String sql) throws SQLException {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement myStat = (Statement) myConn.createStatement();
            ResultSet myRsi = myStat.executeQuery(sql);
            return myRsi;
    }


    public void ØvelseTilØvelseriøkt (int id, String ovelser) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Skriv inn navnet på alle øvelsene du vil ha i økten");
        sc.useDelimiter(", ");
        String line = sc.nextLine();
        String[] lineArray = line.split(", ");

        for (String ovelse : lineArray) {

            if (!ovelser.contains(ovelse)) {
                System.out.println(ovelse + " ligger ikke inne i databasen og må derfor opprettes");

                regOvelse();

            }
            String sql = " insert into øvelseriøkt "
                    + "(øktid, øvelsesnavn)"
                    + "values( '" + id + "', '" + ovelse + "')";
            startConnectiontoDatabaseAndUpdate(sql);
        }
    }


    public void regForhold(int idTreningsøkt, String date) throws SQLException {
        boolean inne = false;
        boolean ute = false;
        ResultSet myRs2;
        idTreningsøkt = 1;
        String sql3 = "SELECT øktid, øvelsesnavn FROM øvelseriøkt " +
                "WHERE '" + idTreningsøkt + "' = øktId";
        ResultSet myRs = startConnectiontoDatabaseAndQuery(sql3);
        while (myRs.next()) {
            System.out.println("hei");
            String ovelse = myRs.getString("øvelsesnavn");

            myRs2 = startConnectiontoDatabaseAndQuery("SELECT inne from øvelse " +
                    "where '" + ovelse + "' = navn");
            myRs2.next();
            int UteEllerInne = myRs2.getInt("inne");
            if (ute == false) {
                ute = (UteEllerInne == 0) ? true : false;
            }
            if (inne == false) {
                inne = (UteEllerInne == 0) ? false : true;
            }
            if ((inne && ute) == true) {
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
                    + "values('" + date + "', '" + lineArray[0] + "','" + lineArray[1] + "', '" + idTreningsøkt + "')";
            startConnectiontoDatabaseAndUpdate(sql);
        }
        if (ute) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Skriv inn vær og temperatur");
            String line = sc.nextLine();
            String[] lineArray = line.split(", ");
            String sql = "insert into utendørsforhold"
                    + "( dato, vær, temperatur, idtreningsøkt)"
                    + "values('" + date + "', '" + lineArray[0] + "','" + lineArray[1] + "', '" + idTreningsøkt + "')";
            startConnectiontoDatabaseAndUpdate(sql);
        }
    }

    public void regTreningsokt() throws SQLException{
        int id = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Oppretter Treningsøkt..");
        System.out.println("Skriv inn datotid, varighet, personlig form, notat, prestasjon");
        sc.useDelimiter(", ");
        String line = sc.nextLine();
        String[] lineArray = line.split(", ");
        String sql = "insert into treningsøkt "
                + "( datotid, varighet, personlig_form, notat, prestasjon )"
                + " values('" + lineArray[0] + "', '" + lineArray[1] + "', '" + lineArray[2] + "', '"
                + lineArray[3] + "', '" + lineArray[4] + "')";
        startConnectiontoDatabaseAndUpdate(sql);

        String key = "Select Max(idtreningsøkt) from treningsøkt";
        ResultSet myRsi = startConnectiontoDatabaseAndQuery(key);
        id = Integer.parseInt(myRsi.getString("Max(idtreningsøkt)"));
        regForhold(id, lineArray[0]);
        String ovelser = getOvelser();
        ØvelseTilØvelseriøkt(id, ovelser);
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

    public void regTypeInfo(String type, String ovelse, String gruppenavn) throws SQLException {
        if(type == "Utholdenhet") {
            Scanner sc = new Scanner(System.in);
            System.out.println("Skriv inn lengde på øvelsen");
            String lengde = sc.nextLine();
            String sql = "insert into utholdenhet"
                    + "lengde, gruppenavn, øvelsesnavn"
                    + "values('"+lengde+"', '"+gruppenavn+"', '"+ovelse+"')";
            startConnectiontoDatabaseAndUpdate(sql);
        } else {
            Scanner sc = new Scanner(System.in);
            sc.useDelimiter(", ");
            System.out.println("Skriv inn belastning, antall repetisjoner og antall sett");
            String line = sc.nextLine();
            String[] lineArray = line.split(", ");
            String sql = "insert into kondisjon/styrke"
                    + "belastning, ant repetisjoner, ant sett, gruppenavn, øvelsesnavn"
                    + "values('"+lineArray[0]+"', '"+lineArray[1]+"', '"+lineArray[2]+"', '"+gruppenavn+"', '"+ovelse+"')";
            startConnectiontoDatabaseAndUpdate(sql);
        }
    }

    public void addGroup(String gruppe) throws SQLException {
        String sql = "insert into undergruppe"
                + "(gruppenavn)"
                + "values('"+gruppe+"')";
        startConnectiontoDatabaseAndUpdate(sql);
    }

    public void addToGroup(String ovelse, String type) throws SQLException {
        System.out.println("Skriv hvilken gruppe øvelsen hører til");
        Scanner sc = new Scanner(System.in);
        String gruppe = sc.nextLine();
        String grupper = "";

        try {
            ResultSet myRs = startConnectiontoDatabaseAndQuery("SELECT gruppenavn from undergruppe");
            while (myRs.next()) {
                grupper += myRs.getString("gruppenavn") + " ";
            }
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
        if (!grupper.contains(gruppe)) {
            addGroup(gruppe);
        }

        regTypeInfo(type, ovelse, gruppe);

    }

    public void regResultat() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Skriv inn øvelsesnavn, bragd, antallsett");
        sc.useDelimiter(",");
        String line = sc.nextLine();
        String[] lineArray = line.split(", ");
        Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
        Statement mystat = (Statement) myConn.createStatement();
        String sql = " insert into resultat "
                + "(øvelsesnavn, bragd, sett)"
                + "values ('" + lineArray[0] + "', '" + lineArray[1] + "', '" + lineArray[2] + "')";
        mystat.executeUpdate(sql);

        MålVsBragd(Integer.parseInt(lineArray[1]), lineArray[0]);
    }

    public void MålVsBragd(int bragd, String øvelse ) throws SQLException {
        String sql = "select mål, måltype from øvelse where navn = '"+øvelse+"'";
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
                        String sql2 = "update øvelse set mål='" + Integer.parseInt(line) + "' where navn ='" + øvelse + "'";
                        startConnectiontoDatabaseAndUpdate(sql2);
                    }
                }
            }

    }
    public void visOkt(int øktid) throws SQLException {

            String key = "select * from treningsøkt where " + øktid + " = idtreningsøkt order by datotid";
            ResultSet myRs = startConnectiontoDatabaseAndQuery(key);
            while (myRs.next()) {
                System.out.println(myRs.getString("datotid") + ", " + myRs.getString("varighet") + ", " + myRs.getString("personlig_form")
                        + ", " + myRs.getString("notat") + ", " + myRs.getString("prestasjon") + ", " + myRs.getString("sett"));
            }


    }

    public void visLogg() throws SQLException{

            ResultSet myRs = startConnectiontoDatabaseAndQuery("select datotid, notat from treningsøkt order by datotid");
            while (myRs.next()){
                System.out.println(myRs.getString("datotid") + ", " + myRs.getString("notat"));
            }

    }
    public void visAlleØkter(String Startdate, String Sluttdato) throws SQLException{
        ResultSet myRs = startConnectiontoDatabaseAndQuery("select * from treningsøkt where datotid > '"+Startdate+"'" +
                " and datotid < '"+Sluttdato+"' order by datotid");
        while (myRs.next()) {
            System.out.println(myRs.getString("datotid") + " " + myRs.getString("varighet") + " " + myRs.getString("personlig_form")
                    + " " + myRs.getString("notat") + " " + myRs.getString("prestasjon"));
        }
    }

    public String getOvelser() throws SQLException {
        String ovelser = "";
        ResultSet myRs = startConnectiontoDatabaseAndQuery("SELECT navn from øvelse");
        while (myRs.next()) {
            ovelser = ovelser + myRs.getString("navn") + " ";
        }

        return ovelser;
    }

    public String getOvelseriokt(int id) throws SQLException{
        String ovelseriokt = "";
        ResultSet myRs = startConnectiontoDatabaseAndQuery("SELECT øvelsesnavn from øvelseriøkt where '" + id + "' = øktid");
        while (myRs.next()) {
            ovelseriokt = ovelseriokt + myRs.getString("øvelsesnavn") + " ";
        }
        return ovelseriokt;
    }
    public void repeatOkt(int choice) throws SQLException{
        String ovelser= "";
        ResultSet myRsi = startConnectiontoDatabaseAndQuery("select datotid, notat from treningsøkt");
        while (myRsi.next()) {
            System.out.println(myRsi.getString("datotid") + ", " + myRsi.getString("notat"));
        }
        ;
        if(choice == 1){
            Scanner sca = new Scanner(System.in);
            System.out.println("Velg økt(1, 2, 3 osv...");
            int id = Integer.parseInt(sca.nextLine());
            ResultSet myRs = startConnectiontoDatabaseAndQuery("SELECT * from treningsøkt inner join øvelseriøkt on " +
                    "treningsøkt.idtreningsøkt = øvelseriøkt.øktid" +
                    " where " + id + "=treningsøkt.idtreningsøkt");
            while (myRs.next()) {
                ovelser += myRs.getString("øvelsesnavn") + " ";
            }
            System.out.println(ovelser);
        }
        else {
            Scanner sc = new Scanner(System.in);
            System.out.println("Velg økt du vil bruke som mal(1, 2, 3 osv...)");
            int id = Integer.parseInt(sc.nextLine());
            ResultSet myRs = startConnectiontoDatabaseAndQuery("SELECT * from treningsøkt inner join øvelseriøkt on " +
                    "treningsøkt.idtreningsøkt = øvelseriøkt.øktid" +
                    " where " + id + "=treningsøkt.idtreningsøkt");
            while (myRs.next()) {
                ovelser += myRs.getString("øvelsesnavn") + " ";
            }
            runUpdate(id);
        }
    }
    // sletter øvelser som blir skrevet inn
    public void runUpdate(int id) throws SQLException {
        Scanner sca = new Scanner(System.in);
        String ovelseriokt = getOvelseriokt(id);
        System.out.println("Hvilke øvelser ønsker du å fjerne? " + ovelseriokt);
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
