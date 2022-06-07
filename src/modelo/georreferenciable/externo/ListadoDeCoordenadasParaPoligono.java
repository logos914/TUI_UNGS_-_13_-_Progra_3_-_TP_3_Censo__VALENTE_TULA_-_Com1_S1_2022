package modelo.georreferenciable.externo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;

public class ListadoDeCoordenadasParaPoligono implements ListadoAbstractoDeCoordenadas {
	
	private ArrayList<Coordinate> datosProcesados;
	

	public ListadoDeCoordenadasParaPoligono(Map<String, Object> mapaDeDatos) {
		procesarDatos(mapaDeDatos);
		
	}
	
	private void procesarDatos(Map<String, Object> datosSinProcesar) {
		@SuppressWarnings("unchecked")
		List<Object> granArregloMayor =(List<Object>) datosSinProcesar.get("coordinates");

		List<Object> granArreglo = (List<Object>) granArregloMayor.get(0);
		
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
