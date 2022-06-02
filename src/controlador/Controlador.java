package controlador;

import modelo.georreferenciable.externo.GeoJSON;

public class Controlador {

	private GeoJSON datos;

	public Controlador() {

	}

	public void importarDatos(String path) {
		try {
			this.datos = GeoJSON.leerJSON(path);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
