package menuJuegos;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import juegos.BuscaMinas;
import juegos.JuegoVida;
import juegos.PixelArt;
import panel_inicio.Panel_inicio;
import perfil.Perfil;

public class Menu extends JPanel {

	private JButton boton_ver_perfil = new JButton("Ver Perfil");
	private JButton botonJugarPixelArt = new JButton("Jugar PixelArt");
	private JButton botonJugarBuscaminas = new JButton("Jugar Buscaminas");
	private JButton botonJugarJuegoDeLaVida = new JButton("Jugar JuegoDeLaVida");
	private JButton botonLogout = new JButton("Logout");
	private int botonJugar = 0;
	private boolean pixelArtAbierto = false;
	private boolean buscaAbierto = false;

	public Menu(String correo) {

		System.out.println(this);
		
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			
		System.out.println(frame);
		
//		frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				// Realizar acciones cuando se cierra la ventana
//				// por ejemplo, mostrar un mensaje o ejecutar un método específico
//				System.out.println("La ventana se está cerrando");
//			}
//		});

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 30, 10);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		boton_ver_perfil.setPreferredSize(new Dimension(110, 35));
		boton_ver_perfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		boton_ver_perfil.setFont(new Font("Dialog", Font.PLAIN, 13));
		add(boton_ver_perfil, c);

		addImageLabel("/imagenes/pixel_art.jpg", 0, 1);
		addImageLabel("/imagenes/busca_minas.jpg", 1, 1);
		addImageLabel("/imagenes/juego_de_la_vida.jpg", 2, 1);

		// Botones Jugar
		GridBagConstraints c1 = new GridBagConstraints();
		c1.insets = new Insets(10, 10, 30, 10);
		c1.gridwidth = 1;
		c1.gridy = 2;

		c1.gridx = 0;
		botonJugarPixelArt.setMargin(new Insets(0, 0, 0, 0));
		botonJugarPixelArt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonJugarPixelArt.setPreferredSize(new Dimension(160, 35));
		botonJugarPixelArt.setFont(new Font("Dialog", Font.PLAIN, 13));
		add(botonJugarPixelArt, c1);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.insets = new Insets(10, 10, 30, 10);
		c2.gridwidth = 1;
		c2.gridy = 2;
		c2.gridx = 1;
		botonJugarBuscaminas.setMargin(new Insets(0, 0, 0, 0));
		botonJugarBuscaminas.setPreferredSize(new Dimension(160, 35));
		botonJugarBuscaminas.setFont(new Font("Dialog", Font.PLAIN, 13));
		botonJugarBuscaminas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(botonJugarBuscaminas, c2);

		GridBagConstraints c3 = new GridBagConstraints();
		c3.insets = new Insets(10, 10, 30, 10);
		c3.gridwidth = 1;
		c3.gridy = 2;
		c3.gridx = 2;
		botonJugarJuegoDeLaVida.setMargin(new Insets(0, 0, 0, 0));
		botonJugarJuegoDeLaVida.setPreferredSize(new Dimension(160, 35));
		botonJugarJuegoDeLaVida.setFont(new Font("Dialog", Font.PLAIN, 13));
		botonJugarJuegoDeLaVida.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(botonJugarJuegoDeLaVida, c3);

		// Botón Logout
		GridBagConstraints c4 = new GridBagConstraints();
		c4.insets = new Insets(10, 10, 30, 10);
		c4.gridx = 1;
		c4.gridy = 3;
		botonLogout.setPreferredSize(new Dimension(110, 35));
		botonLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonLogout.setFont(new Font("Dialog", Font.PLAIN, 13));
		add(botonLogout, c4);

		botonLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean datosGuardados = PixelArt.isGuardado();

