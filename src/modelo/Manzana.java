package modelo;

import org.locationtech.jts.geom.Coordinate; 

import modelo.georreferenciable.geometrico.PoligonoDeCuatroLados;

public class Manzana {

	
	private Censista censistaAsignado;
	private PoligonoDeCuatroLados poligonoGeografico;
	private Coordinate[] coordenadasDeAristas;
	
	private Manzana(Coordinate[] coordenadasDeAristas) {
		this.coordenadasDeAristas = coordenadasDeAristas;
	}

	public static Manzana manzana(float[][] aristas) {
		Manzana manzana = new Manzana(conversionTipoDeCoordenadas (aristas));
		return manzana; 
	}
	
	public static Manzana manzana(Coordinate[] aristas) {
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

	

}
