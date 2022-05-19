package Validacions;

public class validacions {
	public static boolean dniValid(String dni) {
		String codiValidacioDNI="TRWAGMYFPDXBNJZSQVHLCKE";

		boolean valid=true;

		if (dni.length()==9) {
			//primer mirem si hi ha 8 nombres
			int i=0;
			while(valid&&i<8) {
				if(!Character.isDigit(dni.charAt(i))) valid=false;
				else i++;
			}

			//i ara comprovem la lletra
			long valorDniLong=(Long.parseLong(dni.substring(0,8)))%23;// els 8 numeros del dni son massa per integer, hem de fer servir long
			int valorDni=(int)valorDniLong;	// Ara convertim el resultat a enter.					
			if(!(dni.charAt(8)==codiValidacioDNI.charAt(valorDni))) valid=false;
		}	
		else valid=false;		

		return valid;
	}
	public static boolean validarTelefon (String telefon) {

		boolean valid=true;

		if (telefon.length()==9) {
			int i=0;
			while(valid&&i<9) {
				if(!Character.isDigit(telefon.charAt(i))) valid=false;
				else i++;
			}
		}		
		else {
			valid=false;
		}			
		return valid;
	}
	public static boolean validarEmail(String email) {

		boolean valid=true;

		String[] emailSplit=email.split("@");

		if(!(emailSplit.length==2)) valid=false;
		else {
			String[] emailSplit2=emailSplit[1].split("\\.");			
			if(emailSplit2.length<2) valid=false;
		}

		return valid;
	}

}


