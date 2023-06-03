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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
	private Timer timer=new Timer();
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
						int nCelules=random.nextInt(5,15);
						setSize(460, 460);
						// Centramos pantalla
						
						crearTablero(8, nCelules);// NUMERO DE FILAS 8x8 | NUMERO DE CELULAS
						pintarTablero(8, nCelules);
						centrarInterficiePantalla();

					}
				});
				tamañoMediano.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int nCelules=random.nextInt(60,90);
						setSize(570, 570);
						// Centramos pantalla
						
						crearTablero(16, nCelules);
						pintarTablero(16, nCelules);
						centrarInterficiePantalla();

					}
				});
				tamañoGrande.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int nCelules=random.nextInt(200,360);
						setSize(770, 770);
						// Centramos pantalla
						centrarInterficiePantalla();
						crearTablero(60, nCelules);
						pintarTablero(60, nCelules);
					}
				});
				repaint();
				revalidate();

	}
	private void crearTablero(int f, int numeroCelulas) {

		// Verificar que el número de minas sea válido
		int totalCasillas = f * f;

		if (numeroCelulas > totalCasillas) {
			throw new IllegalArgumentException("El número de minas excede el tamaño del tablero.");
		}

		JPanel tablero=new JPanel();
		tablero.setLayout(new BorderLayout(0, 0));
		
		tableroCasillas = new Casilla[f][f];
		
		JPanel partida = new JPanel();
		partida.setLayout(new GridLayout(f, f));
		// con esto sacamos el tamaño para q las casillas sean iguales
		int size = Math.min(getWidth() / f, getHeight() / f);

		for (int fila = 0; fila < f; fila++) {
			for (int columna = 0; columna < f; columna++) {

				Casilla casilla = new Casilla();
				casilla.setPreferredSize(new Dimension(size, size));
				casilla.setBackground(new Color(0, 0, 0,180));

				final int filaFinal = fila;
				final int columnaFinal = columna;


				tableroCasillas[fila][columna] = casilla;
				partida.add(casilla);
			}
		}
		tablero.add(partida,BorderLayout.CENTER);
		JPanel botons = new JPanel();
		tablero.add(botons, BorderLayout.SOUTH);
		GridBagLayout gbl_botons = new GridBagLayout();
		gbl_botons.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_botons.rowHeights = new int[]{0, 0};
		gbl_botons.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_botons.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		botons.setLayout(gbl_botons);
		
		JButton volver = new JButton("Volver");
		GridBagConstraints gbc_Volver = new GridBagConstraints();
		gbc_Volver.insets = new Insets(0, 0, 0, 5);
		gbc_Volver.gridx = 1;
		gbc_Volver.gridy = 0;
		botons.add(volver, gbc_Volver);
		
		JButton pausa = new JButton("||");
		GridBagConstraints gbc_lento = new GridBagConstraints();
		gbc_lento.insets = new Insets(0, 0, 0, 5);
		gbc_lento.gridx = 2;
		gbc_lento.gridy = 0;
		botons.add(pausa, gbc_lento);
		
		JButton lento = new JButton(">");
		GridBagConstraints gbc_pausa = new GridBagConstraints();
		gbc_pausa.insets = new Insets(0, 0, 0, 5);
		gbc_pausa.gridx = 3;
		gbc_pausa.gridy = 0;
		botons.add(lento, gbc_pausa);
		
		JButton start = new JButton(">>");
		GridBagConstraints gbc_start = new GridBagConstraints();
		gbc_start.insets = new Insets(0, 0, 0, 5);
		gbc_start.gridx = 4;
		gbc_start.gridy = 0;
		botons.add(start, gbc_start);
		
		JButton rapido = new JButton(">>>");
		GridBagConstraints gbc_rapido = new GridBagConstraints();
		gbc_rapido.insets = new Insets(0, 0, 0, 5);
		gbc_rapido.gridx = 5;
		gbc_rapido.gridy = 0;
		botons.add(rapido, gbc_rapido);
		contentPane.removeAll();
		contentPane.add(tablero, BorderLayout.CENTER);
		
		//FUNCIONS DELS BOTONS
		volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tableroCasillas = null;
				contentPane.removeAll();
				tablero.removeAll();
				timer.cancel();
				selectDificultat();
			}
		});
		pausa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					timer.cancel();
			}
		});
		
		lento.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.cancel();
				timer=new Timer();
				TimerTask tarea = new TimerTask() {
		        	public void run() {
		        		jugada();
		        		contentPane.revalidate();
		        		contentPane.repaint();
		        		
		        	}
		        };
		        timer.scheduleAtFixedRate(tarea, 0, 1000);
			}
		});
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.cancel();
				timer=new Timer();
				TimerTask tarea = new TimerTask() {
		        	public void run() {
		        		jugada();
		        		contentPane.revalidate();
		        		contentPane.repaint();
		        	}
		        };
		        timer.scheduleAtFixedRate(tarea, 0, 500);
			}
		});
		rapido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.cancel();
				timer=new Timer();
				TimerTask tarea = new TimerTask() {
		        	public void run() {
		        		jugada();
		        		contentPane.revalidate();
		        		contentPane.repaint();
		        	}
		        };
		        timer.scheduleAtFixedRate(tarea, 0, 100);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				timer.cancel();
			}
		});
