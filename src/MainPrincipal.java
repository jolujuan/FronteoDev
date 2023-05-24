
import javax.swing.UIManager;

import panel_inicio.Panel_inicio;

public class MainPrincipal {

	public static void main(String[] args) {

		// Fase de pruebas, con esto podremos cambiar la aparincia de la interfaz por
		// una que esta incluida en nuestras bibliotecas
		
//		Perfil perfil = new Perfil();
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("Error al establecer el look and feel de FlatLaf");
			e.printStackTrace();
		}
 
		Panel_inicio p = new Panel_inicio();

	}
}
