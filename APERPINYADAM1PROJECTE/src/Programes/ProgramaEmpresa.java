package Programes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Validacions.validacions;
public class ProgramaEmpresa {

	public static void main(String[] args) {
		Scanner lector=new Scanner (System.in);
		int opcio=0;	
		do {
			System.out.println("\n\n\n\tMENU BOTIGA\n\n1.Gestio de productes\n2.Dades Clients\n3.Tancar Programa");
				opcio=verificarInt(opcio);
			switch (opcio){
			case 1:
				do {
					System.out.println("\n\n\n\tGESTIO PRODUCTES\n1.Alta productes\n2.Modificar Productes\n3.Baixa Producte\n4. Tornar Enrere");
						opcio=verificarInt(opcio);

					
					switch(opcio) {
					case 1:
						double preu=0.0;
						int stock=0;
						int iva=0;
						System.out.println("Introdueix descripcio del producte:");
						String desc= lector.nextLine();
						System.out.println("A quina secció pertany?");
						String seccio=lector.nextLine();

						System.out.println("Preu de venda al public:");
						preu=verificarDouble(preu);





						System.out.println("Stock inicial:");
						stock=verificarInt(stock);


						System.out.println("Iva del producte");
						iva=verificarInt(iva);


						try {
							altaProducte(desc, seccio, preu, stock,iva);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					case 2:

						try {
							System.out.println(llistaProductes());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						System.out.println("Introdueix el codi del producte a modificar");
						int codi=0;
						codi=verificarInt(codi);
						if (codi>1000) {
						try {

							Producte p1=carregarObjecte(codi);
							System.out.println(p1.getDadesModificables());
							do {
								System.out.println("Dades per modificar\n\n\t1.Preu\n\t2.IVA\n\t3.Stock");
								opcio=verificarInt(opcio);

								if (opcio==1) {
									System.out.println("Introdueix el nou preu");
									double canvipreu=0;
									canvipreu=verificarDouble(canvipreu);
									modificarProducte(p1,"preu",canvipreu);

								}
								else if(opcio==2) {
									System.out.println("Introdueix el nou IVA");
									if (lector.hasNextInt()) {
										int nouiva=0;
										nouiva=verificarInt(nouiva);
										modificarProducteInt(p1,"iva",nouiva);
									}
								}
								else if(opcio==3) {
									System.out.println("Introdueix Cantitat de Stock");
									if (lector.hasNextInt()) {
										int noustock=0;
										noustock=verificarInt(noustock);
										modificarProducteInt(p1,"stock",noustock);
									}
								}
								else {
									System.out.println(opcio+" No és una de les opcions disponibles");
								}

							} 
							while(opcio<1 && opcio>3);
						}
						catch (SQLException e2) {
							e2.printStackTrace();
						}
						}
						break;
					case 3:
						try {
							System.out.println(llistaProductes());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						System.out.println("Introdueix el codi del producte a eliminar");
						int codi1=0;
						codi1=verificarInt(codi1);
						try {
							System.out.println(eliminarProducte(codi1));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				while(opcio!=4);
				break;

			case 2:
				int opcio2=0;

				do {

					System.out.println("\n\n\n\tMENU GESTIO CLIENT\n\n1.Visualitza dades d'un client\n2.visualitza clients\n3.Tornar al menú anterior");
						opcio2=verificarInt(opcio2);

						switch(opcio2) {
						case 1:
							System.out.println("dni del client");
							String dni=lector.nextLine();
							try {
								System.out.println(visualitzaClient(dni));
							} catch (SQLException e) {
								e.printStackTrace();
							}
							lector.nextLine();
							break;
						case 2:
							try {
								System.out.println(llistarClients());
							} catch (SQLException e) {
								e.printStackTrace();
							}
							lector.nextLine();
							break;
						}
					
				}
				while(opcio2!=3);

				break;
			}
		}
		while (opcio!=3);
	}
	public static String visualitzaClient(String dni) throws SQLException {
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement	stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from client where dni=\'"+dni+"\'";
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()) {
			return rs.getString("nom")+"\ndni: "+rs.getString("dni")+"\neMail: "+rs.getString("correu")+"\nTelefon: "+rs.getString("telefon")+"\nAdreça: "+rs.getString("adreca");
		}
		else {
			return"Client inexistent";
		}
	}
	public static String llistarClients() throws SQLException {
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement	stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from client";
		ResultSet rs=stmt.executeQuery(sql);
		String llista="";
		llista=llista+"\n\n\tLLISTA DE CLIENTS";
		while(rs.next()) {
			llista=llista+"\n"+rs.getString("nom")+" \ndni: "+rs.getString("dni")+"  eMail: "+rs.getString("correu")+"  Telefon: "+rs.getString("telefon")+"  Adreça: "+rs.getString("adreca")+"\n\n";
		}
		return llista;
	}
	public static Producte carregarObjecte(int codi) throws SQLException {

		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from producte where codi=\'"+codi+"\'";
		ResultSet rs=stmt.executeQuery(sql);
		Producte p1=new Producte(rs.getInt("codi"),rs.getString("descripcio"),rs.getString("seccio"),rs.getInt("stock"),rs.getDouble("preu"),rs.getInt("iva"));
		return p1;
	}


	public static String modificarProducte(Producte p1,String columna,Double dada) throws SQLException  {
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement	stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from producte where dni=\'"+p1.getCodi()+"\'";
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()) {
			rs.updateDouble(columna,dada);
			rs.updateRow();
			return "Dades actualitzades";
		}
		else {
			return "Producte inexistent";
		}
	}
	public static String modificarProducteInt(Producte p1,String columna,int dada) throws SQLException  {
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement	stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from producte where dni=\'"+p1.getCodi()+"\'";
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()) {
			rs.updateInt(columna,dada);
			rs.updateRow();
			return "Dades actualitzades";
		}
		else {
			return "Producte inexistent";
		}
	}
	public static void altaProducte(String descripcio,String seccio, double preu, int stock, int iva) throws SQLException {
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement();
		stmt.executeUpdate("Insert into producte(descripcio,seccio,preu,stock,iva) values(\'"+descripcio+"\',\'"+seccio+"\',\'"+preu+"\',\'"+stock+"\',\'"+iva+"\');");
	}


	public static String eliminarProducte(int codi) throws SQLException {
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from producte where codi=\'"+codi+"\'";
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()) {
			rs.deleteRow();
			return "Producte eliminat";
		}
		else {
			return "Codi inexistent";
		}

	}
	public static String llistaProductes() throws SQLException {
		String llista="";
		String categoria="a";
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/botigaonline","postgres","1234");    
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
		String sql="select * from producte group by seccio,codi order by seccio, codi;";
		ResultSet rs=stmt.executeQuery(sql);
		llista=llista+"\n\n\t\tLLISTA DE PRODUCTES\n";
		while (rs.next()) {

			if(!categoria.equalsIgnoreCase(rs.getString("seccio"))){
				categoria=rs.getString("seccio");
				llista=llista+categoria+"\n";
			}
			categoria=rs.getString("seccio");

			llista=llista+" \t"+rs.getInt("codi")+" "+rs.getString("descripcio")+"\tStock: "+rs.getInt("stock")+" "+rs.getDouble("preu")+"€ "+rs.getInt("iva")+"%\n";
		}
		return llista;
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

