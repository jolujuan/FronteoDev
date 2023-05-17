import conexionBaseDatos.Conexion;
import panel_inicio.Panel_inicio;

public class MainPrincipal {

	public static void main(String[] args) {
		Conexion conexion = new Conexion();
		boolean comprobaConexion = conexion.conectar();
		
		if (comprobaConexion) {
			System.out.println("Conectado.");
			Panel_inicio panelInicio = new Panel_inicio();
		} else {
			System.out.println("No hay conexion");
		}
		
	}
}
