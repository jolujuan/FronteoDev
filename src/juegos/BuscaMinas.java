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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

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

public class BuscaMinas extends JFrame {
	Random random = new Random();
	private Casilla[][] tableroCasillas;
	private JPanel contentPane;
	private JPanel tablero = new JPanel();

	private boolean juegoTerminado = false;
	private int contadorBanderas = 0;
	private String nombreTablero;
	private JLabel labelMinasRestantes = new JLabel("");

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
		//Iniciamos contador de tiempo
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
				setSize(370, 490);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(8, 10);// NUMERO DE FILAS 8x8 | NUMERO DE MINAS
				recorrerTablero(8, 10);

				// Lo utilizaremos luego para configurar banderas, nueva Partida o imagen
				nombreTablero = "pequeño";

				// Declaramos las banderas que contendra
				contadorBanderas = 10;

				// Inicialmente mostrarara las banderas que tiene
				labelMinasRestantes.setText(Integer.toString(10));
			}
		});
		tamañoMediano.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(570, 690);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(16, 40);
				recorrerTablero(16, 40);
				nombreTablero = "mediano";

				contadorBanderas = 40;

				labelMinasRestantes.setText(Integer.toString(40));
			}
		});
		tamañoGrande.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(770, 890);
				// Centramos pantalla
				centrarInterficiePantalla();
				crearTablero(25, 80);
				recorrerTablero(25, 80);

				nombreTablero = "grande";

				contadorBanderas = 80;

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
		private boolean esRevelada = false;
		private boolean quitarBandera = false;

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
			} else {

				setText("");
			}
		}
	}

	private void crearTablero(int f, int numeroMinas) {

		// Verificar que el número de minas sea válido
		int totalCasillas = f * f;

		if (numeroMinas > totalCasillas) {
			throw new IllegalArgumentException("El número de minas excede el tamaño del tablero.");
		}

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
							if (e.getButton() == MouseEvent.BUTTON1) {

								if (!casilla.getTieneBandera()) {
									manejarClick(casilla, filaFinal, columnaFinal);
								}
							} else if (SwingUtilities.isRightMouseButton(e)) {
								// Disminuir contador cada vez que presionas

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

		BotonesDescartaryGuardar();

	}

	private void manejarClick(Casilla casilla, int fila, int columna) {

		// Si tiene mina, revelar el contenido, mostrar mensaje, terminar juego y
		// desactivar al hacer click en casillas
		if (casilla.getTieneMina()) {

			if (!juegoTerminado) {
				// Mantener primera mina encontrada, activar y llamar en revelar
				if (!casilla.getPrimeraMinaRevelada()) {

					mostrarMina(casilla, "minaRoja");
					casilla.setPrimeraMinaRevelada(true);
					JOptionPane.showMessageDialog(null, "Has encontrado una mina");
					juegoTerminado = true;
					desactivarTablero();

					revelarContenido();
				} else {
					mostrarMina(casilla, "mina");
				}
			}
		} else {
			mostrarNumero(casilla);
			if (casilla.getMinasAdyacentes() == 0) {
				revelarCasilla(fila, columna);
			}
		}
	}

	private void mostrarNumero(Casilla casilla) {
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
				//Eliminar el icono de la bandera para todas las casillas
				casilla.setIcon(null);

				if (!casilla.esRevelada) {

					int minasAdyacentes = casilla.getMinasAdyacentes();
					if (minasAdyacentes > 0 || casilla.getTieneMina()) {
						mostrarNumero(casilla);
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

// de manera recursiva entra a ver el estado de la casilla, si no ha sido revelada la revela y revelara las adyacente
	public void revelarCasilla(int fila, int columna) {
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
		JPanel PanelBotones = new JPanel(new GridBagLayout());
		PanelBotones.setBorder(new EmptyBorder(10, 0, 10, 0));
		PanelBotones.setLayout(new GridBagLayout());

		JButton Borrar = new JButton("Borrar");
		Borrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Borrar.setMinimumSize(new Dimension(100, 35));
		Borrar.setMaximumSize(new Dimension(100, 35));
		Borrar.setPreferredSize(new Dimension(100, 35));
		Borrar.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbcBorrar = new GridBagConstraints();
		gbcBorrar.insets = new Insets(5, 10, 10, 10); // Añade espacio inferior
		gbcBorrar.gridx = 1;
		gbcBorrar.gridy = 0;
		PanelBotones.add(Borrar, gbcBorrar);

		Borrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.removeAll();
				tablero.removeAll();
				// Reininializar el color
				juegoTerminado = false;
				contadorBanderas = 0;
				IniciodeJuego();

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

		contentPane.add(PanelBotones, BorderLayout.SOUTH);

		JPanel PanelContador = new JPanel();
		PanelContador.setBorder(new EmptyBorder(10, 0, 10, 0));
		contentPane.add(PanelContador, BorderLayout.NORTH);
		PanelContador.setLayout(new GridBagLayout());

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

		JButton reinicio = new JButton("Reiniciar");
		Reiniciar.add(reinicio);
		reinicio.setMinimumSize(new Dimension(100, 35));
		reinicio.setMaximumSize(new Dimension(100, 35));
		reinicio.setPreferredSize(new Dimension(100, 35));
		reinicio.setFont(new Font("Unispace", Font.BOLD, 12));

		reinicio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Eliminar todo lo anterior, creamos nueva instancia y reiniciamos contadores
				tablero.removeAll();
				Casilla casilla = new Casilla();

				casilla.setTieneMina(false);
				casilla.setEsRevelada(false);
				juegoTerminado = false;

				repaint();
				revalidate();

				// Dependiendo el tamaño del tablero, volver a inicializar todo
				switch (nombreTablero) {
				case "pequeño": {
					crearTablero(8, 10);
					recorrerTablero(8, 10);
					contadorBanderas = 10;
					labelMinasRestantes.setText(Integer.toString(10));

					break;
				}
				case "mediano": {
					crearTablero(16, 40);
					recorrerTablero(16, 40);
					contadorBanderas = 40;
					labelMinasRestantes.setText(Integer.toString(40));

					break;
				}
				case "grande": {
					crearTablero(25, 80);
					recorrerTablero(25, 80);
					contadorBanderas = 80;
					labelMinasRestantes.setText(Integer.toString(80));

					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + nombreTablero);
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

		JLabel tiempoRestante = new JLabel("000");
		tiempoRestante.setBorder(
				BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY, 2, true), new EmptyBorder(5, 20, 5, 20)));
		tiempoRestante.setFont(new Font("Dialog", Font.BOLD, 22));
		Tiempo.add(tiempoRestante);

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
