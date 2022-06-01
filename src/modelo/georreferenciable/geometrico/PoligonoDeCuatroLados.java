package modelo.georreferenciable.geometrico;




import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;


public class PoligonoDeCuatroLados {
    

    
    private Polygon poligono;
   
    public PoligonoDeCuatroLados(Coordinate[] aristas) {
       
        
        if (parametroDeCreacionCorrecto(aristas)) {
            
            
          
            GeometryFactory fact = new GeometryFactory();
            LinearRing linear = new GeometryFactory().createLinearRing(aristas);
            
            this.poligono = new Polygon(linear, null, fact);
      
            
   
        } else {
            throw new IllegalArgumentException("El poligono debe tener cuatro aristas, " + 
            "o cinco si la arista final es igual a la primera. \n Dato invalido: " + aristas);
        }



    }


   



    private static boolean  parametroDeCreacionCorrecto(Coordinate[] aristas) {

        if (aristas.length == 4) {
            return true;
        }

        if (aristas.length == 5) {
            return esUnCircuitoCerradoDeCincoAristas(aristas);
        }

        return false;

    }

    
    private static boolean esUnCircuitoCerradoDeCincoAristas(Coordinate[] aristas) {
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
