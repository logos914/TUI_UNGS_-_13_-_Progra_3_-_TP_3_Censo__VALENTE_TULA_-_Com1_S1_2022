package grafo;

public class Distancia<T1>{
	private Nodo<T1> destino;
	private Float peso;
	
	public Distancia(Nodo<T1> destino, Float peso) {
		this.destino = destino;
		this.peso = peso;
	}

	public Nodo<T1> getDestino() {
		return destino;
	}

	public void setDestino(Nodo<T1> destino) {
		this.destino = destino;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}
	
	public boolean equals(Distancia<T1> d) {
		if (this.getDestino().equals(d.getDestino()) && this.getPeso().equals(d.getPeso())) {
			return true;
		} else {
			return false;
		}
	}
	
}
