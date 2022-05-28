package grafo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Grafo<T1> {

	private ArrayList<Nodo<T1>> vertices;

	public Grafo() {
		vertices = new ArrayList<Nodo<T1>>();
	}

	public void agregarVertice(T1 informacion) {
		Nodo<T1> nuevoNodo = new Nodo<T1>(informacion);
		this.vertices.add(nuevoNodo);
	}

	public void agregarVertice(Nodo<T1> nodo) {
		this.vertices.add(nodo);
	}

	private void agregarArista(Arista<T1> arista) {
		this.verificarVerticesDeArista(arista);
		this.verificarExisteArista(arista);
		this.verificarQueLosNodosNoSeanElMismo(arista);
		arista.getA().agregarVecino(arista.getB(), arista.getPeso());
		arista.getB().agregarVecino(arista.getA(), arista.getPeso());
	}

	public void agregarArista(Nodo<T1> a, Nodo<T1> b, float peso) {
		try {
			Arista<T1> arista = new Arista(this.obtenerVerticeConVecinos(a), this.obtenerVerticeConVecinos(b), peso);
			this.agregarArista(arista);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public void agregarArista(T1 a, T1 b, float peso) {
		try 
			{
			Nodo<T1> nodoA = this.obtenerVerticeConVecinos(a);
			Nodo<T1> nodoB = this.obtenerVerticeConVecinos(b);

			if (nodoA != null && nodoB != null) {
				Arista<T1> arista = new Arista(nodoA, nodoB, peso);
				this.agregarArista(arista);
			} else {
				throw new IllegalArgumentException("No se puede agregar arista. "
						+ "No se pueden agregar aristas con Vértices con cuyo valor es nulo\n" + "A:" + a.toString()
						+ "\n" + "B:" + b.toString() + "\n");
			}

		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void verificarQueLosNodosNoSeanElMismo(Arista<T1> arista) {
		if (arista.getA().equals(arista.getB())) {
			throw new IllegalArgumentException(
					"Un nodo no puede ser vecino de sí mismo\n Arista: " + arista.toString());
		}
	}

	private void verificarVerticesDeArista(Arista<T1> arista) {
		if (!verificarVertice(arista.getA())) {
			throw new IllegalArgumentException(
					"Este nodo, no forma parte de este gráfo.\n Nodo: " + arista.getA().toString());
		}

		if (!verificarVertice(arista.getB())) {
			throw new IllegalArgumentException(
					"Este nodo, no forma parte de este gráfo.\n Nodo: " + arista.getB().toString());
		}
	}

	private void verificarExisteArista(Arista<T1> arista) {
		if (this.existeArista(arista)) {
			throw new IllegalArgumentException(
					"Esta arista ya existe, y no puede agregarse al grafo: \n" + arista.toString());
		}
	}

	public void eliminarArista(Arista<T1> arista) {
		if (this.existeArista(arista)) {
			Nodo<T1> nodoA = this.obtenerVerticeConVecinos(arista.getA());
			Nodo<T1> nodoB = this.obtenerVerticeConVecinos(arista.getB());
			nodoA.eliminarVecino(nodoB);
			nodoB.eliminarVecino(nodoA);
		}
	}

	// Informa si existe la arista especificada
	public boolean existeArista(Arista<T1> arista) {

		Nodo<T1> nodoA = this.obtenerVerticeConVecinos(arista.getA());
		Nodo<T1> nodoB = this.obtenerVerticeConVecinos(arista.getB());

		if (nodoA == null || nodoB == null) {
			return false;
		}

		if (nodoA.esVecino(nodoB) || nodoB.esVecino(nodoA)) {
			return true;
		} else {
			return false;
		}

	}

	// Cantidad de vertices
	public int tamano() {
		return this.vertices.size();
	}

	// Vecinos de un vertice
	public ArrayList<Distancia<T1>> vecinos(Nodo<T1> nodo) {

		if (this.verificarVertice(nodo)) {
			return nodo.obtenerTodosLosVecinos();
		} else {
			throw new IllegalArgumentException("Este nodo, no forma parte de este gráfo.\n Nodo: " + nodo.toString());
		}

	}

	// Verifica que sea un vertice valido
	public boolean verificarVertice(Nodo<T1> a) {
		for (Nodo<T1> e : vertices) {
			if ((e.equals(a))) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Nodo<T1>> obtenerTodosLosVertices() {

		return this.vertices;
	}

	public Nodo<T1> obtenerVerticeConVecinos(int i) {
		return this.vertices.get(i);
	}

	public Nodo<T1> obtenerVerticeConVecinos(Nodo<T1> e) {
		return this.obtenerVerticeConVecinos(e.getInformacion());
	}

	public Nodo<T1> obtenerVerticeConVecinos(T1 e) {

		for (Nodo<T1> i : this.obtenerTodosLosVertices()) {
			if (i.getInformacion().equals(e)) {
				return i;
			}
		}

		return null;
	}

	public Nodo<T1> obtenerVerticeSinVecinos(T1 e) {
		Nodo<T1> nodoGrafo = this.obtenerVerticeConVecinos(e);
		Nodo<T1> nodoDevolucion = new Nodo(nodoGrafo.getInformacion());
		return nodoDevolucion;
	}

	public T1 obtenerVerticeSinVecinos(Nodo<T1> e) {
		Nodo<T1> nodoGrafo = this.obtenerVerticeConVecinos(e);
		T1 informacionDevolver = nodoGrafo.getInformacion();
		return informacionDevolver;
	}

	public Nodo<T1> obtenerNodoSinVecinos(Nodo<T1> e) {
		Nodo<T1> nodoGrafo = this.obtenerVerticeConVecinos(e);

		if (nodoGrafo == null) {
			return null;
		} else {
			Nodo<T1> nodoDevolucion = new Nodo(nodoGrafo.getInformacion());
			return nodoDevolucion;
		}

	}
}
