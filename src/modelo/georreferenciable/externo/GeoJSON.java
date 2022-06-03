package modelo.georreferenciable.externo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GeoJSON {
	private String type;
	private ArrayList<ElementoGeometricoGeorreferenciable> features;
	
	public ArrayList<ElementoGeometricoGeorreferenciable> getFeatures() {
		return features;
	}


	public void setFeatures(ArrayList<ElementoGeometricoGeorreferenciable> features) {
		this.features = features;
	}


	private ArrayList<ListadoDeCoordenadasParaPoligono> listadoDeCoordParaPoligonos;
	private ArrayList<ListadoDeCoordenadasParaLinea> listadoDeCoordParaLineas;
	private ArrayList<ListadoDeCoordenadasParaPunto> listadoDeCoordParaPuntos;

	
	public GeoJSON (String type, ArrayList<ElementoGeometricoGeorreferenciable> features) {
		
		this.type = type;
		this.features = features;
		System.out.print(features);
		
		this.inicializar();
	}
	
	
	public static GeoJSON leerJSON(String archivo) {
		Gson gson = new Gson();
		GeoJSON ret = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			ret = gson.fromJson(br, GeoJSON.class);
		}
		catch (Exception e ) {
			System.out.println("PROBLEMAS:  " + e);
		}
		ret.inicializar();
		return ret;
	}
	
	
	public void clasificarElementosPorTipo() {
		
		System.out.print("que onda");
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
		
		
	}
	
	public void inicializar() {
		this.listadoDeCoordParaPoligonos = new ArrayList();
		this.listadoDeCoordParaLineas = new ArrayList();
		this.listadoDeCoordParaPuntos = new ArrayList();
		this.clasificarElementosPorTipo();
	}


	public ArrayList<ListadoDeCoordenadasParaPoligono> getListadoDeCoordParaPoligonos() {
		return listadoDeCoordParaPoligonos;
	}


	public ArrayList<ListadoDeCoordenadasParaLinea> getListadoDeCoordParaLineas() {
		return listadoDeCoordParaLineas;
	}


	public ArrayList<ListadoDeCoordenadasParaPunto> getListadoDeCoordParaPuntos() {
		return listadoDeCoordParaPuntos;
	}
	
	
	

}