				if (datosGuardados == false) {
					int option = JOptionPane.showConfirmDialog(null, "¿Desea guardar partida antes de cerrar sesión?");
					if (option == JOptionPane.YES_OPTION) {
						// Lógica para guardar la partida
						System.out.println("Partida guardada." + option);

						// Eliminar tanto los archivos locales como temporales
						eliminarArchivos();

						///////////
						// FUNCION DE GUARDAR PARTIDA
						PixelArt.guardarEstadoTablero("PixelArt.txt");
						PixelArt.guardarDatosBD(correo, "PixelArt.txt");
						//////////

						//// Esto es pa quan tries la opcio de logout y tens una finestra oberta de
						//// algun joc teu tanque (GRACIES CHAT)
						Window[] ventanasAbiertas = Window.getWindows();
						for (Window ventana : ventanasAbiertas) {
							if (ventana != null && ventana.isDisplayable()) {
								ventana.dispose();
							}
						}

						SwingUtilities.getWindowAncestor(Menu.this).dispose();
						Panel_inicio ventanaInicio = new Panel_inicio();

						ventanaInicio.setVisible(true);
					} else if (option == JOptionPane.NO_OPTION) {
						// Lógica para no guardar la partida
						System.out.println("Partida no guardada." + option);
						eliminarArchivos();

						///////////
						// FUNCION DE GUARDAR PARTIDA
						//////////

						//// Esto es pa quan tries la opcio de logout y tens una finestra oberta de
						//// algun joc teu tanque (GRACIES CHAT)
						Window[] ventanasAbiertas = Window.getWindows();
						for (Window ventana : ventanasAbiertas) {
							if (ventana != null && ventana.isDisplayable()) {
								ventana.dispose();
							}
						}

						SwingUtilities.getWindowAncestor(Menu.this).dispose();
						Panel_inicio ventanaInicio = new Panel_inicio();
						ventanaInicio.setVisible(true);
					} else if (option == JOptionPane.CANCEL_OPTION) {
						System.out.println("Acción cancelada." + option);
					} else if (option == JOptionPane.CLOSED_OPTION) {
						
						System.out.println("Sin opción seleccionada." + option);
					}
				} else {
					eliminarArchivos();

					Window[] ventanasAbiertas = Window.getWindows();
					for (Window ventana : ventanasAbiertas) {
						if (ventana != null && ventana.isDisplayable()) {
							ventana.dispose();
						}
					}

					SwingUtilities.getWindowAncestor(Menu.this).dispose();
					Panel_inicio ventanaInicio = new Panel_inicio();
					ventanaInicio.setVisible(true);
				}
			}

			

		});

		botonJugarPixelArt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!pixelArtAbierto) { // Verificar si PixelArt está abierto
					pixelArtAbierto = true; // Establecer como abierto

					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								PixelArt frame1 = new PixelArt(correo);
								frame1.setSize(500, 500);
								frame1.setVisible(true);
								frame1.addWindowListener(new WindowAdapter() {
									@Override
									public void windowClosed(WindowEvent e) {
										pixelArtAbierto = false; // Restablecer como cerrado
									}
								});

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});

		botonJugarBuscaminas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!buscaAbierto) { // Verificar si PixelArt está abierto
					buscaAbierto = true;
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								BuscaMinas frame = new BuscaMinas(correo);
								frame.setSize(500, 500);
								frame.setVisible(true);
								frame.addWindowListener(new WindowAdapter() {
									@Override
									public void windowClosed(WindowEvent e) {
										buscaAbierto = false; // Restablecer como cerrado
									}
								});
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});

		botonJugarJuegoDeLaVida.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!buscaAbierto) { // Verificar si PixelArt está abierto
					buscaAbierto = true;
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								JuegoVida frame = new JuegoVida();
								frame.setSize(500, 500);
								frame.setVisible(true);
								frame.addWindowListener(new WindowAdapter() {
									@Override
									public void windowClosed(WindowEvent e) {
										buscaAbierto = false; // Restablecer como cerrado
									}
								});
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});

		boton_ver_perfil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Perfil perfil = new Perfil(correo);
				Panel_inicio p = (Panel_inicio) SwingUtilities.getWindowAncestor(Menu.this);
				p.getContentPane().removeAll();
				p.getContentPane().add(perfil);
				p.revalidate();
				p.repaint();
			}
		});
	}

	private void addImageLabel(String imagePath, int x, int y) {
	    URL imgURL = getClass().getResource(imagePath);
	    if (imgURL != null) {
	        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
	        JLabel etiqueta = new JLabel(imageIcon);
	        GridBagConstraints c = new GridBagConstraints();
	        c.insets = new Insets(10, 10, 10, 10);

	        c.gridx = x;
	        c.gridy = y;
	        add(etiqueta, c);
	    } else {
	        System.err.println("No se pudo encontrar el archivo: " + imagePath);
	    }
	}


	public static void eliminarArchivos() {
		File archivoCargaDatosPixelArt = new File("partidaCargadaPixelArt.txt");
		File archivoCargadoDatosBuscaminas = new File("partidaCargadaBuscaMinas.datos");
		File archivoLocalPixelArt = new File("PixelArt.txt");
		File archivoLocalBuscaminas = new File("buscaminas.datos");

		//Comprobar si existe porque lo llamaremos desde otro metodo
		if (archivoCargaDatosPixelArt.exists()) {					
			archivoCargaDatosPixelArt.delete();
		}
		if (archivoCargadoDatosBuscaminas.exists()) {					
			archivoCargadoDatosBuscaminas.delete();
		}
		if (archivoLocalPixelArt.exists()) {
			archivoLocalPixelArt.delete();
		}
		if (archivoLocalBuscaminas.exists()) {
			archivoLocalBuscaminas.delete();					
		}
	}

}