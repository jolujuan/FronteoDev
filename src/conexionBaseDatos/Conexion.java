package conexionBaseDatos;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

public class Conexion {

	// private Connection conexion;

	// 192.168.14.200
	private static final String URL = "jdbc:mysql://ticsimarro.org:3306/1daw04_pro";
	private static final String USERNAME = "1daw04_pro";
	private static final String PASSWORD = "V8o1wKVgqV";
	// private static final String URL
	// ="jdbc:mysql://192.168.14.200:3306/1daw04_pro";
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
		Enumeration e;
		try {
			conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			try {
				e = NetworkInterface.getNetworkInterfaces();
				while (e.hasMoreElements()) {
					NetworkInterface n = (NetworkInterface) e.nextElement();
					Enumeration ee = n.getInetAddresses();
					while (ee.hasMoreElements()) {
						InetAddress i = (InetAddress) ee.nextElement();
						System.out.println(i.getHostAddress());
					}
				}
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.out.println("Adreça IP local: ");
			try {
				System.out.println(Inet4Address.getLocalHost().getHostAddress());
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}

			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establir la connexió
			// local
//			String urlBaseDades = "http://ticsimarro.org/phpmyadmin/index.php";
			// remot
			 String urlBaseDades = "jdbc:mysql://ticsimarro.org:3306/1daw04_pro";

			String usuari = "1daw04_pro";
			String contrasenya = "V8o1wKVgqV";
			Connection c = DriverManager.getConnection(urlBaseDades, usuari, contrasenya);

			// Enviar una sentència SQL per recuperar els clients
			Statement s = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet r = s.executeQuery("SELECT * FROM alumne");
			// mentre hi haja alguna fila en el resultat
			while (r.next()) {
				// mostra els camps seleccionats
				System.out.println("Nom: " + r.getString("nom") + ", Cognoms: " + r.getString("cognoms"));
			}

			// Tancar la connexió
			c.close();
		} catch (ClassNotFoundException | SQLException e2) {
			e2.printStackTrace();
		}

		return conexion;
	}

}
