package modelo.georreferenciable.externo;

import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;

public class ListadoDeCoordenadasParaPunto implements ListadoAbstractoDeCoordenadas {
	
	private Coordinate datosProcesados;
	

	public ListadoDeCoordenadasParaPunto(Map<String, Object> mapaDeDatos) {
		procesarDatos(mapaDeDatos);
		
	}
	
	private void procesarDatos(Map<String, Object>  datosSinProcesar) {
		
		List<Object> par = (List<Object>) datosSinProcesar.get("coordinates");
		
				Double latitud = (Double) par.get(1);
				Double longitud = (Double) par.get(0);
				
				this.datosProcesados = new Coordinate(latitud, longitud);
		
	}
	
	

	@Override
	public  Coordinate getDatos() {
		return datosProcesados;
	}
	
	

}
