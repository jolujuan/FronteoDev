package perfil;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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

	public Perfil() {
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
//		
//		add(etiquetaPoblacion);
//		add(datosPoblacion);
//		
//		add(etiquetaCorreo);
//		add(datosCorreo);
//		
//		add(etiquetaVacia);
//		add(botonEliminarCuenta);
		botonVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Menu menu = new Menu();
				Panel_inicio p = (Panel_inicio) SwingUtilities.getWindowAncestor(Perfil.this);
				p.getContentPane().removeAll();
				p.getContentPane().add(menu);
				p.revalidate();
				p.repaint();

			}
		});
	}
}
