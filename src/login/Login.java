package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import conexionBaseDatos.Conexion;
import panel_inicio.Panel_inicio;

public class Login extends JPanel {

	private static final int LONGITUD_SALTO = 16;
	private static final int FORTALEZA = 65536;
	private static final int LONGITUD_HASH = 64 * 8;

	private JPanel contentPane;

	private JTextField textFieldNom;
	private JPasswordField textFieldPwd;

	/**
	 * Create the frame.
	 */
	public Login() {
		setSize(350, 200);
		setBounds(100, 100, 450, 300);
		setBorder(new EmptyBorder(5, 5, 5, 5));

		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 150, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel nomText = new JLabel("Nom:");
		nomText.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_nomText = new GridBagConstraints();
		gbc_nomText.anchor = GridBagConstraints.SOUTHEAST;
		gbc_nomText.insets = new Insets(0, 0, 5, 5);
		gbc_nomText.gridx = 0;
		gbc_nomText.gridy = 0;
		panel.add(nomText, gbc_nomText);

		textFieldNom = new JTextField();
		GridBagConstraints gbc_textFieldNom = new GridBagConstraints();
		gbc_textFieldNom.anchor = GridBagConstraints.SOUTHWEST;
		gbc_textFieldNom.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNom.gridx = 1;
		gbc_textFieldNom.gridy = 0;
		panel.add(textFieldNom, gbc_textFieldNom);
		textFieldNom.setColumns(10);

		JLabel errorNom = new JLabel("");
		errorNom.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_errorNom = new GridBagConstraints();
		gbc_errorNom.gridwidth = 2;
		gbc_errorNom.insets = new Insets(0, 0, 5, 0);
		gbc_errorNom.gridx = 0;
		gbc_errorNom.gridy = 1;
		panel.add(errorNom, gbc_errorNom);

		JLabel lblNewLabel = new JLabel("Password: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		textFieldPwd = new JPasswordField();
		GridBagConstraints gbc_textFieldPwd = new GridBagConstraints();
		gbc_textFieldPwd.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPwd.anchor = GridBagConstraints.NORTHWEST;
		gbc_textFieldPwd.gridx = 1;
		gbc_textFieldPwd.gridy = 2;
		panel.add(textFieldPwd, gbc_textFieldPwd);
		textFieldPwd.setColumns(10);

		JLabel errorPwd = new JLabel("");
		errorPwd.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_errorPwd = new GridBagConstraints();
		gbc_errorPwd.gridwidth = 2;
		gbc_errorPwd.anchor = GridBagConstraints.NORTH;
		gbc_errorPwd.insets = new Insets(0, 0, 0, 5);
		gbc_errorPwd.gridx = 0;
		gbc_errorPwd.gridy = 3;
		panel.add(errorPwd, gbc_errorPwd);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);

		JButton botonLogin = new JButton("Login");
		panel_1.add(botonLogin);

		JButton botonVolver = new JButton("Volver");
		panel_1.add(botonVolver);

		JLabel text = new JLabel("Login");
		text.setFont(new Font("Tahoma", Font.PLAIN, 20));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text, BorderLayout.NORTH);

		botonLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				errorNom.setText("");
				errorPwd.setText("");
				if (textFieldNom.getText().isEmpty()) {
					errorNom.setText("Error. Introduce un nombre.");
				} else if (textFieldPwd.getPassword().length == 0) {
					errorPwd.setText("Error. Introduce una contraseña.");
				} else {
					ResultSet consulta = consultaSql();
					try {
						boolean userTrobat = false;
						while (consulta.next() && !userTrobat) {
							String nom = consulta.getString("email");
							String pwd = consulta.getString("password");
							String salto = consulta.getString("salto");
							// login correcte
							if (textFieldNom.getText().equals(nom)) {
								userTrobat = true;
								if (encriptarPassword(textFieldPwd.getPassword(), salto).equals(pwd)) {
									System.out.println("Login exitoso");
								} else {
//									JOptionPane.showMessageDialog(contentPane, "Contrasenya incorrecta per a l'usuari "+nom);
									System.out.println("No ha coincidit password");
									System.out.println(pwd);
									System.out.println(encriptarPassword(textFieldPwd.getPassword(), salto));
								}
							}
						}
						if (!userTrobat) {
//							JOptionPane.showMessageDialog(contentPane, "No s'ha trobat aquest usuari");
						}
					} catch (SQLException e1) {
						System.err.println("Error al recorrer la consulta.");
						e1.printStackTrace();
					}
				}
			}
		});

		botonVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Panel_inicio p = (Panel_inicio) SwingUtilities.getWindowAncestor(Login.this);
				p.getContentPane().removeAll();
				p.getContentPane().add(p.getContenedor());
				p.revalidate();
				p.repaint();

			}
		});

		setVisible(true);
	}

	public ResultSet consultaSql() {
		Connection c = Conexion.obtenerConexion();
		ResultSet r = null;
		try {
			// Enviar una sentència SQL per recuperar els clients
			Statement cerca = c.createStatement();
			r = cerca.executeQuery(
					"SELECT usuarios.email, passwords.password, passwords.salto FROM usuarios JOIN passwords ON usuarios.id=passwords.idUsuario");
//			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	public String encriptarPassword(char[] passWord, String saltoStr) {
		byte[] salto = null;
		String passwordEncriptada = "";

		try {
			SecureRandom random = new SecureRandom();
			salto = revertirSalto(saltoStr);

			KeySpec spec = new PBEKeySpec(passWord, salto, FORTALEZA, LONGITUD_HASH);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			byte[] hash = factory.generateSecret(spec).getEncoded();
//			passWord = Base64.getEncoder().encodeToString(hash);

			passwordEncriptada = FORTALEZA + saltoStr + LONGITUD_HASH + Base64.getEncoder().encodeToString(hash);

		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

		return passwordEncriptada;
	}

	public static byte[] revertirSalto(String salto) {
		byte[] saltoBytes = new byte[salto.length() / 2];// Cada dos caracters de la String representen un byte de
															// l'array
		for (int i = 0; i < saltoBytes.length; i++) {
			int rango = i * 2;
			String pareja = salto.substring(rango, rango + 2);
			saltoBytes[i] = (byte) Integer.parseInt(pareja, 16);
		}
		return saltoBytes;
	}

}