//		BotonesDescartaryGuardar();

	}
	public void pintarTablero(int f, int numeroCelulasVivas) {
		int celulasAsig = 0; // Contador de celulas asignadas

		while (celulasAsig < numeroCelulasVivas) {
			int filaAleatoria = random.nextInt(f);
			int columnaAleatoria = random.nextInt(f);
			Casilla casilla = tableroCasillas[filaAleatoria][columnaAleatoria];
			if (!casilla.getCelulaViva()) {
				casilla.setCelulaViva(true);
				celulasAsig++;
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
	
	private void jugada() {
		Casilla[][] copiaTableroCasillas=copiaTablero();
		for(int i=0;i<copiaTableroCasillas.length;i++) {
			for(int j=0;j<copiaTableroCasillas[i].length;j++) {
				Casilla casilla = copiaTableroCasillas[i][j];
				boolean celViva=casilla.getCelulaViva();
				int celsVives=contarCelulasVivas(i, j,copiaTableroCasillas);
				if(celViva && (celsVives<2 || celsVives>3)) {
					Casilla casillaOrigin = tableroCasillas[i][j];
					casillaOrigin.setCelulaViva(false);
				}else if (!celViva && celsVives==3){
					Casilla casillaOrigin = tableroCasillas[i][j];
					casillaOrigin.setCelulaViva(true);
				}
			}
		}
	}
	private int contarCelulasVivas(int fila, int columna, Casilla[][] copiaTablero) {
		int celVives=0;
		for (int i = - 1; i <= 1; i++) {
			for (int j =  - 1; j <=1; j++) {
				if (i != 0 || j != 0) {
					int nextFila=fila+i;
					int nextColumna=columna+j;
					if(nextFila >= 0 && nextFila < copiaTablero.length && nextColumna >= 0 && nextColumna < tableroCasillas[0].length) {
						Casilla casilla = copiaTablero[nextFila][nextColumna];
						if (casilla.getCelulaViva()) {
							celVives++;
						}
					}
					
				}
			}
		}
		return celVives;
	}
	private Casilla[][] copiaTablero(){
		Casilla[][] copiaTablero= new Casilla[tableroCasillas.length][tableroCasillas[0].length];
		for(int i=0;i<copiaTablero.length;i++) {
			for(int j=0;j<copiaTablero[i].length;j++) {
				Casilla celulaRef=tableroCasillas[i][j];
				Casilla novaCasella=new Casilla();
				novaCasella.setCelulaViva(celulaRef.getCelulaViva());
				copiaTablero[i][j]=novaCasella;
			}
		}
		return copiaTablero;
	}
	public class Casilla extends JButton {

		private boolean celulaViva = false;
		private int celulasVivasAdyacentes=0;

		public Casilla() {
			super("");
		}

		public void setCelulaViva(boolean celulaViva) {
			this.celulaViva = celulaViva;
			actualizarApariencia();
		}

		public boolean getCelulaViva() {
			return this.celulaViva;
		}
		
		public void setCelulasVivasAdyacentes(int celulasVivasAdyacentes) {
			this.celulasVivasAdyacentes = celulasVivasAdyacentes;
		}

		public int getCelulasVivasAdyacentes() {
			return this.celulasVivasAdyacentes;
		}

		private void actualizarApariencia() {
			if (this.celulaViva) {
				setBackground(new Color(0, 255, 0, 230));
				
			} else {
				setBackground(new Color(0, 0, 0,180));
			}
			repaint();
			revalidate();
		}
	}
}

