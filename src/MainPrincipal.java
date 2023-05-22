
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import panel_inicio.Panel_inicio;

public class MainPrincipal {

	public static void main(String[] args) {

		// Fase de pruebas, con esto podremos cambiar la aparincia de la interfaz por
		// una que esta incluida en nuestras bibliotecas
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		Panel_inicio p = new Panel_inicio();

	}
}
