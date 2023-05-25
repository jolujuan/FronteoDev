package conexionBaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	//private Connection conexion;

	// 192.168.14.200
	private static final String URL = "jdbc:mysql://ticsimarro.org:3306/1daw04_pro";
	private static final String USERNAME = "1daw04_pro";
	private static final String PASSWORD = "V8o1wKVgqV";
	private static final String URL_LOCAL ="jdbc:mysql://192.168.14.200:3306/1daw04_pro";
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
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }
	public static Connection obtenerConexionLocal() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL_LOCAL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }
	

}
