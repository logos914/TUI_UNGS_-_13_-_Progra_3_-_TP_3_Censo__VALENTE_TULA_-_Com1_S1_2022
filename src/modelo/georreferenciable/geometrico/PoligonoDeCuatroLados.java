package modelo.georreferenciable.geometrico;

import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class PoligonoDeCuatroLados {
    
    private Polygon poligono;
   
   
    public PoligonoDeCuatroLados(ArrayList<Coordinate> coordenadasDeAristas) {
       
        if (parametroDeCreacionCorrecto(coordenadasDeAristas)) 
        {
            Coordinate[] coordenadasParaConstruirPoligono = PoligonoDeCuatroLados.convertirArrayListEnArreglo(coordenadasDeAristas);
            GeometryFactory fact = new GeometryFactory();
            LinearRing linear = new GeometryFactory().createLinearRing(coordenadasParaConstruirPoligono);
            
            this.poligono = new Polygon(linear, null, fact);

        } 
        else 
        {
            throw new IllegalArgumentException("El poligono debe tener cuatro aristas, " + 
            "y cinco vértices, dónde el último debe ser igual al primero para cerrar el polígono. \n "
            + "Cantidad de aristas:: " + coordenadasDeAristas.size() + "\n" 
            + "Aristas: " + coordenadasDeAristas);
        }
    }


   private static Coordinate[] convertirArrayListEnArreglo(ArrayList<Coordinate> aristas) {
	   Coordinate[] arregloDeCoordenadas= new Coordinate[aristas.size()];  
	   for (int i = 0; i<aristas.size();i++) 
	   {
		   arregloDeCoordenadas[i] = aristas.get(i);
       }
	   return arregloDeCoordenadas;
   }

    private static boolean  parametroDeCreacionCorrecto(ArrayList<Coordinate> aristas) {
        if (aristas.size() == 5) { return esUnCircuitoCerradoDeCincoAristas(aristas);} 
        else {  return false; }
    }

    private static boolean esUnCircuitoCerradoDeCincoAristas(ArrayList<Coordinate> aristas) {
            return (aristas.get(0).equals(aristas.get(4))) ? true : false;
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
