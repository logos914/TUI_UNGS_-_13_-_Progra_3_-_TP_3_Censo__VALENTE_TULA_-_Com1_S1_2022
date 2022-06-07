package modelo.georreferenciable.externo;

import java.util.Map;

import org.locationtech.jts.geom.Coordinate;

public class ElementoGeometricoGeorreferenciable {

	

	private Properties properties;
	private Map<String, Object> geometry;
	private String type;
	private TipoDeElemento tipo;
	

	public ElementoGeometricoGeorreferenciable(String type, Properties properties, Map<String, Object> geometry) {
		this.type = type;
		this.properties = properties;
		this.geometry = geometry;
		this.setTipoEnumerable();
	}
	
	private void setTipoEnumerable() {
		
		String tipoDeGeometria = (String) this.geometry.get("type");
		
		if (tipoDeGeometria.equals("Polygon")) {
			this.tipo = TipoDeElemento.POLIGONO;
		}
		else if (tipoDeGeometria.equals("LineString")) {
			this.tipo = TipoDeElemento.LINEA;
		}
		else if (tipoDeGeometria.equals("Point")) {
			this.tipo = TipoDeElemento.PUNTO;
		}
	}
	

	
	public TipoDeElemento  getTipo() {
		if (this.tipo == null) {
			this.setTipoEnumerable();
		}
		return this.tipo;
		//return (String) this.geometry.get("type");
		
		
		
		
	}
	
	public Map<String, Object> getGeometry() {
		return geometry;
	}
	

	public class Properties {
			public String name;
			
			public Properties (String name) {
				this.name = name;
			}
			
		}
	
	
	
	
	
	public static Coordinate[]  convertirParDeFloatsEnCoord(float[][] coordenadasPrimitivas) {
		
		Coordinate[] arregloDeCoordenadas = new Coordinate[coordenadasPrimitivas.length];
		
		for (int i = 0; i < coordenadasPrimitivas.length; i++ ) {
			if (coordenadasPrimitivas[i].length == 2) {
				arregloDeCoordenadas[i] = new Coordinate(coordenadasPrimitivas[i][0],coordenadasPrimitivas[i][1]);
			} else {
				throw new IllegalArgumentException("Las coordenadas deben estar formadas solamente "
						+ "por dos componentes (latitud y longitud)" 
						+ "En la pos " + i + "se encuentra: " + coordenadasPrimitivas[i]);
						}
		}
		
		return arregloDeCoordenadas;
		
	}




	

}
