package jbdcdemo;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Driver {

	public static void main(String[] args) {

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("1999-01-12 23:01:24");

        String CT = sdf.format(dt);
		String url = "jdbc:mysql://localhost:3306/treningsdagbokdb";
		String user = "root";
		String password = "Julaften1!";
		
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			/* 2. Create a statement */
			Statement myStmt = myConn.createStatement();
			//3. Execute SQL query
			ResultSet myRs = myStmt.executeQuery("select * from treningsøkt");
            int i = 3;
			String sql = "insert into treningsøkt "
					+ "(idtreningsøkt, datotid, varighet, personlig_form, notat, prestasjon )"
					+ " values('', '1999.01.12 23:54:12', '45', '5', 'OH MY GOD', '9')";
            try {
                myStmt.executeUpdate(sql);
            } catch (Exception e) {
                throw new MySQLIntegrityConstraintViolationException("meh");
            }
            i++;
			// 4. Process the result set
            ResultSet myRsi = myStmt.executeQuery("select * from treningsøkt");
			while (myRsi.next()){
				System.out.println(myRsi.getString("datotid") + ", " + myRsi.getString("prestasjon") + ", " + myRsi.getString("notat"));
			}
		}
		catch (Exception exc){
			exc.printStackTrace();
		}

	}

}
