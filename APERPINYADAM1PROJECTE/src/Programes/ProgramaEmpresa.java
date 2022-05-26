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
			System.out.println("\n\n\n\tMENU BOTIGA\n\n1.Gestio de productes\n2.Dades Clients");
			if(lector.hasNextInt()) {
				opcio=lector.nextInt();
				lector.nextLine();
			}
			switch (opcio){
			case 1:
				do {
					System.out.println("\n\n\n\tGESTIO PRODUCTES\n1.Alta productes\n2.Modificar Productes\n3.Baixa Producte");
					if(lector.hasNextInt()) {
						opcio=lector.nextInt();
						lector.nextLine();
					}
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
						boolean valid=false;
						do {

							if (lector.hasNextDouble()) {
								preu=lector.nextDouble();
								lector.nextLine();
								valid=true;
							}
							else {
								System.out.println("Format incorrecte, introdueix de nou");
							}


						}
						while(!valid);

						System.out.println("Stock inicial:");
						valid=false;
						do {
							if (lector.hasNextInt()) {
								stock=lector.nextInt();
								lector.nextLine();
								valid=true;
							}
							else {
								System.out.println("Format incorrecte, introdueix de nou");
							}
						}
						while(!valid);


						System.out.println("Iva del producte");
						valid=false;
						do {
							if (lector.hasNextInt()) {
								iva=lector.nextInt();
								lector.nextLine();
								valid=true;
							}
							else {
								System.out.println("Format incorrecte, introdueix de nou");
							}
						}
						while(!valid);


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
						int codi=lector.nextInt();
						lector.nextLine();
						try {

							Producte p1=carregarObjecte(codi);
							System.out.println(p1.getDadesModificables());
							do {
								System.out.println("Dades per modificar\n\n\t1.Preu\n\t2.IVA\n\t3.Stock");
								opcio=lector.nextInt();
								lector.nextLine();

								if (opcio==1) {
									System.out.println("Introdueix el nou preu");
									if (lector.hasNextDouble()) {
										double canvipreu=lector.nextDouble();
										lector.nextLine();
										modificarProducte(p1,"preu",canvipreu);
									}
								}
								else if(opcio==2) {
									System.out.println("Introdueix el nou IVA");
									if (lector.hasNextInt()) {
										int nouiva=lector.nextInt();
										lector.nextLine();
										modificarProducteInt(p1,"iva",nouiva);
									}
								}
								else if(opcio==3) {
									System.out.println("Introdueix Cantitat de Stock");
									if (lector.hasNextInt()) {
										int noustock=lector.nextInt();
										lector.nextLine();
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
						break;
					case 3:
						try {
							System.out.println(llistaProductes());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						System.out.println("Introdueix el codi del producte a eliminar");
						codi=lector.nextInt();
						lector.nextLine();
						try {
							System.out.println(eliminarProducte(codi));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				while(opcio!=4);
				break;

			case 2:
				do {
					System.out.println("\n\n\n\tMENU GESTIO CLIENT\n\n1.Visualitza dades d'un client\n2.visualitza clients\n3.Tornar al menú anterior");
					if(lector.hasNextInt()) {
						opcio=lector.nextInt();
						lector.nextLine();
						switch(opcio) {
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
				}
				while(opcio!=3);

				break;
			}
		}
		while (opcio!=111);
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
		while(rs.next()) {
			llista= rs.getString("nom")+" \n-dni: "+rs.getString("dni")+"  -eMail: "+rs.getString("correu")+"  -Telefon: "+rs.getString("telefon")+"  -Adreça: "+rs.getString("adreca");
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
		String sql="select * from producte group by seccio,codi order by codi;";
		ResultSet rs=stmt.executeQuery(sql);

		while (rs.next()) {

			if(!categoria.equalsIgnoreCase(rs.getString("seccio"))){
				categoria=rs.getString("seccio");
				llista=llista+categoria+"\n";
			}
			categoria=rs.getString("seccio");

			llista=llista+" \t-"+rs.getInt("codi")+" "+rs.getString("descripcio")+" "+rs.getInt("stock")+" "+rs.getDouble("preu")+"€ "+rs.getInt("iva")+"%\n";
		}
		return llista;
	}
}

