package guardarCargar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

	Path nuevoArchivo = null;
	private PixelArt pixelArtFrame;
	private JFrame frame;

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { String correo = "edu@gmail.com";
	 * GuardarCargar frame = new GuardarCargar(correo); frame.setVisible(true); }
	 * catch (Exception e) { e.printStackTrace(); } } }); }
	 */

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

	public GuardarCargar(PixelArt pixelArt, String correo) {
		this.pixelArtFrame = pixelArt;
		frame = this;

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
		panelPequeño.setBorder(new MatteBorder(0, 0, 0, 4, (Color) new Color(0, 0, 0)));
		panel.add(panelPequeño);
		GridBagLayout gbl_panelPequeño = new GridBagLayout();
		gbl_panelPequeño.columnWidths = new int[] { 146, 0 };
		gbl_panelPequeño.rowHeights = new int[] { 35, 389, 0 };
		gbl_panelPequeño.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelPequeño.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelPequeño.setLayout(gbl_panelPequeño);

		JPanel tituloPequeño = new JPanel();
		tituloPequeño.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc_tituloPequeño = new GridBagConstraints();
		gbc_tituloPequeño.fill = GridBagConstraints.BOTH;
		gbc_tituloPequeño.insets = new Insets(0, 0, 5, 0);
		gbc_tituloPequeño.gridx = 0;
		gbc_tituloPequeño.gridy = 0;
		panelPequeño.add(tituloPequeño, gbc_tituloPequeño);

		JLabel labelPequeño = new JLabel("PEQUEÑO");
		labelPequeño.setFont(new Font("Dialog", Font.BOLD, 18));
		tituloPequeño.add(labelPequeño);

		contenidoPequeño.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gbc_contenidoPequeño = new GridBagConstraints();
		gbc_contenidoPequeño.fill = GridBagConstraints.VERTICAL;
		gbc_contenidoPequeño.gridx = 0;
		gbc_contenidoPequeño.gridy = 1;
		panelPequeño.add(contenidoPequeño, gbc_contenidoPequeño);
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
		gbl_panelMediano.rowWeights = new double[] { 0.0, 1.0 };
		panelMediano.setLayout(gbl_panelMediano);

		JPanel tituloMediano = new JPanel();
		tituloMediano.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc_tituloMediano = new GridBagConstraints();
		gbc_tituloMediano.fill = GridBagConstraints.BOTH;
		gbc_tituloMediano.insets = new Insets(0, 0, 5, 0);
		gbc_tituloMediano.gridx = 0;
		gbc_tituloMediano.gridy = 0;
		panelMediano.add(tituloMediano, gbc_tituloMediano);

		JLabel labelMediano = new JLabel("MEDIANO");
		labelMediano.setFont(new Font("Dialog", Font.BOLD, 18));
		tituloMediano.add(labelMediano);

		GridBagConstraints gbc_contenidoMediano = new GridBagConstraints();
		gbc_contenidoMediano.fill = GridBagConstraints.VERTICAL;
		gbc_contenidoMediano.gridx = 0;
		gbc_contenidoMediano.gridy = 1;
		panelMediano.add(contenidoMediano, gbc_contenidoMediano);
		contenidoMediano.setLayout(new BoxLayout(contenidoMediano, BoxLayout.Y_AXIS));

		botonTableroMediano.setFont(new Font("Dialog", Font.PLAIN, 14));
		botonTableroMediano.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		botonTableroMediano.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JPanel panelGrande = new JPanel();
		panelGrande.setBorder(new MatteBorder(0, 4, 0, 0, (Color) new Color(0, 0, 0)));
		panel.add(panelGrande);
		GridBagLayout gbl_panelGrande = new GridBagLayout();
		gbl_panelGrande.columnWidths = new int[] { 146, 0 };
		gbl_panelGrande.rowHeights = new int[] { 35, 132, 0 };
		gbl_panelGrande.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelGrande.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelGrande.setLayout(gbl_panelGrande);

		JPanel tituloGrande = new JPanel();
		tituloGrande.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc_tituloGrande = new GridBagConstraints();
		gbc_tituloGrande.fill = GridBagConstraints.BOTH;
		gbc_tituloGrande.insets = new Insets(0, 0, 5, 0);
		gbc_tituloGrande.gridx = 0;
		gbc_tituloGrande.gridy = 0;
		panelGrande.add(tituloGrande, gbc_tituloGrande);

		JLabel labelGrande = new JLabel("GRANDE");
		labelGrande.setFont(new Font("Dialog", Font.BOLD, 18));
		tituloGrande.add(labelGrande);

		GridBagConstraints gbc_contenidoGrande = new GridBagConstraints();
		gbc_contenidoGrande.fill = GridBagConstraints.VERTICAL;
		gbc_contenidoGrande.gridx = 0;
		gbc_contenidoGrande.gridy = 1;
		panelGrande.add(contenidoGrande, gbc_contenidoGrande);
		contenidoGrande.setLayout(new BoxLayout(contenidoGrande, BoxLayout.Y_AXIS));

		botonTableroGrande.setFont(new Font("Dialog", Font.PLAIN, 14));
		botonTableroGrande.setAlignmentY(1.0f);

		leerDatosBD(correo);
	}

	public void leerDatosBD(String correo) {
		String selectPartida = "SELECT pixelart.* FROM usuarios, pixelart WHERE usuarios.id = pixelart.idUsuario AND email = ?";
		Connection conexion = Conexion.obtenerConexion();
		String archivoDescarga = "partidaCargada.txt";

		try {
			PreparedStatement preparandoConsulta = conexion.prepareStatement(selectPartida);

			preparandoConsulta.setString(1, correo);

			ResultSet resultado = preparandoConsulta.executeQuery();

			String tablero = "";
			Date fecha;

			if (!resultado.next()) {
				JOptionPane.showMessageDialog(null, "Todavía no tienes ninguna partida guardada.", "Información",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				// Resto del código cuando hay resultados
				do {
					// Guardamos el resultado para recorrerlo
					tablero = resultado.getString("tablero");
					fecha = resultado.getDate("fecha");
					Blob archivoBlob = resultado.getBlob("ficheroPartida");

					switch (tablero) {
					case "pequeño": {

						botonTableroPequeño = new JButton("" + fecha);
						// Obtenemos un atributo oculto para obtener el archivo del boton presionado
						botonTableroPequeño.putClientProperty("nombreArchivo", archivoBlob);

						contenidoPequeño.add(botonTableroPequeño);
						contenidoPequeño.add(Box.createVerticalStrut(10));

						botonTableroPequeño.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

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
						// Obtenemos un atributo oculto para obtener el archivo del boton presionado
						botonTableroMediano.putClientProperty("nombreArchivo", archivoBlob);

						contenidoMediano.add(botonTableroMediano);
						contenidoMediano.add(Box.createVerticalStrut(10));

						botonTableroMediano.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

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
						// Obtenemos un atributo oculto para obtener el archivo del boton presionado
						botonTableroGrande.putClientProperty("nombreArchivo", archivoBlob);

						contenidoGrande.add(botonTableroGrande);
						contenidoGrande.add(Box.createVerticalStrut(10));

						botonTableroGrande.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

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
				} while (resultado.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: cargarpartidaDesdeBD()" + e);
		}
	}
}
