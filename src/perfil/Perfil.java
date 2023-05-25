package perfil;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import conexionBaseDatos.Conexion;
import menuJuegos.Menu;
import panel_inicio.Panel_inicio;

public class Perfil extends JPanel {

	JPanel panel_titulo = new JPanel();
	JPanel panel_nombre = new JPanel();
	JPanel panel_apellido = new JPanel();
	JPanel panel_poblacion = new JPanel();
	JPanel panel_correo = new JPanel();
	JPanel panel_boton = new JPanel();

	JLabel etiquetaTitulo = new JLabel("Perfil Usuario");
	JLabel etiquetaNombre = new JLabel("Nombre:");
	JLabel datosNombre = new JLabel("");

	JLabel etiquetaApellido = new JLabel("Apellidos");
	JLabel datosApellido = new JLabel("Datos Apellidos");

	JLabel etiquetaPoblacion = new JLabel("Poblacion");
	JLabel datosPoblacion = new JLabel("Datos Poblacion");

	JLabel etiquetaCorreo = new JLabel("Correo");
	JLabel datosCorreo = new JLabel("Datos Correo");

	JLabel etiquetaVacia = new JLabel();
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

		panel_titulo.setLayout(new GridLayout(0, 3));
		panel_titulo.add(etiquetaVacia);
		panel_titulo.add(etiquetaTitulo);

		add(panel_titulo);

		panel_nombre.setLayout(new GridLayout(0, 3));
		panel_nombre.add(etiquetaNombre);
		panel_nombre.add(datosNombre);

		add(panel_nombre);

		panel_apellido.setLayout(new GridLayout(0, 3));
		panel_apellido.add(etiquetaApellido);
		panel_apellido.add(datosApellido);

		add(panel_apellido);
//
		panel_poblacion.setLayout(new GridLayout(0, 3));
		panel_poblacion.add(etiquetaPoblacion);
		panel_poblacion.add(datosPoblacion);
		add(panel_poblacion);
//
		panel_correo.setLayout(new GridLayout(0, 3));
		panel_correo.add(etiquetaCorreo);
		panel_correo.add(datosCorreo);
		add(panel_correo);
// 
		panel_boton.setLayout(new GridLayout(0, 3));
		panel_boton.add(etiquetaVacia1);
		panel_boton.add(botonEliminarCuenta);
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
				System.out.println("Imagen:" + resultado.getBytes("imagen"));
				datos[4] = resultado.getString("poblacion");
				datos[5] = resultado.getString("email");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return datos;
	}

	public void eleminarUsuario(int id) {
		String sentenciaTablaPasswords = "DELETE FROM passwords WHERE idUsuario = ?";
		String sentenciaTablaUsuarios = "DELETE FROM usuarios WHERE id = ?";
		Connection c = Conexion.obtenerConexion();
		
		int opcion = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar su cuenta?", "Eliminar cuenta", JOptionPane.YES_NO_OPTION);
		
		
		if(opcion == JOptionPane.YES_OPTION) {
			try {
				PreparedStatement consulta = c.prepareStatement(sentenciaTablaPasswords);
				consulta.setInt(1, id);
				consulta.executeUpdate();
				
				consulta = c.prepareStatement(sentenciaTablaUsuarios);
				consulta.setInt(1, id);
				consulta.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "La cuenta ha sido eliminada");
				Panel_inicio p = (Panel_inicio) SwingUtilities.getWindowAncestor(Perfil.this);
				p.getContentPane().removeAll();
				p.getContentPane().add(p.getContenedor());
				p.revalidate();
				p.repaint();
				
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
		}
		
		
	}

}
