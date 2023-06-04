package perfil;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import conexionBaseDatos.Conexion;
import menuJuegos.Menu;
import panel_inicio.Panel_inicio;

public class Perfil extends JPanel {
	private static byte[] arrayBits;
	JPanel panel_titulo = new JPanel();
	JPanel panel_nombre = new JPanel();
	JPanel panel_apellido = new JPanel();
	JPanel panel_poblacion = new JPanel();
	JPanel panel_correo = new JPanel();
	JPanel panel_boton = new JPanel();

	JLabel etiquetaTitulo = new JLabel("Perfil Usuario");
	JLabel etiquetaNombre = new JLabel("Nombre:");
	JLabel datosNombre = new JLabel("");

	JLabel etiquetaApellido = new JLabel("Apellidos:");
	JLabel datosApellido = new JLabel("Datos Apellidos");

	JLabel etiquetaPoblacion = new JLabel("Población:");
	JLabel datosPoblacion = new JLabel("Datos Poblacion");

	JLabel etiquetaCorreo = new JLabel("Correo:");
	JLabel datosCorreo = new JLabel("Datos Correo");

	JLabel etiquetaImagenPerfil = new JLabel();
	JLabel etiquetaVacia1 = new JLabel();

	JLabel etiquetaVacia2 = new JLabel();
	JLabel etiquetaVacia3 = new JLabel();
	JLabel etiquetaVacia4 = new JLabel();
	JLabel etiquetaVacia5 = new JLabel();
	JLabel etiquetaVacia6 = new JLabel();
	JButton botonEliminarCuenta = new JButton("Eliminar Cuenta");
	JButton botonVolver = new JButton("Volver");

