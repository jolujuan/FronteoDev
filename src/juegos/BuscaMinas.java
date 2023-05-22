package juegos;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import juegos.PixelArt.Casilla;

public class BuscaMinas extends JFrame{
	
	public BuscaMinas() {
		
		JPanel Dificultat = new JPanel();
		getContentPane().add(Dificultat, BorderLayout.CENTER);
		GridBagLayout gbl_Dificultat = new GridBagLayout();
		gbl_Dificultat.columnWidths = new int[]{0, 0, 0, 0};
		gbl_Dificultat.rowHeights = new int[] {85, 50, 25};
		gbl_Dificultat.columnWeights = new double[]{2.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_Dificultat.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		Dificultat.setLayout(gbl_Dificultat);
		
		JLabel text = new JLabel("<html><h1>Buscaminas<h1><p>Selecciona la dificultad</p>");
		text.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_text = new GridBagConstraints();
		gbc_text.gridwidth = 3;
		gbc_text.insets = new Insets(0, 0, 5, 5);
		gbc_text.gridx = 0;
		gbc_text.gridy = 0;
		Dificultat.add(text, gbc_text);
		
		JButton facil = new JButton("<html><p>Fácil</p><p>8 x 8 casillas</p><p>10 minas</p></html>");
		GridBagConstraints gbc_facil = new GridBagConstraints();
		gbc_facil.fill = GridBagConstraints.BOTH;
		gbc_facil.insets = new Insets(0, 0, 0, 5);
		gbc_facil.gridx = 0;
		gbc_facil.gridy = 1;
		Dificultat.add(facil, gbc_facil);
		
		JButton normal = new JButton("<html><p>Normal</p><p>16 x 16 casillas</p><p>40 minas</p></html>");
		GridBagConstraints gbc_normal = new GridBagConstraints();
		gbc_normal.fill = GridBagConstraints.BOTH;
		gbc_normal.insets = new Insets(0, 0, 0, 5);
		gbc_normal.gridx = 1;
		gbc_normal.gridy = 1;
		Dificultat.add(normal, gbc_normal);
		
		JButton dificil = new JButton("<html><p>Dificil</p><p>30 x 16 casillas</p><p>99 minas</p></html>");
		GridBagConstraints gbc_dificil = new GridBagConstraints();
		gbc_dificil.fill = GridBagConstraints.BOTH;
		gbc_dificil.gridx = 2;
		gbc_dificil.gridy = 1;
		Dificultat.add(dificil, gbc_dificil);
		
		facil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				creaJoc(getName());
			}
		});
		normal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().removeAll();
				setContentPane(creaJoc(getName()));
				revalidate();
				repaint();
			}
		});
		dificil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				creaJoc(getName());
			}
		});
		
	}
	public JPanel creaJoc(String dificultat) {
		JPanel tauler=new JPanel();
		switch(dificultat) {
		case "facil":
			tauler=creaMapa(8,8,10);
			break;
		case "normal":
			tauler=creaMapa(16,16,40);
			break;
		case "dificil":
			tauler=creaMapa(30,16,99);
			break;
		}
		return tauler;
	}
	public JPanel creaMapa(int files, int columnes, int mines) {
		JPanel mapaJoc=new JPanel();
		mapaJoc.setLayout(new GridLayout(files, columnes));
		
		for (int y = 0; y < files; y++) {
			for (int x = 0; x < columnes; x++) {
				JPanel casilla=new JPanel();
				casilla.setBackground(Color.red);
				casilla.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128, 50), 1));

				casilla.addMouseListener(new MouseAdapter() {
					// mousePressed para nada más apretar que revele si es mina
					@Override
					public void mousePressed(MouseEvent e) {
						if (SwingUtilities.isLeftMouseButton(e)) {
							
						}
						if (SwingUtilities.isRightMouseButton(e)) {
							
						}
					}
				});
				mapaJoc.add(casilla);
				
			}
		}
		repaint();
		revalidate();
		return mapaJoc;
	}
	
}
