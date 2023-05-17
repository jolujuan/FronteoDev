package registro;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import conexionBaseDatos.Conexion;

public class Registro extends JFrame {

	private JPanel contentPanel = new JPanel();
	private JLabel etiqueta_Vacia = new JLabel("");
	private JLabel etiquetaTitulo = new JLabel("Formulario Registro", SwingConstants.CENTER);
	private JLabel mensajeGeneral = new JLabel();
	private JPanel panel_titulo = new JPanel();

	private JLabel etiqueta_Nombre = new JLabel("Nombre:");
	private JTextField campoNombre = new JTextField(20);
	private JLabel errorNombre = new JLabel();
	private JPanel panel_nombre = new JPanel();

	private JLabel etiqueta_Apellido = new JLabel("Apellidos:");
	private JTextField campoApellido = new JTextField(20);
	private JLabel errorApellido = new JLabel();
	private JPanel panel_apellido = new JPanel();

	private JLabel etiqueta_Correo = new JLabel("Correo:");
	private JTextField campoCorreo = new JTextField(20);
	private JLabel errorCorreo = new JLabel();
	private JPanel panel_correo = new JPanel();

	private JLabel etiqueta_Poblacion = new JLabel("Poblacion:");
	private JTextField campoPoblacion = new JTextField(20);
	private JLabel errorPoblacion = new JLabel();
	private JPanel panel_poblacion = new JPanel();

	private JLabel etiqueta_imagen = new JLabel("Imagen:");
	private JTextField campoImagen = new JTextField(20);
	private JLabel errorImagen = new JLabel();
	private JPanel panel_imagen = new JPanel();

	private JLabel etiqueta_password = new JLabel("Contraseña:");
	private JPasswordField campoPassword = new JPasswordField(20);
	private JLabel errorPassword = new JLabel();
	private JPanel panel_password = new JPanel();

	private JButton botonCerrar = new JButton("Cerrar");
	private JButton botonResetear = new JButton("Reset");
	private JButton botonRetgistrar = new JButton("Registrar");
	private JPanel panel_botones = new JPanel();

