package modelo.georreferenciable.externo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;

public class ListadoDeCoordenadasParaLinea implements ListadoAbstractoDeCoordenadas {
	
	private ArrayList<Coordinate> datosProcesados;
	

	public ListadoDeCoordenadasParaLinea(Map<String, Object> mapaDeDatos) {
		procesarDatos(mapaDeDatos);
	}
	
	private void procesarDatos(Map<String, Object> datosSinProcesar) {


		List<Object> granArreglo = (List<Object>) datosSinProcesar.get("coordinates");
		
		this.datosProcesados = new ArrayList();
		
		for (Object e : granArreglo) {
			
			List<Object> arreglo = (List<Object>) e;
			
			Double latitud = (Double) arreglo.get(1);
			Double longitud = (Double) arreglo.get(0);
			
			Coordinate coordenada = new Coordinate(latitud, longitud);
			this.datosProcesados.add(coordenada);
		}

	
	}

	@Override
	public ArrayList<Coordinate> getDatos() {
		return this.datosProcesados;
	}
	

}
