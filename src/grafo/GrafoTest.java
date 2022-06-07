package grafo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GrafoTest {

	private Nodo<String> a;
	private Nodo<String> b;
	private Nodo<String> c;
	private Nodo<String> d;
	private Nodo<String> e;
	private Nodo<String> f;
	
	
	
	
	private Grafo<String> grafo;
	
	@Before
	public void setUp() throws Exception {
		
		
		this.grafo = new Grafo<String>();
		
		this.a = new Nodo<String>("A");
		this.b = new Nodo<String>("B");
		this.c = new Nodo<String>("C");
		this.d = new Nodo<String>("D");
		this.e = new Nodo<String>("E");
		this.f = new Nodo<String>("F");
	}
	
	@Test
	public void testAgregarVerticeNodo() {
		this.grafo.agregarVertice(this.a);
		assertTrue(grafo.verificarVertice(this.a));
	}
	
	@Test
	public void testAgregarArista() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarArista(this.a,this.b,0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		assertTrue(grafo.existeArista(new Arista(this.b,this.a,1.0f)));
		
	}
	
	
	
	@Test 
	public void testEliminarArista() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarArista(a,b,0.5f);
		this.grafo.eliminarArista(new Arista(this.b,this.a,0.0f));
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		
		assertFalse(grafo.existeArista(new Arista(this.b,this.a,1.0f)));
	
	}
	
	
	@Test
	public void testTamano() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarVertice(this.d);
		this.grafo.agregarVertice(this.e);
		this.grafo.agregarVertice(this.f);
		
		assertEquals(6,this.grafo.tamano());
	}
	
	@Test
	public void testVecinos() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista(this.a,this.b,0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		assertTrue(this.a.esVecino(b));
		
	}
	
	@Test
	public void testNoVecinos() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista(this.a,this.b,0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		assertFalse(this.a.esVecino(c));
		
	}
	
	@Test
	public void unVecinoNullNoEsVecino() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista(this.a,this.b,0.5f);
		assertFalse(this.a.esVecino(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNoSePuedeAristaHaciaMismoVertice() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista(this.a,this.a,0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		
		
	}
	
	@Test (expected = Exception.class)
	public void testNoSePuedeAristaHaciaMismoVertice2() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista("A","A",0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		
		
	}
	
	
	@Test (expected = Exception.class)
	public void testNoSePuedeAristaHaciaVerticeQueNoPertenece() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista(this.a,this.d,0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		
		
	}
	
	@Test (expected = Exception.class)
	public void testNoSePuedeAristaHaciaVerticeQueNoPertenece2() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista(this.d,this.b,0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		
		
	}
	
	@Test (expected = Exception.class)
	public void testNoSePuedeAristaHaciaVerticeQueNoPertenece3() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista("A","D",0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		
		
	}
	
	@Test (expected = Exception.class)
	public void testNoSePuedeAristaHaciaVerticeQueNoPertenece4() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista("D","B",0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		
		
	}
	
	@Test (expected = Exception.class)
	public void testNoSePuedeAristaHaciaVerticeQueNoPertenece5() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista("D","E",0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		
		
	}
	
	@Test (expected = Exception.class)
	public void testNoSePuedeAristaCuandoYaSonVecinos() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista(this.a,this.b,0.5f);
		this.grafo.agregarArista(this.c,this.b,0.5f);
		this.grafo.agregarArista(this.a,this.b,0.5f);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		
		
	}
	
	@Test (expected = Exception.class)
	public void testNoSePuedePedirVecinosDeUnNodoQueNoFormaParteDelGrafo() {
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarArista(this.a,this.b,0.5f);
		this.grafo.agregarArista(this.c,this.b,0.5f);
		
		this.grafo.vecinos(this.e);
		
		//la arista está al revés, y con valor diferente
		//Igual es la misma arista
		
		
	}
	
	
	
	
}
