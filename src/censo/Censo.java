package censo;

import java.awt.EventQueue;

import javax.swing.UIManager;

import controlador.Controlador;
import frontend.Frontend;

public class Censo {

	

	
	public static void main(String[] args) {
		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.print("No se puede iniciar el apartado gráfico de la aplicación\n Problema: " + e);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controlador controlador =  new Controlador();
					Frontend gui = new Frontend(controlador);
				
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
		
		
	}
	
	
	}



