package jbdcdemo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Driver {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/treningsdagbokdb";
		String user = "root";
		String password = "AVGvisualstudio123?";
		
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			Statement myStmt = myConn.createStatement();
			//3. Execute SQL query
			ResultSet myRs = myStmt.executeQuery("select * from treningsøkt");
			String sql = "insert into treningsøkt "
					+ "(idtreningsøkt, dato/tidspunkt, varighet, personlig_form, notat, prestasjon )"
					+ " values(2, 31.12.1999, 45, 5, 'OH MY GOD', 9)";
			myStmt.executeUpdate(sql);
			//4. Process the result set
			while (myRs.next()){
				System.out.println(myRs.getString("dato/tidspunkt") + ", " + myRs.getString("prestasjon") + ", " + myRs.getString("notat"));
			}
		}
		catch (Exception exc){
			exc.printStackTrace();
		}

	}

}
