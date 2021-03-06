package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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

public class Frontend {

	private JFrame frameInicial;
	private JFrame frameMapa;
	private JFrame frameCensistas;
	private JMapViewer viewerMapa;
	private Controlador controlador;
	private JCheckBox chkboxIgnoraOrdenImportacion;
	private String pathArchivoGeoJson;

	public Frontend(Controlador controlador) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			this.controlador = controlador;
			initialize();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		this.frameInicial();
		this.frameInicial.setVisible(true);
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
		botonInicio.setBounds(366, 455, 220, 23);
		botonInicio.setOpaque(false);
		botonInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importarRadioCensal();
			}
		});

		chkboxIgnoraOrdenImportacion = new JCheckBox("Ignorar la jerarqu\u00EDa u orden de importaci\u00F3n");
		chkboxIgnoraOrdenImportacion.setToolTipText("");
		chkboxIgnoraOrdenImportacion.setSelected(true);
		chkboxIgnoraOrdenImportacion.setBounds(350, 500, 250, 23);
		chkboxIgnoraOrdenImportacion.setOpaque(false);
		chkboxIgnoraOrdenImportacion.setForeground(Color.WHITE);

		JLabel lblSobreJerarquia = new JLabel(
				"<html>Los datos importados de un geojson provienen con un orden secuencial. "
						+ "Las manzanas y la uni\u00F3n entre ellas se ingestan siempre en dicho orden, "
						+ "y producen una primera manzana y recorridos de asignaci\u00F3n fijos que "
						+ "son determinados por dicha ordenaci\u00F3n. Ignorar esa jerarqu\u00EDa u "
						+ "orden, obliga al programa a asignar manzanas de diferente manera en cada ejecuci\u00F3n.");
		lblSobreJerarquia.setVerticalAlignment(SwingConstants.TOP);
		lblSobreJerarquia.setHorizontalAlignment(SwingConstants.CENTER);
		lblSobreJerarquia.setBounds(300, 525, 450, 80);
		lblSobreJerarquia.setOpaque(false);
		lblSobreJerarquia.setForeground(Color.LIGHT_GRAY);

		frameInicial.getContentPane().add(botonInicio);
		frameInicial.getContentPane().add(chkboxIgnoraOrdenImportacion);
		frameInicial.getContentPane().add(lblSobreJerarquia);

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

		JButton botonUbicacionMapa = new JButton("Distribuir censistas");
		botonUbicacionMapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				distribuirCensistas();
				botonUbicacionMapa.setText("Redistribuir censistas");
			}
		});

		botonUbicacionMapa.setBounds(10, 647, 150, 23);
		this.viewerMapa.add(botonUbicacionMapa);

		JButton botonZoom = new JButton("Centrar");
		botonZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoomAMarcadores();
			}
		});
		botonZoom.setBounds(170, 647, 110, 23);
		this.viewerMapa.add(botonZoom);

		JButton botonMenu = new JButton("Volver al inicio");
		botonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volverAlMenu();
			}
		});
		botonMenu.setBounds(290, 647, 110, 23);
		this.viewerMapa.add(botonMenu);

		JButton botonCensistas = new JButton("Ver censistas asignados");
		botonCensistas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verCensistas();
			}
		});
		botonCensistas.setBounds(410, 647, 200, 23);
		this.viewerMapa.add(botonCensistas);

		frameMapa.setLocationRelativeTo(null);
		return frameMapa;
	}

	private JFrame frameCensistas() {
		frameCensistas = new JFrame();
		frameCensistas.setLayout(new BorderLayout());
		frameCensistas.setTitle("Perfil de los Censistas");
		frameCensistas.setBounds(0, 0, 330, 200);
		frameCensistas.add(switcherPane());
		frameCensistas.pack();
		JOptionPane.showMessageDialog(null,
				"Asumimos que algunos censistas son gemelos de otros.");
		return frameCensistas;
	}

	private JPanel switcherPane() {
		JPanel panel = new JPanel();
		BufferedImage[] images;
		JLabel lblBackground;
		JComboBox comboBox;
		String[] IMAGES = new String[] { "src/visual/censista1.png", "src/visual/censista2.png",
				"src/visual/censista3.png", "src/visual/censista4.png", "src/visual/censista5.png",
				"src/visual/censista6.png", "src/visual/censista7.png", "src/visual/censista8.png",
				"src/visual/censista9.png", "src/visual/censista10.png", "src/visual/censista11.png",
				"src/visual/censista12.png", "src/visual/censista13.png", "src/visual/censista14.png",
				"src/visual/censista15.png", "src/visual/censista16.png", "src/visual/censista17.png",
				"src/visual/censista18.png", "src/visual/censista19.png", "src/visual/censista20.png",
				"src/visual/censista21.png", "src/visual/censista22.png", "src/visual/censista23.png",
				"src/visual/censista24.png", "src/visual/censista25.png", "src/visual/censista26.png",
				"src/visual/censista27.png", "src/visual/censista28.png", "src/visual/censista29.png",
				"src/visual/censista30.png", "src/visual/censista31.png", "src/visual/censista32.png",
				"src/visual/censista33.png", "src/visual/censista34.png", "src/visual/censista35.png" };

		images = new BufferedImage[this.controlador.listadoCensistas.size()];
		System.out.println(this.controlador.listadoCensistas.size());
		for (int index = 0; index < this.controlador.listadoCensistas.size(); index++) {
			try {
				images[index] = ImageIO.read(new File(IMAGES[index]));
			} catch (IOException e1) {
			}
		}
		panel.setLayout(new BorderLayout());
		lblBackground = new JLabel();
		lblBackground.setIcon(new ImageIcon(images[0]));
		panel.add(lblBackground);
		comboBox = new JComboBox();
		comboBox.setSize(150,150);
		comboBox.getSelectedItem();
		comboBox.setFont(comboBox.getFont().deriveFont(20f));
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (String s : this.controlador.listadoCensistas) {
			model.addElement(s);
		}
		comboBox.setModel(model);
		comboBox.setSelectedIndex(0);
		lblBackground.setLayout(new GridBagLayout());
		lblBackground.add(comboBox);
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				lblBackground.setIcon(new ImageIcon(images[comboBox.getSelectedIndex()]));
			}
		});
		return panel;
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
		this.controlador = null;
		this.controlador = new Controlador();
		frameMapa.setVisible(false);
		frameMapa.dispose();
		frameInicial.setVisible(true);
	}

	private void zoomAMarcadores() {
		this.viewerMapa.setDisplayToFitMapPolygons();
	}

	private void verCensistas() {
		frameCensistas();
		frameCensistas.setVisible(true);
	}

	private void importarRadioCensal() {
		buscarArchivoMapa();
	}

	private void mostrarRadioCensalImportado() {
		this.controlador.importarDatos(this.pathArchivoGeoJson, this.chkboxIgnoraOrdenImportacion.isSelected());
		frameMapa();
		frameInicial.setVisible(false);
		frameMapa.setVisible(true);
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
			this.pathArchivoGeoJson = chooser.getSelectedFile().toString();
			mostrarRadioCensalImportado();
		} else {
			buscarArchivoMapa();
		}
	}

	private void ubicarEnMapa() {
		for (Nodo<Manzana> i : this.controlador.getManzanas()) {

			// Los pol??gonos del mapa visual se forman con un tipo de coordenada diferente
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

		if (this.controlador.hayRecorridoYaCreado()) {
			this.controlador.reinicializar();
		}

		this.controlador.obtenerArbolCensal();
		this.viewerMapa.removeAllMapMarkers();
		this.viewerMapa.removeAllMapPolygons();

		for (Nodo<Manzana> i : this.controlador.getManzanasDeRecorrido()) {

			// Los pol??gonos del mapa visual se forman con un tipo de coordenada diferente
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

			org.openstreetmap.gui.jmapviewer.Coordinate coordCentro = new org.openstreetmap.gui.jmapviewer.Coordinate(
					i.getInformacion().getCentro().x, i.getInformacion().getCentro().y);
			MapMarker marcador = new MapMarkerDot(String.valueOf(i.getInformacion().getCensista().getNumero()), coordCentro);
			this.viewerMapa.addMapMarker(marcador);

		}

		this.viewerMapa.setDisplayToFitMapPolygons();
	}
}
