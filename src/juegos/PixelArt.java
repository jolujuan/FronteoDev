package juegos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PixelArt extends JFrame {
	private Color colorSeleccionado = Color.BLACK;
	private JPanel contentPane;
	private JPanel tablero = new JPanel();

	//Para trabajar directamente eliminaremos desmarcaremos el main
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PixelArt frame = new PixelArt();
					frame.setSize(500, 500);
					frame.setVisible(true);
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("PixelArt");
//		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		IniciodeJuego();
		 addWindowListener(new WindowAdapter() {
         	
             @Override      
             public void windowClosed(WindowEvent e) {
             	setVisible(false);
                
             }
         });

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

	private void IniciodeJuego() {

		// Texto de inicio: SELECCION DE TAMAÑO DE TABLERO
		JPanel inicio = new JPanel();
		inicio.setFont(new Font("Verdana", Font.BOLD, 13));
		JLabel seleccionaTableroJLabel = new JLabel("Selecciona el tamaño del tablero");
		seleccionaTableroJLabel.setBorder(new EmptyBorder(30, 0, 0, 0));
		seleccionaTableroJLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		inicio.add(seleccionaTableroJLabel);
		// Siempre tendra el mismo tamaño
		setSize(500, 500);
		// Centramos pantalla
		centrarInterficiePantalla();

		// Botones de selección de tamaño del tablero
		JPanel botonesJPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);

		JButton tamañoPequeño = new JButton("Pequeño");
		tamañoPequeño.setPreferredSize(new Dimension(130, 40));
		tamañoPequeño.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc.gridx = 0;
		gbc.gridy = 0;
		botonesJPanel.add(tamañoPequeño, gbc);

		JButton tamañoMediano = new JButton("Mediano");
		tamañoMediano.setPreferredSize(new Dimension(130, 40));// TAMAÑO BOTON
		tamañoMediano.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.gridy = 1;
		botonesJPanel.add(tamañoMediano, gbc);

		JButton tamañoGrande = new JButton("Grande");
		tamañoGrande.setPreferredSize(new Dimension(130, 40));// TAMAÑO
		tamañoGrande.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);

		gbc.gridy = 2;
		botonesJPanel.add(tamañoGrande, gbc);

		JButton cargarPartida = new JButton("Cargar Partida");
		cargarPartida.setPreferredSize(new Dimension(130, 40));// TAMAÑO
		cargarPartida.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);

		gbc.gridy = 3;
		botonesJPanel.add(cargarPartida, gbc);
		// Lo que se visualizará directamente
		contentPane.add(inicio, BorderLayout.NORTH);
		contentPane.add(botonesJPanel, BorderLayout.CENTER);

		// CARGAR PARTIDA
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
				setSize(470, 470);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(20);
			}
		});
		tamañoMediano.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(670, 670);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(50);
			}
		});
		tamañoGrande.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(870, 870);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(100);
			}
		});
		repaint();
		revalidate();

	}
	
	public class MyButton extends JButton {
	    private Color color;

	    public MyButton(Color color) {
	        this.color = color;
	        setOpaque(true);
	        setBorderPainted(false);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        g.setColor(color);
	        g.fillRect(0, 0, getWidth(), getHeight());
	        super.paintComponent(g);
	    }
	}


	public class PaletaColores extends JPanel {

		public PaletaColores() {
			// Obtener el ancho y la altura configurado como 50 pixeles
			this.setPreferredSize(new Dimension(getWidth(), 50));
			// Formato para los botnes
			this.setLayout(new GridLayout(0, 6));
			this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

			// Crear el array con los colores que deseamos
			Color[] colores = { Color.WHITE, Color.BLUE, Color.GREEN, Color.GRAY, Color.ORANGE, Color.RED };
			
			for (Color color : colores) {

				// Pintar los botones con sus colores
				JButton buttonColor = new JButton();
				 //buttonColor.setOpaque(false);
				buttonColor.setBackground(color);
				buttonColor.setBorder(new LineBorder(color,15));


				// Para el color blanco mostrar un mensaje de seleccion color
				if (color.equals(Color.WHITE)) {
					buttonColor.setText("Selecciona");
					buttonColor.setBackground(new Color(230,230,230));
					buttonColor.setBorder(null);
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

					// System.out.println("Estado del tablero guardado correctamente en: " +
					// filePath);
				}
			}
		} catch (IOException e) {
			System.out.println("Error al guardar el estado del tablero: " + e.getMessage());
		}
	}

	private void cargarPartidaDesdeArchivo(String filepath) {
		File file = new File(filepath);
		Color color = null;
		int contadorLinieas = 0;
		String lineaString = null;

		try {
			BufferedReader leerCasilla = new BufferedReader(new FileReader(file));
			BufferedReader contarcasillas = new BufferedReader(new FileReader(file));
			while ((lineaString = contarcasillas.readLine()) != null) {
				contadorLinieas++; // guarda en el numero de lineas que equivalen a las casillas y sus colores adjuntos , y con el la raiz cuadrada sacamos el tamaño el tablero
			}
			if (Math.sqrt(contadorLinieas) == 20) {
				setSize(470, 470);
				crearTablero(20);

			} else if (Math.sqrt(contadorLinieas) == 50) {
				setSize(670, 670);
				crearTablero(50);

			} else if (Math.sqrt(contadorLinieas) == 100) {
				setSize(870, 870);
				crearTablero(100);

			} else {
				System.err.println("No creo tablero");
			}

			if (file.exists()) {
				// Obtenermos todos los paneles del tablero en una matriz
				Component[] casillas = tablero.getComponents();

				String line = null;

				int recorrido = 0;
				while ((line = leerCasilla.readLine()) != null) {
					// En cada linea del archivo tenemos 3 series de colores (RGB)
					String[] colores = line.split(",");
					if (colores.length == 3) {

						int red = Integer.parseInt(colores[0]);
						int green = Integer.parseInt(colores[1]);
						int blue = Integer.parseInt(colores[2]);
						// Si la linea no tiene color (255,255,255): es blanco
						// Entonces crea y guarda el color blanco dentro del array casillas
						if (red == 255 && blue == 255 && green == 255) {
							color = new Color(255, 255, 255);
							casillas[recorrido].setBackground(color);
							recorrido++;
						} else {
							// Sino guardar el color con las propiedades leidas del fichero
							color = new Color(red ,green , blue);
							//System.out.println(" " + recorrido + " color " + color);
							
							casillas[recorrido].setBackground(color);
							recorrido++;
						}
					}
				}
				leerCasilla.close();
				contarcasillas.close();
			}
		} catch (Exception e) {
			System.out.println("Error cargar partida: " + e);
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
				BufferedImage image = new BufferedImage(tablero.getWidth(), tablero.getHeight(),
						BufferedImage.TYPE_INT_ARGB);

				// Obtiene el contexto gráfico de la imagen
				Graphics2D g2d = image.createGraphics();

				// Dibuja el contenido del panel en el contexto gráfico de la imagen
				tablero.paint(g2d);

				// Libera los recursos del contexto gráfico
				g2d.dispose();

				// Ruta de la imagen
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
