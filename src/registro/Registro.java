package registro;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import conexionBaseDatos.Conexion;
import menuJuegos.Menu;
import panel_inicio.Panel_inicio;
import usuarios.User;

public class Registro extends JPanel {

	/// VARIABLES GLOBALES
	private static final int LONGITUD_SALTO = 16;
	private static final int FORTALEZA = 65536;
	private static final int LONGITUD_HASH = 64 * 8;
	private static byte[] arrayBits;

	// private JPanel contentPanel = new JPanel();
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

	private JTextField campoImagen = new JTextField(20);
	private JButton botonSeleccionarImagen = new JButton("Imagen perfil:");
	private JLabel errorImagen = new JLabel();
	private JPanel panel_imagen = new JPanel();

	private JLabel etiqueta_password = new JLabel("Contraseña:");
	private JPasswordField campoPassword = new JPasswordField(20);
	private JLabel errorPassword = new JLabel();
	private JPanel panel_password = new JPanel();

	private JLabel etiqueta_password_repetida = new JLabel("Repetir contraseña:");
	private JPasswordField campoPasswordRepetida = new JPasswordField(20);
	private JLabel errorPasswordRepetida = new JLabel();
	private JPanel panel_password_repetida = new JPanel();

	private JButton botonVolver = new JButton("Volver");
	private JButton botonResetear = new JButton("Reset");
	private JButton botonRetgistrar = new JButton("Registrar");
	private JPanel panel_botones = new JPanel();

