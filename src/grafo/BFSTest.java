package grafo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BFSTest {

	private String dataA = "Hola";
	private String dataB = "Mundo";
	private String dataC = "Grafos";
	
	private Grafo<String> grafoA = new Grafo();
	private Grafo<String> grafoB = new Grafo();
	
	@Before
	public void setUp() throws Exception {
		
		this.grafoA.agregarVertice(this.dataA);
		this.grafoA.agregarVertice(this.dataB);
		this.grafoA.agregarVertice(this.dataC);
		
		this.grafoB.agregarVertice(this.dataA);
		this.grafoB.agregarVertice(this.dataB);
		this.grafoB.agregarVertice(this.dataC);
		
		
		
	}

	@Test
	public void testEsConexo() {
		this.grafoA.agregarArista(dataA, dataB, 1);
		this.grafoA.agregarArista(dataB, dataC, 1);
		BFS bfs = new BFS(this.grafoA);
		assertTrue(bfs.esConexo());
	}
	
	@Test
	public void testNoEsConexo() {
		this.grafoB.agregarArista(dataC, dataB, 1);
		
		BFS bfs = new BFS(this.grafoB);
		assertFalse(bfs.esConexo());
	}

}
