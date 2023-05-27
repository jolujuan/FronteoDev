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
import javax.swing.JOptionPane;
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
					UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");

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

			}
		});
		repaint();
		revalidate();
	}

	public class Casilla extends JButton {

		private Color colorActual = Color.WHITE;

		private boolean vacia;
		private boolean tieneNum;
		private boolean tieneMina;
		private int minasAdyacentes;
//		private int filaC;
//		private int columnaC;
		private boolean esRevelada = false;

		public Casilla() {
			super(" ");
			this.tieneMina = false;
			this.minasAdyacentes = 0;
		}

		public void setTieneMina(boolean tieneMina) {
			this.tieneMina = tieneMina;
		}

		public boolean getTieneMina() {
			return this.tieneMina;
		}

		public void setMinasAdyacentes(int minasAdyacentes) {
			this.minasAdyacentes = minasAdyacentes;
		}

		public int getMinasAdyacentes() {
			return this.minasAdyacentes;
		}
		
		 private void actualizarApariencia() {
		        if (esRevelada) {
		            if (tieneMina) {
		               
		            } else {
		               
		            	setFont(new Font("Dialog", Font.BOLD, 20));
						setForeground(getColorNumero(minasAdyacentes));
						setText(String.valueOf(minasAdyacentes));
						setBackground(new Color(128, 128, 128, 50));
		            }
		        } else {
		           
		            setText("");
		        }
		    }

	}

	private void crearTablero(int f, int numeroMinas) {
//		JPanel TableroFinal= new JPanel();
//		TableroFinal.setLayout(new OverlayLayout(TableroFinal));

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
				int filas = fila;
				int columnas = columna;
				Casilla casilla = new Casilla();

				casilla.setPreferredSize(new Dimension(size, size));

				casilla.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (casilla.getTieneMina()) {

								// Asignar la imagen de la mina aquí
								ImageIcon icono = new ImageIcon("src/imagenes/mina.png"); // Ajusta la ruta y el nombre
								// del archivo de imagen
								// Obtener el tamaño deseado para la imagen
								int anchoDeseado = 25; // Ajusta el ancho deseado de la imagen
								int altoDeseado = 25; // Ajusta el alto deseado de la imagen
								// Redimensionar la imagen al tamaño deseado
								Image imagenOriginal = icono.getImage();
								Image imagenRedimensionada = imagenOriginal.getScaledInstance(anchoDeseado, altoDeseado,
										Image.SCALE_SMOOTH);
								// Crear un nuevo ImageIcon con la imagen redimensionada
								ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);

								// Asignar la imagen al JLabel de la casilla
								JLabel etiqueta = new JLabel(iconoRedimensionado);
//								casilla.add(etiqueta);
								casilla.setIcon(iconoRedimensionado);
								
								casilla.setText(""); // Ya no queremos mostrar un texto si hay una mina
								casilla.setEnabled(false);
								
								casilla.esRevelada = true;

							} else {
								int minasAdyacentes = casilla.getMinasAdyacentes();
								if (minasAdyacentes > 0) {
//					                    casilla.setForeground(getColorNumero(minasAdyacentes));
									casilla.setFont(new Font("Dialog", Font.BOLD, 20));
									casilla.setForeground(getColorNumero(minasAdyacentes));
									casilla.setText(String.valueOf(minasAdyacentes));
									casilla.setBackground(new Color(128, 128, 128, 50));// color grisacio transparente para ver los clicks 
									casilla.esRevelada = true;

								}
//					                casilla.setEnabled(false);
							}

							if (casilla.tieneMina == true) {
								JOptionPane.showMessageDialog(null, "	Has perdido		");
							}

							if (casilla.tieneMina == false && casilla.minasAdyacentes == 0) {
//								JOptionPane.showMessageDialog(null, "	Casilla vacia		");

								revelarCasilla(filas, columnas);
							}
						}
					}
				});
				tableroCasillas[fila][columna] = casilla;
				tablero.add(casilla);
			}
		}

		contentPane.removeAll();
		contentPane.add(tablero, BorderLayout.CENTER);
		BotonesDescartaryGuardar();

	}
// de manera recursiva entra a ver el estado de la casilla, si no ha sido revelada la revela y revelara las adyacente
	public void revelarCasilla(int fila, int columna) {
		// Verificar los límites del tablero
		if (fila < 0 || fila >= tableroCasillas.length || columna < 0 || columna >= tableroCasillas.length) {
			return;
		}

		Casilla casilla = tableroCasillas[fila][columna];

		// Verificar si la casilla ya ha sido revelada
		if (casilla.esRevelada == true) {
			return;
		}

		// Marcar la casilla como revelada y mostrar su contenido
		casilla.esRevelada = true;
		casilla.actualizarApariencia();

		// Verificar si la casilla es vacía
		if (casilla.tieneMina == false && casilla.minasAdyacentes == 0) {
			// Recursivamente revelar las casillas vecinas vacías
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (i == 0 && j == 0) {
						continue; // Saltar la casilla actual
					}
					revelarCasilla(fila + i, columna + j);
				}
			}

		}
	}

	private Color getColorNumero(int numeroMinas) {
		switch (numeroMinas) {
		case 0:
			return new Color(128, 128, 128, 0);//aqui pongo que el numero cero se ponga totalmente transparente
		case 1:
			return Color.decode("#0000FF");
		case 2:
			return Color.GREEN;
		case 3:
			return Color.RED;
		case 4:
			return Color.decode("#000080");
		default:
			return Color.decode("#8B4513");
		}
	}

	public void recorrerTablero(int f, int numeroMinas) {
		int minasAsignadas = 0; // Contador de minas asignadas

		while (minasAsignadas < numeroMinas) {
			int filaAleatoria = obtenerFilaAleatoria(f);
			int columnaAleatoria = obtenerColumnaAleatoria(f);
			Casilla casilla = tableroCasillas[filaAleatoria][columnaAleatoria];
			if (!casilla.getTieneMina()) {
				casilla.setTieneMina(true);
				minasAsignadas++;
				actualizarNumerosAdyacentes(filaAleatoria, columnaAleatoria);
			}
		}
	}

	private void actualizarNumerosAdyacentes(int fila, int columna) {
		for (int i = fila - 1; i <= fila + 1; i++) {
			for (int j = columna - 1; j <= columna + 1; j++) {
				if (i >= 0 && i < tableroCasillas.length && j >= 0 && j < tableroCasillas[0].length) {
					Casilla casilla = tableroCasillas[i][j];
					if (!casilla.getTieneMina()) {
//	                    casilla.setFont(new Font("Dialog", Font.BOLD, 20));
//	                    casilla.setForeground(Color.red);
						casilla.setMinasAdyacentes(casilla.getMinasAdyacentes() + 1);
					}
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
