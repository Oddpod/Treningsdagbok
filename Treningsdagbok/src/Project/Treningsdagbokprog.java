package Project;

import java.util.Scanner;;
/**
 * Created by Acer on 13.03.2016.
 */
public class Treningsdagbokprog {


    public void ØvelseTilØvelserIØkt(String ØvelseNavn){
        String yo = "select count(distinct øktid) from øvelseriøkt;"
        int øktid = myStmt.executeUpdate(yo) + 1
        String getØvelse = "insert into øvelseriøkt(øktid , øvelsesnavn) values(" + øktid + "," + ØvelseNavn + " )"
        myStmt.executeUpdate(getØvelse)
    }



    private String url = "jdbc:mysql://localhost:3306/treningsdagbokdb";
    private String user = "root";
    private String password = "meh";

    public void main(String[] args) {
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("1999-01-12 23:01:24");

        String CT = sdf.format(dt);

        Scanner sc = new Scanner(System.in);
            System.out.println("Opprett Treningsøkt:");
            this.user = sc.nextLine();
            this.password = sc.nextLine();
        System.out.println(user + " " + password);

    }

    public void Createsumthin(){

    }

}
