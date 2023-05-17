import conexionBaseDatos.Conexion;
import panel_inicio.Panel_inicio;

public class MainPrincipal {

	public static void main(String[] args) {
		Conexion c = new Conexion();
		boolean conexion = c.conectar();
		if (conexion) {
			System.out.println("Conectado.");
			Panel_inicio p = new Panel_inicio();
		} else {
			System.out.println("No hay conexion");
			System.out.println("No hay conexion");
		}
	}

}
