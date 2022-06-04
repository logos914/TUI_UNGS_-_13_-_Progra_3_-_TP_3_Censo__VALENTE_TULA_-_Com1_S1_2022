package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import controlador.Controlador;
import grafo.Nodo;
import modelo.Manzana;
import modelo.georreferenciable.externo.ListadoDeCoordenadasParaPoligono;

public class Frontend {

	private JFrame frameInicial;
	private JFrame frameMapa;
	private JMapViewer viewerMapa;
	private Controlador controlador;

	public Frontend(Controlador controlador) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			this.controlador = controlador;
			this.frameInicial = this.frameInicial();
			this.frameInicial.setVisible(true);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		frameInicial();
		frameInicial.setVisible(true);
	}

	private JFrame frameInicial() {
		frameInicial = new JFrame();
		fondoDePantalla(frameInicial);

		frameInicial.setTitle("Censo 2022");
		frameInicial.setBounds(100, 100, 850, 700);
		frameInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameInicial.getContentPane().setLayout(null);
		frameInicial.setLocationRelativeTo(null);

		JButton botonInicio = new JButton("Importar Radio Censal");
		botonInicio.setBounds(350, 455, 220, 23);
		botonInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importarRadioCensal();
			}
		});
		frameInicial.getContentPane().add(botonInicio);
		
		frameInicial.setLocationRelativeTo(null);
		return frameInicial;
	}

	private JFrame frameMapa() {
		frameMapa = new JFrame();
		frameMapa.setTitle("Censo 2022");
		frameMapa.setBounds(300, 300, 1280, 720);
		frameMapa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.viewerMapa = new JMapViewer();
		Coordinate coordenadaInicial = new Coordinate(-34.52202, -58.70002);
		this.viewerMapa.setDisplayPosition(coordenadaInicial, 14);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelMapa = new JPanel();
		contentPane.add(panelMapa, BorderLayout.CENTER);
		panelMapa.setLayout(new GridLayout(0, 1, 0, 0));

		frameMapa.add(contentPane);

		panelMapa.add(this.viewerMapa);

		JButton botonUbicacionMapa = new JButton("Distribuir Censistas");
		botonUbicacionMapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	distribuirCensistas();
			}
		});
		
		botonUbicacionMapa.setBounds(10, 647, 110, 23);
		this.viewerMapa.add(botonUbicacionMapa);
		
		JButton botonZoom = new JButton("Hacer zoom");
		botonZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoomAMarcadores();
			}
		});
		botonZoom.setBounds(130, 647, 110, 23);
		this.viewerMapa.add(botonZoom);
		
		JButton botonMenu = new JButton("Volver al inicio");
		botonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volverAlMenu();
			}
		});
		botonMenu.setBounds(250, 647, 110, 23);
		this.viewerMapa.add(botonMenu);

		
		
		frameMapa.setLocationRelativeTo(null);
		return frameMapa;
	}

	private void fondoDePantalla(JFrame frame) {
		try {
			BackgroundPane background = new BackgroundPane();
			background.setBackground(ImageIO.read(new File("src/visual/censo.jpg")));
			frame.setContentPane(background);
			frame.getContentPane().setLayout(null);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void volverAlMenu() {
		frameMapa.setVisible(false);
		frameMapa.dispose();
		frameInicial.setVisible(true);
	}
	
	private void zoomAMarcadores() {
		this.viewerMapa.setDisplayToFitMapPolygons();
	}

	private void importarRadioCensal() {
		buscarArchivoMapa();
	}

	private void mostrarRadioCensalImportado(String pathRadioCensalImportado) {
		this.controlador.importarDatos(pathRadioCensalImportado);
		buscarArchivoCensistas();
		ubicarEnMapa();
		zoomAMarcadores();
		
	}

	public void buscarArchivoMapa() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("./Radios Censales Para Importar/"));
		chooser.setFileFilter(new FileNameExtensionFilter(".geojson", "geojson"));
		chooser.setDialogTitle("Importar archivo con radio censal");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			mostrarRadioCensalImportado(chooser.getSelectedFile().toString());
		} else {
			buscarArchivoMapa();
		}
	}
	
	public void buscarArchivoCensistas() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("./Censistas Disponibles/"));
		chooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
		chooser.setDialogTitle("Importar archivo con los censistas");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			enviarCensistas(chooser.getSelectedFile().toString());
		} else {
			buscarArchivoCensistas();
		}
	}
	
	private void enviarCensistas(String pathCensista) {
		this.controlador.setCensistas(pathCensista);
		frameMapa();
		frameInicial.setVisible(false);
		frameMapa.setVisible(true);
	}

	private void ubicarEnMapa() {

		for (Nodo<Manzana> i : this.controlador.getManzanas()) {

			// Los polígonos del mapa visual se forman con un tipo de coordenada diferente
			List<org.openstreetmap.gui.jmapviewer.Coordinate> listaPuntos = new ArrayList<Coordinate>();

			// pero debemos convertirlos desde el tipo de coordenada de jts
			for (org.locationtech.jts.geom.Coordinate e : i.getInformacion().getCoordenadasDeAristas()) {
				listaPuntos.add(new org.openstreetmap.gui.jmapviewer.Coordinate(e.getX(), e.getY()));
			}

			MapPolygonImpl rectangulo = new MapPolygonImpl(listaPuntos);
			this.viewerMapa.addMapPolygon(rectangulo);
			rectangulo.setColor(Color.RED);
			
			rectangulo.setVisible(true);
		}

		this.viewerMapa.setDisplayToFitMapPolygons();
	}
	
	
	
	private void distribuirCensistas() {
		this.controlador.obtenerArbolCensal();
		this.viewerMapa.removeAllMapMarkers();
		this.viewerMapa.removeAllMapPolygons();
		
		// TODO: Refactuor urgente aqui con el mismo codigo de la f anterior
		for (Nodo<Manzana> i : this.controlador.getManzanasDeRecorrido()) {

			// Los polígonos del mapa visual se forman con un tipo de coordenada diferente
			List<org.openstreetmap.gui.jmapviewer.Coordinate> listaPuntos = new ArrayList<Coordinate>();

			// pero debemos convertirlos desde el tipo de coordenada de jts
			for (org.locationtech.jts.geom.Coordinate e : i.getInformacion().getCoordenadasDeAristas()) {
				listaPuntos.add(new org.openstreetmap.gui.jmapviewer.Coordinate(e.getX(), e.getY()));
			}

			MapPolygonImpl rectangulo = new MapPolygonImpl(listaPuntos);
			this.viewerMapa.addMapPolygon(rectangulo);
			rectangulo.setColor(Color.BLUE);
			rectangulo.setBackColor(Color.GREEN);
			rectangulo.setVisible(true);
			
			org.openstreetmap.gui.jmapviewer.Coordinate coordCentro = new org.openstreetmap.gui.jmapviewer.Coordinate(i.getInformacion().getCentro().x,i.getInformacion().getCentro().y);
			MapMarker marcador = new MapMarkerDot(i.getInformacion().getCensista().getNombre(), coordCentro);
			this.viewerMapa.addMapMarker(marcador);
			
		}

		this.viewerMapa.setDisplayToFitMapPolygons();
	}
}
