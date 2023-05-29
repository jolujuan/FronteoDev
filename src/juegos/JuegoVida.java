package juegos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import juegos.BuscaMinas.Casilla;

public class JuegoVida extends JFrame{
	Random random = new Random();
	private Casilla[][] tableroCasillas;
	private JPanel contentPane;
	private int contadorBanderas;
	public JuegoVida() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Juego de la Vida");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		selectDificultat();
		
	}
	private void selectDificultat() {
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
						int nCelules=random.nextInt(5,30);
						setSize(370, 490);
						// Centramos pantalla
						centrarInterficiePantalla();
						crearTablero(8, nCelules);// NUMERO DE FILAS 8x8 | NUMERO DE CELULAS
						recorrerTablero(8, nCelules);
						

					}
				});
				tamañoMediano.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int nCelules=random.nextInt(90,129);
						setSize(570, 690);
						// Centramos pantalla
						centrarInterficiePantalla();
						crearTablero(16, nCelules);
						recorrerTablero(16, nCelules);

					}
				});
				tamañoGrande.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int nCelules=random.nextInt(120,313);
						setSize(770, 890);
						// Centramos pantalla
						centrarInterficiePantalla();
						crearTablero(25, nCelules);
						recorrerTablero(25, nCelules);
					}
				});
				repaint();
				revalidate();

	}
	private void crearTablero(int f, int numeroMinas) {

		// Verificar que el número de minas sea válido
		int totalCasillas = f * f;

		if (numeroMinas > totalCasillas) {
			throw new IllegalArgumentException("El número de minas excede el tamaño del tablero.");
		}

		tableroCasillas = new Casilla[f][f];
		JPanel tablero = new JPanel();
		tablero.setLayout(new GridLayout(f, f));

		// con esto sacamos el tamaño para q las casillas sean iguales
		int size = Math.min(getWidth() / f, getHeight() / f);

		for (int fila = 0; fila < f; fila++) {
			for (int columna = 0; columna < f; columna++) {

				Casilla casilla = new Casilla();
				casilla.setPreferredSize(new Dimension(size, size));

				final int filaFinal = fila;
				final int columnaFinal = columna;

//				casilla.addMouseListener(new MouseAdapter() {
//					@Override
//					public void mousePressed(MouseEvent e) {
//						// Mientras no encuentres mina seguir
//						if (!juegoTerminado) {
//							if (e.getButton() == MouseEvent.BUTTON1) {
//
//								if (!casilla.getTieneBandera()) {
//									manejarClick(casilla, filaFinal, columnaFinal);
//								}
//							} else if (SwingUtilities.isRightMouseButton(e)) {
//								// Disminuir contador cada vez que presionas
//
//								// Dependiendo el tablero le pasaremos las banderas
//								switch (nombreTablero) {
//								case "pequeño": {
//
//									if (!casilla.getTieneBandera()) {
//										mostrarBandera(casilla, 10);
//
//									} else {
//										quitarBandera(casilla, 10);
//									}
//									break;
//								}
//								case "mediano": {
//
//									if (!casilla.getTieneBandera()) {
//										mostrarBandera(casilla, 40);
//
//									} else {
//										quitarBandera(casilla, 40);
//									}
//									break;
//								}
//								case "grande": {
//
//									if (!casilla.getTieneBandera()) {
//										mostrarBandera(casilla, 80);
//
//									} else {
//										quitarBandera(casilla, 80);
//									}
//									break;
//								}
//								default:
//									throw new IllegalArgumentException("Unexpected value: " + nombreTablero);
//								}
//							}
//						}
//					}
//				});
				tableroCasillas[fila][columna] = casilla;
				tablero.add(casilla);
			}
		}

		contentPane.removeAll();
		contentPane.add(tablero, BorderLayout.CENTER);

//		BotonesDescartaryGuardar();

	}
	public void recorrerTablero(int f, int numeroMinas) {
		int minasAsignadas = 0; // Contador de minas asignadas

		while (minasAsignadas < numeroMinas) {
			int filaAleatoria = random.nextInt(f);
			int columnaAleatoria = random.nextInt(f);
			Casilla casilla = tableroCasillas[filaAleatoria][columnaAleatoria];
			if (!casilla.getTieneMina()) {
				casilla.setTieneMina(true);
				minasAsignadas++;
//				actualizarNumerosAdyacentes(filaAleatoria, columnaAleatoria);
			}
		}
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
					setBackground(new Color(128, 128, 128, 50));
				}
			} else {

				setText("");
			}
		}
	}
}

