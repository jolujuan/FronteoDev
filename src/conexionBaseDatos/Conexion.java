package conexionBaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private Connection conexion;
	
	public boolean conectar() {
		String url = "jdbc:mysql://ticsimarro.org:3306/1daw04_pro";
		String usuario = "1daw04_pro";
		String password = "V8o1wKVgqV";
		try {
			 conexion = DriverManager.getConnection(url, usuario, password);
			return true;
		} catch (SQLException  e) {
			System.out.println("Error: " + e);
			return false;
		}
	}

}
