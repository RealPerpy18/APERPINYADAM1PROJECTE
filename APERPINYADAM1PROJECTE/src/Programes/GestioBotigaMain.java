package Programes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class GestioBotigaMain {

	public static void main(String[] args) {
		Scanner lector=new Scanner (System.in);

		int opcio=0;	
		do {
			System.out.println("1.Gestio de productes\nDades Clients");
			if(lector.hasNextInt()) {
				opcio=lector.nextInt();
				lector.nextLine();
			}
			switch (opcio){
			case 1:
				

				break;
			}

		}
		while (opcio!=111);
	}
}