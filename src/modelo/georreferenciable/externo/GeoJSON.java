package modelo.georreferenciable.externo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GeoJSON {
	private String type;
	private ArrayList<ElementoGeometricoGeorreferenciable> features;
	
	public GeoJSON (String type, ArrayList<ElementoGeometricoGeorreferenciable> features) {
		
		this.type = type;
		this.features = features;
		
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
		
		return ret;
	}
	
}
