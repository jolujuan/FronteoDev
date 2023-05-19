package panel_inicio;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		panel_superior.add(etiqueta_superior);

		etiqueta_registro.setText("Resgistrarse");
		boton_registro.add(etiqueta_registro);

		etiqueta_login.setText("Iniciar Sesión");
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

		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	private void mostrarRegistro() {
        // Crear y mostrar el contenido de la ventana de registro
        Registro ventanaRegistro = new Registro();
        setContentPane(ventanaRegistro);
        revalidate();
        repaint();
 //       ventanaRegistro.setVisible(true);
 //       ventanaRegistro.setSize(500, 500);
        // Ocultar la ventana actual
 //       setVisible(false);
    }
	
}
