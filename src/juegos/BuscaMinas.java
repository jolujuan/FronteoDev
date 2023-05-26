package juegos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import java.util.Random;
import javax.swing.ImageIcon;

public class BuscaMinas extends JFrame {
	Random random = new Random();
	private Casilla[][] tableroCasillas;
	private JPanel contentPane;
	private JPanel tablero = new JPanel();
	private JPanel tableroSecundario = new JPanel();
	private int tamañoCasilla = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuscaMinas frame = new BuscaMinas();
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
	public BuscaMinas() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("BuscaMinas");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		IniciodeJuego();// Inicio del juego,
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				setVisible(false);

			}
		});
	}

	public void IniciodeJuego() {

		JPanel inicio = new JPanel();
		inicio.setFont(new Font("Verdana", Font.BOLD, 13));
		// Siempre tendra el mismo tamaño
		setSize(500, 500);
		// Centramos pantalla
		centrarInterficiePantalla();

		// Botones de selección de tamaño del tablero
		JPanel botonesJPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);

		JButton tamañoPequeño = new JButton("Facil");
		tamañoPequeño.setPreferredSize(new Dimension(130, 40));
		tamañoPequeño.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc.gridx = 0;
		gbc.gridy = 0;
		botonesJPanel.add(tamañoPequeño, gbc);

		JButton tamañoMediano = new JButton("Normal");
		tamañoMediano.setPreferredSize(new Dimension(130, 40));// TAMAÑO BOTON
		tamañoMediano.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.gridy = 1;
		botonesJPanel.add(tamañoMediano, gbc);

		JButton tamañoGrande = new JButton("Dificil");
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
		JLabel seleccionaTableroJLabel = new JLabel("Selecciona la dificultat de la partida \n");
		inicio.add(seleccionaTableroJLabel);
		seleccionaTableroJLabel.setBorder(new EmptyBorder(30, 0, 0, 0));
		seleccionaTableroJLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPane.add(botonesJPanel, BorderLayout.CENTER);

		// Llamada al método para crear el tablero
		tamañoPequeño.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(470, 470);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(8, 10);// NUMERO DE FILAS 8x8 | NUMERO DE MINAS
				recorrerTablero(8, 10);
				contarMinasAdyacentes(8);

			}
		});
		tamañoMediano.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(670, 670);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(16, 18);
				recorrerTablero(16, 18);
				contarMinasAdyacentes(16);

			}
		});
		tamañoGrande.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(870, 870);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(25, 27);
				recorrerTablero(25, 27);
				contarMinasAdyacentes(25);

			}
		});
		repaint();
		revalidate();
	}

	public class Casilla extends JPanel {

		private Color colorActual = Color.WHITE;

		private boolean tieneMina;
		private int minasAdyacentes;

		public Casilla() {
			setBackground(colorActual);
			setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128, 50), 1));
		}
		

	}

	private void crearTablero(int f, int numeroMinas) {
		JPanel TableroFinal= new JPanel();
		TableroFinal.setLayout(new OverlayLayout(TableroFinal));
		tableroCasillas = new Casilla[f][f];
		// Verificar que el número de minas sea válido
		int totalCasillas = f * f;
		if (numeroMinas > totalCasillas) {
			throw new IllegalArgumentException("El número de minas excede el tamaño del tablero.");
		}

		tablero.setLayout(new GridLayout(f, f));
		int anchoVentana = getWidth();
		int altoVentana = getHeight();
		// con esto sacamos el tamaño para q las casillas sean iguales
		int size = Math.min(anchoVentana / f, altoVentana / f);
		tamañoCasilla = size;
		for (int fila = 0; fila < f; fila++) {
			for (int columna = 0; columna < f; columna++) {

				Casilla casilla = new Casilla();
				casilla.setPreferredSize(new Dimension(size, size));

				tableroCasillas[fila][columna] = casilla;

				tablero.add(casilla);

			}
		}

		
		

		// TABLERO SECUNDARIO DONDE SE TAPARAN LAS MINAS Y LOS NUMEROS .....
		// Crear el tablero secundario con casillas de fondo gris

		tableroSecundario = new JPanel();
		tableroSecundario.setPreferredSize(new Dimension(f * tamañoCasilla, f * tamañoCasilla));
		tableroSecundario.setBounds(50, 50, f * tamañoCasilla, f * tamañoCasilla);
		tableroSecundario.setLayout(new GridLayout(f, f));

		for (int fila = 0; fila < f; fila++) {
			for (int columna = 0; columna < f; columna++) {
				Casilla casilla = new Casilla();

				casilla.setPreferredSize(new Dimension(tamañoCasilla, tamañoCasilla));
				casilla.addMouseListener(new MouseAdapter() {
		            @Override
		            public void mouseClicked(MouseEvent e) {
		                if (e.getButton() == MouseEvent.BUTTON1) {
		                    // Código a ejecutar cuando se hace clic izquierdo en el panel
		                	casilla.setVisible(false);
		                }
		            }
		        });
				tableroSecundario.add(casilla);
			}
		}
		TableroFinal.add(tableroSecundario);
		TableroFinal.add(tablero);
		contentPane.removeAll();
		contentPane.add(TableroFinal, BorderLayout.CENTER);

		BotonesDescartaryGuardar();

		repaint();
		revalidate();
	}

	public void recorrerTablero(int f, int numeroMinas) {
		int minasAsignadas = 0; // Contador de minas asignadas

		while (minasAsignadas < numeroMinas) {
			int filaAleatoria = obtenerFilaAleatoria(f);
			int columnaAleatoria = obtenerColumnaAleatoria(f);
			for (int fila = 0; fila < f; fila++) {
				for (int columna = 0; columna < f; columna++) {

					Casilla casilla = tableroCasillas[fila][columna];
					if (filaAleatoria == fila && columnaAleatoria == columna) {

						if (!casillaTieneMina(filaAleatoria, columnaAleatoria, casilla)) {
							asignarMina(filaAleatoria, columnaAleatoria, casilla);
							minasAsignadas++;
						}
					}

				}
			}
		}
	}

	public void contarMinasAdyacentes(int f) {
		for (int fila = 0; fila < f; fila++) {
			// Iteramos sobre cada fila del tablero
			for (int columna = 0; columna < f; columna++) {
				// Si la celda actual contiene una mina, no necesitamos contar las minas
				// adyacentes
				if (tableroCasillas[fila][columna].tieneMina) {
					continue;
				}

				int minasAdyacentes = 0;

				// Revisar las celdas adyacentes
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						// Calculamos las coordenadas de la celda adyacente
						int filaAdyacente = fila + i;
						int columnaAdyacente = columna + j;

						// Verificar que la celda adyacente se encuentre dentro del tablero
						if (filaAdyacente >= 0 && filaAdyacente < f && columnaAdyacente >= 0 && columnaAdyacente < f) {
							// Si la celda adyacente tiene una mina, incrementamos el contador de minas
							// adyacentes
							if (tableroCasillas[filaAdyacente][columnaAdyacente].tieneMina) {
								minasAdyacentes++;
							}
						}
					}
				}

				// Asignar el número de minas adyacentes a la celda actual
				tableroCasillas[fila][columna].minasAdyacentes = minasAdyacentes;
				// Si hay minas adyacentes, añadimos una etiqueta a la celda con el número de
				// minas adyacentes
				if (minasAdyacentes > 0) {
					JLabel label = new JLabel(String.valueOf(minasAdyacentes));
					label.setFont(new Font("Dialog", Font.BOLD, 20));
//					label.setHorizontalAlignment(JLabel.CENTER);
//					label.setVerticalAlignment(JLabel.CENTER);

					if (label.getText().equals("1")) {
						label.setForeground(Color.decode("#0000FF"));

					} else if (label.getText().equals("2")) {
						label.setForeground(Color.green);

					} else if (label.getText().equals("3")) {
						label.setForeground(Color.red);

					} else if (label.getText().equals("4")) {
						label.setForeground(Color.decode("#000080"));

					} else {
						label.setForeground(Color.decode("#8B4513"));

					}
					tableroCasillas[fila][columna].add(label);
				}

			}
		}
	}

	private int obtenerFilaAleatoria(int f) {
		return random.nextInt(f);
	}

	private int obtenerColumnaAleatoria(int f) {
		return random.nextInt(f);
	}

	private boolean casillaTieneMina(int fila, int columna, Casilla casilla) {
		// Comprobar si la casilla ya tiene un componente JLabel
		Component[] components = casilla.getComponents();
		for (Component component : components) {
			if (component instanceof JLabel) {
				// La casilla ya tiene una etiqueta, se puede realizar alguna acción o ignorar
				// Puedes agregar aquí tu lógica específica
				return true; // Salir del método para no asignar una nueva imagen
			}
		}

		return false;
		// Verificar si la casilla en la fila y columna especificadas ya tiene una mina
		// asignada
	}

	private void asignarMina(int fila, int columna, Casilla casilla) {
		// Asignar una mina a la casilla en la fila y columna

		ImageIcon icono = new ImageIcon("src/imagenes/mina.png"); // Ajusta la ruta y el nombre del archivo de imagen

		// Establecer que esta casilla tiene una mina
		casilla.tieneMina = true;
		// Obtener el tamaño deseado para la imagen
		int anchoDeseado = 25; // Ajusta el ancho deseado de la imagen
		int altoDeseado = 25; // Ajusta el alto deseado de la imagen

		// Redimensionar la imagen al tamaño deseado
		Image imagenOriginal = icono.getImage();
		Image imagenRedimensionada = imagenOriginal.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);

		// Crear un nuevo ImageIcon con la imagen redimensionada
		ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);
		// Asignar la imagen al JLabel de la casilla
		JLabel etiqueta = new JLabel(iconoRedimensionado);
		casilla.add(etiqueta);
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

				IniciodeJuego();

			}
		});
		JButton Nueva = new JButton("Nueva partida");
		Nueva.setPreferredSize(new Dimension(120, 40));
		Nueva.setFont(new Font("Unispace", Font.BOLD, 12));
		GridBagConstraints gbcGNewGame = new GridBagConstraints();
		gbcGNewGame.anchor = GridBagConstraints.CENTER;
		gbcGNewGame.insets = new Insets(5, 10, 10, 10); // No añade espacio inferior
		gbcGNewGame.gridx = 2;
		gbcGNewGame.gridy = 0;
		BotonesJuego.add(Nueva, gbcGNewGame);
		Nueva.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		JButton Ranking = new JButton("Ranking");
		Ranking.setPreferredSize(new Dimension(120, 40));
		Ranking.setFont(new Font("Unispace", Font.BOLD, 12));
		GridBagConstraints gbcGNewRank = new GridBagConstraints();
		gbcGNewRank.anchor = GridBagConstraints.CENTER;
		gbcGNewRank.insets = new Insets(5, 10, 10, 10); // No añade espacio inferior
		gbcGNewRank.gridx = 3;
		gbcGNewRank.gridy = 0;
		BotonesJuego.add(Ranking, gbcGNewRank);
		Nueva.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		JButton Guardar = new JButton("Guardar");
		Guardar.setPreferredSize(new Dimension(120, 40));
		Guardar.setFont(new Font("Unispace", Font.BOLD, 12));
		GridBagConstraints gbcGuardar = new GridBagConstraints();
		gbcGuardar.anchor = GridBagConstraints.CENTER;
		gbcGuardar.insets = new Insets(5, 10, 10, 10); // No añade espacio inferior
		gbcGuardar.gridx = 4;
		gbcGuardar.gridy = 0;
		BotonesJuego.add(Guardar, gbcGuardar);
		Guardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		contentPane.add(BotonesJuego, BorderLayout.SOUTH);
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