	public Perfil(String correo) {
		String[] datosUsuario = datosUsuarioPerfil(correo);
		datosNombre.setText(datosUsuario[1]);
		datosApellido.setText(datosUsuario[2]);
		datosPoblacion.setText(datosUsuario[4]);
		datosCorreo.setText(datosUsuario[5]);

		setLayout(new GridLayout(6, 3));
		panel_titulo.setLayout(new GridLayout(0, 3, 0, 0));
		etiquetaImagenPerfil.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_titulo.add(etiquetaImagenPerfil);

		panel_titulo.setLayout(new GridLayout(0, 3));
		panel_titulo.setBorder(new EmptyBorder(0, 20, 0, 0));

		etiquetaTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		etiquetaTitulo.setBorder(new EmptyBorder(0, 0, 0, 0));
		etiquetaTitulo.setFont(new Font("Dialog", Font.BOLD, 16));

		panel_titulo.add(etiquetaTitulo);

		add(panel_titulo);

		panel_nombre.setLayout(new GridLayout(0, 3));
		etiquetaNombre.setBorder(new EmptyBorder(0, 20, 0, 0));
		etiquetaNombre.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_nombre.add(etiquetaNombre);
		panel_nombre.add(datosNombre);

		add(panel_nombre);

		panel_apellido.setLayout(new GridLayout(0, 3));
		etiquetaApellido.setBorder(new EmptyBorder(0, 20, 0, 0));
		etiquetaApellido.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_apellido.add(etiquetaApellido);
		panel_apellido.add(datosApellido);

		add(panel_apellido);
//
		panel_poblacion.setLayout(new GridLayout(0, 3));
		etiquetaPoblacion.setBorder(new EmptyBorder(0, 20, 0, 0));
		etiquetaPoblacion.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_poblacion.add(etiquetaPoblacion);
		panel_poblacion.add(datosPoblacion);
		add(panel_poblacion);
//
		panel_correo.setLayout(new GridLayout(0, 3));
		etiquetaCorreo.setBorder(new EmptyBorder(0, 20, 0, 0));
		etiquetaCorreo.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_correo.add(etiquetaCorreo);
		panel_correo.add(datosCorreo);
		add(panel_correo);
		panel_boton.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		botonEliminarCuenta.setPreferredSize(new Dimension(150, 35));
		botonEliminarCuenta.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_boton.add(botonEliminarCuenta);
		botonVolver.setPreferredSize(new Dimension(150, 35));
		botonVolver.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_boton.add(botonVolver);
		add(panel_boton);

		botonVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Menu menu = new Menu(correo);

				Panel_inicio p = (Panel_inicio) SwingUtilities.getWindowAncestor(Perfil.this);
				p.getContentPane().removeAll();
				p.getContentPane().add(menu);
				p.revalidate();
				p.repaint();

			}
		});

		botonEliminarCuenta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				eleminarUsuario(Integer.parseInt(datosUsuario[0]));

			}
		});
	}

	public String[] datosUsuarioPerfil(String correo) {
		String[] datos = new String[6];
		String sentencia = "SELECT * FROM usuarios WHERE email = ?";
		Connection c = Conexion.obtenerConexion();

		try {
			PreparedStatement consulta = c.prepareStatement(sentencia);
			consulta.setString(1, correo);
			ResultSet resultado = consulta.executeQuery();

			while (resultado.next()) {
				datos[0] = "" + resultado.getInt("id");

				datos[1] = resultado.getString("nombre");
				datos[2] = resultado.getString("apellidos");
				arrayBits = resultado.getBytes("imagen");
//				System.out.println("Array de bits " + arrayBits);
//				System.out.println("Consulta " + resultado.getBytes("imagen"));
//				System.out.println("Longitud: " + arrayBits.length);

				datos[4] = resultado.getString("poblacion");
				datos[5] = resultado.getString("email");

			}
			// adaptarImagen(arrayBits);
			// etiquetaImagenPerfil.setIcon(obtenerImagenDesdeBytes(arrayBits));
			adaptarImagen(arrayBits);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}

		return datos;
	}
 
	public void eleminarUsuario(int id) {
		String sentenciaTablaPixelArt = "DELETE FROM pixelart WHERE idUsuario = ?";
		String sentenciaTablaBuscaminas = "DELETE FROM buscaminas WHERE idUsuario = ?";
		String sentenciaTablaPasswords = "DELETE FROM passwords WHERE idUsuario = ?";
		String sentenciaTablaUsuarios = "DELETE FROM usuarios WHERE id = ?";
		Connection c = Conexion.obtenerConexion();

		int opcion = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar su cuenta?",
				"Eliminar cuenta", JOptionPane.YES_NO_OPTION);

		if (opcion == JOptionPane.YES_OPTION) {
			try {
				// ELIMANAR EL USER DE PASSWORDS
				PreparedStatement consulta = c.prepareStatement(sentenciaTablaPasswords);
				consulta.setInt(1, id);
				consulta.executeUpdate();
				// ELIMANAR EL USER DE PIXELART
				consulta = c.prepareStatement(sentenciaTablaPixelArt);
				consulta.setInt(1, id);
				consulta.executeUpdate();
				// ELIMANAR EL USER DE BUSCAMINAS
				consulta = c.prepareStatement(sentenciaTablaBuscaminas);
				consulta.setInt(1, id);
				consulta.executeUpdate();
				// ELIMANAR EL USER DE USUARIOS
				consulta = c.prepareStatement(sentenciaTablaUsuarios);
				consulta.setInt(1, id);
				consulta.executeUpdate();

				Panel_inicio p = (Panel_inicio) SwingUtilities.getWindowAncestor(Perfil.this);
				p.getContentPane().removeAll();
				p.getContentPane().add(p.getContenedor());
				p.revalidate();
				p.repaint();
				JOptionPane.showMessageDialog(null, "La cuenta ha sido eliminada");
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
		}
	}

	public void adaptarImagen(byte[] arrayBits) {

		ImageIcon icono = new ImageIcon(arrayBits);
		Image imagenPerfil = icono.getImage();
		int anchoImagen = icono.getIconWidth();
		int altoImagen = icono.getIconHeight();
		int nuevaResolucion = (altoImagen * 100) / anchoImagen;
		Image nuevaImagen = imagenPerfil.getScaledInstance(100, nuevaResolucion, java.awt.Image.SCALE_SMOOTH);
		ImageIcon nuevoIcono = new ImageIcon(nuevaImagen);
		etiquetaImagenPerfil.setIcon(nuevoIcono);

	}

}
