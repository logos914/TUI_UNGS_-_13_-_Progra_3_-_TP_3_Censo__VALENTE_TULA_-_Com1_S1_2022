package controlador;

import modelo.georreferenciable.externo.GeoJSON;

public class Controlador {
	
	private GeoJSON datos;
	private String rutaDatosGeoJSON;
	
	public Controlador () {
		
	}
	
	
	public void setRutaDatosGeoJSON(String ruta) {
		this.rutaDatosGeoJSON = ruta;
	}
	
	public void importarDatos() {
		try {
			this.datos = GeoJSON.leerJSON(this.rutaDatosGeoJSON);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	// TODO: convertir cada dato en grafo de manzana
	

}
