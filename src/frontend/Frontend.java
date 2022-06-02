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
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import controlador.Controlador;

public class Frontend {

	private JFrame frameInicial;
	private JFrame frameMapa;

	private JMapViewer viewerMapa;

	

	public Frontend() {
		initialize();
	}

	public void crearVistaInterfaz() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frontend window = new Frontend();
					window.frameInicial.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initialize() {
		frameInicial();
	}

	private JFrame frameInicial() {
		frameInicial = new JFrame();
		
		fondoDePantalla(frameInicial);
		
		frameInicial.setTitle("Censo 2022");
		frameInicial.setBounds(100, 100, 850, 700);
		frameInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameInicial.getContentPane().setLayout(null);
		frameInicial.setLocationRelativeTo(null);

		JButton botonInicio = new JButton("Comenzar");
		botonInicio.setBounds(390, 455, 110, 23);
		botonInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionBoton("Comenzar");
			}
		});
		frameInicial.getContentPane().add(botonInicio);
		return frameInicial;
	}

	private JFrame frameMapa() {
		frameMapa = new JFrame();
		frameMapa.setTitle("Censo 2022");
		frameMapa.setBounds(300, 300, 1280, 720);
		frameMapa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		viewerMapa = new JMapViewer();
		Coordinate coordenadaInicial = new Coordinate(-34.52202, -58.70002);
		viewerMapa.setDisplayPosition(coordenadaInicial, 14);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelMapa = new JPanel();
		contentPane.add(panelMapa, BorderLayout.CENTER);
		panelMapa.setLayout(new GridLayout(0, 1, 0, 0));

		frameMapa.add(contentPane);

		panelMapa.add(viewerMapa);

		JButton botonUbicacionMapa = new JButton("Ubicar en mapa");
		botonUbicacionMapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionBoton("Ubicar");
			}
		});

		botonUbicacionMapa.setBounds(10, 647, 110, 23);
		viewerMapa.add(botonUbicacionMapa);

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

	private void accionBoton(String boton) {
		switch (boton) {
		case "Comenzar": {
			buscarArchivo();
			frameMapa();
			frameMapa.setVisible(true);
			frameInicial.setVisible(false);
			break;
		}
		case "Ubicar": {
			ubicarEnMapa();
		}
		}
	}

	public String buscarArchivo() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setFileFilter(new FileNameExtensionFilter(".geojson","geojson"));
		chooser.setDialogTitle("Importar archivo con radio censal");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			//Le enviamos el path como string al controlador.
			return chooser.getSelectedFile().toString();
		} else {
			return "";
		}
	}
	
	private void ubicarEnMapa() {
		Coordinate uno = new Coordinate(-34.536196, -58.712407);
		Coordinate dos = new Coordinate(-34.535595, -58.711796);
		Coordinate tres = new Coordinate(-34.536275, -58.710852);
		Coordinate cuatro = new Coordinate(-34.536841, -58.711452);

		List<Coordinate> listaPuntos = new ArrayList<Coordinate>(Arrays.asList(uno, dos, tres, cuatro));

		MapPolygonImpl rectangulo = new MapPolygonImpl(listaPuntos);

		viewerMapa.addMapPolygon(rectangulo);

		rectangulo.setColor(Color.RED);
		rectangulo.setBackColor(Color.GREEN);
	}
}
