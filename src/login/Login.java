package login;


import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import conexionBaseDatos.Conexion;
import menuJuegos.Menu;
import panel_inicio.Panel_inicio;

import javax.swing.JLabel;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JButton;
import java.sql.*;
import java.util.Base64;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPasswordField;

public class Login extends JPanel {
	
	private static final int FORTALEZA = 65536;
	private static final int LONGITUD_HASH = 64 * 8;
	
	private JTextField fieldUsr;
	private JPasswordField fieldPwd;
	private JLabel text;

	
	public Login() {
		setBorder(new EmptyBorder(5, 5, 5, 5));

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gbl_contentPane);
			
		text = new JLabel();
		text.setFont(new Font("Dialog", Font.BOLD, 30));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setText("Login");
		GridBagConstraints gbc_text = new GridBagConstraints();
		gbc_text.gridwidth = 2;
		gbc_text.insets = new Insets(0, 0, 5, 0);
		gbc_text.gridx = 0;
		gbc_text.gridy = 0;
		add(text, gbc_text);
			
		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 238, 238));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{300, 0};
		gbl_panel.rowHeights = new int[]{15, 40, 0, 15, 40, 1, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
			
		JLabel texto1 = new JLabel("Usuario");
		texto1.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_texto1 = new GridBagConstraints();
		gbc_texto1.anchor = GridBagConstraints.WEST;
		gbc_texto1.insets = new Insets(0, 0, 5, 0);
		gbc_texto1.gridx = 0;
		gbc_texto1.gridy = 0;
		panel.add(texto1, gbc_texto1);
			
		fieldUsr = new JTextField();
		GridBagConstraints gbc_fieldUsr = new GridBagConstraints();
		gbc_fieldUsr.anchor = GridBagConstraints.WEST;
		gbc_fieldUsr.insets = new Insets(0, 0, 5, 0);
		gbc_fieldUsr.gridx = 0;
		gbc_fieldUsr.gridy = 1;
		panel.add(fieldUsr, gbc_fieldUsr);
		fieldUsr.setColumns(15);
		
		JLabel errorUsr = new JLabel("");
		errorUsr.setForeground(new Color(224, 27, 36));
		GridBagConstraints gbc_errorUsr = new GridBagConstraints();
		gbc_errorUsr.anchor = GridBagConstraints.WEST;
		gbc_errorUsr.insets = new Insets(0, 0, 5, 0);
		gbc_errorUsr.gridx = 0;
		gbc_errorUsr.gridy = 2;
		panel.add(errorUsr, gbc_errorUsr);
			
		JLabel text2 = new JLabel("Contraseña");
		GridBagConstraints gbc_text2 = new GridBagConstraints();
		gbc_text2.anchor = GridBagConstraints.WEST;
		gbc_text2.insets = new Insets(0, 0, 5, 0);
		gbc_text2.gridx = 0;
		gbc_text2.gridy = 3;
		panel.add(text2, gbc_text2);
			
		fieldPwd = new JPasswordField();
		GridBagConstraints gbc_fieldPwd = new GridBagConstraints();
		gbc_fieldPwd.anchor = GridBagConstraints.WEST;
		gbc_fieldPwd.insets = new Insets(0, 0, 5, 0);
		gbc_fieldPwd.gridx = 0;
		gbc_fieldPwd.gridy = 4;
		panel.add(fieldPwd, gbc_fieldPwd);
		fieldPwd.setColumns(15);
			
		JLabel errorPwd = new JLabel("");
		errorPwd.setForeground(new Color(224, 27, 36));
		GridBagConstraints gbc_errorPwd = new GridBagConstraints();
		gbc_errorPwd.anchor = GridBagConstraints.WEST;
		gbc_errorPwd.gridx = 0;
		gbc_errorPwd.gridy = 5;
		panel.add(errorPwd, gbc_errorPwd);
			
		JButton botoLogin = new JButton("Entra");
			
		GridBagConstraints gbc_botoLogin = new GridBagConstraints();
		gbc_botoLogin.anchor = GridBagConstraints.NORTHEAST;
		gbc_botoLogin.insets = new Insets(0, 0, 0, 5);
		gbc_botoLogin.gridx = 0;
		gbc_botoLogin.gridy = 2;
		add(botoLogin, gbc_botoLogin);
			
		JButton botoTorna = new JButton("Atrás");
			
		GridBagConstraints gbc_botoTorna = new GridBagConstraints();
		gbc_botoTorna.anchor = GridBagConstraints.NORTHWEST;
		gbc_botoTorna.gridx = 1;
		gbc_botoTorna.gridy = 2;
		add(botoTorna, gbc_botoTorna);
			
		botoLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				errorUsr.setText("");
				errorPwd.setText("");
				if (fieldUsr.getText().isEmpty()) {
					errorUsr.setText("Error. Introduce un nombre.");
				} else if (fieldPwd.getPassword().length == 0) {
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
							if (fieldUsr.getText().equals(nom)) {
								userTrobat = true;
								if (encriptarPassword(fieldPwd.getPassword(), salto).equals(pwd)) {
									System.out.println("Login exitoso");//Si el loguin es exitoso abre menu juegos
									Menu menuJuegos = new Menu();
									Panel_inicio p = (Panel_inicio) SwingUtilities.getWindowAncestor(Login.this);
									p.getContentPane().removeAll();
									p.getContentPane().add(menuJuegos);
									p.revalidate();
									p.repaint();

								} else {
//											JOptionPane.showMessageDialog(contentPane, "Contrasenya incorrecta per a l'usuari "+nom);
									System.out.println("No ha coincidit password");
									System.out.println(pwd);
									System.out.println(encriptarPassword(fieldPwd.getPassword(), salto));
								}
							}
						}
						if (!userTrobat) {
//									JOptionPane.showMessageDialog(contentPane, "No s'ha trobat aquest usuari");
						}
					} catch (SQLException e1) {
						System.err.println("Error al recorrer la consulta.");
						e1.printStackTrace();
					}
				}
			
			}
		});
		botoTorna.addActionListener(new ActionListener() {
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
