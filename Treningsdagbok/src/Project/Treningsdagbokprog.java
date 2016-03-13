package Project;

import java.util.Scanner;;
/**
 * Created by Acer on 13.03.2016.
 */
public class Treningsdagbokprog {

    public static void main(String[] args) {
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("1999-01-12 23:01:24");

        String CT = sdf.format(dt);
        String url = "jdbc:mysql://localhost:3306/treningsdagbokdb";
        String user = "root";
        String password = "meh";

        Scanner sc = new Scanner(System.in);
            System.out.println();
            user = sc.nextLine();
            password = sc.nextLine();
    }





}
