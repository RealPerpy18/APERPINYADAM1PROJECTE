package Programes;

public class Producte {
	int codi;
	String nom;
	String seccio;
	int stock;
	double preu;
	int iva;

	public Producte() {

	}
	public Producte(int codiProducte, String nom,String seccio, int stock, double preuProd, int iva) {
		this.codi = codiProducte;
		this.nom = nom;
		this.seccio=seccio;
		this.stock = stock;
		this.preu = preuProd;
		this.iva = iva;
	}

	public int getCodi() {
		return codi;
	}
	public void setCodi(int codiProducte) {
		this.codi = codiProducte;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public double getPreuProd() {
		return preu;
	}
	public void setPreuProd(double preuProd) {
		this.preu = preuProd;
	}
	public double getIva() {
		return iva;
	}
	public void setIva(int iva) {
		this.iva = iva;
	}
	public String getDadesModificables() {
		String dades="";
		dades=dades+"Dades del producte "+nom+"\n";
		dades=dades+" -"+stock+"\n";
		dades=dades+" -"+preu+"\n";
		dades=dades+" -"+iva+"\n";
		return dades;	
	}



}
