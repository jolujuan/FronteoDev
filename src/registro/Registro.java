package registro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

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

	private JLabel etiqueta_Nombre = new JLabel("Nombre:", SwingConstants.CENTER);
	private JTextField campoNombre = new JTextField(20);
	private JLabel errorNombre = new JLabel();
	private JPanel panel_nombre = new JPanel();

	private JLabel etiqueta_Apellido = new JLabel("Apellidos:", SwingConstants.CENTER);
	private JTextField campoApellido = new JTextField(20);
	private JLabel errorApellido = new JLabel();
	private JPanel panel_apellido = new JPanel();

	private JLabel etiqueta_Correo = new JLabel("Correo:", SwingConstants.CENTER);
	private JTextField campoCorreo = new JTextField(20);
	private JLabel errorCorreo = new JLabel();
	private JPanel panel_correo = new JPanel();

	private JLabel etiqueta_Poblacion = new JLabel("Poblacion:", SwingConstants.CENTER);
	private JTextField campoPoblacion = new JTextField(20);
	private JLabel errorPoblacion = new JLabel();
	private JPanel panel_poblacion = new JPanel();

	private JFileChooser fileChooser = new JFileChooser();
	private File selectedFile = fileChooser.getSelectedFile();
	private JTextField campoImagen = new JTextField(20);
	private JButton botonSeleccionarImagen = new JButton("Imagen perfil:");
	private JLabel errorImagen = new JLabel();
	private JPanel panel_imagen = new JPanel();

	private JLabel etiqueta_password = new JLabel("Contraseña:", SwingConstants.CENTER);
	private JPasswordField campoPassword = new JPasswordField(20);
	private JLabel errorPassword = new JLabel();
	private JPanel panel_password = new JPanel();

	private JLabel etiqueta_password_repetida = new JLabel("Repetir contraseña:", SwingConstants.CENTER);
	private JPasswordField campoPasswordRepetida = new JPasswordField(20);
	private JLabel errorPasswordRepetida = new JLabel();
	private JPanel panel_password_repetida = new JPanel();

	private JButton botonVolver = new JButton("Volver");
	private JButton botonResetear = new JButton("Reset");
	private JButton botonRetgistrar = new JButton("Registrar");
	private JPanel panel_botones = new JPanel();

	public Registro() {
		setBorder(new EmptyBorder(10, 10, 10, 10));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		panel_titulo.setBorder(new EmptyBorder(15, 15, 15, 15));

		panel_titulo.setLayout(new GridLayout(0, 3));
		panel_titulo.add(etiqueta_Vacia);
		etiquetaTitulo.setFont(new Font("Dialog", Font.BOLD, 16));
		panel_titulo.add(etiquetaTitulo);
		panel_titulo.add(mensajeGeneral);
		add(panel_titulo);

		panel_nombre.setLayout(new GridLayout(0, 3));
		etiqueta_Nombre.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_nombre.add(etiqueta_Nombre);
		campoNombre.setHorizontalAlignment(SwingConstants.CENTER);
		campoNombre.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_nombre.add(campoNombre);
		panel_nombre.add(errorNombre);
		add(panel_nombre);
		panel_nombre.setBorder(new EmptyBorder(4, 4, 4, 4));

		panel_apellido.setLayout(new GridLayout(0, 3));
		etiqueta_Apellido.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_apellido.add(etiqueta_Apellido);
		campoApellido.setHorizontalAlignment(SwingConstants.CENTER);
		campoApellido.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_apellido.add(campoApellido);
		panel_apellido.add(errorApellido);
		add(panel_apellido);
		panel_apellido.setBorder(new EmptyBorder(4, 4, 4, 4));

		panel_correo.setLayout(new GridLayout(0, 3));
		etiqueta_Correo.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_correo.add(etiqueta_Correo);
		campoCorreo.setHorizontalAlignment(SwingConstants.CENTER);
		campoCorreo.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_correo.add(campoCorreo);
		panel_correo.add(errorCorreo);
		add(panel_correo);
		panel_correo.setBorder(new EmptyBorder(4, 4, 4, 4));

		panel_poblacion.setLayout(new GridLayout(0, 3));
		etiqueta_Poblacion.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_poblacion.add(etiqueta_Poblacion);
		campoPoblacion.setHorizontalAlignment(SwingConstants.CENTER);
		campoPoblacion.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_poblacion.add(campoPoblacion);
		panel_poblacion.add(errorPoblacion);
		add(panel_poblacion);
		panel_poblacion.setBorder(new EmptyBorder(4, 4, 4, 4));

		panel_imagen.setLayout(new GridLayout(0, 3));
		botonSeleccionarImagen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonSeleccionarImagen.setMargin(new Insets(0, 0, 0, 0));
		botonSeleccionarImagen.setPreferredSize(new Dimension(20, botonSeleccionarImagen.getPreferredSize().height));

		panel_imagen.add(botonSeleccionarImagen);

		campoImagen.setHorizontalAlignment(SwingConstants.CENTER);
		campoImagen.setFont(new Font("Dialog", Font.PLAIN, 13));
		campoImagen.setEnabled(false);
		panel_imagen.add(campoImagen);
		panel_imagen.add(errorImagen);
		add(panel_imagen);
		panel_imagen.setBorder(new EmptyBorder(4, 4, 4, 4));

		panel_password.setLayout(new GridLayout(0, 3));
		etiqueta_password.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_password.add(etiqueta_password);
		campoPassword.setHorizontalAlignment(SwingConstants.CENTER);
		campoPassword.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_password.add(campoPassword);
		panel_password.add(errorPassword);
		add(panel_password);
		panel_password.setBorder(new EmptyBorder(4, 4, 4, 4));

		panel_password_repetida.setLayout(new GridLayout(0, 3));
		etiqueta_password_repetida.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_password_repetida.add(etiqueta_password_repetida);
		campoPasswordRepetida.setHorizontalAlignment(SwingConstants.CENTER);
		campoPasswordRepetida.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_password_repetida.add(campoPasswordRepetida);
		panel_password_repetida.add(errorPasswordRepetida);
		add(panel_password_repetida);
		panel_password_repetida.setBorder(new EmptyBorder(4, 4, 4, 4));

		panel_botones.setLayout(new GridLayout(0, 3, 15, 0));
		botonVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonVolver.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_botones.add(botonVolver);
		botonResetear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonResetear.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_botones.add(botonResetear);
		botonRetgistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonRetgistrar.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_botones.add(botonRetgistrar);
		add(panel_botones);
		panel_botones.setBorder(new EmptyBorder(20, 10, 10, 10));

		botonSeleccionarImagen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					System.out.println(selectedFile.exists());

					if (!selectedFile.exists()) {
						errorImagen.setText("Archivo no existente");
						errorImagen.setHorizontalAlignment(SwingConstants.CENTER);
						errorImagen.setForeground(Color.red);
					} else {
						if (selectedFile.length() <= 64 * 1024) {
							errorImagen.setText("");
							// arrayBits = convertirImagen(selectedFile);
							arrayBits = converitirImagenByte(selectedFile);
							campoImagen.setText(selectedFile.getName());
							
						} else {
							errorImagen.setText("Tamaño máximo 64KB.");
							campoImagen.setText("");
							errorImagen.setHorizontalAlignment(SwingConstants.CENTER);
							errorImagen.setForeground(Color.red);
						}
					}
					// Comprobar que la imagen seleccionada sea del tamaño correcto

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
				selectedFile = null;

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
					errorNombre.setHorizontalAlignment(SwingConstants.CENTER);
				} else {
					nombre = campoNombre.getText();
					errorNombre.setText("");
					camposCompletados++; // 1
				}
				if (campoApellido.getText().isEmpty()) {
					errorApellido.setText("El campo no puede estar vacio.");
					errorApellido.setForeground(Color.red);
					errorApellido.setHorizontalAlignment(SwingConstants.CENTER);
				} else {
					apellido = campoApellido.getText();
					errorApellido.setText("");
					camposCompletados++;// 2
				}

				if (campoCorreo.getText().isEmpty()) {
					errorCorreo.setText("El campo no puede estar vacio.");
					errorCorreo.setForeground(Color.red);
					errorCorreo.setHorizontalAlignment(SwingConstants.CENTER);

				} else if (!Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$").matcher(campoCorreo.getText())
						.find()) {
					errorCorreo.setText("El correo introducido no es correcto.");
					errorCorreo.setForeground(Color.red);
					errorCorreo.setHorizontalAlignment(SwingConstants.CENTER);

				} else {

					Connection c = Conexion.obtenerConexion();
					if (!existeTabla("usuarios")) {
						crearTablas();
					}

					try {
						// Obtener los correos de la base de datos
						String correosListados = "SELECT email FROM usuarios";
						PreparedStatement comprobarCorreos = c.prepareStatement(correosListados);
						ResultSet resultado = comprobarCorreos.executeQuery();
						boolean comproba = false;

						while (resultado.next()) {
							// Comprobar que no exista coincidencia con el campo introducido
							// Si es asi cambiar el semaforo ha acertado
							if (resultado.getString("email").equals(campoCorreo.getText())) {
								errorCorreo.setText("El correo introducido ya existe.");
								errorCorreo.setForeground(Color.red);
								errorCorreo.setHorizontalAlignment(SwingConstants.CENTER);
								comproba = true;
							}
						}

						comprobarCorreos.close();
						resultado.close();

						if (!comproba) {
							email = campoCorreo.getText();
							errorCorreo.setText("");
							camposCompletados++;// 3

						}

					} catch (Exception e2) {
						System.out.println(e2);
					} finally {
						try {
							c.close();
						} catch (Exception e3) {
							System.out.println("Error tancant connexió comproba correu " + e3);
						}
					}
				}

				if (campoPoblacion.getText().isEmpty()) {
					errorPoblacion.setText("El campo no puede estar vacio.");
					errorPoblacion.setForeground(Color.red);
					errorPoblacion.setHorizontalAlignment(SwingConstants.CENTER);

				} else {
					poblacion = campoPoblacion.getText();
					errorPoblacion.setText("");
					camposCompletados++;// 4

				}

				if (campoImagen.getText().isEmpty()) {
					errorImagen.setText("El campo no puede estar vacio.");
					errorImagen.setForeground(Color.red);
					errorImagen.setHorizontalAlignment(SwingConstants.CENTER);

				} else {
					imagen = campoImagen.getText();
					System.out.println("regex "+imagen.matches("^.+\\.(jpg|png)$"));
					if (imagen.matches("^.+\\.(jpg|png)$")) {
						imagen = campoImagen.getText();
						errorImagen.setText("");
						camposCompletados++;// 5
						System.out.println("Cumple el regex");
					} else {
						imagen = "";
						
						errorImagen.setText("Archivo imagen incorrecto");
						errorImagen.setForeground(Color.red);
						
					}
				}

				if (campoPassword.getText().isEmpty() || campoPasswordRepetida.getText().isEmpty()) {
					errorPassword.setText("El campo no puede estar vacio.");
					errorPassword.setHorizontalAlignment(SwingConstants.CENTER);
					errorPassword.setForeground(Color.red);

					errorPasswordRepetida.setText("El campo no puede estar vacio.");
					errorPasswordRepetida.setHorizontalAlignment(SwingConstants.CENTER);
					errorPasswordRepetida.setForeground(Color.red);
				} else {
					password = campoPassword.getText();
					errorPassword.setText("");
					passwordRepetida = campoPasswordRepetida.getText();
					errorPasswordRepetida.setText("");
					camposCompletados++;// 6

					if (password.length() < 5 || passwordRepetida.length() < 5) {

						errorPassword.setText("Minimo 5 caracteres");
						errorPassword.setForeground(Color.red);
						errorPassword.setHorizontalAlignment(SwingConstants.CENTER);

						errorPasswordRepetida.setText("Minimo 5 caracteres");
						errorPasswordRepetida.setForeground(Color.red);
						errorPasswordRepetida.setHorizontalAlignment(SwingConstants.CENTER);
					} else {
						if (password.equals(passwordRepetida)) {
							camposCompletados++;// 7
							errorPassword.setText("");
							errorPasswordRepetida.setText("");
						} else {
							errorPassword.setText("La contraseña debe ser la misma");
							errorPassword.setForeground(Color.red);
							errorPassword.setHorizontalAlignment(SwingConstants.CENTER);

							errorPasswordRepetida.setText("La contraseña debe ser la misma");
							errorPasswordRepetida.setForeground(Color.red);
							errorPasswordRepetida.setHorizontalAlignment(SwingConstants.CENTER);
						}
					}

				}

				// SI AUN NO HAY TABLAS CREADAS PONER -> if(camposCompletados == 6) y comentar
				// las lineas de comprobacion del correo
				if (camposCompletados == 7) {
					// crearTablas();
//					Connection c = Conexion.obtenerConexion();
//
//					String sentenciaCrearTablaUsuario = "CREATE TABLE IF NOT EXISTS usuarios (id INT AUTO_INCREMENT PRIMARY KEY, "
//							+ "nombre VARCHAR(50), " + "apellidos VARCHAR(50), " + "imagen BLOB, "
//							+ "poblacion VARCHAR(50), " + "email VARCHAR(50))";
//					String sentenciaCrearTablaPassword = "CREATE TABLE IF NOT EXISTS passwords (id INT AUTO_INCREMENT PRIMARY KEY, "
//							+ "idUsuario INT, " + "password VARCHAR(200), " + "salto VARCHAR(200),"
//							+ "FOREIGN KEY (idUsuario) REFERENCES usuarios(id))";
//					String sentenciaCrearTablaPixelArt = "CREATE TABLE IF NOT EXISTS pixelart (idPixelart INT AUTO_INCREMENT PRIMARY KEY, "
//							+ "idUsuario INT, " + "tablero VARCHAR(50), " + "ficheroPartida MEDIUMBLOB, "
//							+ "fecha DATE, " + "FOREIGN KEY (idUsuario) REFERENCES usuarios(id))";
//					String sentenciaCrearTablaBuscaMinas = "CREATE TABLE IF NOT EXISTS buscaminas (idBuscaminas INT AUTO_INCREMENT PRIMARY KEY, "
//							+ "idUsuario INT, " + "tablero VARCHAR(50), " + "nivel VARCHAR(50), "
//							+ "ficheroPartida MEDIUMBLOB, " + "tiempo INT, "
//							+ "FOREIGN KEY (idUsuario) REFERENCES usuarios(id))";
//
//					try {
//						Statement consulta = c.createStatement();
//						consulta.execute(sentenciaCrearTablaUsuario);
//						consulta.close();
//						System.out.println("Tabla usarios creada");
//
//						consulta = c.createStatement();
//						consulta.execute(sentenciaCrearTablaPassword);
//						consulta.close();
//						System.out.println("Tabla password creada");
//
//						consulta = c.createStatement();
//						consulta.execute(sentenciaCrearTablaPixelArt);
//						consulta.close();
//						System.out.println("Tabla pixelArt creada");
//
//						consulta = c.createStatement();
//						consulta.execute(sentenciaCrearTablaBuscaMinas);
//						consulta.close();
//						System.out.println("Tabla buscaminas creada");
//
//					} catch (SQLException e1) {
//						System.out.println("Error: A" + e1);
//						if (password.equals(passwordRepetida)) {
//							camposCompletados++;
//							errorPassword.setText("");
//							errorPasswordRepetida.setText("");
//						} else {
//							errorPassword.setText("La contraseña debe ser la misma");
//							errorPassword.setForeground(Color.red);
//							errorPasswordRepetida.setText("La contraseña debe ser la misma");
//							errorPasswordRepetida.setForeground(Color.red);
//						}
//					}
					try {
						registrarUsuario(nombre, apellido, poblacion, arrayBits, email, password);
						mensajeGeneral.setText("Datos Guardados");
						mensajeGeneral.setForeground(Color.green);
					} catch (Exception e2) {
						System.out.println("Error: " + e2);
					} finally {
						try {
							// c.close();
						} catch (Exception e3) {
							System.out.println("Error tancant connexió crear taules " + e3);
						}
					}
				} else {
					mensajeGeneral.setText("Error Datos/Conexion");
					mensajeGeneral.setFont(new Font("Dialog", Font.BOLD, 16));
					mensajeGeneral.setForeground(Color.red);
					mensajeGeneral.setHorizontalAlignment(SwingConstants.CENTER);

				}
			}
		});

		setVisible(true);
	}

	public void crearTablas() {
		Connection c = Conexion.obtenerConexion();

		String sentenciaCrearTablaUsuario = "CREATE TABLE IF NOT EXISTS usuarios (id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "nombre VARCHAR(50), " + "apellidos VARCHAR(50), " + "imagen BLOB, " + "poblacion VARCHAR(50), "
				+ "email VARCHAR(50))";
		String sentenciaCrearTablaPassword = "CREATE TABLE IF NOT EXISTS passwords (id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "idUsuario INT, " + "password VARCHAR(200), " + "salto VARCHAR(200),"
				+ "FOREIGN KEY (idUsuario) REFERENCES usuarios(id))";
		String sentenciaCrearTablaPixelArt = "CREATE TABLE IF NOT EXISTS pixelart (idPixelart INT AUTO_INCREMENT PRIMARY KEY, "
				+ "idUsuario INT, " + "tablero VARCHAR(50), " + "ficheroPartida MEDIUMBLOB, " + "fecha DATE, "
				+ "FOREIGN KEY (idUsuario) REFERENCES usuarios(id))";
		String sentenciaCrearTablaBuscaMinas = "CREATE TABLE IF NOT EXISTS buscaminas (idBuscaminas INT AUTO_INCREMENT PRIMARY KEY, "
				+ "idUsuario INT, " + "tablero VARCHAR(50), " + "nivel VARCHAR(50), " + "ficheroPartida MEDIUMBLOB, "
				+ "tiempo INT, " + "FOREIGN KEY (idUsuario) REFERENCES usuarios(id))";

		try {
			Statement consulta = c.createStatement();
			consulta.execute(sentenciaCrearTablaUsuario);
			consulta.close();
			System.out.println("Tabla usarios creada");

			consulta = c.createStatement();
			consulta.execute(sentenciaCrearTablaPassword);
			consulta.close();
			System.out.println("Tabla password creada");

			consulta = c.createStatement();
			consulta.execute(sentenciaCrearTablaPixelArt);
			consulta.close();
			System.out.println("Tabla pixelArt creada");

			consulta = c.createStatement();
			consulta.execute(sentenciaCrearTablaBuscaMinas);
			consulta.close();
			System.out.println("Tabla buscaminas creada");

		} catch (SQLException e1) {
			System.out.println("Error: " + e1);
		}
	}

	public boolean existeTabla(String nombreTabla) {
		Connection conexion = Conexion.obtenerConexion();
		DatabaseMetaData metaData;
		try {
			metaData = conexion.getMetaData();
			ResultSet resultSet = metaData.getTables(null, null, nombreTabla, null);
			return resultSet.next();
		} catch (SQLException e) {
			System.out.println("Error: " + e);
			return false;
		}

	}

	public void registrarUsuario(String nombre, String apellido, String poblacion, byte[] imagen, String email,
			String password) {
		Connection conexion = Conexion.obtenerConexion();
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

			preparandoInsert.close();

			Panel_inicio p = (Panel_inicio) SwingUtilities.getWindowAncestor(Registro.this);

			Menu menuJuegos = new Menu(usuario.getEmail());
			p.getContentPane().removeAll();
			p.getContentPane().setLayout(new BorderLayout());

			p.getContentPane().add(menuJuegos);
			p.revalidate();
			p.repaint();

		} catch (SQLException e) {
			e.printStackTrace();
		}
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

			preparandoConsultaSelect.close();
			consultaSelect.close();
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

//	public byte[] convertirImagen(File imagen) {
//		byte[] fileBytes = new byte[(int) imagen.length()];
//		return fileBytes;
//	}

	public byte[] converitirImagenByte(File imagen) {
		byte[] imagenBytes = null;

		try {
			FileInputStream fis = new FileInputStream(imagen);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			imagenBytes = bos.toByteArray();
			fis.close();
			bos.close();
			return imagenBytes;

		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return imagenBytes;

	}

}
