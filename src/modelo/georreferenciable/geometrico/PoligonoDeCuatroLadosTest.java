package modelo.georreferenciable.geometrico;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;

public class PoligonoDeCuatroLadosTest  {

	private Coordinate a;
	private Coordinate b;
	private Coordinate c;
	private Coordinate d;
	private Coordinate e;
	private Coordinate[] cinco;
	private Coordinate[] cuatro;
	
	@Before
	public void setUp() throws Exception {
		a = new Coordinate(1,1);
		b = new Coordinate(1,10);
		c = new Coordinate(10,10);
		d = new Coordinate(10,1);
		e = new Coordinate(20,20);
		
		cinco = new Coordinate[] {a,b,c,d,e};
		cuatro = new Coordinate[] {a,b,c,d,};
	}

	
	@Test  (expected = IllegalArgumentException.class)
	public void noSePuedeCrearPoligonoDeCincoLadosAbierto() {
		
		ArrayList<Coordinate> coords =new ArrayList();
		
		coords.add(a);
		coords.add(b);
		coords.add(c);
		coords.add(d);
		coords.add(e);
	
		PoligonoDeCuatroLados poligonoDePrueba = new PoligonoDeCuatroLados(coords);
	
	}
	
	@Test  (expected = IllegalArgumentException.class)
	public void noSePuedeCrearPoligonoDeCincoLadosCerrado() {
		
		ArrayList<Coordinate> coords =new ArrayList();
		
		coords.add(a);
		coords.add(b);
		coords.add(c);
		coords.add(d);
		coords.add(e);
		coords.add(a);
	
		PoligonoDeCuatroLados poligonoDePrueba = new PoligonoDeCuatroLados(coords);
	
	}
	
	
	@Test  (expected = IllegalArgumentException.class)
	public void noSePuedeCrearPoligonoDeTresLadosCerrado() {
		
		ArrayList<Coordinate> coords =new ArrayList();
		
		coords.add(a);
		coords.add(b);
		coords.add(c);
		coords.add(a);

		PoligonoDeCuatroLados poligonoDePrueba = new PoligonoDeCuatroLados(coords);
	
	}
	
	@Test  (expected = IllegalArgumentException.class)
	public void noSePuedeCrearPoligonoDeTresLadosAbierto() {
		
		ArrayList<Coordinate> coords =new ArrayList();
		
		coords.add(a);
		coords.add(b);
		coords.add(c);
		

		PoligonoDeCuatroLados poligonoDePrueba = new PoligonoDeCuatroLados(coords);
	
	}
	
	
	@Test
	public void testContiene() {
		
		ArrayList<Coordinate> coords =new ArrayList();
	
	coords.add(a);
	coords.add(b);
	coords.add(c);
	coords.add(d);
	coords.add(a);

	PoligonoDeCuatroLados poligonoDePrueba = new PoligonoDeCuatroLados(coords);
	Coordinate adentro = new Coordinate(5,5);
	boolean resultado = poligonoDePrueba.contiene(adentro);
	assertTrue(resultado);
	
	}
	
	@Test
	public void testNoContiene() {
		
		ArrayList<Coordinate> coords =new ArrayList();
	
	coords.add(a);
	coords.add(b);
	coords.add(c);
	coords.add(d);
	coords.add(a);

	PoligonoDeCuatroLados poligonoDePrueba = new PoligonoDeCuatroLados(coords);
	Coordinate afuera = new Coordinate(25,25);
	assertFalse(poligonoDePrueba.contiene(afuera));
	
	} 

}
