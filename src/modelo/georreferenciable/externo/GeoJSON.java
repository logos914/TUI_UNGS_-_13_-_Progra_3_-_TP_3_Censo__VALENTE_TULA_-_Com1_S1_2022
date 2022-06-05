package modelo.georreferenciable.externo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import org.locationtech.jts.geom.Coordinate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GeoJSON {
	
	// Datos que se importan desde un archivo .geojson
	private String type;
	private ArrayList<ElementoGeometricoGeorreferenciable> features;
	
	// Listado procesable de coordenadas para formar figuras
	private ArrayList<ListadoDeCoordenadasParaPoligono> listadoDeCoordParaPoligonos;
	private ArrayList<ListadoDeCoordenadasParaLinea> listadoDeCoordParaLineas;
	private ArrayList<ListadoDeCoordenadasParaPunto> listadoDeCoordParaPuntos;

	
	public GeoJSON (String type, ArrayList<ElementoGeometricoGeorreferenciable> features) {
		this.type = type;
		this.features = features;
		this.inicializar();
	}
	
	public static GeoJSON leerJSON(String archivo) {
		Gson gson = new Gson();
		GeoJSON geojsonParaRetornar = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			geojsonParaRetornar = gson.fromJson(br, GeoJSON.class);
		}
		catch (Exception e ) {
			System.out.println("No se puede importar el archivo .geojson \n  Excepción: " + e);
		}
		
		geojsonParaRetornar.inicializar();
		return geojsonParaRetornar;
	}
	
	public void inicializar() {
		this.listadoDeCoordParaPoligonos = new ArrayList();
		this.listadoDeCoordParaLineas = new ArrayList();
		this.listadoDeCoordParaPuntos = new ArrayList();
	}
	
	
	public void clasificarElementosPorTipo() {

		for (ElementoGeometricoGeorreferenciable e : features) {
			
			if (e.getTipo().equals(TipoDeElemento.POLIGONO)) {
				ListadoDeCoordenadasParaPoligono CoordPoligono = new ListadoDeCoordenadasParaPoligono(e.getGeometry());
				listadoDeCoordParaPoligonos.add(CoordPoligono);
			}
			
			if (e.getTipo().equals(TipoDeElemento.LINEA)) {
				ListadoDeCoordenadasParaLinea CoordLinea = new ListadoDeCoordenadasParaLinea(e.getGeometry());
				listadoDeCoordParaLineas.add(CoordLinea);
			}
			
			if (e.getTipo().equals(TipoDeElemento.PUNTO)) {
				ListadoDeCoordenadasParaPunto CoordPunto = new ListadoDeCoordenadasParaPunto(e.getGeometry());
				listadoDeCoordParaPuntos.add(CoordPunto);
			}

		}
		
		this.quitarJerarquiaDeImportacion();
		
	}
	

	public ArrayList<ListadoDeCoordenadasParaPoligono> obtenerListadoDeCoordParaPoligonos() {
		return listadoDeCoordParaPoligonos;
	}


	public ArrayList<ListadoDeCoordenadasParaLinea> obtenerListadoDeCoordParaLineas() {
		return listadoDeCoordParaLineas;
	}


	public ArrayList<ListadoDeCoordenadasParaPunto> obtenerListadoDeCoordParaPuntos() {
		return listadoDeCoordParaPuntos;
	}
	
	public void quitarJerarquiaDeImportacion() {
		Collections.shuffle(listadoDeCoordParaPoligonos);
		Collections.shuffle(listadoDeCoordParaLineas);

	}
	
	public ArrayList<ElementoGeometricoGeorreferenciable> getFeatures() {
		return features;
	}

	public void setFeatures(ArrayList<ElementoGeometricoGeorreferenciable> features) {
		this.features = features;
	}
	
	

}
