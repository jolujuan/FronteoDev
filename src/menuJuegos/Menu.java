package menuJuegos;

import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel {

	private JPanel panel_1 = new JPanel();
	private JPanel panel_2 = new JPanel();
	private JPanel panel_3 = new JPanel();
	private JPanel panel_4 = new JPanel();

	private JLabel etiqueta_usuario = new JLabel("Hola");
	private JButton boton_ver_perfil = new JButton("Ver Perfil");

	private JLabel etiqueta_pixelArt = new JLabel();
	private ImageIcon imagenPixelArt = new ImageIcon("src/imagenes/pixel_art.jpg");
	private JLabel etiqueta_BuscaMinas = new JLabel();
	private ImageIcon imagenBuscaminas = new ImageIcon("src/imagenes/busca_minas.jpg");
	private JLabel etiqueta_JuegoDeLavida = new JLabel();
	private ImageIcon imagenJuegoDeLaVida = new ImageIcon("src/imagenes/juego_de_la_vida.jpg");

	private JButton botonJugarPixelArt = new JButton("Jugar");
	private JButton botonJugarBuscaminas = new JButton("Jugar");
	private JButton botonJugarJuegoDeLaVida = new JButton("Jugar");

	private JLabel etiqueta_vacia = new JLabel();
	private JButton botonLogout = new JButton("LogOut");

	public Menu() {
		setLayout(new GridLayout(4, 3));

		panel_1.setLayout(new GridLayout(0, 3));
		panel_1.add(etiqueta_usuario);
		panel_1.add(boton_ver_perfil);
		add(panel_1);
		
		panel_2.setLayout(new GridLayout(0, 3));
		etiqueta_pixelArt.setIcon(imagenPixelArt);
		etiqueta_BuscaMinas.setIcon(imagenBuscaminas);
		etiqueta_JuegoDeLavida.setIcon(imagenJuegoDeLaVida);
		panel_2.add(etiqueta_pixelArt);
		panel_2.add(etiqueta_BuscaMinas);
		panel_2.add(etiqueta_JuegoDeLavida);
		add(panel_2);
		
		panel_3.setLayout(new GridLayout(0, 3));
		panel_3.add(botonJugarPixelArt);
		panel_3.add(botonJugarBuscaminas);
		panel_3.add(botonJugarJuegoDeLaVida);
		add(panel_3);
		
		panel_4.setLayout(new GridLayout(0, 3));
		panel_4.add(etiqueta_vacia);
		panel_4.add(botonLogout);
		add(panel_4);
		
		setSize(600,600);
	}
	
	public static void main (String[] args) {
		Menu m = new Menu();
	}

}
