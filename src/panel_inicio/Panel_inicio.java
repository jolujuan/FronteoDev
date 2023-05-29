package panel_inicio;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import login.Login;
import registro.Registro;

public class Panel_inicio extends JFrame {

	private Container contenedor = getContentPane();
	private JPanel panel_superior = new JPanel();

	private JLabel etiqueta_superior = new JLabel();
	private JButton boton_registro = new JButton();
	private JLabel etiqueta_registro = new JLabel();
	private JButton boton_login = new JButton();
	private JLabel etiqueta_login = new JLabel();
	private JPanel panel_principal = new JPanel();
	private GridBagConstraints gbc = new GridBagConstraints();

	
	
	public Panel_inicio() {
		

		this.setTitle("Programa Juegos");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contenedor.setLayout(new BorderLayout());

		panel_superior.setLayout(new FlowLayout());

		etiqueta_superior.setText("¡BIENVENIDO!");
		etiqueta_superior.setFont(new Font("Dialog", Font.BOLD, 25));
		etiqueta_superior.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_superior.add(etiqueta_superior);
		
		etiqueta_registro.setText("Registrarse");
		etiqueta_registro.setFont(new Font("Dialog", Font.BOLD, 18));
		etiqueta_registro.setAlignmentX(Component.CENTER_ALIGNMENT);
		etiqueta_registro.setPreferredSize(new Dimension(160, 55)); 
		boton_registro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		boton_registro.add(etiqueta_registro);

		etiqueta_login.setText("Iniciar Sesión");
		etiqueta_login.setFont(new Font("Dialog", Font.BOLD, 18));
		etiqueta_login.setAlignmentX(Component.CENTER_ALIGNMENT);
		etiqueta_login.setPreferredSize(new Dimension(160, 55)); 
		boton_login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		boton_login.add(etiqueta_login);


		panel_principal.setLayout(new FlowLayout());

		panel_principal.add(boton_registro);
		panel_principal.add(boton_login);

		contenedor.setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.CENTER;
		contenedor.add(panel_superior, gbc);

		gbc.gridy = 1;
		gbc.weighty = 1.0;
		contenedor.add(panel_principal, gbc);

		boton_registro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 mostrarRegistro();
			}
		});

		boton_login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 mostrarLogin();
			}
		});
 
		this.setSize(650, 550);
		this.setVisible(true);
		centrarInterficiePantalla();
	}

	public Container getContenedor() {
		return contenedor;
	}

	public void setContenedor(Container contenedor) {
		this.contenedor = contenedor;
	}

	public JPanel getPanel_superior() {
		return panel_superior;
	}

	public void setPanel_superior(JPanel panel_superior) {
		this.panel_superior = panel_superior;
	}

	public JLabel getEtiqueta_superior() {
		return etiqueta_superior;
	}

	public void setEtiqueta_superior(JLabel etiqueta_superior) {
		this.etiqueta_superior = etiqueta_superior;
	}

	public JButton getBoton_registro() {
		return boton_registro;
	}

	public void setBoton_registro(JButton boton_registro) {
		this.boton_registro = boton_registro;
	}

	public JLabel getEtiqueta_registro() {
		return etiqueta_registro;
	}

	public void setEtiqueta_registro(JLabel etiqueta_registro) {
		this.etiqueta_registro = etiqueta_registro;
	}

	public JButton getBoton_login() {
		return boton_login;
	}

	public void setBoton_login(JButton boton_login) {
		this.boton_login = boton_login;
	}

	public JLabel getEtiqueta_login() {
		return etiqueta_login;
	}

	public void setEtiqueta_login(JLabel etiqueta_login) {
		this.etiqueta_login = etiqueta_login;
	}

	public JPanel getPanel_principal() {
		return panel_principal;
	}

	public void setPanel_principal(JPanel panel_principal) {
		this.panel_principal = panel_principal;
	}

	public GridBagConstraints getGbc() {
		return gbc;
	}

	public void setGbc(GridBagConstraints gbc) {
		this.gbc = gbc;
	}

	
	private void mostrarLogin() {
		// Crear y mostrar el contenido de la ventana de registro
		Login ventanaLogin = new Login();
		setContentPane(ventanaLogin);
		revalidate();
		repaint();
		// ventanaRegistro.setVisible(true);
		// ventanaRegistro.setSize(500, 500);
		// Ocultar la ventana actual
		// setVisible(false);
	}

	private void mostrarRegistro() {
		// Crear y mostrar el contenido de la ventana de registro
		Registro ventanaRegistro = new Registro();
		setContentPane(ventanaRegistro);
		revalidate();
		repaint();
		// ventanaRegistro.setVisible(true);
		// ventanaRegistro.setSize(500, 500);
		// Ocultar la ventana actual
		// setVisible(false);
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
