package modelo.georreferenciable.externo;

import java.util.Map;

import org.locationtech.jts.geom.Coordinate;

public class ListadoDeCoordenadasParaPunto implements ListadoAbstractoDeCoordenadas {
	
	private float[] datosProcesados;
	

	public ListadoDeCoordenadasParaPunto(Map<String, Object> mapaDeDatos) {
		
		procesarDatos(mapaDeDatos);
		
	}
	
	private void procesarDatos(Map<String, Object>  datosSinProcesar) {
		this.datosProcesados = (float[]) datosSinProcesar.get("coordinates");
	}
	
	

	@Override
	public  Coordinate getDatos() {
		return new Coordinate(datosProcesados[0],datosProcesados[1]);
	}
	
	

}
