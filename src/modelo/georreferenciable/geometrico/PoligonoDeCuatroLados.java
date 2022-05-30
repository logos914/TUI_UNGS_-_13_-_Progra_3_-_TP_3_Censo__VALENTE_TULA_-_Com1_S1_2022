package modelo.georreferenciable.geometrico;




import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;


public class PoligonoDeCuatroLados {
    

    private float[][] aristas;
    private Polygon poligono;
   
    public PoligonoDeCuatroLados(float[][] aristas) {
       
        
        if (parametroDeCreacionCorrecto(aristas)) {
            this.aristas = aristas;
            
            Coordinate[] aristasComoCoordenadas = this.convertirArregloDeAristasEnArregloDeCoordenadas( );
            GeometryFactory fact = new GeometryFactory();
            LinearRing linear = new GeometryFactory().createLinearRing(aristasComoCoordenadas);
            
            this.poligono = new Polygon(linear, null, fact);
      
            
   
        } else {
            throw new IllegalArgumentException("El poligono debe tener cuatro aristas, " + 
            "o cinco si la arista final es igual a la primera. \n Dato invalido: " + aristas);
        }



    }


    public Coordinate[] convertirArregloDeAristasEnArregloDeCoordenadas( ) {
    	Coordinate[] aritasComoCoordenadas = new Coordinate[this.aristas.length];
        int contador = 0;
        for (float[] e : this.aristas) {
        	aritasComoCoordenadas[contador] = new Coordinate(e[0], e[1]);
        	contador++;
        }
        return aritasComoCoordenadas;
    }

    public float[][] getAristas() {
        return aristas;
    }


    private static boolean  parametroDeCreacionCorrecto(float[][] aristas) {

        if (aristas.length == 4) {
            return true;
        }

        if (aristas.length == 5) {
            return esUnCircuitoCerradoDeCincoAristas(aristas);
        }

        return false;

    }

    
    private static boolean esUnCircuitoCerradoDeCincoAristas(float[][] aristas) {
            return (aristas[0].equals(aristas[4])) ? true : false;
        }

    
    public Coordinate[] obtenerCoordenadas() {
    	return this.poligono.getCoordinates();
    }

    
    
    public boolean contiene (Coordinate coordenadas) {
    
    	GeometryFactory fact = new GeometryFactory();
    	Point punto = fact.createPoint(coordenadas);
    	return this.poligono.contains(punto);
    }
        



   

  
}
