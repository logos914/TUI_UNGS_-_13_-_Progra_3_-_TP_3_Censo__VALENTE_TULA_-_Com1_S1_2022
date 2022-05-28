package grafo;

import java.util.ArrayList;

public class Nodo<T1> {

	private T1 informacion;
	private ArrayList<Distancia<T1>> vecinos;


	
	public Nodo(T1 informacion) {
		this.informacion = informacion;
		this.vecinos = new ArrayList<Distancia<T1>>();
	}


	public T1 getInformacion() {
		return informacion;
	}
	
	public String toString() {
		return informacion.toString();
	}


	public void setInformacion(T1 informacion) {
		this.informacion = informacion;
	}
	
	public void agregarVecino(Nodo<T1> vecino, Float peso) {
		this.verificarRequisitoParaSerVecino(vecino);
		Distancia<T1> distancia = new Distancia<T1>(vecino, peso);
		this.vecinos.add(distancia);
	}
	
	public ArrayList<Distancia<T1>> obtenerTodosLosVecinos() {
		return this.vecinos;
	}
	
	public int obtenerCantidadDeVecinos() {
		return this.vecinos.size();
	}
	
	public Distancia<T1> obtenerDistancia(int indice) {
		return this.vecinos.get(indice);
	}
	
	public Distancia<T1> obtenerDistancia(Nodo<T1> nodoVecino) {
		
		Distancia<T1> vecinoEncontrado = null;
		
		for (Distancia<T1> d : vecinos) {
			if (d.getDestino().equals(nodoVecino)) {
				vecinoEncontrado = d;
			}
		}
		
		return vecinoEncontrado;
	}
	
	public boolean esVecino(Nodo<T1> nodoVecino) {
		
		if (this.vecinos.size() == 0) {
			return false;
		} else {
			Distancia<T1> encontrado = null;
			
			encontrado = this.obtenerDistancia(nodoVecino);
			
			if (encontrado == null) {
				return false;
			} else {
				return true;
			}
		}
		
	}
	
	public void verificarRequisitoSerVecino(Nodo<T1> nodoVecino) {
		if (!this.esVecino(nodoVecino)) {
			throw new IllegalArgumentException("No se puede realizar "
					+ "la operación porque el nodo pasado por parámetro "
					+ "no es vecino de este nodo. "
					+ "Nodo Actual: " + this.toString() + "\n"
					+ "Nodo No-Vecino: " + nodoVecino.toString());
		}
	}
	
	public void verificarRequisitoParaSerVecino(Nodo<T1> nodoVecino) {
		if (this.esVecino(nodoVecino)) {
			throw new IllegalArgumentException("No se puede realizar "
					+ "la operación porque el nodo pasado por parámetro "
					+ "YA ES vecino de este nodo. "
					+ "Nodo Actual: " + this.toString() + "\n"
					+ "Nodo Vecino: " + nodoVecino.toString());
		}
		
		if (this.equals(nodoVecino)) {
			throw new IllegalArgumentException("No se puede realizar "
					+ "la operación porque el nodo pasado por parámetro "
					+ "ES EL MISMO nodo. "
					+ "Nodo Actual: " + this.toString() + "\n"
					+ "Nodo Vecino: " + nodoVecino.toString());
		}
	}
	
	
	
	public void eliminarVecino(int indice) {
		if (indice < 0) {
			throw new IllegalArgumentException("No se puede eliminar un "
					+ "vecino cuyo índice en la lista de vecinos "
					+ "es menor que 0 | Valor indicado: " 
					+ indice);
		}
		
		if (indice >= this.vecinos.size()) {
			throw new IllegalArgumentException("No se puede eliminar un "
					+ "vecino cuyo índice excede la cantidad de vecinos "
					+ "Valor indicado: " 
					+ indice);
		}
		
		this.vecinos.remove(indice);
	}
	

	public void eliminarVecino(Nodo<T1> nodoVecino) {		
		this.eliminarVecino(this.indiceDeVecino(nodoVecino));
	}
	
	
	public int indiceDeVecino(Nodo<T1> nodoVecino) {
		
		this.verificarRequisitoSerVecino(nodoVecino);
		Distancia<T1> distancia = this.obtenerDistancia(nodoVecino);
		return this.vecinos.indexOf(distancia);
		
	}
	
	
	public boolean equals(Nodo<T1> e) {
		if (this.informacion.equals(e.getInformacion())) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
}
