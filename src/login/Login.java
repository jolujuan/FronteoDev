package login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import conexionBaseDatos.Conexion;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.ImageIcon;
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
		ImageIcon icona = new ImageIcon("Imatges/Ex05/login.jpeg");
		Image imatge = icona.getImage();
		Image novaImg = imatge.getScaledInstance(180, (icona.getIconHeight() * 180) / icona.getIconWidth(),
				Image.SCALE_SMOOTH);
		ImageIcon novaIcona = new ImageIcon(novaImg);

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

		JButton login = new JButton("Login");
		panel_1.add(login);
		
		JButton arrere = new JButton("Vés enrere");
		panel_1.add(arrere);
		
		JLabel text = new JLabel("Login");
		text.setFont(new Font("Tahoma", Font.PLAIN, 20));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text, BorderLayout.NORTH);
		
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				errorNom.setText("");
				errorPwd.setText("");
				if (textFieldNom.getText().isEmpty()) {
					errorNom.setText("Error. Introduce un nombre.");
				}else if(textFieldPwd.getPassword().length==0) {
					errorPwd.setText("Error. Introduce una contraseña.");
				}else {
					ResultSet consulta = consultaSql();
					try {
						boolean userTrobat=false;
						while (consulta.next() && !userTrobat) {
							String nom = consulta.getString("email");
							String pwd= consulta.getString("password");
							// login correcte
							if (textFieldNom.getText().equals(nom)) {
								userTrobat=true;
								if(encriptarPassword(textFieldPwd.getPassword(),extraeSalto(pwd)).equals(pwd)) {
									panel.setVisible(false);
									login.setText("Logout");
								}else {
//									JOptionPane.showMessageDialog(contentPane, "Contrasenya incorrecta per a l'usuari "+nom);
									System.out.println("No ha coincidit password");
									System.out.println(pwd);
									System.out.println(encriptarPassword(textFieldPwd.getPassword(),extraeSalto(pwd)));
								}
							}
						}
						if(!userTrobat) {
//							JOptionPane.showMessageDialog(contentPane, "No s'ha trobat aquest usuari");
						}
					} catch (SQLException e1) {
						System.err.println("Error al recorrer la consulta.");
						e1.printStackTrace();
					}
				}
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
			r = cerca.executeQuery("SELECT usuarios.email, passwords.password FROM usuarios JOIN passwords ON usuarios.id=passwords.idUsuario");
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
			salto = new byte[LONGITUD_SALTO];

			KeySpec spec = new PBEKeySpec(passWord, salto, FORTALEZA, LONGITUD_HASH);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			byte[] hash = factory.generateSecret(spec).getEncoded();
//			passWord = Base64.getEncoder().encodeToString(hash);

			passwordEncriptada = FORTALEZA + conversionSalto(salto) + LONGITUD_HASH + Base64.getEncoder().encodeToString(hash);

		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

		return passwordEncriptada;
	}
	public String conversionSalto(byte[] salto) {
		String saltosTexto = "";
		for (int i = 0; i < salto.length; i++) {
			// PASAMOS LA CONVERSION DE BYTES A CADENA
			saltosTexto += String.format("%02x", salto[i]);
		}
		return saltosTexto;
	}
	public String extraeSalto(String password) {
		
		return "";
	}

}
