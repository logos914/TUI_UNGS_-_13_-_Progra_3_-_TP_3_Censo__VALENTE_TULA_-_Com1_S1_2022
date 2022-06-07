package grafo;

import java.util.ArrayList;

public class ArbolGeneradorMinimo<T1> {

	private Grafo<T1> grafo;
	private Grafo<T1> arbol;
	private ArrayList<Arista<T1>> aristas;

	
	Nodo<T1> nodoActualDelGrafo;
	Nodo<T1> nodoActualParaArbol;
	Arista<T1> aristaActualDeMenorPeso;
	Nodo<T1> nodoParaAgregarDelGrafo;
	Nodo<T1> nodoParaAgregarEnElArbol;
	
	
	public ArbolGeneradorMinimo(Grafo<T1> grafo) {
		this.grafo = grafo;
		
		this.arbol = new Grafo<T1>();
		
	}
	
	
	public Grafo<T1> generarMinimo() {
		
		this.aristas = new ArrayList<Arista<T1>>();
		
		
		tomarElPrimerVerticeDelGrafo();
		agregarElPrimerVerticeAlArbol();
		agregarAristasParaVerificar();
		
		// Mientras haya aristas que procesar
		while (this.aristas.size() > 0) {
			
		
			encontrarLaAristaDeMenorPeso();
			

			tomarElSiguienteVerticeDelGrafo();
			tomarElSiguienteVerticeParaElArbol();
			revalidarVerticeActualDelGrafo();
			
	
			agregarAristaDeMenorPesoAlArbol();
			quitarAristaDeMenorPesoDelListadoParaProcesar();
			
			
			confirmarElNuevoVerticeAgregado();
			
			quitarAristasQueNoRequierenProcesar();
			agregarAristasParaVerificar();
			quitarAristasQueNoRequierenProcesar();
			
		}
		
		
		
		
		return this.arbol;
		
		
		}


	private void confirmarElNuevoVerticeAgregado() {
		nodoActualDelGrafo = this.grafo.obtenerVerticeConVecinos(nodoParaAgregarDelGrafo);
		nodoActualParaArbol = nodoParaAgregarEnElArbol;
	}


	private void quitarAristaDeMenorPesoDelListadoParaProcesar() {
		this.aristas.remove(aristaActualDeMenorPeso);
	}


	private void agregarAristaDeMenorPesoAlArbol() {
		this.arbol.agregarArista(nodoActualParaArbol, nodoParaAgregarEnElArbol, aristaActualDeMenorPeso.getPeso());
	}


	private void revalidarVerticeActualDelGrafo() {
		
		//Si la arista de menor peso actualmente posee un vértice que no es el último vértice que se agregó
		// (el que trajo nuevas aristas posibles para evaluar. 
		// Entonces debemos cambiarlo por el vértice que corresponde de esta arista de menor peso.
		
		nodoActualParaArbol =  this.arbol.obtenerVerticeConVecinos(aristaActualDeMenorPeso.getA());

	}

	private void agregarElSiguienteVerticeParaElArbolSiEsNecesario() {
		if (this.arbol.obtenerVerticeConVecinos(nodoParaAgregarEnElArbol) == null) {
			this.arbol.agregarVertice(nodoParaAgregarEnElArbol);
		}
	}

	private void tomarElSiguienteVerticeParaElArbol() {
		
		//Obtenemos una copia del nodo, sin vecinos
		nodoParaAgregarEnElArbol = this.grafo.obtenerNodoSinVecinos(nodoParaAgregarDelGrafo);
		
		//Si el nodo no existe en el árbol, lo agregamos.
		agregarElSiguienteVerticeParaElArbolSiEsNecesario();
		
		//Obtenemos el verdadero nodo del árbol para operar con él.
		nodoParaAgregarEnElArbol = this.arbol.obtenerVerticeConVecinos(nodoParaAgregarEnElArbol);
	}


	private void tomarElSiguienteVerticeDelGrafo() {
		nodoParaAgregarDelGrafo = aristaActualDeMenorPeso.getB();
	}


	private void agregarElPrimerVerticeAlArbol() {
		nodoActualParaArbol = this.grafo.obtenerNodoSinVecinos(nodoActualDelGrafo);
		agregarVerticeActualAlArbol();
	}
	
	private void agregarVerticeActualAlArbol() {
		this.arbol.agregarVertice(nodoActualParaArbol);
	
	}


	private void tomarElPrimerVerticeDelGrafo() {
		nodoActualDelGrafo = this.grafo.obtenerVerticeConVecinos(0);
	}
	
		
		
	private boolean estaAristaYaExiste(Arista<T1> a) {

		return this.arbol.existeArista(a);

	}
		
		
	private void agregarAristasParaVerificar() {
		
		for (Distancia<T1> i : this.grafo.vecinos(nodoActualDelGrafo)) {
			
			Nodo<T1> nodoOrigenTemporal = this.arbol.obtenerVerticeConVecinos(this.nodoActualDelGrafo);
			Nodo<T1> nodoDestinoTemporal = this.grafo.obtenerNodoSinVecinos(i.getDestino());
					
			Arista<T1> arista = new Arista(nodoOrigenTemporal,nodoDestinoTemporal,i.getPeso());
				if (!this.arbol.existeArista(arista)) {
					this.aristas.add(arista);
				
				}
		}
	}
	
	
	private void encontrarLaAristaDeMenorPeso() {
		
		
		float pesoMinimoActual = this.aristas.get(0).getPeso();
		Arista<T1> aristaCandidataDeMenorPeso = this.aristas.get(0);
		
		for (Arista<T1> e : aristas) {
			
				if (pesoMinimoActual >= e.getPeso()) {
					pesoMinimoActual = e.getPeso();
					aristaCandidataDeMenorPeso = e;
				}
			}
		
		aristaActualDeMenorPeso = aristaCandidataDeMenorPeso;
	}
	
	
	private void quitarAristasQueNoRequierenProcesar() {
		
		ArrayList<Arista<T1>> listadoParaQuitar = new ArrayList<Arista<T1>>();
		
		for (Arista<T1> e : aristas) {
			
			Nodo<T1> a = this.arbol.obtenerVerticeConVecinos(e.getA());
			Nodo<T1> b = this.arbol.obtenerVerticeConVecinos(e.getB());

			// Marcamos para borrar las aristas cuyos dos vértices existan ya en el árbol, y además dichos vértices ya tengan vecinos.
			// Básicamente borramos las aristas que pueden crear circuitos en el árbol.
			if (this.arbol.verificarVertice(e.getA()) && this.arbol.verificarVertice(e.getB())
				&&	a.obtenerCantidadDeVecinos() != 0 && b.obtenerCantidadDeVecinos() != 0 	){
				listadoParaQuitar.add(e);
			}
		}
		
		
		if (listadoParaQuitar.size() > 0) {
			for (Arista<T1> e : listadoParaQuitar) {
				this.aristas.remove(e);
			}
		}
	}
	
	
	
	
}
