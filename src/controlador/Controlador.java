package controlador;

import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;

import grafo.ArbolGeneradorMinimo;
import grafo.BFS;
import grafo.Grafo;
import grafo.Nodo;
import modelo.Censista;
import modelo.Manzana;
import modelo.georreferenciable.externo.GeoJSON;
import modelo.georreferenciable.externo.ListadoDeCoordenadasParaLinea;
import modelo.georreferenciable.externo.ListadoDeCoordenadasParaPoligono;
import modelo.georreferenciable.externo.ListadoDeCoordenadasParaPunto;

public class Controlador {

	private GeoJSON datos;
	private Grafo<Manzana> radiocensal;

	public ArrayList<String> listadoCensistas = new ArrayList<String>();

	private Grafo<Manzana> arbolcensal;
	private Grafo<Manzana> recorrido;

	private int contadorNombres;

	public Controlador() {
		this.radiocensal = new Grafo<Manzana>();
	}

	public void importarDatos(String path, boolean sinJerarquia) {
		try {
			this.datos = GeoJSON.leerJSON(path);
			if (sinJerarquia) {
				this.datos.quitarJerarquiaDeImportacion();
			}
			this.convertirGeoJsonenGrafo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void convertirGeoJsonenGrafo() {
		this.convertirPoligonosDeJsonEnVertices();
		this.convertirLineasDeJsonEnAristas();
		this.convertirPuntosDeJsonEnCentrosDeNodo();
	}

	private void convertirPoligonosDeJsonEnVertices() {
		for (ListadoDeCoordenadasParaPoligono e : this.datos.obtenerListadoDeCoordParaPoligonos()) {
			Manzana manzana = Manzana.manzanaDesdeCoordenadas(e.getDatos());
			this.radiocensal.agregarVertice(manzana);
		}
	}

	private void convertirLineasDeJsonEnAristas() {
		for (ListadoDeCoordenadasParaLinea e : this.datos.obtenerListadoDeCoordParaLineas()) {
			Nodo<Manzana> nodoA = encontrarManzanaALaQuePertenece(e.getDatos().get(0));
			Nodo<Manzana> nodoB = encontrarManzanaALaQuePertenece(e.getDatos().get(1));
			this.radiocensal.agregarArista(nodoA, nodoB, 1);
		}
	}

	private void convertirPuntosDeJsonEnCentrosDeNodo() {
		for (ListadoDeCoordenadasParaPunto e : this.datos.obtenerListadoDeCoordParaPuntos()) {
			Coordinate centro = e.getDatos();
			Nodo<Manzana> nodo = encontrarManzanaALaQuePertenece(centro);
			nodo.getInformacion().setCentro(centro);
		}
	}

	public ArrayList<Nodo<Manzana>> getManzanas() {
		return this.radiocensal.obtenerTodosLosVertices();
	}

	public ArrayList<Nodo<Manzana>> getManzanasDeRecorrido() {
		return this.recorrido.obtenerTodosLosVertices();
	}

	public boolean hayRecorridoYaCreado() {
		if (this.recorrido != null) {

			return true;
		} else {

			return false;
		}

	}

	private Nodo<Manzana> encontrarManzanaALaQuePertenece(Coordinate coordenada) {
		for (Nodo<Manzana> i : this.radiocensal.obtenerTodosLosVertices()) {
			if (i.getInformacion().laManzanaContiene(coordenada)) {
				return i;
			}
		}
		return null;

	}

	public void obtenerArbolCensal() {

		ArbolGeneradorMinimo<Manzana> arbolGeneradorMinimo = new ArbolGeneradorMinimo(this.radiocensal);
		this.arbolcensal = arbolGeneradorMinimo.generarMinimo();

		BFS bfs = new BFS(this.arbolcensal);
		bfs.esConexo();
		this.recorrido = bfs.obtenerRecorrido();

		int contadorDeAsignacionMaxima = 0;
		Integer contadorDeCensistas = 0;
		Censista CensistaActual = null;
		Nodo<Manzana> anterior = null;

		for (Nodo<Manzana> e : recorrido.obtenerTodosLosVertices()) {

			if (contadorDeAsignacionMaxima == 3 || !e.esVecino(anterior)) {
				contadorDeCensistas++;
				contadorDeAsignacionMaxima = 0;
				CensistaActual = new Censista(elegirNombre(), contadorDeCensistas);
				this.contadorNombres++;
				this.listadoCensistas.add(CensistaActual.getNombre().toString());
			}

			e.getInformacion().asignarCensista(CensistaActual);
			contadorDeAsignacionMaxima++;
			anterior = e;

		}
	}

	public String elegirNombre() {
		String[] nombres = { "Marcos", "Alejo", "Tomas", "Miguel", "Sandra", "Luciana", "Micaela", "Mauro", "Ignacio",
				"Franco", "Martin", "Anabela", "Ernesto", "Sofia", "Manuel", "Antonio", "Jose", "William", "Maria",
				"Patricia", "Rosario", "Isabel", "Juana", "Emma", "Cecilia", "Gabriela", "Gabriel", "Maitena", "Lucas",
				"Alejandro", "Adrian" };
		return nombres[contadorNombres];
	}

	public void reinicializar() {
		this.datos.inicializar();
		this.datos.quitarJerarquiaDeImportacion();

		this.radiocensal = null;
		this.radiocensal = new Grafo<Manzana>();

		this.arbolcensal = null;
		this.recorrido = null;

		this.contadorNombres = 0;

		this.listadoCensistas.clear();

		this.convertirGeoJsonenGrafo();
	}

}
