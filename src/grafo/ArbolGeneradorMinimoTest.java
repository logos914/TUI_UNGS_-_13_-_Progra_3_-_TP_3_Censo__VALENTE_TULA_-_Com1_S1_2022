package grafo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class ArbolGeneradorMinimoTest {
	private Nodo<String> a;
	private Nodo<String> b;
	private Nodo<String> c;
	private Nodo<String> d;
	private Nodo<String> e;
	private Nodo<String> f;
	
	private Arista<String> aristaAB;
	private Arista<String> aristaAD;
	private Arista<String> aristaAE;
	private Arista<String> aristaCD;
	private Arista<String> aristaCF;
	private Arista<String> aristaCB;
	
	
	private Grafo<String> grafo;
	private ArbolGeneradorMinimo<String> agm;
	private Grafo<String> arbol;
	
	@Before
	public void setUp() throws Exception {
		
		this.grafo = new Grafo<String>();
		
		this.a = new Nodo<String>("A");
		this.b = new Nodo<String>("B");
		this.c = new Nodo<String>("C");
		this.d = new Nodo<String>("D");
		this.e = new Nodo<String>("E");
		this.f = new Nodo<String>("F");
		
		this.grafo.agregarVertice(this.a);
		this.grafo.agregarVertice(this.b);
		this.grafo.agregarVertice(this.c);
		this.grafo.agregarVertice(this.d);
		this.grafo.agregarVertice(this.e);
		this.grafo.agregarVertice(this.f);
		

		this.grafo.agregarArista(a,b,0.5f);
		this.grafo.agregarArista(a,d,0.1f);
		this.grafo.agregarArista(a,e,0.6f);
		this.grafo.agregarArista(d,c,0.2f);
		this.grafo.agregarArista(c,b,0.4f);
		this.grafo.agregarArista(c,f,0.1f);
		
		aristaAE = new Arista(a,e,0.6f);
		aristaAB = new Arista(a,b,0.5f);
		
		agm = new ArbolGeneradorMinimo(this.grafo);
		arbol = agm.generarMinimo();
		
	}

	@Test
	public void testNoDeberiaExistirAristaAB() {
		assertFalse(this.arbol.existeArista(aristaAB));
	}
	
	@Test
	public void testDeberiaExistirAristaAE() {
		assertTrue(this.arbol.existeArista(aristaAE));
	}
	
	@Test
	public void testDeberiaSerConexo() {
		BFS bfs = new BFS(this.arbol);
		assertTrue(bfs.esConexo());
	}
	
	@Test
	public void testMismaCantidadDeNodos() {
		assertTrue(this.arbol.tamano() == this.grafo.tamano());
	}
	
	@Test
	public void testMismosNodos() {
		ArrayList<Nodo<String>> verticesGrafoAlfabeticamente = this.grafo.obtenerTodosLosVertices();
		ArrayList<Nodo<String>> verticesArbolAlfabeticamente = this.arbol.obtenerTodosLosVertices();
		
		ArrayList<String> letrasGrafo = new ArrayList<String>();
		ArrayList<String> letrasArbol = new ArrayList<String>();
		
		
		//Sacamos las Strings de cada nodo
		for (Nodo<String> i : verticesGrafoAlfabeticamente) {
			letrasGrafo.add(i.getInformacion());
		}
		
		for (Nodo<String> i : verticesArbolAlfabeticamente) {
			letrasArbol.add(i.getInformacion());
		}
		
		//Ordenamos alfabetica las del árbol
		Collections.sort(letrasArbol);
		
		assertTrue(letrasGrafo.equals(letrasArbol));
	}
	
}



