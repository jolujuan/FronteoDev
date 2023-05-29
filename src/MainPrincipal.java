import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import panel_inicio.Panel_inicio;

public class MainPrincipal {

	public static void main(String[] args) {

		// Fase de pruebas, con esto podremos cambiar la aparincia de la interfaz por
		// una que está incluida en nuestras bibliotecas

		String  lookAndFeel = "com.formdev.flatlaf.FlatLightLaf";
//        String lookAndFeel = "com.formdev.flatlaf.FlatDarkLaf";

		try {

			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.out.println("dsfds");
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
		}
		
		Panel_inicio p = new Panel_inicio();

	}
}
