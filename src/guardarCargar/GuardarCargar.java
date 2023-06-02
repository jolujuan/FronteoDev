package guardarCargar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import conexionBaseDatos.Conexion;
import juegos.PixelArt;

public class GuardarCargar extends JFrame {

	private JPanel contentPane;
	JButton botonTableroPequeño = new JButton();
	JButton botonTableroMediano = new JButton();
	JButton botonTableroGrande = new JButton();

	JPanel contenidoPequeño = new JPanel();
	JPanel contenidoMediano = new JPanel();
	JPanel contenidoGrande = new JPanel();

	// Ventana principal a la que le pasamos ventana pixelArt o buscaminas
	// Otra ventana interna para obtener y cerrar componenetes
	private JFrame ventanaJuego;
	private JFrame frame;
	private Path nuevoArchivo = null;

	// VARIABLE PARA SABER SI A CERRADO CON LA X
	public static boolean guardado = false;

	public static boolean getGuardado() {
		return guardado;
	}

	public static void setGuardado(boolean guardado) {
		GuardarCargar.guardado = guardado;
	}

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					String correo = "joselu@gmail.com";
//					GuardarCargar frame = new GuardarCargar(new PixelArt(correo), correo);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	// Pasar la variable para no mostrarla en las clases
	public GuardarCargar(JFrame ventana, String correo) {
		this.ventanaJuego = ventana;
		this.frame = this;

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Esbozo de método generado automáticamente
				GuardarCargar.setGuardado(true);
			};
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setTitle("Elegir Partida");
		centrarInterficiePantalla();

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panelPequeño = new JPanel();
		panelPequeño.setBorder(new MatteBorder(0, 0, 0, 3, (Color) new Color(75, 75, 75)));
		panel.add(panelPequeño);
		GridBagLayout gbl_panelPequeño = new GridBagLayout();
		gbl_panelPequeño.columnWidths = new int[] { 146, 0 };
		gbl_panelPequeño.rowHeights = new int[] { 35, 389, 0 };
		gbl_panelPequeño.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelPequeño.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelPequeño.setLayout(gbl_panelPequeño);

		JPanel tituloPequeño = new JPanel();
		tituloPequeño.setBorder(new EmptyBorder(10, 10, 0, 10));
		GridBagConstraints gbc_tituloPequeño = new GridBagConstraints();
		gbc_tituloPequeño.fill = GridBagConstraints.CENTER;
		gbc_tituloPequeño.insets = new Insets(0, 0, 5, 0);
		gbc_tituloPequeño.gridx = 0;
		gbc_tituloPequeño.gridy = 0;
		panelPequeño.add(tituloPequeño, gbc_tituloPequeño);

		JLabel labelPequenio = new JLabel("FÁCIL");
		labelPequenio.setFont(new Font("Serif", Font.BOLD, 24)); 
		tituloPequeño.add(labelPequenio);
		contenidoPequeño.setFont(new Font("Dialog", Font.PLAIN, 12));

		// Creamos el scroll panel
		JScrollPane scrollPanePequeño = new JScrollPane(contenidoPequeño);
		scrollPanePequeño.setBorder(null);
		// Cambiar el comportamiento del scroll para desplazar más lineas
		scrollPanePequeño.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPanePequeño.getVerticalScrollBar().setUnitIncrement(5);

		GridBagConstraints gbc_contenidoPequeño = new GridBagConstraints();
		gbc_contenidoPequeño.fill = GridBagConstraints.BOTH;
		gbc_contenidoPequeño.gridx = 0;
		gbc_contenidoPequeño.gridy = 1;
		// Añadimos el scroll panel aqui
		panelPequeño.add(scrollPanePequeño, gbc_contenidoPequeño);

		contenidoPequeño.setLayout(new BoxLayout(contenidoPequeño, BoxLayout.Y_AXIS));

		botonTableroPequeño.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		botonTableroPequeño.setFont(new Font("Dialog", Font.PLAIN, 14));
		botonTableroPequeño.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JPanel panelMediano = new JPanel();
		panel.add(panelMediano);
		GridBagLayout gbl_panelMediano = new GridBagLayout();
		gbl_panelMediano.columnWidths = new int[] { 146, 0 };
		gbl_panelMediano.rowHeights = new int[] { 35, 389 };
		gbl_panelMediano.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelMediano.rowWeights = new double[] { 0.0, 1.0  };
		panelMediano.setLayout(gbl_panelMediano);

		JPanel tituloMediano = new JPanel();
		tituloMediano.setBorder(new EmptyBorder(10, 10, 0, 10));
		GridBagConstraints gbc_tituloMediano = new GridBagConstraints();
		gbc_tituloMediano.fill = GridBagConstraints.BOTH;
		gbc_tituloMediano.insets = new Insets(0, 0, 5, 0);
		gbc_tituloMediano.gridx = 0;
		gbc_tituloMediano.gridy = 0;
		panelMediano.add(tituloMediano, gbc_tituloMediano);

