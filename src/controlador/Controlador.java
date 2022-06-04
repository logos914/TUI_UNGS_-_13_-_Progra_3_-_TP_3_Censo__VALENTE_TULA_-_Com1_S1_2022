package controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.locationtech.jts.geom.Coordinate;

import frontend.Frontend;
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
	private Censista censista;
	private Frontend frontend;
	private ArrayList<Censista> listadoCensistas = new ArrayList<Censista>();
	private Grafo<Manzana> arbolcensal;
	private Grafo<Manzana> recorrido;

	public Controlador() {

		this.radiocensal = new Grafo<Manzana>();
	}

	public void importarDatos(String path) {
		try {
			this.datos = GeoJSON.leerJSON(path);
			this.convertirGeoJsonenGrafo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void convertirGeoJsonenGrafo() {

		this.convertirPoligonosDeJsonEnVertices();
		this.convertirLineasDeJsonEnAristas();
		this.convertirPuntosDeJsonEnCentrosDeNodo();

		// TODO: Falta las aristas del grafo (lineas de geojson)

	}

	private void convertirPoligonosDeJsonEnVertices() {

		for (ListadoDeCoordenadasParaPoligono e : this.datos.getListadoDeCoordParaPoligonos()) {
			Manzana manzana = Manzana.manzanaDesdeCoordenadas(e.getDatos());
			this.radiocensal.agregarVertice(manzana);
		}

	}

	private void convertirLineasDeJsonEnAristas() {

		for (ListadoDeCoordenadasParaLinea e : this.datos.getListadoDeCoordParaLineas()) {

			Nodo<Manzana> nodoA = encontrarManzanaALaQuePertenece(e.getDatos().get(0));
			Nodo<Manzana> nodoB = encontrarManzanaALaQuePertenece(e.getDatos().get(1));

			this.radiocensal.agregarArista(nodoA, nodoB, 1);
		}

	}

	private void convertirPuntosDeJsonEnCentrosDeNodo() {

		for (ListadoDeCoordenadasParaPunto e : this.datos.getListadoDeCoordParaPuntos()) {

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

	public ArrayList<Censista> setCensistas(String pathCensista) {

		List<String> cencistasString = new ArrayList<String>();

		Scanner archivoCensistas;
		try {
			archivoCensistas = new Scanner(new File(pathCensista)).useDelimiter(",\\s*");

			while (archivoCensistas.hasNext()) {
				cencistasString.add(archivoCensistas.nextLine());
			}
			archivoCensistas.close();

			for (String s : cencistasString) {
				Censista c = new Censista(s, listadoCensistas.size());
				listadoCensistas.add(c);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return listadoCensistas;
	}

	private ArrayList<Censista> getCensistas() {
		return listadoCensistas;
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
		ArbolGeneradorMinimo<Manzana> arbol = new ArbolGeneradorMinimo(this.radiocensal);
		this.arbolcensal = arbol.generarMinimo();
		BFS bfs = new BFS(this.arbolcensal);
		bfs.esConexo();
		this.recorrido = bfs.obtenerRecorrido();

		int contadorDeAsignacionMaxima = 0;
		Integer contadorDeCensistas = 1;
		Censista CensistaActual = new Censista(contadorDeCensistas.toString(), contadorDeCensistas);
		Nodo<Manzana> anterior = null;

		for (Nodo<Manzana> e : recorrido.obtenerTodosLosVertices()) {
			if (anterior == null) {
				e.getInformacion().asignarCensista(CensistaActual);
				contadorDeAsignacionMaxima++;
				anterior = e;
			} else {
					if (contadorDeAsignacionMaxima < 3 && e.esVecino(anterior)) {
						e.getInformacion().asignarCensista(CensistaActual);
						contadorDeAsignacionMaxima++;
						anterior = e;
	
					} else {
						contadorDeAsignacionMaxima = 0;
						contadorDeCensistas++;
						CensistaActual = new Censista(contadorDeCensistas.toString(), contadorDeCensistas);
						e.getInformacion().asignarCensista(CensistaActual);
						contadorDeAsignacionMaxima++;
						anterior = e;
					}

			}
		}
	}
//	private Map<Manzana, Censista> algoritmoGoloso() {
//		for (Nodo<Manzana> i : this.getManzanas()) {
//			for (Censista c : this.getCensistas()) {
//				i.getInformacion();
//				if(c.getNumero()<=3) {
//					
//				}
//			}
//		}
//	}

}
