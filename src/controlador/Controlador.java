package controlador;


import java.util.ArrayList;

import grafo.Grafo;
import grafo.Nodo;
import modelo.Manzana;
import modelo.georreferenciable.externo.GeoJSON;
import modelo.georreferenciable.externo.ListadoDeCoordenadasParaPoligono;

public class Controlador {

	private GeoJSON datos;
	private Grafo<Manzana> radiocensal;

	public Controlador() {
		
		this.radiocensal = new Grafo<Manzana>();
	}

	public void importarDatos(String path) {
		try {
			this.datos = GeoJSON.leerJSON(path);
			System.out.print("que onda");
			this.convertirGeoJsonenGrafo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
	
		
	}
	
public void convertirGeoJsonenGrafo() {
		
		for (ListadoDeCoordenadasParaPoligono e : this.datos.getListadoDeCoordParaPoligonos()) {
			Manzana manzana = Manzana.manzanaDesdeCoordenadas(e.getDatos());
			this.radiocensal.agregarVertice(manzana);
		}
		
		//TODO: Falta las aristas del grafo (lineas de geojson)
		
	}

public ArrayList<Nodo<Manzana>> getManzanas() {
	return this.radiocensal.obtenerTodosLosVertices();
}
	
	
	
	
}
