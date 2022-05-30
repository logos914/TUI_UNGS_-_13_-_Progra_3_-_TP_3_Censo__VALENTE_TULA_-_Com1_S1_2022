package modelo.georreferenciable.externo;

import java.util.Map;

public class ElementoGeometricoGeorreferenciable {

	
	private String type;
	private Properties properties;
	private Map<String, Object> geometry;
	
	
	public ElementoGeometricoGeorreferenciable(String type, Properties properties, Map<String, Object> geometry) {
		this.type = type;
		this.properties = properties;
		this.geometry = geometry;
	}
	
	
	
	
	public class Properties {
			public String name;
			
			public Properties (String name) {
				this.name = name;
			}
			
		}




	

}
