package Programes;
import java.sql.Connection;
import  java.util.Random;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import Validacions.validacions;
public class ProgramaClient {

	public static void main(String[] args) {
		Scanner lector=new Scanner (System.in);
		Scanner LDades=new Scanner(System.in);
		int opcio=0;	
		do {
			System.out.println("\n\t BOTIGA ONLINE\n\n1. Login\n2. Donar-se d'alta");
			opcio=verificarInt(opcio);
			switch (opcio){
			case 1:
				System.out.println("\tINICIA SESSIO");
				System.out.println("Entra DNI");
				String dni=LDades.nextLine();
				System.out.println("Entra la contrasenya");
				String contrasenya=LDades.nextLine();

				String resultat="";
				try {
					resultat = Login(dni,contrasenya);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				if (resultat.equals("contrasenyaerror")){
					String sino="";
					do {
						System.out.println("Contrasenya Icorrecta, introdueix-la de nou");
						contrasenya=LDades.nextLine();
						try {
							resultat=Login(dni,contrasenya);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						System.out.println("Vols tornar a al menú de login (si) o vols repetir la contrasenya(no)");
						sino=lector.nextLine();
						do {
							if (!sino.equalsIgnoreCase("Si")&&!(sino.equalsIgnoreCase("no"))) {
								System.out.println("La resposta ha de ser si o no, vols donar-te d'alta?");
								sino=lector.nextLine();
							}
						}
						while (!sino.equalsIgnoreCase("Si")&&(!sino.equalsIgnoreCase("no")));
					}
					while(!resultat.equals("ok")&&sino.equalsIgnoreCase("no"));
				}	
				else if(resultat.equals("nousuari")){
					System.out.println("Usuari inexistent, vols donar-te d'alta?");
					String sino=lector.nextLine();
					do {
						if (!sino.equalsIgnoreCase("Si")&&!(sino.equalsIgnoreCase("no"))) {
							System.out.println("La resposta ha de ser si o no, vols donar-te d'alta?");
							sino=lector.nextLine();
						}
					}
					while (!sino.equalsIgnoreCase("Si")&&(!sino.equalsIgnoreCase("no")));

					if(sino.equalsIgnoreCase("si")) {
						System.out.println("\tCREAR USUARI");
						do{
							System.out.println("Introdueix DNI:");
							dni=LDades.nextLine();
						}
						while(!validacions.dniValid(dni));

						System.out.println("Introdueix nom:");
						String nom=LDades.nextLine();

						System.out.println("Introdueix Contrasenya:");
						contrasenya=LDades.nextLine();

						String contrasenya2="";
						do {
							System.out.println("Repeteix la Contrasenya:");
							contrasenya2=LDades.nextLine();
						}
						while (!contrasenya2.equalsIgnoreCase(contrasenya));
						String email="";
						do{
							System.out.println("Introdueix correu electronic:");
							email=LDades.nextLine();
						}
						while(!validacions.validarEmail(email));

						String telefon="";
						do{
							System.out.println("Introdueix telefon:");
							telefon=LDades.nextLine();
						}
						while(!validacions.validarTelefon(telefon));

						System.out.println("Introdueix adreça:");
						String adreca=lector.nextLine();
						try {
							altaClient(dni,nom,contrasenya,email,telefon,adreca);

						}
						catch(Exception e) {
							System.out.println(e);
						}
					}

				}
				else if (resultat.equals("ok")) {
					boolean sortir=false;
					Client c1=new Client();
					try {
						dadesObjecteClient(c1,dni);
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					//------------------------------------------------------------------------------------------------MENU CLIENT----------------------------------------------------------
					do {
						System.out.println("\n\n\tMENU CLIENT\n");
						System.out.println("1. Comprar\n2. Editar dades compte\n3. Donar-se de baixa\n4. Tanca Sessió");
						opcio=LDades.nextInt();
						LDades.nextLine();

						switch(opcio) {
						case 1:
							ArrayList<LiniaFactura>a=new ArrayList<LiniaFactura>();
							int producte=0;
							String op="a";


							do {
								try {
									System.out.println(llistaProductes());
									System.out.println("Escriu el codi del producte que vulguis\n1.Per accedir al Carret\n0.Per finalitzar la compra");
								} catch (SQLException e2) {
									e2.printStackTrace();
								}
								producte=verificarInt(producte);

								if(producte==0) {
									System.out.println("Vols realitzar la compra? S/n");
									op=lector.nextLine();
									if (op.equalsIgnoreCase("S")) {
										boolean validat=false;
										Random rnd = new Random();
										do {
											int numFactura=rnd.nextInt()*1000;
											try {
												if(!existeix(numFactura)) {
													Factura f1=new Factura(c1,LocalDate.now(),numFactura,a);
													validat=true;
													facturaabd(f1);
													System.out.println(imprimirfactura(f1));
													f1.productes.clear();
												}
											} catch (SQLException e) {
												e.printStackTrace();
											}
										}
										while(!validat);
									}

									if(op.equalsIgnoreCase("n")) {
										a.clear();
									}
								}


								else if(producte==1) {
									int opcio2=0;
									do {
										System.out.println(carret(a));	
										System.out.println("Prem el numero de linia que vulguis editar o 0 si vols seguir comprant.");
										opcio=verificarInt(opcio);
										opcio=opcio-1;
										if(opcio>-1) {
											System.out.println(a.get(opcio).producte.nom+" - "+a.get(opcio).unitat);
											System.out.println("1.Restar unitats\n 2.Sumar unitats -\n 3.Eliminar del carret");
											opcio2=verificarInt(opcio2);
											int quantitat=0;
											if (opcio2==1) {
												System.out.println("Quantes unitats de "+a.get(opcio).producte.nom+" vols restar?");
												quantitat=verificarInt(quantitat);

												a.get(opcio).unitat=a.get(opcio).unitat-quantitat;
											}
											else if (opcio2==2) {
												System.out.println("Quantes unitats de "+a.get(opcio).producte.nom+" vols sumar?");
												quantitat=verificarInt(quantitat);

												a.get(opcio).unitat=a.get(opcio).unitat+quantitat;
											}
											else if(opcio2==3) {
												a.remove(opcio);
											}
										}

									}
									while(opcio!=-1);
								}

								else {
									int unitats=0;

									System.out.println("Cuantes unitats del producte vols?");

									unitats=verificarInt(unitats);




									LiniaFactura aux=new LiniaFactura();
									try {
										aux.producte=carregarObjecte(producte);
									} catch (SQLException e) {
										e.printStackTrace();
									}

									aux.unitat=unitats;
									a.add(aux);

								}
							}
							while(producte!=0);






							break;
						case 2:
							try {
								String dada="";
								System.out.println(c1.getDaddesModificables());
								System.out.println("\nQuina dada vols canviar?\n\t1.Correu\n\t2.Telefon\n\t3.adreca\n\t4.cancel·lar");
								opcio=lector.nextInt();
								lector.nextLine();
								if(opcio==1) {	
									do{
										System.out.println("Introdueix el nou correu electronic:");
										dada=LDades.nextLine();
									}
									while(!validacions.validarEmail(dada));
									modificarDada(c1, "correu",dada);
								}
								else if(opcio==2) {
									do{
										System.out.println("Introdueix el nou telefon:");
										dada=LDades.nextLine();
									}
									while(!validacions.validarEmail(dada));
									modificarDada(c1,"telefon",dada);
								}
								else if(opcio==3) {

									System.out.println("Introdueix la nova adreça:");
									dada=LDades.nextLine();									
									modificarDada(c1,"adreca",dada);
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							break;
						case 3:
							try {
								baixaClient(c1);

							} catch (SQLException e) {
								e.printStackTrace();
							}
							sortir=true;
							break;
						case 4:
							sortir=true;

						}
					}
					while(!sortir);

				}
				break;
				//-----------------------------------------------------------------------------------FI MENU CLIENT-------------------------------------------------------------------------



			case 2:

				System.out.println("\tDona't d'Alta");
				do{
					System.out.println("Introdueix DNI:");
					dni=LDades.nextLine();
				}
				while(!validacions.dniValid(dni));

				System.out.println("Introdueix nom:");
				String nom=LDades.nextLine();

				System.out.println("Introdueix Contrasenya:");
				contrasenya=LDades.nextLine();

				String contrasenya2="";
				do {
					System.out.println("Repeteix la Contrasenya:");
					contrasenya2=LDades.nextLine();
				}
				while (!contrasenya2.equalsIgnoreCase(contrasenya));
				String email="";
				do{
					System.out.println("Introdueix correu electronic:");
					email=LDades.nextLine();
				}
				while(!validacions.validarEmail(email));

				String telefon="";
				do{
					System.out.println("Introdueix telefon:");
					telefon=LDades.nextLine();
				}
				while(!validacions.validarTelefon(telefon));

				System.out.println("Introdueix adreça:");
				String adreca=lector.nextLine();

				try {
					altaClient(dni,nom,contrasenya,email,telefon,adreca);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		while(opcio!=3);
	}

	public static String Login(String dni,String contrasenya)throws SQLException {
		String resultat="";
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );

		String sql="select * from client where dni=\'"+dni+"\'";

		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()) {
			if(!rs.getString("contrasenya").equals(contrasenya)) {
				resultat="contrasenyaerror";
			}
			else resultat="ok";

		}
		else resultat="nousuari";
		return resultat;
	}
	public static void dadesObjecteClient(Client c1,String dni) throws SQLException {
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );

		String sql="select * from client where dni=\'"+dni+"\'";
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()) {
			c1.setClient(rs.getString("dni"), rs.getString("nom"), rs.getString("correu"), rs.getString("telefon"), rs.getString("adreca"), rs.getString("contrasenya"));
			System.out.println(rs.getString("nom"));
		}
	}

	public static void altaClient(String dni,String nom, String correu, String telefon, String adreca, String contrasenya)throws SQLException {

		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement();
		stmt.executeUpdate("Insert into client values(\'"+dni+"\',\'"+nom+"\',\'"+correu+"\',\'"+telefon+"\',\'"+adreca+"\',\'"+contrasenya+"\');");

	}
	public static void baixaClient(Client c1) throws SQLException {
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    

		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );

		String sql="select * from client where dni=\'"+c1.getDni()+"\'";

		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()) {
			rs.deleteRow();
		}
	}

	public static void modificarDada(Client c1,String columna, String dada) throws SQLException {
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement	stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from client where dni=\'"+c1.getDni()+"\'";
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()) {
			rs.updateString(columna,dada);
			rs.updateRow();
		}
	}
	public static String llistaProductes() throws SQLException {
		String llista="";
		String categoria="a";
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from producte group by seccio,codi order by seccio,codi;";
		ResultSet rs=stmt.executeQuery(sql);

		while (rs.next()) {

			if(!categoria.equalsIgnoreCase(rs.getString("seccio"))){
				categoria=rs.getString("seccio");
				llista=llista+categoria+"\n";
			}
			categoria=rs.getString("seccio");

			llista=llista+" \t"+rs.getInt("codi")+" "+rs.getString("descripcio")+" "+rs.getInt("stock")+" "+rs.getDouble("preu")+"€ "+rs.getInt("iva")+"%\n";
		}
		return llista;
	}
	public static Producte carregarObjecte(int codi) throws SQLException {
		Producte p1=new Producte();
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from producte where codi=\'"+codi+"\'";
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()) {
			p1=new Producte(rs.getInt("codi"),rs.getString("descripcio"),rs.getString("seccio"),rs.getInt("stock"),rs.getDouble("preu"),rs.getInt("iva"));
		}
		return p1;
	}
	public static String carret(ArrayList<LiniaFactura>a){
		String carret="";
		double preuiva=0;
		double total=0;
		for(int i=0;i<a.size();i++) {
			preuiva=a.get(i).unitat*(a.get(i).producte.preu+((a.get(i).producte.preu/100)*a.get(i).producte.iva));
			carret=carret+(i+1)+" "+a.get(i).producte.nom+" \t"+a.get(i).producte.preu+"€ "+a.get(i).unitat +" "+a.get(i).producte.iva+"%  "+String.format("%2f",preuiva)+"€\n";
			total=total+preuiva;
		}
		carret=carret+"Total: "+String.format("%2f",total)+"€\n\n";
		return carret;
	}
	public static boolean existeix(int numfactura) throws SQLException {

		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from factura where num_factura=\'"+numfactura+"\'";
		ResultSet rs=stmt.executeQuery(sql);
		if(!rs.next()) {
			return false ;
		}
		else {
			return true;
		}
	}
	public static void facturaabd(Factura f1) throws SQLException {
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement();
		Statement stmt2=con.createStatement();

		stmt.executeUpdate("Insert into factura values(\'"+f1.getNumFactura()+"\',\'"+f1.getClient().getDni()+"\',\'"+f1.getData()+"\');");
		for (int i=0;i<f1.getProductes().size();i++) {
			stmt2.executeUpdate("Insert into linia_factures values(\'"+f1.getNumFactura()+"\',\'"+f1.getProductes().get(i).producte.codi+"\',\'"+f1.getProductes().get(i).unitat+"\');");

		}
	}
	public static String imprimirfactura(Factura f1) throws SQLException {

		String carret="";
		double preuiva=0;
		double total=0;
		carret=carret+"Num Factura: "+f1.getNumFactura()+"\nDni Client: "+f1.getClient()+"\nData defactura: "+f1.getData()+"\n\n";
		for(int i=0;i<f1.productes.size();i++) {
			preuiva=f1.productes.get(i).unitat*(f1.productes.get(i).producte.preu+(f1.productes.get(i).producte.preu/100*f1.productes.get(i).producte.iva));
			carret=carret+f1.productes.get(i).producte.nom+" \t"+f1.productes.get(i).producte.preu+"€ "+f1.productes.get(i).unitat +" "+f1.productes.get(i).producte.iva+"% "+String.format("%2f",f1.productes.get(i).producte.preu*f1.productes.get(i).producte.preu)+" "+String.format("%2f",preuiva)+"€\n";
			total=total+preuiva;
		}
		carret=carret+"Total: "+total+"€\n\n";
		return carret;


	}
	public static double verificarDouble(double n) {
		Scanner lector = new Scanner(System.in);
		boolean funciona = lector.hasNextDouble();     
		if(funciona) { 
			n = lector.nextDouble(); lector.nextLine(); 
		}
		else {
			while((!funciona)) { 
				lector.nextLine();         
				System.out.println("Format incorrecte, introdueix de nou"); 
				funciona = lector.hasNextDouble(); 
				if(funciona) {
					n = lector.nextDouble(); lector.nextLine();
				}
			}
		}
		return n;
	}
	public static int verificarInt(int n) {
		Scanner lector = new Scanner(System.in);
		boolean funciona = lector.hasNextInt();     
		if(funciona) { 
			n = lector.nextInt(); lector.nextLine(); 
		}
		else {
			while((!funciona)) { 
				lector.nextLine();         
				System.out.println("Format incorrecte, introdueix de nou"); 
				funciona = lector.hasNextInt(); 
				if(funciona) { 
					n = lector.nextInt(); lector.nextLine();
				}
			}
		}
		return n;
	}
}
