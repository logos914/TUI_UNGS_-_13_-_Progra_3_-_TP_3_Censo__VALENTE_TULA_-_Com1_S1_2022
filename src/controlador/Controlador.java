package controlador;


import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;

import grafo.Grafo;
import grafo.Nodo;
import modelo.Manzana;
import modelo.georreferenciable.externo.GeoJSON;
import modelo.georreferenciable.externo.ListadoDeCoordenadasParaLinea;
import modelo.georreferenciable.externo.ListadoDeCoordenadasParaPoligono;
import modelo.georreferenciable.externo.ListadoDeCoordenadasParaPunto;

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
		
	this.convertirPoligonosDeJsonEnVertices();
	this.convertirLineasDeJsonEnAristas();
	this.convertirPuntosDeJsonEnCentrosDeNodo();
		
		//TODO: Falta las aristas del grafo (lineas de geojson)
		
	}


private void convertirPoligonosDeJsonEnVertices() {
	
	for (ListadoDeCoordenadasParaPoligono e : this.datos.getListadoDeCoordParaPoligonos()) {
		Manzana manzana = Manzana.manzanaDesdeCoordenadas(e.getDatos());
		this.radiocensal.agregarVertice(manzana);
	}
	
}


private void convertirLineasDeJsonEnAristas() {
	
	for (ListadoDeCoordenadasParaLinea e : this.datos.getListadoDeCoordParaLineas()) {
		
		Nodo<Manzana> nodoA = encontrarManzanaALaQuePertenece(e.getDatos().get(0));
		Nodo<Manzana> nodoB = encontrarManzanaALaQuePertenece(e.getDatos().get(1));
		
		this.radiocensal.agregarArista(nodoA, nodoB, 1);
	}
	
} 

private void convertirPuntosDeJsonEnCentrosDeNodo() {
	
	for (ListadoDeCoordenadasParaPunto e : this.datos.getListadoDeCoordParaPuntos()) {
		
		Coordinate centro = e.getDatos();
		
		Nodo<Manzana> nodo = encontrarManzanaALaQuePertenece(centro);
				
		nodo.getInformacion().setCentro(centro);
	}
	
} 


public ArrayList<Nodo<Manzana>> getManzanas() {
	return this.radiocensal.obtenerTodosLosVertices();
}
	

private Nodo<Manzana> encontrarManzanaALaQuePertenece(Coordinate coordenada) {
	for (Nodo<Manzana> i : this.radiocensal.obtenerTodosLosVertices()) {
		if (i.getInformacion().laManzanaContiene(coordenada)) {
			return i;
		}
	
	}
	return null;
	
}
	
	
	
}
