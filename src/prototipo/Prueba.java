package prototipo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import modelo.georreferenciable.externo.GeoJSON;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Prueba {

	public JFrame frame;
	public JMapViewer mapa;
	public GeoJSON importarDatos;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			GeoJSON importarDatos = GeoJSON.leerJSON("src/prototipo/circuito1.geojson");
			System.out.print("que onda");
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					Prueba window = new Prueba();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Prueba() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 300, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mapa = new JMapViewer();
		Coordinate coordenadaInicial= new Coordinate(-34.52202,-58.70002);
		mapa.setDisplayPosition(coordenadaInicial,14);
		
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelMapa = new JPanel();
		contentPane.add(panelMapa, BorderLayout.CENTER);
		panelMapa.setLayout(new GridLayout(0, 1, 0, 0));
		
		frame.add(contentPane);
	
	
		
		panelMapa.add(mapa);
		
		
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Coordinate uno = new Coordinate( -34.536196,-58.712407);
				Coordinate dos = new Coordinate(-34.535595,  -58.711796);
				Coordinate tres = new Coordinate( -34.536275,  -58.710852);
				Coordinate cuatro = new Coordinate(-34.536841, -58.711452);

				
				List<Coordinate>  listaPuntos = new ArrayList<Coordinate>(Arrays.asList(uno, dos, tres, cuatro));
				
				MapPolygonImpl  rectangulo = new MapPolygonImpl(listaPuntos);
						
				mapa.addMapPolygon(rectangulo);
		//		rectangulo.setName("hola");
				rectangulo.setColor(Color.GREEN);

		//		MapMarkerDot marcador = new MapMarkerDot("hola", uno);
				
	//			mapa.addMapMarker(marcador);
				
				
			
				
			}
		});
		
		btnNewButton.setBounds(10, 647, 89, 23);
		mapa.add(btnNewButton);
		
	
	
	}
}