	public Registro() {
		setTitle("Registro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
//
//		// Crear el panel de registro
		contentPanel.setLayout(new GridLayout(8, 4));

		panel_titulo.setLayout(new GridLayout(0, 3));
		panel_titulo.add(etiqueta_Vacia);
		panel_titulo.add(etiquetaTitulo);
		panel_titulo.add(mensajeGeneral);
		contentPanel.add(panel_titulo);

		panel_nombre.setLayout(new GridLayout(0, 3));
		panel_nombre.add(etiqueta_Nombre);
		panel_nombre.add(campoNombre);
		panel_nombre.add(errorNombre);
		contentPanel.add(panel_nombre);

		panel_apellido.setLayout(new GridLayout(0, 3));
		panel_apellido.add(etiqueta_Apellido);
		panel_apellido.add(campoApellido);
		panel_apellido.add(errorApellido);
		contentPanel.add(panel_apellido);

		panel_correo.setLayout(new GridLayout(0, 3));
		panel_correo.add(etiqueta_Correo);
		panel_correo.add(campoCorreo);
		panel_correo.add(errorCorreo);
		contentPanel.add(panel_correo);

		panel_poblacion.setLayout(new GridLayout(0, 3));
		panel_poblacion.add(etiqueta_Poblacion);
		panel_poblacion.add(campoPoblacion);
		panel_poblacion.add(errorPoblacion);
		contentPanel.add(panel_poblacion);

		panel_imagen.setLayout(new GridLayout(0, 3));
		panel_imagen.add(etiqueta_imagen);
		panel_imagen.add(campoImagen);
		panel_imagen.add(errorImagen);
		contentPanel.add(panel_imagen);

		panel_password.setLayout(new GridLayout(0, 3));
		panel_password.add(etiqueta_password);
		panel_password.add(campoPassword);
		panel_password.add(errorPassword);
		contentPanel.add(panel_password);

		panel_botones.setLayout(new GridLayout(0, 3));
		panel_botones.add(botonCerrar);
		panel_botones.add(botonResetear);
		panel_botones.add(botonRetgistrar);
		contentPanel.add(panel_botones);

		botonCerrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		botonResetear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				campoNombre.setText(null);
				campoApellido.setText(null);
				campoCorreo.setText(null);
				campoPoblacion.setText(null);
				campoImagen.setText(null);
				campoPassword.setText(null);

				errorNombre.setText(null);
				errorApellido.setText(null);
				errorCorreo.setText(null);
				errorPoblacion.setText(null);
				errorImagen.setText(null);
				errorPassword.setText(null);

				mensajeGeneral.setText(null);
			}
		});

		botonRetgistrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int camposCompletados = 0;
				String nombre = "", apellido = "", email = "", poblacion = "", imagen = "", password = "";
				if (campoNombre.getText().isEmpty()) {
					errorNombre.setText("El campo no puede estar vacio.");
					errorNombre.setForeground(Color.red);
				} else {
					nombre = campoNombre.getText();
					errorNombre.setText("");
					camposCompletados++;
				}
				if (campoApellido.getText().isEmpty()) {
					errorApellido.setText("El campo no puede estar vacio.");
					errorApellido.setForeground(Color.red);
				} else {
					apellido = campoApellido.getText();
					errorApellido.setText("");
					camposCompletados++;
				}
				if (campoCorreo.getText().isEmpty()) {
					errorCorreo.setText("El campo no puede estar vacio.");
					errorCorreo.setForeground(Color.red);
				} else {
					email = campoCorreo.getText();
					errorCorreo.setText("");
					camposCompletados++;
				}
				if (campoPoblacion.getText().isEmpty()) {
					errorPoblacion.setText("El campo no puede estar vacio.");
					errorPoblacion.setForeground(Color.red);
				} else {
					poblacion = campoPoblacion.getText();
					errorPoblacion.setText("");
					camposCompletados++;
				}
				if (campoImagen.getText().isEmpty()) {
					errorImagen.setText("El campo no puede estar vacio.");
					errorImagen.setForeground(Color.red);
				} else {
					imagen = campoImagen.getText();
					errorImagen.setText("");
					camposCompletados++;
				}
				if (campoPassword.getText().isEmpty()) {
					errorPassword.setText("El campo no puede estar vacio.");
					errorPassword.setForeground(Color.red);

				} else {
//					if (!(campoCiclo.getText().equals("DAW") || campoCiclo.getText().equals("DAM")
//							|| campoCiclo.getText().equals("ASIX") || campoCiclo.getText().equals("SMX"))) {
//						errorCiclo.setText("SOLO VALIDAS: DAW, DAM, ASIX, SMX");
//						errorCiclo.setForeground(Color.red);
//					} else {
//						ciclo = campoCiclo.getText();
//						errorCiclo.setText("");
					camposCompletados++;
				}

				if (camposCompletados == 6) {

					Connection c = Conexion.obtenerConexion();

					String sentenciaCrearTablaUsuario = "CREATE TABLE IF NOT EXISTS usuarios (id INT AUTO_INCREMENT PRIMARY KEY, "
							+ "nombre VARCHAR(50), " + "apellidos VARCHAR(50), " + "imagen VARCHAR(50), "
							+ "poblacion VARCHAR(50), " + "email VARCHAR(50))";
					String sentenciaCrearTablaPassword = "CREATE TABLE IF NOT EXISTS passwords (id INT AUTO_INCREMENT PRIMARY KEY, "
							+ "idUsuario INT, " + "password VARCHAR(100), "
							+ "FOREIGN KEY (idUsuario) REFERENCES usuarios(id))";

					try {
						Statement consulta = c.createStatement();
						consulta.execute(sentenciaCrearTablaUsuario);
						consulta.close();

						consulta = c.createStatement();
						consulta.execute(sentenciaCrearTablaPassword);
						consulta.close();
					} catch (SQLException e1) {
						System.out.println("Error: " + e1);
					}

					try {
						System.out.println("Aqui pondremos codigo para subir los datos a la base de datos");

						// System.out.println(nombre + apellido + poblacion + imagen + correo + password
						// + c);
						registrarUsuario(nombre, apellido, poblacion, imagen, email, password, c);
						mensajeGeneral.setText("Datos Guardados");
						mensajeGeneral.setForeground(Color.green);
					} catch (Exception e2) {
						System.out.println("Error: " + e2);
					}
				} else {
					mensajeGeneral.setText("Error en los datos");
					mensajeGeneral.setForeground(Color.red);
				}

			}
		});

		setContentPane(contentPanel);
	}

	public void registrarUsuario(String nombre, String apellido, String imagen, String poblacion, String email,
			String password, Connection conexion) {
		String insertUsuarios = "INSERT INTO usuarios (nombre, apellidos, imagen, poblacion, email) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement preparandoInsert = conexion.prepareStatement(insertUsuarios);
			preparandoInsert.setString(1, nombre);
			preparandoInsert.setString(2, apellido);
			preparandoInsert.setString(3, imagen);
			preparandoInsert.setString(4, poblacion);
			preparandoInsert.setString(5, email);

			preparandoInsert.executeUpdate();

			obtenerUltimoId(conexion);
			System.out.println("Usuario de prueba registrado");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/// EDU ACUERDATE DE HACER UN REINICIO EN LA BASE DE DATOS DE LA PRIMARY KEY
		/// AUN TENGO QUE HACER ESTE INSERT
		String insertPasswords = "INSERT INTO passwords (idUsuario, password) VALUES (?, ?)";
	}

	public int obtenerUltimoId(Connection conexion) {
		String sentenciaSelect = "SELECT * FROM usuarios";
		int id = 0;

		try {
			PreparedStatement preparandoConsultaSelect = conexion.prepareStatement(sentenciaSelect);
			ResultSet consultaSelect = preparandoConsultaSelect.executeQuery();

			while (consultaSelect.next()) {
				id = consultaSelect.getInt("id");
			}
			System.out.println(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;
	}

}
