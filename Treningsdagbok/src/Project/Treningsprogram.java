package Project;

import java.util.Scanner;

/**
 * Created by Odd on 16.03.2016.
 */
public class Treningsprogram {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hva ønsker de å gjøre?: 1. Registrere treningsøkt, " +
                "2. Vis treningslogg, 3 vis alle treningsøkter, 4. Lag treningsøkt frå mal, " +
                "5. Sammenlign beste resultat");
        int alt = Integer.parseInt(sc.nextLine());
        Treningsdagbokprog tdb = new Treningsdagbokprog();
        switch(alt){
            case 1:
                System.out.println("regTren");
                break;
            case 2:
                System.out.println("vis logg");
                tdb.visLogg();
                break;
            case 3:
                System.out.println("vis alle treningsøkter");
                tdb.visAlleØkter();
                break;
            case 4:
                break;
        }
    }
}