package juegos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class PixelArt extends JFrame {
	private Casilla casillaSeleccionada = null;
	private Color colorSeleccionado = Color.BLACK;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PixelArt frame = new PixelArt();
					frame.setSize(500, 500);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PixelArt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		IniciodeJuego();

	}

	private void IniciodeJuego() {
		// Texto de inicio: SELECCION DE TAMAÑO DE TABLERO
		JPanel inicio = new JPanel();
		inicio.setFont(new Font("Verdana", Font.BOLD, 13));
		JLabel seleccionaTableroJLabel = new JLabel("Selecciona el tamaño del tablero");
		seleccionaTableroJLabel.setFont(new Font("Unispace", Font.BOLD, 12));
		inicio.add(seleccionaTableroJLabel);

		// Botones de selección de tamaño del tablero
		JPanel botonesJPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);

		// Esto es para simplificar el codigo, sustituye todo lo de abajo

		/*
		 * JButton[] tamanos = new JButton[3]; String[] labels = {"Pequeño", "Mediano",
		 * "Grande"}; int[] tableroSize = {20, 50, 100}; for (int i = 0; i <
		 * tamanos.length; i++) { final int index = i; // Necesitamos declararla como
		 * final para usarla en la clase listener tamanos[i] = new JButton(labels[i]);
		 * tamanos[i].setFont(new Font("Unispace", Font.BOLD, 12));
		 * tamanos[i].setPreferredSize(new Dimension(120, 40)); gbc.gridy = i;
		 * botonesJPanel.add(tamanos[i], gbc); tamanos[i].addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { setSize(600 + 200 *
		 * index, 600 + 200 * index); crearTablero(tableroSize[index]); } }); }
		 * contentPane.add(inicio, BorderLayout.NORTH); contentPane.add(botonesJPanel,
		 * BorderLayout.CENTER);
		 */

		JButton tamañoPequeño = new JButton("Pequeño");
		tamañoPequeño.setPreferredSize(new Dimension(120, 40));
		tamañoPequeño.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc.gridx = 0;
		gbc.gridy = 0;
		botonesJPanel.add(tamañoPequeño, gbc);

		JButton tamañoMediano = new JButton("Mediano");
		tamañoMediano.setPreferredSize(new Dimension(120, 40));// TAMAÑO BOTON
		tamañoMediano.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.gridy = 1;
		botonesJPanel.add(tamañoMediano, gbc);

		JButton tamañoGrande = new JButton("Grande");
		tamañoGrande.setPreferredSize(new Dimension(120, 40));// TAMAÑO
		tamañoGrande.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);

		gbc.gridy = 2;
		botonesJPanel.add(tamañoGrande, gbc);

		// Lo que se visualizará directamente
		contentPane.add(inicio, BorderLayout.NORTH);
		contentPane.add(botonesJPanel, BorderLayout.CENTER);

		// Llamada al método para crear el tablero
		tamañoPequeño.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setSize(600, 600);
				crearTablero(20);
			}
		});
		tamañoMediano.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setSize(800, 800);
				crearTablero(50);
			}
		});
		tamañoGrande.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setSize(1000, 1000);
				crearTablero(100);
			}
		});

	}

	public class PaletaColores extends JPanel {

		public PaletaColores() {
			// Obtener el ancho y la altura configurado como 50 pixeles
			this.setPreferredSize(new Dimension(getWidth(), 50));
			this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
			// Formato para los botnes
			this.setLayout(new GridLayout(0, 6));

			// Crear el array con los colores que deseamos
			Color[] colores = { Color.WHITE, Color.BLUE, Color.GREEN, Color.GRAY, Color.ORANGE, Color.RED };
			for (Color color : colores) {

				// Pintar los botones con sus colores
				JButton buttonColor = new JButton();
				buttonColor.setBackground(color);

				// Para el color blanco mostrar un mensaje de seleccion color
				if (color.equals(Color.WHITE)) {
					buttonColor.setText("Selecciona");
				}

				buttonColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// Si el color es igual a blanco podremos obtener para cambiar las propiedades
						if (colorSeleccionado.equals(Color.WHITE)) {
							Color nuevoColor = JColorChooser.showDialog(null, "Select", colorSeleccionado);
							if (nuevoColor != null) {
								buttonColor.setBackground(nuevoColor);
								buttonColor.setForeground(Color.WHITE);
								// cambiar color aqui
								colorSeleccionado = nuevoColor;
							}

						} else
							// cambiar color aqui
							colorSeleccionado = color;
					}
				});
				this.add(buttonColor);
			}
		}
	}

	public class Casilla extends JPanel {

		private Color colorActual = Color.WHITE;

		public Casilla() {
			setBackground(colorActual);
			setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128, 50), 1));
		}
	}

	private void crearTablero(int f) {

		JPanel tablero = new JPanel(new GridLayout(f, f));
		int anchoVentana = getWidth();
		int altoVentana = getHeight();
		// con esto sacamos el tamaño para q las casillas sean iguales
		int size = Math.min(anchoVentana / f, altoVentana / f);

		for (int fila = 0; fila < f; fila++) {
			for (int columna = 0; columna < f; columna++) {
				Casilla casilla = new Casilla();
				casilla.setPreferredSize(new Dimension(size, size));

				// Añade un MouseListener para cambiar el color al hacer clic
				casilla.addMouseListener(new MouseAdapter() {
					// mousePressed para nada más apretar que cambie
					@Override
					public void mousePressed(MouseEvent e) {
						if (SwingUtilities.isLeftMouseButton(e)) {
							casilla.setBackground(colorSeleccionado);

						}
					}
				});
				//Añadimos el listener a las casillas
				casilla.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						if (SwingUtilities.isLeftMouseButton(e)) {
							// Convetir las coordenadas donde se ha presionado el raton (e.getComponent) con
							// (e.getPoint) obtenemos las coordenadas donde ocurrio el evento, para cuando
							// se arrastre que empieze desde ese punto.
							Point coordenasPanelPresionado = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(),
									tablero);
							// Obtener el componente del panel tablero que se encuentra en la posicion
							// presionada
							Component componente = tablero.getComponentAt(coordenasPanelPresionado);
							// Comprobar si el componente obtenido es una instancia de la clase Casilla
							if (componente instanceof Casilla) {
								componente.setBackground(colorSeleccionado);
							}
						}
					}
				});
				tablero.add(casilla);
				// MAS TARDE PONER EL GRISEN BLANCO , AHORA
				// DEJARLO ASI PARA
				// DISTINGUIR MEJOR EL COLOR
				// casilla.setBackground((fila + columna) % 2 == 0 ? Color.WHITE : Color.WHITE);

			}
		}
		PaletaColores paleta = new PaletaColores(); // Pasa la instancia de Casilla
		contentPane.removeAll();
		BotonesDescartaryGuardar();
		contentPane.add(tablero, BorderLayout.CENTER);
		contentPane.add(paleta, BorderLayout.SOUTH);
		repaint();
		revalidate();
	}

	private void BotonesDescartaryGuardar() {
		JPanel BotonesJuego = new JPanel(new GridBagLayout());

		JButton Borrar = new JButton("Borrar");
		Borrar.setPreferredSize(new Dimension(120, 40));
		Borrar.setFont(new Font("Unispace", Font.BOLD, 12));
		GridBagConstraints gbcBorrar = new GridBagConstraints();
		gbcBorrar.anchor = GridBagConstraints.CENTER;
		gbcBorrar.insets = new Insets(5, 10, 10, 10); // Añade espacio inferior
		gbcBorrar.gridx = 0;
		gbcBorrar.gridy = 0;
		BotonesJuego.add(Borrar, gbcBorrar);

		Borrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				contentPane.removeAll();
				IniciodeJuego();

			}
		});

		JButton Guardar = new JButton("Guardar");
		Guardar.setPreferredSize(new Dimension(120, 40));
		Guardar.setFont(new Font("Unispace", Font.BOLD, 12));
		GridBagConstraints gbcGuardar = new GridBagConstraints();
		gbcGuardar.anchor = GridBagConstraints.CENTER;
		gbcGuardar.insets = new Insets(5, 10, 10, 10); // No añade espacio inferior
		gbcGuardar.gridx = 1;
		gbcGuardar.gridy = 0;
		BotonesJuego.add(Guardar, gbcGuardar);

		contentPane.add(BotonesJuego, BorderLayout.NORTH);
	}

}
