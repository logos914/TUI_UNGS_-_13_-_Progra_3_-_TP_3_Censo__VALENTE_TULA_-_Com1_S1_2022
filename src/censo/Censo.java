package censo;

import java.awt.EventQueue;

import javax.swing.UIManager;

import controlador.Controlador;
import frontend.Frontend;

public class Censo {
	public static void main(String[] args) {
		Controlador controlador =  new Controlador();
		Frontend gui = new Frontend(controlador);
	}	
}
