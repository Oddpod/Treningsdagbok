package Project;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;;

public class Treningsdagbokprog {



    private String url = "jdbc:mysql://localhost:3306/treningsdagbokdb";
    private String user = "root";
    private String password = "meh";

    public static void main(String[] args) {
        Treningsdagbokprog dagbok = new Treningsdagbokprog();
        try {
            dagbok.regOvelse();
        } catch (Exception e) {
            System.out.println("Exception thrown:" + e);
        }
    }


    public void regTreningsokt(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Oppretter Treningsøkt..");
        System.out.println("");
        sc.close();
    }

    public void regOvelse() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Skriv inn Navn, type, beskrivelse, mål, inne?");
        while(sc.hasNext()) {
            String navnet = sc.next();
            String type = sc.next();
            String beskrivelse = sc.next();
            String mål = sc.next();
            Boolean inne = sc.nextBoolean();
        }

        try {
            Connection myConn = (Connection) DriverManager.getConnection(url, user, password);
            Statement myStat = (Statement) myConn.createStatement();
            String sql = " insert into øvelse "
                    + "(navn, type, beskrivelse, mål, inne?, øktid, treningsid)"
                    + "values(navnet, type, beskrivelse, mål, inne)";
            myStat.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Exception thrown" + e);
        }
    }

}
