import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import panel_inicio.Panel_inicio;

public class MainPrincipal {

	public static void main(String[] args) {

		// Cambiar la aparincia de la interfaz por una que está incluida en nuestras
		// bibliotecas

		String lookAndFeel = "com.formdev.flatlaf.FlatLightLaf";

		try {

			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			try {
				//Aplicar el tema por defecto si no puede cargarse bien
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
		}

		//INICIAR EL PROGRAMA CON UN SOLO MAIN
		new Panel_inicio();

	}
}
