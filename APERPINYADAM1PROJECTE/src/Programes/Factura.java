package Programes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Factura {
	Client client;
	LocalDate data;
	int numFactura;
	ArrayList<LiniaFactura>productes;

	public Factura() {
		
	}
	public Factura(Client client, LocalDate data, int numFactura, ArrayList<LiniaFactura> productes) {
		this.client = client;
		this.data = data;
		this.numFactura = numFactura;
		this.productes = productes;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public int getNumFactura() {
		return numFactura;
	}
	public void setNumFactura(int numFactura) {
		this.numFactura = numFactura;
	}
	public ArrayList<LiniaFactura> getProductes() {
		return productes;
	}
	public void setProductes(ArrayList<LiniaFactura> productes) {
		this.productes = productes;
	}
}
