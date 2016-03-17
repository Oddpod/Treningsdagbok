package Project;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Odd on 16.03.2016.
 */
public class Treningsprogram {
    public static void main(String[] args)throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hva ønsker de å gjøre?: 1. Registrere treningsøkt, " +
                "2. Vis treningslogg, 3 vis alle treningsøkter, 4. Lag treningsøkt frå mal, " +
                "5. Vis Øvelser i økt");
        int alt = Integer.parseInt(sc.nextLine());
        Treningsdagbokprog tdb = new Treningsdagbokprog();
        try {
            switch (alt) {
                case 1:
                    System.out.println("regTren");
                    tdb.regTreningsokt();
                    break;
                case 2:
                    System.out.println("vis logg");
                    tdb.visLogg();
                    break;
                case 3:
                    System.out.println("Skriv startdato, sluttdato");
                    sc.useDelimiter(", ");
                    String line = sc.nextLine();
                    String[] lineArray = line.split(", ");
                    tdb.visAlleØkter(lineArray[0], lineArray[1]);
                    break;
                case 4:
                    System.out.println("Lag treningsøkt frå mal");
                    tdb.repeatOkt(0);
                    break;
                case 5:
                    System.out.println("Øvelser i økt");
                    tdb.repeatOkt(1);
                    break;
            }
        }catch(Exception e){
            System.out.println("Feil i databasen, feilkode" + e);
        }
    }
}