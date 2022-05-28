package grafo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DistanciaTest {

	private Nodo<String> dataA = new Nodo<String>("hola") ;
	private Nodo<String> dataB = new Nodo<String>("adios");
	private Distancia<Nodo<String>> distA1;
	private Distancia<Nodo<String>> distB1;
	private Distancia<Nodo<String>> distA2;
	private Distancia<Nodo<String>> distA3;
	
	@Before
	public void setUp() throws Exception {
		this.distA1 = new Distancia(dataA,1.0f);
		this.distB1 = new Distancia(dataB,1.0f);
		this.distA2 = new Distancia(dataA,0.5f);
		this.distA3 = new Distancia(dataA,0.5f);
		
	}

	@Test
	public void testDistanciasIguales() {
		assertTrue(distA2.equals(distA3));
	}
	
	@Test
	public void testDistanciasIguales2() {
		assertTrue(distA2.equals(distA2));
	}
	
	
	@Test
	public void testDistanciasNOIgualesAunqueMismoDestino() {
		assertFalse(distA1.equals(distA3));
	}
	
	@Test
	public void testDistanciasNOIgualesAunqueMismoPeso() {
		assertFalse(distA1.equals(distB1));
	}

}
