package grafo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NodoTest {

	private String dataA, dataB, dataC;
	private Nodo<String> nodoA, nodoB, nodoC;
	
	@Before
	public void setUp() throws Exception {
		this.dataA = "0";
		this.dataB = "a";
		this.dataC = "hola";
		
		this.nodoA = new Nodo<String>(dataA);
		this.nodoB = new Nodo<String>(dataB);
		this.nodoC = new Nodo<String>(dataC);


	}

	
	@Test
	public void testGetInformacion() {
		assertTrue("0" == this.nodoA.getInformacion());
	}
	

	@Test
	public void testToString() {
		assertTrue("0" == this.nodoA.toString());
	}
	
	
	@Test
	public void testSetInfo() {
		this.nodoA.setInformacion("adios");
		assertTrue("adios" == this.nodoA.toString());
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testNoAgregarVecinoEsSiMismo() {
		this.nodoA.agregarVecino(nodoA, 1.0f);
	}
	
	
	@Test
	public void testNoSonVecinosAun() {
		assertFalse(this.nodoA.esVecino(nodoB));
	}
	
	

	@Test
	public void testAgregarVecino() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		Distancia<String> distanciaEsperada = new Distancia<String>(nodoB, 1.0f);
		Distancia<String> distanciaReal = this.nodoA.obtenerDistancia(0);
		assertTrue(distanciaEsperada.equals(distanciaReal));
	}
	
	
	@Test
	public void testAgregarSegundoVecino() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		this.nodoA.agregarVecino(nodoC, 2.0f);
		Distancia<String> distanciaEsperada = new Distancia<String>(nodoC, 2.0f);
		Distancia<String> distanciaReal = this.nodoA.obtenerDistancia(1);
		assertTrue(distanciaEsperada.equals(distanciaReal));
	}
	
	@Test
	public void testVecinosDiferentes() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		this.nodoA.agregarVecino(nodoC, 2.0f);
		Distancia<String> distanciaEsperada = this.nodoA.obtenerDistancia(0);
		Distancia<String> distanciaReal = this.nodoA.obtenerDistancia(1);
		assertFalse(distanciaEsperada.equals(distanciaReal));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNoSepuedeSerDosVecesVecinos() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		this.nodoA.agregarVecino(nodoB, 2.0f);
		
	}
	
		

	@Test (expected = IllegalArgumentException.class)
	public void testNoAgregarVecinosDuplicados() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		this.nodoA.agregarVecino(nodoC, 2.0f);
		this.nodoA.agregarVecino(nodoB, 1.0f);
		
	}
	
	@Test 
	public void testObtenerBuenIndice() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		this.nodoA.agregarVecino(nodoC, 2.0f);
		
		int indice = this.nodoA.indiceDeVecino(nodoC);
		
		assertEquals(indice,1);
		
	}
	
	@Test 
	public void testObtenerBuenaCantidadDeVecinos() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		this.nodoA.agregarVecino(nodoC, 2.0f);
		
		int cant = this.nodoA.obtenerCantidadDeVecinos();
		
		assertEquals(cant,2);
		
	}
	
	
	@Test
	public void testEliminarVecinoPorIndice() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		this.nodoA.agregarVecino(nodoC, 2.0f);
		
		this.nodoA.eliminarVecino(0);
		
		
		
		Distancia<String> distanciaEsperada = this.nodoA.obtenerDistancia(0);
		Distancia<String> distanciaReal = new Distancia(nodoC, 2.0f);
		assertTrue(distanciaEsperada.equals(distanciaReal));
	}
	
	
	@Test
	public void testEliminarVecinoPorNodo() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		this.nodoA.agregarVecino(nodoC, 2.0f);
		
		this.nodoA.eliminarVecino(nodoB);
		
		
		
		Distancia<String> distanciaEsperada = this.nodoA.obtenerDistancia(0);
		Distancia<String> distanciaReal = new Distancia(nodoC, 2.0f);
		assertTrue(distanciaEsperada.equals(distanciaReal));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNoEliminarvecinoQueNoEsVecino() {
		this.nodoA.agregarVecino(nodoC, 2.0f);
		this.nodoA.eliminarVecino(nodoB);
		
		
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testNoEliminarvecinoConIndiceNegativo() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		this.nodoA.agregarVecino(nodoC, 2.0f);
		this.nodoA.eliminarVecino(-1);
		
		
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testNoEliminarvecinoConIndiceMayor() {
		this.nodoA.agregarVecino(nodoB, 1.0f);
		this.nodoA.agregarVecino(nodoC, 2.0f);
		this.nodoA.eliminarVecino(2);
		
		
	}

}
