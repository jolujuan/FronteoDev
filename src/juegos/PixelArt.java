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
					frame.setSize(500, 400);
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
		tamañoPequeño.setFont(new Font("Unispace", Font.BOLD, 12));//FUENTE
		gbc.gridx = 0;
		gbc.gridy = 0;
		botonesJPanel.add(tamañoPequeño, gbc);

		JButton tamañoMediano = new JButton("Mediano");
		tamañoMediano.setPreferredSize(new Dimension(120, 40));//TAMAÑO BOTON
		tamañoMediano.setFont(new Font("Unispace", Font.BOLD, 12));//FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.gridy = 1;
		botonesJPanel.add(tamañoMediano, gbc);

		JButton tamañoGrande = new JButton("Grande");
		tamañoGrande.setPreferredSize(new Dimension(120, 40));//TAMAÑO
		tamañoGrande.setFont(new Font("Unispace", Font.BOLD, 12));//FUENTE
		gbc = new GridBagConstraints(); // PARA CENTRAR BOTONES
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 10, 0);

		gbc.gridy = 2;
		botonesJPanel.add(tamañoGrande, gbc);

		// Lo que se visualizará directamente
		contentPane.add(inicio, BorderLayout.NORTH);
		contentPane.add(botonesJPanel, BorderLayout.CENTER);
	
	
	   // Llamada al método para crear el tablero //ESTADO DE  PRUEBAS DE AQUI PARA ABAJO
		tamañoPequeño.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 crearTablero(15);
			}
		});

	}
private void crearTablero(int f) {
    JPanel tablero = new JPanel(new GridLayout(f, f));
    int size =1; // Tamaño de cada casilla del tablero

    for (int fila = 0; fila < f; fila++) {
        for (int columna = 0; columna < f; columna++) {
            Casilla casilla= new Casilla();
            casilla.setPreferredSize(new Dimension(size, size));
           
            	casilla.setBackground((fila + columna) % 2 == 0 ? Color.WHITE : Color.GRAY); //MAS TARDE PONER  EL GRIS EN BLANCO , AHORA DEJARLO ASI PARA DISTINGUIR MEJOR EL TABLERO
                tablero.add(casilla);

			
            
    }
}
contentPane.removeAll();
contentPane.add(tablero, BorderLayout.CENTER);
repaint();
revalidate();

}








	
	

}
