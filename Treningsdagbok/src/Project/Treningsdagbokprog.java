package Project;

import java.util.Scanner;;
/**
 * Created by Acer on 13.03.2016.
 */
public class Treningsdagbokprog {

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