		JLabel labelMediano = new JLabel("NORMAL");
		labelMediano.setFont(new Font("Serif", Font.BOLD, 24)); 
		tituloMediano.add(labelMediano);

		JScrollPane scrollPaneMediano = new JScrollPane(contenidoMediano);
		scrollPaneMediano.setBorder(null);
		// Cambiar el comportamiento del scroll para desplazar más lineas
		scrollPaneMediano.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPaneMediano.getVerticalScrollBar().setUnitIncrement(5);

		GridBagConstraints gbc_contenidoMediano = new GridBagConstraints();
		gbc_contenidoMediano.fill = GridBagConstraints.VERTICAL;
		gbc_contenidoMediano.gridx = 0;
		gbc_contenidoMediano.gridy = 1;
		panelMediano.add(scrollPaneMediano, gbc_contenidoMediano);
		contenidoMediano.setLayout(new BoxLayout(contenidoMediano, BoxLayout.Y_AXIS));

		botonTableroMediano.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		botonTableroMediano.setFont(new Font("Dialog", Font.PLAIN, 14));
		botonTableroMediano.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JPanel panelGrande = new JPanel();
		panelGrande.setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(75, 75, 75)));
		panel.add(panelGrande);
		GridBagLayout gbl_panelGrande = new GridBagLayout();
		gbl_panelGrande.columnWidths = new int[] { 146, 0 };
		gbl_panelGrande.rowHeights = new int[] { 35, 389, 0 };
		gbl_panelGrande.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelGrande.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelGrande.setLayout(gbl_panelGrande);

		JPanel tituloGrande = new JPanel();
		tituloGrande.setBorder(new EmptyBorder(10, 10, 0, 10));
		GridBagConstraints gbc_tituloGrande = new GridBagConstraints();
		gbc_tituloGrande.fill = GridBagConstraints.BOTH;
		gbc_tituloGrande.insets = new Insets(0, 0, 5, 0);
		gbc_tituloGrande.gridx = 0;
		gbc_tituloGrande.gridy = 0;
		panelGrande.add(tituloGrande, gbc_tituloGrande);

		JLabel labelGrande = new JLabel("DIFÍCIL");
		labelGrande.setFont(new Font("Serif", Font.BOLD, 24)); 
		tituloGrande.add(labelGrande);

		JScrollPane scrollPaneGrande = new JScrollPane(contenidoGrande);
		scrollPaneGrande.setBorder(null);
		// Cambiar el comportamiento del scroll para desplazar más lineas
		scrollPaneGrande.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPaneGrande.getVerticalScrollBar().setUnitIncrement(5);

		GridBagConstraints gbc_contenidoGrande = new GridBagConstraints();
		gbc_contenidoGrande.fill = GridBagConstraints.VERTICAL;
		gbc_contenidoGrande.gridx = 0;
		gbc_contenidoGrande.gridy = 1;
		panelGrande.add(scrollPaneGrande, gbc_contenidoGrande);
		contenidoGrande.setLayout(new BoxLayout(contenidoGrande, BoxLayout.Y_AXIS));

		

		leerDatosBD(correo);
	}

	public void leerDatosBD(String correo) {

		String selectPartida;
		String archivoDescarga;

		String tablero;
		Date fecha;

		if (ventanaJuego.getTitle().equals("PixelArt")) {
			selectPartida = "SELECT pixelart.* FROM usuarios, pixelart WHERE usuarios.id = pixelart.idUsuario AND email = ?";
			archivoDescarga = "partidaCargadaPixelArt.txt";
		} else {
			selectPartida = "SELECT buscaminas.* FROM usuarios, buscaminas WHERE usuarios.id = buscaminas.idUsuario AND email = ?";
			archivoDescarga = "partidaCargadaBuscaMinas.datos";
		}
		Connection conexion = Conexion.obtenerConexion();

		try {
			PreparedStatement preparandoConsulta = conexion.prepareStatement(selectPartida);
			preparandoConsulta.setString(1, correo);
			ResultSet resultado = preparandoConsulta.executeQuery();

			while (resultado.next()) {
				// Resto del código cuando hay resultados
				// Guardamos el resultado para recorrerlo
				tablero = resultado.getString("tablero");
				fecha = resultado.getDate("fecha");
				Blob archivoBlob = resultado.getBlob("ficheroPartida");

				switch (tablero) {
				case "pequeño": {

					botonTableroPequeño = new JButton("" + fecha);
					botonTableroPequeño.setMargin(new Insets(7, 15, 7, 15));
					// Obtenemos un atributo oculto para obtener el archivo del boton presionado
					botonTableroPequeño.putClientProperty("nombreArchivo", archivoBlob);

					contenidoPequeño.setBorder(new EmptyBorder(10, 18, 10, 10));
					contenidoPequeño.add(botonTableroPequeño);
					contenidoPequeño.add(Box.createVerticalStrut(15));

					botonTableroPequeño.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							// Seguir adelante, si no ha seleccionado "X" en la ventana
							GuardarCargar.setGuardado(false);

							JButton botonPulsado = (JButton) e.getSource();
							// Recuperamos el valor del archivo del boton
							Blob archivoBlobBoton = (Blob) botonPulsado.getClientProperty("nombreArchivo");

							InputStream obtenerInputStream;
							try {
								obtenerInputStream = archivoBlobBoton.getBinaryStream();
								nuevoArchivo = Path.of(archivoDescarga);

								Files.copy(obtenerInputStream, nuevoArchivo, StandardCopyOption.REPLACE_EXISTING);

								// Obtiene la referencia al JFrame que contiene el botón y lo cierra
								Component component = (Component) e.getSource();
								JFrame frame = (JFrame) SwingUtilities.getRoot(component);
								frame.dispose();

							} catch (SQLException | IOException e1) {
								System.out.println("Error recuperando blob boton " + e1);
							}
						}
					});

					break;
				}
				case "mediano": {

					botonTableroMediano = new JButton("" + fecha);
					botonTableroMediano.setMargin(new Insets(7, 15, 7, 15));

					// Obtenemos un atributo oculto para obtener el archivo del boton presionado
					botonTableroMediano.putClientProperty("nombreArchivo", archivoBlob);

					contenidoMediano.setBorder(new EmptyBorder(10, 18, 10, 10));
					contenidoMediano.add(botonTableroMediano);
					contenidoMediano.add(Box.createVerticalStrut(15));

					botonTableroMediano.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							GuardarCargar.setGuardado(false);

							JButton botonPulsado = (JButton) e.getSource();
							// Recuperamos el valor del archivo del boton
							Blob archivoBlobBoton = (Blob) botonPulsado.getClientProperty("nombreArchivo");

							InputStream obtenerInputStream;
							try {
								obtenerInputStream = archivoBlobBoton.getBinaryStream();
								nuevoArchivo = Path.of(archivoDescarga);

								Files.copy(obtenerInputStream, nuevoArchivo, StandardCopyOption.REPLACE_EXISTING);

								// Obtiene la referencia al JFrame que contiene el botón y lo cierra
								Component component = (Component) e.getSource();
								JFrame frame = (JFrame) SwingUtilities.getRoot(component);
								frame.dispose();

							} catch (SQLException | IOException e1) {
								System.out.println("Error recuperando blob boton " + e1);
							}
						}
					});

					break;
				}
				case "grande": {

					botonTableroGrande = new JButton("" + fecha);
					botonTableroGrande.setMargin(new Insets(7, 15, 7, 15));
					// Obtenemos un atributo oculto para obtener el archivo del boton presionado
					botonTableroGrande.putClientProperty("nombreArchivo", archivoBlob);

					contenidoGrande.setBorder(new EmptyBorder(10, 18, 10, 10));
					contenidoGrande.add(botonTableroGrande);
					contenidoGrande.add(Box.createVerticalStrut(15));

					botonTableroGrande.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							GuardarCargar.setGuardado(false);

							JButton botonPulsado = (JButton) e.getSource();
							// Recuperamos el valor del archivo del boton
							Blob archivoBlobBoton = (Blob) botonPulsado.getClientProperty("nombreArchivo");

							InputStream obtenerInputStream;
							try {
								obtenerInputStream = archivoBlobBoton.getBinaryStream();
								nuevoArchivo = Path.of(archivoDescarga);

								Files.copy(obtenerInputStream, nuevoArchivo, StandardCopyOption.REPLACE_EXISTING);

								// Obtiene la referencia al JFrame que contiene el botón y lo cierra
								Component component = (Component) e.getSource();
								JFrame frame = (JFrame) SwingUtilities.getRoot(component);
								frame.dispose();

							} catch (SQLException | IOException e1) {
								System.out.println("Error recuperando blob boton " + e1);
							}
						}
					});

					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + tablero);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: cargarpartidaDesdeBD()" + e);
		}
	}

	private void centrarInterficiePantalla() {
		// Calcular la posición de la ventana
		Dimension tamañoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int width = this.getSize().width;
		int height = this.getSize().height;
		int x = (tamañoPantalla.width - width) / 2; // Centrado horizontalmente
		int y = (tamañoPantalla.height - height) / 2;
		// En la parte superior de la pantalla

		// Establecer la posición de la ventana
		this.setLocation(x, y);
	}
}