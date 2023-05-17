package conexionBaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	//private Connection conexion;

	private static final String URL = "jdbc:mysql://ticsimarro.org:3306/1daw04_pro";
	private static final String USERNAME = "1daw04_pro";
	private static final String PASSWORD = "V8o1wKVgqV";
	
//	public boolean conectar() {
//		
//		try {
//			conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			return true;
//		} catch (SQLException e) {
//			System.out.println("Error: " + e);
//			return false;
//		}
//	}
	
	public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }
	

}
