package modelo;

import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate; 

import modelo.georreferenciable.geometrico.PoligonoDeCuatroLados;

public class Manzana {

	
	private Censista censistaAsignado;
	private PoligonoDeCuatroLados poligonoGeografico;
	private ArrayList<Coordinate> coordenadasDeAristas;
	
	private Manzana(ArrayList<Coordinate> aristas) {
		this.coordenadasDeAristas = aristas;
	}

	
	public static Manzana manzanaDesdeCoordenadas(ArrayList<Coordinate> aristas) {
		Manzana manzana = new Manzana(aristas);
		return manzana; 
	}
	
	private static Coordinate[]  conversionTipoDeCoordenadas (float[][] aristas) {
		Coordinate[] aristasComoCoordenadas = new Coordinate[aristas.length];
	    int contador = 0;
	    for (float[] e : aristas) {
	    	aristasComoCoordenadas[contador] = new Coordinate(e[0], e[1]);
        	contador++;
        }
	    return aristasComoCoordenadas;
	}
	
	public void asignarCensista(Censista censista) {
		this.censistaAsignado = censista;
	}

	private void crearPoligonoDeCuatroLados() {
		this.poligonoGeografico = new PoligonoDeCuatroLados(this.coordenadasDeAristas);
	}

	public ArrayList<Coordinate> getCoordenadasDeAristas() {
		return this.coordenadasDeAristas;
	}

	
	

}
