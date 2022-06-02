package controlador;

import frontend.Frontend;
import modelo.georreferenciable.externo.GeoJSON;

public class Controlador {

	private GeoJSON datos;
	private Frontend gui;

	public Controlador() {
		this.gui = new Frontend();
		this.gui.crearVistaInterfaz();

	}

	public void importarDatos(String path) {
		try {
			this.datos = GeoJSON.leerJSON(path);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
