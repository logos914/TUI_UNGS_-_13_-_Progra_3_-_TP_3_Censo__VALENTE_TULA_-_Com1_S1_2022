package grafo;

import java.util.ArrayList;

public class BFS<T1> {

	private Grafo<T1> grafo;
	ArrayList<Nodo<T1>> nodosPendientes;
	boolean[] nodosProcesados; // no los toco más, ya me paré en ellos y obtuve sus vecinos y los marqué
	boolean[] nodosMarcados; // Este nodo es alcanzable
	private Grafo<T1> recorrido;

	public BFS(Grafo<T1> grafo) {
		this.grafo = grafo;
		this.recorrido = new Grafo<T1>();
	}

	public boolean esConexo() {

		Nodo<T1> i;
		nodosPendientes = this.grafo.obtenerTodosLosVertices();
		nodosProcesados = new boolean[this.grafo.tamano()];
		nodosMarcados = new boolean[this.grafo.tamano()];
		while (!this.seRecorrieronTodosLosVertices() && this.hayMasParaProcesar()) 
		{
			obtenerNodoActualYMarcarVecinos();
		}
		this.completarRecorridoSiEsNecesario();
		return seRecorrieronTodosLosVertices();

	}
	
	private void obtenerNodoActualYMarcarVecinos() {
		int pos = proximoParaProcesar();
		Nodo<T1> i = this.nodosPendientes.get(pos);
		marcarTambienConfirmarActual(pos);
		marcarVecinos(i);
	}
	
	private void marcarVecinos(Nodo<T1> nodoI) {
		for (Distancia<T1> e : this.grafo.vecinos(nodoI)) 
		{
			int indiceVecinoParaMarcar = this.nodosPendientes.indexOf(e.getDestino());
			this.nodosMarcados[indiceVecinoParaMarcar] = true;
			Arista<T1> arista = new Arista(e.getDestino(),nodoI,1.0f);
		}
	}
	
	private void marcarTambienConfirmarActual(int pos) {
		this.nodosMarcados[pos] = true;
		this.nodosProcesados[pos] = true;
		agregarVerticeEnElRecorrido(pos);
	}

	private void agregarVerticeEnElRecorrido(int pos) {	
		this.recorrido.agregarVertice(this.grafo.obtenerVerticeConVecinos(pos));
	}
	
	private void completarRecorridoSiEsNecesario() {
		int i = proximoParaProcesar();
		while(i != -1) {
		this.agregarVerticeEnElRecorrido(i);
			this.nodosProcesados[i] = true;
			i = proximoParaProcesar();
		}
	}

	private boolean seRecorrieronTodosLosVertices() {
		boolean acumulador = true;
		for (boolean i : this.nodosMarcados) 
		{
			acumulador = acumulador && i;
		}
		return acumulador;
	}
	
	private boolean hayMasParaProcesar() {
		if (this.proximoParaProcesar() == -1) 
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private int proximoParaProcesar() {
		if (!this.nodosProcesados[0]) 
		{
			return 0;
		}

		for (int i = 0; i < this.nodosPendientes.size(); i++) 
		{
			if (this.nodosMarcados[i] && !this.nodosProcesados[i]) 
			{
				return i;
			}
		}
		return -1;
}
	
	
	
	public Grafo<T1> obtenerRecorrido() {
		return this.recorrido;
	}
}