	public Registro() {
//		setTitle("Registro");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setSize(350, 200);
//
//		// Crear el panel de registro
		setLayout(new GridLayout(9, 4));

		panel_titulo.setLayout(new GridLayout(0, 3));
		panel_titulo.add(etiqueta_Vacia);
		panel_titulo.add(etiquetaTitulo);
		panel_titulo.add(mensajeGeneral);
		add(panel_titulo);

		panel_nombre.setLayout(new GridLayout(0, 3));
		panel_nombre.add(etiqueta_Nombre);
		panel_nombre.add(campoNombre);
		panel_nombre.add(errorNombre);
		add(panel_nombre);

		panel_apellido.setLayout(new GridLayout(0, 3));
		panel_apellido.add(etiqueta_Apellido);
		panel_apellido.add(campoApellido);
		panel_apellido.add(errorApellido);
		add(panel_apellido);

		panel_correo.setLayout(new GridLayout(0, 3));
		panel_correo.add(etiqueta_Correo);
		panel_correo.add(campoCorreo);
		panel_correo.add(errorCorreo);
		add(panel_correo);

		panel_poblacion.setLayout(new GridLayout(0, 3));
		panel_poblacion.add(etiqueta_Poblacion);
		panel_poblacion.add(campoPoblacion);
		panel_poblacion.add(errorPoblacion);
		add(panel_poblacion);

		panel_imagen.setLayout(new GridLayout(0, 3));
		panel_imagen.add(botonSeleccionarImagen);
		panel_imagen.add(campoImagen);
		panel_imagen.add(errorImagen);
		add(panel_imagen);

		panel_password.setLayout(new GridLayout(0, 3));
		panel_password.add(etiqueta_password);
		panel_password.add(campoPassword);
		panel_password.add(errorPassword);
		add(panel_password);

		panel_password_repetida.setLayout(new GridLayout(0, 3));
		panel_password_repetida.add(etiqueta_password_repetida);
		panel_password_repetida.add(campoPasswordRepetida);
		panel_password_repetida.add(errorPasswordRepetida);
		add(panel_password_repetida);

		panel_botones.setLayout(new GridLayout(0, 3));
		panel_botones.add(botonVolver);
		panel_botones.add(botonResetear);
		panel_botones.add(botonRetgistrar);
		add(panel_botones);

		botonSeleccionarImagen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();

					arrayBits = convertirImagen(selectedFile);
					// etiqueta_imagen.setText(selectedFile.getName());
					campoImagen.setText(selectedFile.getName());
				} else {
					System.out.println("Ningún archivo seleccionado");
				}

			}
		});

		botonVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Panel_inicio p = (Panel_inicio) SwingUtilities.getWindowAncestor(Registro.this);
				// p.getContentPane().add(p.getContenedor());
				p.getContentPane().removeAll();
				p.getContentPane().add(p.getContenedor());
				p.revalidate();
				p.repaint();

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
				campoPasswordRepetida.setText(null);

				errorNombre.setText(null);
				errorApellido.setText(null);
				errorCorreo.setText(null);
				errorPoblacion.setText(null);
				errorImagen.setText(null);
				errorPassword.setText(null);
				errorPasswordRepetida.setText(null);

				mensajeGeneral.setText(null);
			}
		});

		botonRetgistrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int camposCompletados = 0;
				String nombre = "", apellido = "", email = "", poblacion = "", imagen = "", password = "",
						passwordRepetida = "";

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
					errorImagen.setText("");
					if (!campoImagen.getText().contains(".jpg")) {
						errorImagen.setText("Solo archivos .jpg");
						errorImagen.setForeground(Color.red);
					} else {
						imagen = campoImagen.getText();

						errorImagen.setText("");
						camposCompletados++;

					}
				}

				if (campoPassword.getText().isEmpty() || campoPasswordRepetida.getText().isEmpty()) {
					errorPassword.setText("El campo no puede estar vacio.");
					errorPassword.setForeground(Color.red);
					errorPasswordRepetida.setText("El campo no puede estar vacio.");
					errorPasswordRepetida.setForeground(Color.red);
				} else {
					password = campoPassword.getText();
					errorPassword.setText("");
					passwordRepetida = campoPasswordRepetida.getText();
					errorPasswordRepetida.setText("");
					camposCompletados++;
					if (password.equals(passwordRepetida)) {
						camposCompletados++;
						errorPassword.setText("");
						errorPasswordRepetida.setText("");
					} else {
						errorPassword.setText("La contraseña debe ser la misma");
						errorPassword.setForeground(Color.red);
						errorPasswordRepetida.setText("La contraseña debe ser la misma");
						errorPasswordRepetida.setForeground(Color.red);
					}
				}

				if (camposCompletados == 7) {

					Connection c = Conexion.obtenerConexion();

					String sentenciaCrearTablaUsuario = "CREATE TABLE IF NOT EXISTS usuarios (id INT AUTO_INCREMENT PRIMARY KEY, "
							+ "nombre VARCHAR(50), " + "apellidos VARCHAR(50), " + "imagen BLOB, "
							+ "poblacion VARCHAR(50), " + "email VARCHAR(50))";
					String sentenciaCrearTablaPassword = "CREATE TABLE IF NOT EXISTS passwords (id INT AUTO_INCREMENT PRIMARY KEY, "
							+ "idUsuario INT, " + "password VARCHAR(200), " + "salto VARCHAR(200),"  
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
						if (password.equals(passwordRepetida)) {
							camposCompletados++;
							errorPassword.setText("");
							errorPasswordRepetida.setText("");
						} else {
							errorPassword.setText("La contraseña debe ser la misma");
							errorPassword.setForeground(Color.red);
							errorPasswordRepetida.setText("La contraseña debe ser la misma");
							errorPasswordRepetida.setForeground(Color.red);
						}
					}

					try {
						registrarUsuario(nombre, apellido, poblacion, arrayBits, email, password, c);
						mensajeGeneral.setText("Datos Guardados");
						mensajeGeneral.setForeground(Color.green);
					} catch (Exception e2) {
						System.out.println("Error: " + e2);
					}
				} else {
					mensajeGeneral.setText("Error Datos/Conexion");
					mensajeGeneral.setForeground(Color.red);
				}
			}
		});
	}

	public void registrarUsuario(String nombre, String apellido, String poblacion, byte[] imagen, String email,
			String password, Connection conexion) {
		String insertUsuarios = "INSERT INTO usuarios (nombre, apellidos, imagen, poblacion, email) VALUES (?,?,?,?,?)";
		User usuario = new User(nombre, apellido, imagen, password, email, poblacion);
		String[] datosPassword = encriptarPassword(usuario.getPassword());

		try {
			PreparedStatement preparandoInsert = conexion.prepareStatement(insertUsuarios);
			preparandoInsert.setString(1, usuario.getNombre());
			preparandoInsert.setString(2, usuario.getApellidos());
			preparandoInsert.setBytes(3, usuario.getImagen());
			preparandoInsert.setString(4, usuario.getPoblacion());
			preparandoInsert.setString(5, usuario.getEmail());

			preparandoInsert.executeUpdate();

			String insertPasswords = "INSERT INTO passwords (idUsuario, password, salto) VALUES (?, ?, ?)";
			preparandoInsert = conexion.prepareStatement(insertPasswords);
			preparandoInsert.setInt(1, obtenerUltimoId(conexion));
			preparandoInsert.setString(2, datosPassword[0]);
			preparandoInsert.setString(3, datosPassword[1]);

			preparandoInsert.executeUpdate();

			System.out.println("Usuario de prueba registrado");
			
			
			Menu menuJuegos = new Menu();
			removeAll();
			//add(menuJuegos);
			
			revalidate();
			repaint();
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		/// EDU ACUERDATE DE HACER UN REINICIO EN LA BASE DE DATOS DE LA PRIMARY KEY
		/// AUN TENGO QUE HACER ESTE INSERT

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public String[] encriptarPassword(String passWord) {
		String[] datosPasswords = new String[2]; 
		byte[] salto = null;
		String passwordEncriptada = "", saltoPassword = "";
		
		try {
			SecureRandom random = new SecureRandom();
			salto = new byte[LONGITUD_SALTO];
			random.nextBytes(salto);

			KeySpec spec = new PBEKeySpec(passWord.toCharArray(), salto, FORTALEZA, LONGITUD_HASH);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			byte[] hash = factory.generateSecret(spec).getEncoded();
			passWord = Base64.getEncoder().encodeToString(hash);
			
			saltoPassword = conversionSalto(salto);
			passwordEncriptada = FORTALEZA + saltoPassword + LONGITUD_HASH + passWord;
			
			datosPasswords[0] = passwordEncriptada;
			datosPasswords[1] = saltoPassword;
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

		return datosPasswords;
	}

	public String conversionSalto(byte[] salto) {
		String saltosTexto = "";
		for (int i = 0; i < salto.length; i++) {
			// PASAMOS LA CONVERSION DE BYTES A CADENA
			saltosTexto += String.format("%02x", salto[i]);
		}
		return saltosTexto;
	}

	public byte[] convertirImagen(File imagen) {
		byte[] fileBytes = new byte[(int) imagen.length()];
		try {
			FileInputStream fis = new FileInputStream(imagen);

			fis.read(fileBytes);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		return fileBytes;
	}
	


}
