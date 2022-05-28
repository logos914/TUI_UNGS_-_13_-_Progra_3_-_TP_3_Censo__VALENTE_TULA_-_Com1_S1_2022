package grafo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AristaTest {

	private Nodo<String> dataA = new Nodo<String>("hola") ;
	private Nodo<String> dataB = new Nodo<String>("adios");
	
	private Arista<Nodo<String>> arista1;
	private Arista<Nodo<String>> arista2;
	private Arista<Nodo<String>> arista3;
	private Arista<Nodo<String>> arista4;
	
	@Before
	public void setUp() throws Exception {
		this.arista1 = new Arista(dataA, dataB,1.0f);
		this.arista2 = new Arista(dataB, dataA,1.0f);
		this.arista3 = new Arista(dataB, dataA,2.0f);
		this.arista4 = new Arista(dataA, dataB,2.0f);
		
	}

	@Test
	public void testSonExactamenteLaMismaAristaInvertida() {
		assertTrue(this.arista1.equals(arista2));
	}
	
	@Test
	public void testSonLaMismaAristaConDistintoPeso() {
		assertTrue(this.arista1.equals(arista4));
	}
	
	@Test
	public void testSonLaMismaAristaConDistintoPesoInvertida() {
		assertTrue(this.arista1.equals(arista3));
	}

	
	@Test
	public void testSonExactamenteLaMismaArista() {
		assertTrue(this.arista1.equals(arista1));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNoSePuedeCrearAristaConMismosExtremos() {
		Arista<Nodo<String>> arista5 = new Arista(dataA, dataA, 2.0f);
	}
}
