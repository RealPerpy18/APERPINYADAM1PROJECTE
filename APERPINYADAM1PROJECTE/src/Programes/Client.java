package Programes;

public class Client {
	String dni;
	String nom;
	String correu;
	String telefon;
	String adreca;
	String contrasenya;

	public Client() {
		
	}
	public Client(String dni, String nom, String correu, String telefon, String adreca, String contrasenya) {
		super();
		this.dni = dni;
		this.nom = nom;
		this.correu = correu;
		this.telefon = telefon;
		this.adreca = adreca;
		this.contrasenya = contrasenya;
	}
	
	public void setClient(String dni, String nom, String correu, String telefon, String adreca, String contrasenya) {
		this.dni = dni;
		this.nom = nom;
		this.correu = correu;
		this.telefon = telefon;
		this.adreca = adreca;
		this.contrasenya = contrasenya;
	}
	public String getDni() {
		return dni;
	}

	public String getNom() {
		return nom;
	}

	public String getCorreu() {
		return correu;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getAdreca() {
		return adreca;
	}

	public String getContrasenya() {
		return contrasenya;
	}
	public String getDaddesModificables() {
		String dades="";

		dades=dades+"Dades de l'usuari "+nom+"\n";
		dades=dades+" -"+correu+"\n";
		dades=dades+" -"+telefon+"\n";
		dades=dades+" -"+adreca+"\n";
		return dades;
	}

}
