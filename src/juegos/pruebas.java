package juegos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JLabel;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class pruebas extends JFrame {
	Random random = new Random();
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatLightLaf.install();

					pruebas frame = new pruebas();
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
	public pruebas() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("BuscaMinas");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		JPanel BotonesJuego = new JPanel(new GridBagLayout());
		BotonesJuego.setBorder(new EmptyBorder(10, 0, 10, 0));

		JButton Borrar = new JButton("Borrar");
		Borrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Borrar.setMinimumSize(new Dimension(100, 35));
		Borrar.setMaximumSize(new Dimension(100, 35));
		Borrar.setPreferredSize(new Dimension(100, 35));
		Borrar.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbcBorrar = new GridBagConstraints();
		gbcBorrar.anchor = GridBagConstraints.CENTER;
		gbcBorrar.insets = new Insets(5, 10, 10, 10); // Añade espacio inferior
		gbcBorrar.gridx = 1;
		gbcBorrar.gridy = 0;
		BotonesJuego.add(Borrar, gbcBorrar);

		JButton Ranking = new JButton("Ranking");
		Ranking.setMinimumSize(new Dimension(100, 35));
		Ranking.setMaximumSize(new Dimension(100, 35));
		Ranking.setPreferredSize(new Dimension(100, 35));
		Ranking.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbcGNewRank = new GridBagConstraints();
		gbcGNewRank.anchor = GridBagConstraints.CENTER;
		gbcGNewRank.insets = new Insets(5, 10, 10, 10); // No añade espacio inferior
		gbcGNewRank.gridx = 2;
		gbcGNewRank.gridy = 0;
		BotonesJuego.add(Ranking, gbcGNewRank);

		JButton Guardar = new JButton("Guardar");
		Guardar.setMinimumSize(new Dimension(100, 35));
		Guardar.setMaximumSize(new Dimension(100, 35));
		Guardar.setPreferredSize(new Dimension(100, 35));
		Guardar.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbcGuardar = new GridBagConstraints();
		gbcGuardar.anchor = GridBagConstraints.CENTER;
		gbcGuardar.insets = new Insets(5, 10, 10, 10); // No añade espacio inferior
		gbcGuardar.gridx = 3;
		gbcGuardar.gridy = 0;
		BotonesJuego.add(Guardar, gbcGuardar);

		contentPane.add(BotonesJuego, BorderLayout.SOUTH);

		
		JPanel PanelContador = new JPanel();
		PanelContador.setBorder(new EmptyBorder(10, 0, 10, 0));
		contentPane.add(PanelContador, BorderLayout.NORTH);
		PanelContador.setLayout(new BoxLayout(PanelContador, BoxLayout.X_AXIS));


		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL; // Para que se llene horizontalmente

		JPanel MinasRestantes = new JPanel();
		PanelContador.add(MinasRestantes);
		// ... Resto del código para minasRestantes ...

		PanelContador.add(Box.createHorizontalGlue());

		JLabel minasRestantes = new JLabel("0");
		minasRestantes.setBorder(
				BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY, 2, true), new EmptyBorder(5, 20, 5, 20)));
		minasRestantes.setFont(new Font("Dialog", Font.BOLD, 22));
		MinasRestantes.add(minasRestantes);

		JPanel Reiniciar = new JPanel();
		PanelContador.add(Reiniciar);
		// ... Resto del código para Reiniciar ...

		PanelContador.add(Box.createHorizontalGlue());


		JButton reinicio = new JButton("Reiniciar");
		Reiniciar.add(reinicio);
		reinicio.setMinimumSize(new Dimension(100, 35));
		reinicio.setMaximumSize(new Dimension(100, 35));
		reinicio.setPreferredSize(new Dimension(100, 35));
		reinicio.setFont(new Font("Unispace", Font.BOLD, 12));

		JPanel TiempoRestante = new JPanel();
		PanelContador.add(TiempoRestante);
		
		JLabel tiempoRestante = new JLabel("000");
		tiempoRestante.setBorder(
				BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY, 2, true), new EmptyBorder(5, 20, 5, 20)));
		tiempoRestante.setFont(new Font("Dialog", Font.BOLD, 22));
		TiempoRestante.add(tiempoRestante);
	}

}
