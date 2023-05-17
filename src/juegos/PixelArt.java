package juegos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PixelArt extends JFrame {

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

		// Llamada al método para crear el tablero //ESTADO DE PRUEBAS DE AQUI PARA
		// ABAJO
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

	public class Casilla extends JPanel {
		private static final Color COLOR_CASILLA = Color.WHITE;
		private static final Color COLOR_BORDE = new Color(128, 128, 128, 50);
		// COLOR DE GRIS UN POCO TRANSPARENTE
		private static final int GROSOR_BORDE = 1;

		public Casilla() {
			setBackground(COLOR_CASILLA);
			setBorder(BorderFactory.createLineBorder(COLOR_BORDE, GROSOR_BORDE));
		}
	}

	private void crearTablero(int f) {
		JPanel tablero = new JPanel(new GridLayout(f, f));
		int anchoVentana = getWidth();
		int altoVentana = getHeight();
		int size = Math.min(anchoVentana / f, altoVentana / f);// con esto sacamos el tamaño para q las casillas sean

		for (int fila = 0; fila < f; fila++) {
			for (int columna = 0; columna < f; columna++) {
				Casilla casilla = new Casilla();
				casilla.setSize(size, size);
				casilla.setPreferredSize(new Dimension(size, size));
				// MAS TARDE PONER EL GRISEN BLANCO , AHORA
				// DEJARLO ASI PARA
				// DISTINGUIR MEJOR EL
				tablero.add(casilla);
				casilla.setBackground((fila + columna) % 2 == 0 ? Color.WHITE : Color.WHITE);// SI LA POSICION DE LA
																								// CASILLA ES PARA COGE
																								// UN COLOR SI NO OTRO

			}
		}
		contentPane.removeAll();// BORRAR INICIO
		contentPane.add(tablero, BorderLayout.CENTER);// AÑADIR EL TABLERO A LA PANTALLA
		BotonesDescartaryGuardar();
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
