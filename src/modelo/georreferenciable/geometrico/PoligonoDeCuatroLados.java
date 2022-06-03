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
       
        
        if (parametroDeCreacionCorrecto(coordenadasDeAristas)) {
            
            
          
            GeometryFactory fact = new GeometryFactory();
            LinearRing linear = new GeometryFactory().createLinearRing((Coordinate[]) coordenadasDeAristas.toArray());
            
            this.poligono = new Polygon(linear, null, fact);
      
            
   
        } else {
            throw new IllegalArgumentException("El poligono debe tener cuatro aristas, " + 
            "o cinco si la arista final es igual a la primera. \n Dato invalido: " + coordenadasDeAristas);
        }



    }


   



    private static boolean  parametroDeCreacionCorrecto(ArrayList<Coordinate> aristas) {

        if (aristas.size() == 4) {
            return true;
        }

        if (aristas.size() == 5) {
            return esUnCircuitoCerradoDeCincoAristas(aristas);
        }

        return false;

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
