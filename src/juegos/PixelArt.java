package juegos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	private JPanel tablero = new JPanel();

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

	private void centrarInterficiePantalla() {
		// Calcular la posición de la ventana
		Dimension tamañoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int width = this.getSize().width;
		int height = this.getSize().height;
		int x = (tamañoPantalla.width - width) / 2; // Centrado horizontalmente
		int y = (tamañoPantalla.height - height) / 2;
		; // En la parte superior de la pantalla

		// Establecer la posición de la ventana
		this.setLocation(x, y);
	}

	private void IniciodeJuego() {

		// Texto de inicio: SELECCION DE TAMAÑO DE TABLERO
		JPanel inicio = new JPanel();
		inicio.setFont(new Font("Verdana", Font.BOLD, 13));
		JLabel seleccionaTableroJLabel = new JLabel("Selecciona el tamaño del tablero");
		seleccionaTableroJLabel.setFont(new Font("Unispace", Font.BOLD, 12));
		inicio.add(seleccionaTableroJLabel);
		// Siempre tendra el mismo tamaño
		setSize(600, 600);
		// Centramos pantalla
		centrarInterficiePantalla();

		// Botones de selección de tamaño del tablero
		JPanel botonesJPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);

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

		
		JButton cargarPartida = new JButton("Cargar partida");
		cargarPartida.setPreferredSize(new Dimension(120, 40));// TAMAÑO
		cargarPartida.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);

		gbc.gridy = 3;
		botonesJPanel.add(cargarPartida, gbc);
		// Lo que se visualizará directamente
		contentPane.add(inicio, BorderLayout.NORTH);
		contentPane.add(botonesJPanel, BorderLayout.CENTER);

		
		//CARGAR PARTIDA
		cargarPartida.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cargarPartidaDesdeArchivo("PixelArt.txt");
			}
		});
		// Llamada al método para crear el tablero
		tamañoPequeño.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(600, 600);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(20);
			}
		});
		tamañoMediano.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(800, 800);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(50);
			}
		});
		tamañoGrande.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(1000, 1000);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(100);
			}
		});
		repaint();
		revalidate();

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
						// Si el colorSeleccionado es blanco, abrimos el diálogo de selección de color
						if (color.equals(Color.WHITE)) {
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

		tablero.setLayout(new GridLayout(f, f));
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
					// Pintar las casillas seleccionadas
					// mousePressed para nada más apretar que cambie
					@Override
					public void mousePressed(MouseEvent e) {
						if (SwingUtilities.isLeftMouseButton(e)) {
							casilla.setBackground(colorSeleccionado);
						}
						if (SwingUtilities.isRightMouseButton(e)) {
							casilla.setBackground(Color.WHITE);
						}
					}
				});
				// Añadimos el listener para pintar las casillas arrastradas
				casilla.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						if (SwingUtilities.isLeftMouseButton(e)) {
							// Convetir las coordenadas donde se ha presionado el raton (e.getComponent) con
							// (e.getPoint) obtenemos las coordenadas donde ocurrio el evento, para cuando
							// se arrastre que empieze desde ese punto.
							Point coordenadasPanelPresionado = SwingUtilities.convertPoint(e.getComponent(),
									e.getPoint(), tablero);
							// Obtener el componente del panel tablero que se encuentra en la posicion
							// presionada
							Component componente = tablero.getComponentAt(coordenadasPanelPresionado);
							// Comprobar si el componente obtenido es una instancia de la clase Casilla
							if (componente instanceof Casilla) {
								componente.setBackground(colorSeleccionado);
							}
						} else {
							// Borrar las casillas arrastradas
							// Hacer lo mismo pero a la inversa para borrar desde el click derecho
							Point coordenadasPanelPresionado = SwingUtilities.convertPoint(e.getComponent(),
									e.getPoint(), tablero);
							Component componente = tablero.getComponentAt(coordenadasPanelPresionado);
							if (componente instanceof Casilla) {
								componente.setBackground(Color.WHITE);
							}
						}
					}
				});
				tablero.add(casilla);
				// MAS TARDE PONER EL GRISEN BLANCO , AHORA
				// DEJARLO ASI PARA
				// DISTINGUIR MEJOR EL COLOR
				// Color colorGris = new Color(217, 217, 217);
				// casilla.setBackground((fila + columna) % 2 == 0 ? Color.WHITE : colorGris);

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

	private void guardarEstadoTablero(String filePath) {
		File file = new File(filePath);
		try (FileWriter writer = new FileWriter(file)) {
			// Verificar si el archivo no existe y crearlo
			if (!file.exists()) {
				file.createNewFile();
			}

			// Recorrer todas las casillas del tablero y guardar su color en el archivo
			Component[] casillas = tablero.getComponents();

			for (Component componente : casillas) {

				if (componente instanceof Casilla) {
					Color color = componente.getBackground();
					// Escribir el color en el archivo en formato RGB
					writer.write(color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "\n");

					System.out.println("Estado del tablero guardado correctamente en: " + filePath);
				}
			}
		} catch (IOException e) {
			System.out.println("Error al guardar el estado del tablero: " + e.getMessage());
		}
//		contentPane.removeAll();
//		IniciodeJuego();
	}
	private void cargarPartidaDesdeArchivo(String filepath) {
		File file =new File(filepath);
		try {
			FileReader leerCasilla=new FileReader(file);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
				tablero.removeAll();
				// Reininializar el color
				colorSeleccionado = Color.BLACK;
				IniciodeJuego();

			}
		});
		JButton ExportarImagen = new JButton("Exportar");
		ExportarImagen.setPreferredSize(new Dimension(120, 40));
		ExportarImagen.setFont(new Font("Unispace", Font.BOLD, 12));
		GridBagConstraints gbcexport = new GridBagConstraints();
		gbcexport.anchor = GridBagConstraints.CENTER;
		gbcexport.insets = new Insets(5, 10, 10, 10); // No añade espacio inferior
		gbcexport.gridx = 2;
		gbcexport.gridy = 0;
		BotonesJuego.add(ExportarImagen, gbcexport);
		
		
		ExportarImagen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image = new BufferedImage(tablero.getWidth(), tablero.getHeight(), BufferedImage.TYPE_INT_ARGB);
				
				
				 // Obtiene el contexto gráfico de la imagen
	            Graphics2D g2d = image.createGraphics();
	            
	            // Dibuja el contenido del panel en el contexto gráfico de la imagen
	            tablero.paint(g2d);
	            
	            // Libera los recursos del contexto gráfico
	            g2d.dispose();
	            
	            
	            //Ruta de la imagen 
	            File outputFile = new File("FotoExportada.png");
	            // Guarda la imagen en el archivo en formato PNG
	            try {
					ImageIO.write(image, "png", outputFile);
					System.out.println("Se ha guardado perfectamente");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				
			}
		});

		JButton Guardar = new JButton("Guardar");
		Guardar.setPreferredSize(new Dimension(120, 40));
		Guardar.setFont(new Font("Unispace", Font.BOLD, 12));
		GridBagConstraints gbcGuardar = new GridBagConstraints();
		gbcGuardar.anchor = GridBagConstraints.CENTER;
		gbcGuardar.insets = new Insets(5, 10, 10, 10); // No añade espacio inferior
		gbcGuardar.gridx = 3;
		gbcGuardar.gridy = 0;
		BotonesJuego.add(Guardar, gbcGuardar);
		Guardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// Mostrar un cuadro de diálogo para que el usuario seleccione la ubicación y el
				// nombre del archivo
				// Guardar el estado del tablero en el archivo seleccionado
				String filePath = "PixelArt.txt"; // Aquí debes asignar la ruta del archivo seleccionado
				guardarEstadoTablero(filePath);

			}
		});

		contentPane.add(BotonesJuego, BorderLayout.NORTH);
	}
}
