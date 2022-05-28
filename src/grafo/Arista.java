package grafo;

public class Arista<T1> {

	private Nodo<T1> a;
	private Nodo<T1> b;
	private Float peso;
	
	
	public Arista(Nodo<T1> nodoA, Nodo<T1> nodoB, Float peso) {
		
		if (this.losExtremosSonElMismoVertice(nodoA, nodoB)) {
			throw new IllegalArgumentException("No se puede crear una arista"
					+ "cuyos extremos son el mismo vértice");
		} else {
			this.a = nodoA;
			this.b = nodoB;
			this.peso = peso;
		}
		
		
		
	}


	public Nodo<T1> getA() {
		return a;
	}


	public void setA(Nodo<T1> a) {
		this.a = a;
	}


	public Nodo<T1> getB() {
		return b;
	}


	public void setB(Nodo<T1> b) {
		this.b = b;
	}


	public Float getPeso() {
		return peso;
	}


	public void setPeso(Float peso) {
		this.peso = peso;
	}
	
	
	public String toString() {
		String cadena = "Nodo A:" + this.getA().toString() + "\n";
		cadena += "Nodo B:" + this.getB().toString() + "\n";
		cadena += "Peso o Distancia: " + this.getPeso();
		return cadena;
	}
	
	
	public boolean equals(Arista<T1> e) {
		
		if (this.getA().equals(e.getA()) && this.getB().equals(e.getB())) {
			return true;
		}
		
		if (this.getA().equals(e.getB()) && this.getB().equals(e.getA())) {
			return true;
		}
		
		
		return false;
		
	}
	
	
	public boolean losExtremosSonElMismoVertice(Nodo<T1> a, Nodo<T1> b) {
		
		if (a.equals(b)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}
