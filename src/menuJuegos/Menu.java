package menuJuegos;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import juegos.PixelArt;
import panel_inicio.Panel_inicio;

public class Menu extends JPanel {

	private JButton boton_ver_perfil = new JButton("Ver Perfil");
	private JButton botonJugarPixelArt = new JButton("Jugar PixelArt");
	private JButton botonJugarBuscaminas = new JButton("Jugar Buscaminas");
	private JButton botonJugarJuegoDeLaVida = new JButton("Jugar JuegoDeLaVida");
	private JButton botonLogout = new JButton("Logout");
	private int botonJugar = 0;

	public Menu() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,30,10);  

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		add(boton_ver_perfil, c);

		addImageLabel("src/imagenes/pixel_art.jpg", 0, 1);
		addImageLabel("src/imagenes/busca_minas.jpg", 1, 1);
		addImageLabel("src/imagenes/juego_de_la_vida.jpg", 2, 1);

		 // Botones Jugar
        c.gridwidth = 1;
        c.gridy = 2;

        c.gridx = 0;
        add(botonJugarPixelArt, c);

        c.gridx = 1;
        add(botonJugarBuscaminas, c);

        c.gridx = 2;
        add(botonJugarJuegoDeLaVida, c);
 
        // Botón Logout
        c.gridx = 1;
        c.gridy = 3;
        add(botonLogout, c);

		botonJugarPixelArt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Esbozo de método generado automáticamente

				if (botonJugar == 0) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								PixelArt frame = new PixelArt();
								frame.setSize(500, 500);
								frame.setVisible(true);
								botonJugar++;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		
		botonLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.getWindowAncestor(Menu.this).dispose();
				
				Panel_inicio ventanaInicio = new Panel_inicio();
				ventanaInicio.setVisible(true);
				
			}
		});
	}

	
	
	private void addImageLabel(String imagePath, int x, int y) {
		ImageIcon imagen = new ImageIcon(
				new ImageIcon(imagePath).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		JLabel etiqueta = new JLabel(imagen);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = x;
		c.gridy = y;
		add(etiqueta, c);
	}
	

	public static void main(String[] args) {
		Menu m = new Menu();
	}
}
