package Project;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;;

public class Treningsdagbokprog {

    private String url = "jdbc:mysql://localhost:3306/treningsdagbokdb";
    private String user = "root";
    private String password = "AVGvisualstudio123?";

    public static void main(String[] args) {
        Treningsdagbokprog dagbok = new Treningsdagbokprog();
        try {
            int meh = dagbok.newKey("treningsøkt");
            System.out.println(meh);;
        } catch(Exception e){

        }
    }

    public int newKey(String navn) throws MySQLIntegrityConstraintViolationException {
        try {
            Connection myConn = DriverManager.getConnection(url, user, password);
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select count(*) from treningsøkt");
            System.out.println(myRs.getString("count*"));
            //String sql = " select * from navn count *";
            //myStmt.executeUpdate(sql);
        } catch (Exception e) {
            throw new MySQLIntegrityConstraintViolationException("meh");
        }

        return myRs.toInteger();
    }
}
