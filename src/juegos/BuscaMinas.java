package juegos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import conexionBaseDatos.Conexion;
import guardarCargar.GuardarCargar;

public class BuscaMinas extends JFrame {
	// Variables para crear
	Random random = new Random();
	private Casilla[][] tableroCasillas;
	private JPanel contentPane;
	private JPanel tablero = new JPanel();
	private JButton reinicio = new JButton();

	// Variables comprobaciones juego
	private boolean juegoTerminado = false;
	private int contadorBanderas = 0;
	private String nombreTablero;
	private boolean esReveladaGlobal = false;

	// Mostrar mensajes de banderas
	private JLabel labelMinasRestantes = new JLabel("");
	private JLabel labelCasillasaRevelar = new JLabel("");

	// Para mostrar si hay victoria
	private int ContadorCasillasinrevelar = 0;
	private int CasillasRevelarReset = 0;

	// Variables de tiempo
	private int segons;
	JLabel labelTemps = new JLabel();
	transient Timer timer;

	// variable para controlar cómo se cierra la ventana
	private boolean botonPresionado = false;
	public boolean cargar = false;
	private BuscaMinas buscaMinasFrame;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					BuscaMinas frame = new BuscaMinas("joselu@gmail.com");
					frame.setSize(500, 500);
					frame.setVisible(true);
					UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BuscaMinas(String correo) {
		// Pasar la ventana a una variable global
		buscaMinasFrame = this;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("BuscaMinas");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		IniciodeJuego(correo);// Inicio del juego,
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				setVisible(false);

			}
		});
	}

	public void IniciodeJuego(String correo) {

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
		tamañoPequeño.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tamañoPequeño.setPreferredSize(new Dimension(130, 40));
		tamañoPequeño.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc.gridx = 0;
		gbc.gridy = 0;
		botonesJPanel.add(tamañoPequeño, gbc);

		JButton tamañoMediano = new JButton("Normal");
		tamañoMediano.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tamañoMediano.setPreferredSize(new Dimension(130, 40));// TAMAÑO BOTON
		tamañoMediano.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.gridy = 1;
		botonesJPanel.add(tamañoMediano, gbc);

		JButton tamañoGrande = new JButton("Dificil");
		tamañoGrande.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tamañoGrande.setPreferredSize(new Dimension(130, 40));// TAMAÑO
		tamañoGrande.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);

		gbc.gridy = 2;
		botonesJPanel.add(tamañoGrande, gbc);

		JButton cargarPartida = new JButton("Cargar Partida");
		cargarPartida.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cargarPartida.setPreferredSize(new Dimension(130, 40));// TAMAÑO
		cargarPartida.setFont(new Font("Unispace", Font.BOLD, 12));// FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);
		cargarPartida.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				botonPresionado = true; // se ha presionado el botón

				if (!cargar) { // Verificar si cargar está abierto
					cargar = true;
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								// buscaMinasFrame.setVisible(false);

								GuardarCargar frame = new GuardarCargar(BuscaMinas.this, correo);
								frame.setSize(500, 500);
								centrarInterficiePantalla();
								frame.setVisible(true);

								frame.addWindowListener(new WindowAdapter() {
									@Override
									public void windowClosed(WindowEvent e) {
										// Una vez cerrado la ventana de cargar, iniciar el juego que hayas
										// seleccionado
										String archivoDescarga = "partidaCargadaBuscaMinas.datos";
										cargarPartidaDesdeArchivo(archivoDescarga, correo);

										cargar = false; // Restablecer como cerrado
										frame.dispose();
										buscaMinasFrame.setVisible(true);
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
				setSize(370, 575);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(8, 10, correo);// NUMERO DE FILAS 8x8 | NUMERO DE MINAS
				generarMinas(8, 10);

				// Lo utilizaremos luego para configurar banderas, nueva Partida o imagen
				nombreTablero = "pequeño";

				// Declaramos las banderas que contendra
				contadorBanderas = 10;

				ContadorCasillasinrevelar = 54;
				labelCasillasaRevelar.setText(Integer.toString(ContadorCasillasinrevelar));
				CasillasRevelarReset = ContadorCasillasinrevelar;

				// Inicialmente mostrarara las banderas que tiene
				labelMinasRestantes.setText(Integer.toString(10));
			}
		});
		tamañoMediano.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(570, 775);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(16, 40, correo);
				generarMinas(16, 40);
				nombreTablero = "mediano";

				contadorBanderas = 40;

				ContadorCasillasinrevelar = 216;
				labelCasillasaRevelar.setText(Integer.toString(216));

				// Banderas
				labelMinasRestantes.setText(Integer.toString(40));
			}
		});
		tamañoGrande.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(770, 975);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(25, 80, correo);
				generarMinas(25, 80);

				nombreTablero = "grande";

				contadorBanderas = 80;

				ContadorCasillasinrevelar = 545;
				labelCasillasaRevelar.setText(Integer.toString(545));
				// Banderass
				labelMinasRestantes.setText(Integer.toString(80));
			}
		});
		repaint();
		revalidate();
	}

	public class Casilla extends JButton {

		private boolean tieneMina = false;
		private boolean primeraMinaRevelada = false;
		private boolean tieneBandera = false;
		private int minasAdyacentes;
		private boolean quitarBandera = false;
		private boolean esRevelada = false;

		public Casilla() {
			super(" ");
			this.minasAdyacentes = 0;
		}

		public void setTieneMina(boolean tieneMina) {
			this.tieneMina = tieneMina;
		}

		public boolean getTieneMina() {
			return this.tieneMina;
		}

		public boolean getPrimeraMinaRevelada() {
			return primeraMinaRevelada;
		}

		public void setPrimeraMinaRevelada(boolean primeraMinaRevelada) {
			this.primeraMinaRevelada = primeraMinaRevelada;
		}

		public boolean getQuitarBandera() {
			return this.quitarBandera;
		}

		public void setQuitarBandera(boolean quitarBandera) {
			this.quitarBandera = quitarBandera;
		}

		public boolean getTieneBandera() {
			return tieneBandera;
		}

		public void setTieneBandera(boolean tieneBandera) {
			this.tieneBandera = tieneBandera;
		}

		public void setMinasAdyacentes(int minasAdyacentes) {
			this.minasAdyacentes = minasAdyacentes;
		}

		public int getMinasAdyacentes() {
			return this.minasAdyacentes;
		}

		public boolean setEsRevelada(boolean esRevelada) {
			esReveladaGlobal = esRevelada;

			return this.esRevelada;

		}

		private void actualizarApariencia() {
			if (esRevelada) {
				if (esRevelada && !tieneMina) {
					setFont(new Font("Dialog", Font.BOLD, 20));
					setForeground(getColorNumero(minasAdyacentes));
					setText(String.valueOf(minasAdyacentes));
					setBackground(new Color(128, 128, 128, 50));
				}
			}
		}
	}

	private void crearTablero(int f, int numeroMinas, String correo) {

		// Verificar que el número de minas sea válido
		int totalCasillas = f * f;

		if (numeroMinas > totalCasillas) {
			throw new IllegalArgumentException("El número de minas excede el tamaño del tablero.");
		}

		if (tableroCasillas == null) {

			tableroCasillas = new Casilla[f][f];
			tablero.setLayout(new GridLayout(f, f));

			// con esto sacamos el tamaño para q las casillas sean iguales
			int size = Math.min(getWidth() / f, getHeight() / f);

			for (int fila = 0; fila < f; fila++) {
				for (int columna = 0; columna < f; columna++) {

					Casilla casilla = new Casilla();
					casilla.setPreferredSize(new Dimension(size, size));

					final int filaFinal = fila;
					final int columnaFinal = columna;

					casilla.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// Mientras no encuentres mina seguir
							if (!juegoTerminado) {
								if (SwingUtilities.isLeftMouseButton(e)) {

									// Para arrancar solo una vez
									iniciaComptador();

									if (!casilla.getTieneBandera()) {
										manejarClick(casilla, filaFinal, columnaFinal);
									}
								} else if (SwingUtilities.isRightMouseButton(e)) {

									// Dependiendo el tablero le pasaremos las banderas
									switch (nombreTablero) {
									case "pequeño": {

										if (!casilla.getTieneBandera()) {
											mostrarBandera(casilla, 10);

										} else {
											quitarBandera(casilla, 10);
										}
										break;
									}
									case "mediano": {

										if (!casilla.getTieneBandera()) {
											mostrarBandera(casilla, 40);

										} else {
											quitarBandera(casilla, 40);
										}
										break;
									}
									case "grande": {

										if (!casilla.getTieneBandera()) {
											mostrarBandera(casilla, 80);

										} else {
											quitarBandera(casilla, 80);
										}
										break;
									}
									default:
										throw new IllegalArgumentException("Unexpected value: " + nombreTablero);
									}
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

			BotonesDescartaryGuardar(correo);

		} else {
			// Marcar para reiniciar partida cargada
			esReveladaGlobal = true;
			tablero.setLayout(new GridLayout(f, f));

			for (int fila = 0; fila < f; fila++) {
				for (int columna = 0; columna < f; columna++) {

					final int filaFinal = fila;
					final int columnaFinal = columna;

					Casilla casilla = tableroCasillas[fila][columna];
					casilla.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// Mientras no encuentres mina seguir
							if (!juegoTerminado) {
								if (SwingUtilities.isLeftMouseButton(e)) {

									// Para arrancar solo una vez
									iniciaComptador();

									if (!casilla.getTieneBandera()) {
										manejarClick(casilla, filaFinal, columnaFinal);
									}
								} else if (SwingUtilities.isRightMouseButton(e)) {

									// Dependiendo el tablero le pasaremos las banderas
									switch (nombreTablero) {
									case "pequeño": {

										if (!casilla.getTieneBandera()) {
											mostrarBandera(casilla, 10);

										} else {
											quitarBandera(casilla, 10);
										}
										break;
									}
									case "mediano": {

										if (!casilla.getTieneBandera()) {
											mostrarBandera(casilla, 40);

										} else {
											quitarBandera(casilla, 40);
										}
										break;
									}
									case "grande": {

										if (!casilla.getTieneBandera()) {
											mostrarBandera(casilla, 80);

										} else {
											quitarBandera(casilla, 80);
										}
										break;
									}
									default:
										throw new IllegalArgumentException("Unexpected value: " + nombreTablero);
									}
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

			BotonesDescartaryGuardar(correo);

		}
	}

	private void manejarClick(Casilla casilla, int fila, int columna) {

		// Si tiene mina, revelar el contenido, mostrar mensaje, terminar juego y
		// desactivar al hacer click en casillas
		if (casilla.getTieneMina()) {

			if (!juegoTerminado) {
				// Mantener primera mina encontrada, activar y llamar en revelar
				if (!casilla.getPrimeraMinaRevelada()) {

					// Parar el contador
					paraComptador();

					mostrarMina(casilla, "minaRoja");
					casilla.setPrimeraMinaRevelada(true);
					JOptionPane.showMessageDialog(null, "Has encontrado una mina");
					juegoTerminado = true;
					desactivarTablero();
					revelarContenido();

					// Cambiar el emoticono
					Image cara = new ImageIcon("src/imagenes/caraTriste.png").getImage().getScaledInstance(38, 38,
							Image.SCALE_SMOOTH);
					reinicio.setIcon(new ImageIcon(cara));
				} else {
					mostrarMina(casilla, "mina");
				}
			}
		} else {

			if (ContadorCasillasinrevelar <= 1) {
				paraComptador();
				JOptionPane.showMessageDialog(null, "Has Ganado, Felicidades");
				juegoTerminado = true;
				desactivarTablero();
			}

			// Solo restar cuando no contenga un número
			if (casilla.esRevelada == false) {
				ContadorCasillasinrevelar--;
			}

			labelCasillasaRevelar.setText(Integer.toString(ContadorCasillasinrevelar));

			mostrarNumero(casilla);
			if (casilla.getMinasAdyacentes() == 0) {
				revelarCasilla(fila, columna);
			}
		}
	}

	private void mostrarNumero(Casilla casilla) {

		casilla.setEsRevelada(true); // Marcar la casilla como revelada

		// Configuramos los numeros y los colores
		int minasAdyacentes = casilla.getMinasAdyacentes();
		// No añadir número si la casilla contiene una bandera
		if (minasAdyacentes > 0) {

			casilla.setFont(new Font("Dialog", Font.BOLD, 20));
			casilla.setBackground(new Color(128, 128, 128, 50));
			casilla.setForeground(getColorNumero(minasAdyacentes));
			// Configurar para mostrar el texto
			casilla.setText(String.valueOf(minasAdyacentes));
			casilla.esRevelada = true;
		}
	}

	private void mostrarMina(Casilla casilla, String nom) {

		casilla.setEsRevelada(true); // Marcar la casilla como revelada

		// Segun la mina roja inicial o las demás obtendremos la ruta y
		// redimensionaremos
		Image imagenRedimensionada = null;
		if (nom.equals("mina")) {
			imagenRedimensionada = new ImageIcon("src/imagenes/mina.png").getImage().getScaledInstance(25, 25,
					Image.SCALE_SMOOTH);

		} else {
			imagenRedimensionada = new ImageIcon("src/imagenes/mina_roja.png").getImage().getScaledInstance(25, 25,
					Image.SCALE_SMOOTH);

		}
		// Agregar la mina
		casilla.setIcon(new ImageIcon(imagenRedimensionada));
		casilla.setText("");
	}

	private void quitarBandera(Casilla casilla, int numeroBanderas) {

		if (contadorBanderas < numeroBanderas) {

			casilla.setTieneBandera(false);
			casilla.setIcon(null);
			contadorBanderas++;
			labelMinasRestantes.setText(Integer.toString(contadorBanderas));
		}
	}

	private void mostrarBandera(Casilla casilla, int numeroBanderas) {

		// Si contiene numero o casilla blanca, no agregar bandera
		if (casilla.esRevelada == true) {
			return;
		}
		// Verificamos si podemos poner más banderas.
		if (contadorBanderas > 0) {

			casilla.setEsRevelada(true); // Marcar la casilla como revelada
			casilla.setTieneBandera(true);

			// Quitamos cualquier etiqueta anterior de la casilla.
			casilla.removeAll();

			Image imagenRedimensionada = new ImageIcon("src/imagenes/bandera.png").getImage().getScaledInstance(25, 25,
					Image.SCALE_SMOOTH);

			casilla.setBackground(new Color(128, 128, 128, 0));
			casilla.setIcon(new ImageIcon(imagenRedimensionada));
			casilla.setText("");

			// Disminuimos el contador de banderas y actualizamos la etiqueta de banderas
			// restantes.
			contadorBanderas--;
			labelMinasRestantes.setText(Integer.toString(contadorBanderas));

		}
	}

	private void desactivarTablero() {
		// Desactivar el listener para no poder seguir clickando en alguna casilla

		for (Casilla[] filaCasillas : tableroCasillas) {
			for (Casilla casilla : filaCasillas) {
				casilla.removeMouseListener(casilla.getMouseListeners()[0]);
			}
		}
	}

	private void revelarContenido() {

		// Revelar todas las minas y los números
		for (Casilla[] filaCasillas : tableroCasillas) {
			for (Casilla casilla : filaCasillas) {
				// Eliminar el icono de la bandera para todas las casillas
				casilla.setIcon(null);

				int minasAdyacentes = casilla.getMinasAdyacentes();
				if (minasAdyacentes > 0 || casilla.getTieneMina()) {

					// No revelar donde se encuentran los numeros
					// mostrarNumero(casilla);
					if (casilla.getTieneMina()) {
						// Aqui mantenemos el color de la primera mina
						if (casilla.getPrimeraMinaRevelada()) {
							mostrarMina(casilla, "minaRoja");
						} else {
							mostrarMina(casilla, "mina");
							casilla.setPrimeraMinaRevelada(true);
						}
					}
				}
			}
		}
	}

	private Color getColorNumero(int numeroMinas) {
		switch (numeroMinas) {
		case 0:
			return new Color(128, 128, 128, 0);// zaqui pongo que el numero cero se ponga totalmente transparente
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

	public void revelarCasilla(int fila, int columna) {
		// de manera recursiva entra a ver el estado de la casilla, si no ha sido
		// revelada la revela y revelara las adyacente

		// Verificar los límites del tablero
		if (fila < 0 || fila >= tableroCasillas.length || columna < 0 || columna >= tableroCasillas.length) {
			return;
		}
		Casilla casilla = tableroCasillas[fila][columna];

		// Verificar si la casilla ya ha sido revelada o tiene bandera
		if (casilla.esRevelada == true || casilla.getTieneBandera() == true) {
			return;
		}

		// Marcar la casilla como revelada y mostrar su contenido
		casilla.esRevelada = true;

		if (ContadorCasillasinrevelar <= 1) {
			JOptionPane.showMessageDialog(null, "Has Ganado, Felicidades");
			juegoTerminado = true;
			desactivarTablero();
		}
		ContadorCasillasinrevelar--;
		labelCasillasaRevelar.setText(Integer.toString(ContadorCasillasinrevelar));

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

	public void generarMinas(int f, int numeroMinas) {
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

	private void BotonesDescartaryGuardar(String correo) {
		JPanel PanelBotones = new JPanel(new GridBagLayout());
		PanelBotones.setBorder(new EmptyBorder(10, 0, 10, 0));
		PanelBotones.setLayout(new GridBagLayout());

		JButton botonVolver = new JButton("Volver");
		botonVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonVolver.setMinimumSize(new Dimension(100, 35));
		botonVolver.setMaximumSize(new Dimension(100, 35));
		botonVolver.setPreferredSize(new Dimension(100, 35));
		botonVolver.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbcBorrar = new GridBagConstraints();
		gbcBorrar.insets = new Insets(5, 10, 10, 10); // Añade espacio inferior
		gbcBorrar.gridx = 1;
		gbcBorrar.gridy = 0;
		PanelBotones.add(botonVolver, gbcBorrar);

		botonVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Despues de cargar el tablero, se establece tableroCasillas, para borrarlo
				// completamente establecerlo coom null
				// Resetear contador y campos para volver a llamar el juego
				paraComptador();
				tableroCasillas = null;
				contentPane.removeAll();
				tablero.removeAll();
				// Reininializar el color
				juegoTerminado = false;
				contadorBanderas = 0;
				IniciodeJuego(correo);

			}
		});

		JButton Ranking = new JButton("Ranking");
		Ranking.setMinimumSize(new Dimension(100, 35));
		Ranking.setMaximumSize(new Dimension(100, 35));
		Ranking.setPreferredSize(new Dimension(100, 35));
		Ranking.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbcGNewRank = new GridBagConstraints();
		gbcGNewRank.insets = new Insets(5, 10, 10, 10); // No añade espacio inferior
		gbcGNewRank.gridx = 2;
		gbcGNewRank.gridy = 0;
		PanelBotones.add(Ranking, gbcGNewRank);

		JButton Guardar = new JButton("Guardar");
		Guardar.setMinimumSize(new Dimension(100, 35));
		Guardar.setMaximumSize(new Dimension(100, 35));
		Guardar.setPreferredSize(new Dimension(100, 35));
		Guardar.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbcGuardar = new GridBagConstraints();
		gbcGuardar.insets = new Insets(5, 10, 10, 10); // No añade espacio inferior
		gbcGuardar.gridx = 3;
		gbcGuardar.gridy = 0;
		PanelBotones.add(Guardar, gbcGuardar);

		Guardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String filePath = "buscaminas.datos";

				guardarEstadoTablero(filePath);
				guardarDatosBD(correo, filePath);
			}
		});

		contentPane.add(PanelBotones, BorderLayout.SOUTH);

		JPanel PanelContador = new JPanel();
		PanelContador.setBorder(new EmptyBorder(10, 0, 10, 0));
		contentPane.add(PanelContador, BorderLayout.NORTH);
		PanelContador.setLayout(new GridBagLayout());

		JPanel CasillasaReveladas = new JPanel();
		GridBagConstraints gbcCasillas = new GridBagConstraints();
		gbcCasillas.weightx = 3; // Absorbe el espacio extra
		gbcCasillas.insets = new Insets(0, 20, 0, 20);
		gbcCasillas.gridx = 2;
		gbcCasillas.gridy = 1;
		PanelContador.add(CasillasaReveladas, gbcCasillas);

		labelCasillasaRevelar.setBorder(
				BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY, 2, true), new EmptyBorder(5, 20, 5, 20)));
		labelCasillasaRevelar.setFont(new Font("Dialog", Font.BOLD, 22));

		CasillasaReveladas.add(labelCasillasaRevelar);

		JPanel MinasRestantes = new JPanel();
		GridBagConstraints gbcMinasRestantes = new GridBagConstraints();
		gbcMinasRestantes.weightx = 3; // Absorbe el espacio extra
		gbcMinasRestantes.insets = new Insets(0, 20, 0, 20);
		gbcMinasRestantes.gridx = 1;
		gbcMinasRestantes.gridy = 0;
		PanelContador.add(MinasRestantes, gbcMinasRestantes);
		labelMinasRestantes.setBorder(
				BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY, 2, true), new EmptyBorder(5, 20, 5, 20)));
		labelMinasRestantes.setFont(new Font("Dialog", Font.BOLD, 22));

		MinasRestantes.add(labelMinasRestantes);

		JPanel Reiniciar = new JPanel();
		GridBagConstraints gbcReiniciar = new GridBagConstraints();
		gbcReiniciar.insets = new Insets(0, 13, 0, 13);
		gbcReiniciar.weightx = 3; // Absorbe el espacio extra
		gbcReiniciar.gridx = 2;
		gbcReiniciar.gridy = 0;
		PanelContador.add(Reiniciar, gbcReiniciar);

		Image cara = new ImageIcon("src/imagenes/cara.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		reinicio.setIcon(new ImageIcon(cara));

		Reiniciar.add(reinicio);
		reinicio.setBorderPainted(false);
		reinicio.setBackground(Color.decode("#c0c0c0"));
		reinicio.setMinimumSize(new Dimension(45, 45));
		reinicio.setMaximumSize(new Dimension(45, 45));
		reinicio.setPreferredSize(new Dimension(45, 45));

		reinicio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (esReveladaGlobal) {
					// Resetear contador para volver a empezar
					paraComptador();
					tableroCasillas = null;
					// Eliminar todo lo anterior, creamos nueva instancia y reiniciamos contadores
					contentPane.removeAll();
					tablero.removeAll();

					esReveladaGlobal = false;
					juegoTerminado = false;

					ContadorCasillasinrevelar = CasillasRevelarReset; // para resetear el contador al numero inicial de

					// Dependiendo el tamaño del tablero, volver a inicializar todo
					switch (nombreTablero) {
					case "pequeño": {
						crearTablero(8, 10, correo);
						generarMinas(8, 10);
						contadorBanderas = 10;
						labelMinasRestantes.setText(Integer.toString(10));

						ContadorCasillasinrevelar = 54;
						labelCasillasaRevelar.setText(Integer.toString(ContadorCasillasinrevelar));
						repaint();
						revalidate();
						break;
					}
					case "mediano": {
						crearTablero(16, 40, correo);
						generarMinas(16, 40);
						contadorBanderas = 40;
						labelMinasRestantes.setText(Integer.toString(40));

						ContadorCasillasinrevelar = 216;
						labelCasillasaRevelar.setText(Integer.toString(ContadorCasillasinrevelar));
						repaint();
						revalidate();
						break;
					}
					case "grande": {
						crearTablero(25, 80, correo);
						generarMinas(25, 80);
						contadorBanderas = 80;
						labelMinasRestantes.setText(Integer.toString(80));

						ContadorCasillasinrevelar = 545;
						labelCasillasaRevelar.setText(Integer.toString(ContadorCasillasinrevelar));
						repaint();
						revalidate();
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + nombreTablero);
					}
				}
			}
		});

		JPanel Tiempo = new JPanel();
		GridBagConstraints gbcTiempoRestante = new GridBagConstraints();
		gbcTiempoRestante.insets = new Insets(0, 13, 0, 13);
		gbcTiempoRestante.weightx = 3; // Absorbe el espacio extra
		gbcTiempoRestante.gridx = 3;
		gbcTiempoRestante.gridy = 0;
		PanelContador.add(Tiempo, gbcTiempoRestante);

		labelTemps.setText("000");
		labelTemps.setBorder(
				BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY, 2, true), new EmptyBorder(5, 20, 5, 20)));
		labelTemps.setFont(new Font("Dialog", Font.BOLD, 22));
		Tiempo.add(labelTemps);

	}

	public void iniciaComptador() {
		// Solo iniciar una vez
		if (timer == null) {
			timer = new Timer();
			TimerTask tarea = new TimerTask() {
				public void run() {
					segons++;
					labelTemps.setText(String.format("%03d", segons));
				}
			};
			timer.scheduleAtFixedRate(tarea, 0, 1000);
		}
	}

	public String paraComptador() {
		segons = 0;
		if (timer != null) {
			timer.cancel();
			timer = null; // establece el timer a null después de cancelarlo
		}
		return labelTemps.getText();
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

	private void guardarEstadoTablero(String filePath) {
		// TODO Esbozo de método generado automáticamente
		if (juegoTerminado) {
			JOptionPane.showMessageDialog(null, "NO PUEDES GUARDAR UNA PARTIDA TERMINADA");
		} else {
			// serialització
			ObjectOutputStream oos = null;
			FileOutputStream fout = null;
			try {
				// obrim el fitxer per escriure, sense afegir
				// només tindrem un ArrayList d'objectes
				fout = new FileOutputStream(new File(filePath), false);
				oos = new ObjectOutputStream(fout);

				// Guardar en una linea aparte el contadodor de banderas, los segundos y las
				// casillas a revelar
				oos.writeObject("#" + contadorBanderas + ":" + segons + ":" + ContadorCasillasinrevelar);
				oos.writeObject(tableroCasillas);
				oos.flush();
				oos.close();
				JOptionPane.showMessageDialog(null, "Se ha guardado correctamente");
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (oos != null) {
					try {
						oos.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	private void comprobarExistenciaDatos(String correo) {
		// TODO Esbozo de método generado automáticamente
		String selectPartida = "SELECT pixelart.* FROM usuarios, pixelart WHERE usuarios.id = pixelart.idUsuario AND email = ?";

		Connection conexion = Conexion.obtenerConexion();
		
		try {
			PreparedStatement preparandoConsulta = conexion.prepareStatement(selectPartida);
			preparandoConsulta.setString(1, correo);
			ResultSet resultado = preparandoConsulta.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
		}


	}

	private void cargarPartidaDesdeArchivo(String nombreArchivo, String correo) {
		// TODO Esbozo de método generado automáticamente
		File file = new File(nombreArchivo);

		try {
			// obrim fitxer per a lectura
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
			try {
				String line = (String) reader.readObject();

				// Recuperar la linea que contiene la almohadilla
				// Empezar con uno para no guardar el simbolo
				if (line.contains("#")) {
					String[] parts = line.substring(1).split(":");
					contadorBanderas = Integer.parseInt(parts[0]);
					segons = Integer.parseInt(parts[1]);
					ContadorCasillasinrevelar = Integer.parseInt(parts[2]);
				}
				// llegim l'objecte que hi ha al fitxer (1 sol array List)
				tableroCasillas = (Casilla[][]) reader.readObject();
			} catch (Exception ex) {
				System.err.println("Final del fitxer");
			}

			reader.close();

		} catch (Exception ex) {
			System.err.println("Error en llegir usuaris.dades " + ex);
		}

		// Dependiendo el tamaño creamos el tipo de tablero
		if (tableroCasillas.length == 8) {

			contentPane.removeAll();
			tablero.removeAll();

			setSize(370, 575);
			// Centramos pantalla
			centrarInterficiePantalla();
			crearTablero(8, 10, correo);// NUMERO DE FILAS 8x8 | NUMERO DE MINAS
			repaint();
			revalidate();
			// Lo utilizaremos luego para configurar banderas, nueva Partida o imagen
			nombreTablero = "pequeño";

			// ContadorCasillasinrevelar = 54;
			labelCasillasaRevelar.setText(Integer.toString(ContadorCasillasinrevelar));
			CasillasRevelarReset = ContadorCasillasinrevelar;

			// Inicialmente mostrarara las banderas que tiene
			labelMinasRestantes.setText(Integer.toString(contadorBanderas));

			// Mostrar los segundos donde se quedo la partida
			labelTemps.setText(Integer.toString(segons));

		} else if (tableroCasillas.length == 16) {
			contentPane.removeAll();
			tablero.removeAll();

			setSize(570, 775);
			// Centramos pantalla
			centrarInterficiePantalla();
			crearTablero(16, 40, correo);
			repaint();
			revalidate();
			nombreTablero = "mediano";

			labelCasillasaRevelar.setText(Integer.toString(ContadorCasillasinrevelar));
			CasillasRevelarReset = ContadorCasillasinrevelar;

			// Inicialmente mostrarara las banderas que tiene
			labelMinasRestantes.setText(Integer.toString(contadorBanderas));

			// Mostrar los segundos donde se quedo la partida
			labelTemps.setText(Integer.toString(segons));

		} else if (tableroCasillas.length == 25) {
			contentPane.removeAll();
			tablero.removeAll();

			setSize(770, 975);
			// Centramos pantalla
			centrarInterficiePantalla();

			crearTablero(25, 80, correo);
			repaint();
			revalidate();
			// Lo utilizaremos luego para configurar banderas, nueva Partida o imagen
			nombreTablero = "grande";

			// ContadorCasillasinrevelar = 54;
			labelCasillasaRevelar.setText(Integer.toString(ContadorCasillasinrevelar));
			CasillasRevelarReset = ContadorCasillasinrevelar;

			// Inicialmente mostrarara las banderas que tiene
			labelMinasRestantes.setText(Integer.toString(contadorBanderas));

			// Mostrar los segundos donde se quedo la partida
			labelTemps.setText(Integer.toString(segons));

		}
	}

	public void guardarDatosBD(String correo, String path) {

		String insertarDatosPartida = "INSERT INTO buscaminas (idUsuario, tablero, ficheroPartida, fecha) VALUES (?,?,?,?)";
		Connection conexion = Conexion.obtenerConexion();
		String[] idUsuario = datosUsuarioPerfil(correo);

		File archivo = new File(path);

		LocalDate fechaActual = LocalDate.now();

		try {
			FileInputStream archivoInputStream = new FileInputStream(archivo);

			PreparedStatement preparandoInsert = conexion.prepareStatement(insertarDatosPartida);
			preparandoInsert.setInt(1, Integer.parseInt(idUsuario[0]));
			preparandoInsert.setString(2, nombreTablero);
			preparandoInsert.setBlob(3, archivoInputStream);
			preparandoInsert.setDate(4, java.sql.Date.valueOf(fechaActual));

			preparandoInsert.executeUpdate();
			preparandoInsert.close();
			System.out.println("Partida buscaMinas guardada en BD");

		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	public String[] datosUsuarioPerfil(String correo) {
		String[] datos = new String[6];
		String sentencia = "SELECT * FROM usuarios WHERE email = ?";
		Connection c = Conexion.obtenerConexion();

		try {
			PreparedStatement consulta = c.prepareStatement(sentencia);
			consulta.setString(1, correo);
			ResultSet resultado = consulta.executeQuery();

			while (resultado.next()) {
				datos[0] = "" + resultado.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}

		return datos;
	}

}