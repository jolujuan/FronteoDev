package login;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import conexionBaseDatos.Conexion;
import juegos.BuscaMinas;
import juegos.PixelArt;
import menuJuegos.Menu;
import panel_inicio.Panel_inicio;

public class Login extends JPanel {

	private static final int FORTALEZA = 65536;
	private static final int LONGITUD_HASH = 64 * 8;

	private JTextField fieldUsr;
	private JPasswordField fieldPwd;
	private JLabel text;
	private JPanel panel;
  
	public Login() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel panel_1 = new JPanel();
		add(panel_1);

		text = new JLabel("Login"); 
		panel_1.add(text);
		text.setFont(new Font("Dialog", Font.BOLD, 30));
		text.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_2 = new JPanel();
		add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

		panel = new JPanel();
		panel.setOpaque(false);
		panel_2.add(panel);
		panel.setBackground(new Color(238, 238, 238));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 300, 0 };
		gbl_panel.rowHeights = new int[] { 15, 40, 0, 15, 40, 1, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel texto1 = new JLabel("Email");
		texto1.setFont(new Font("Dialog", Font.BOLD, 13));
		texto1.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_texto1 = new GridBagConstraints();
		gbc_texto1.anchor = GridBagConstraints.WEST;
		gbc_texto1.insets = new Insets(0, 0, 5, 0);
		gbc_texto1.gridx = 0;
		gbc_texto1.gridy = 0;
		panel.add(texto1, gbc_texto1);

		fieldUsr = new JTextField();
		fieldUsr.setFont(new Font("Dialog", Font.PLAIN, 13));
		Border existingBorder = fieldUsr.getBorder();
		Border emptyBorder = BorderFactory.createEmptyBorder(5, 8, 5, 8);
		Border compoundBorder = BorderFactory.createCompoundBorder(existingBorder, emptyBorder);
		fieldUsr.setBorder(compoundBorder);
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
		text2.setFont(new Font("Dialog", Font.BOLD, 13));
		GridBagConstraints gbc_text2 = new GridBagConstraints();
		gbc_text2.anchor = GridBagConstraints.WEST;
		gbc_text2.insets = new Insets(0, 0, 5, 0);
		gbc_text2.gridx = 0;
		gbc_text2.gridy = 3;
		panel.add(text2, gbc_text2);

		fieldPwd = new JPasswordField();
		fieldPwd.setFont(new Font("Dialog", Font.PLAIN, 13));
		Border existingBorder2 = fieldPwd.getBorder();
		Border emptyBorder2 = BorderFactory.createEmptyBorder(5, 8, 5, 8);
		Border compoundBorder2 = BorderFactory.createCompoundBorder(existingBorder2, emptyBorder2);
		fieldPwd.setBorder(compoundBorder2);
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
		gbc_errorPwd.insets = new Insets(0, 0, 5, 0);
		gbc_errorPwd.anchor = GridBagConstraints.WEST;
		gbc_errorPwd.gridx = 0;
		gbc_errorPwd.gridy = 5;
		panel.add(errorPwd, gbc_errorPwd);

		JPanel panelBotones = new JPanel();
		panelBotones.setAlignmentY(Component.TOP_ALIGNMENT);
		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(10);
		add(panelBotones);

		JButton botoTorna = new JButton("Atrás");
		botoTorna.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botoTorna.setPreferredSize(new Dimension(110, 35));
		botoTorna.setFont(new Font("Dialog", Font.PLAIN, 14));
		panelBotones.add(botoTorna);

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


		JButton botoLogin = new JButton("Entra");
		botoLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botoLogin.setPreferredSize(new Dimension(110, 35));
		botoLogin.setFont(new Font("Dialog", Font.PLAIN, 14));
		panelBotones.add(botoLogin);

		botoLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				errorUsr.setText("");
				errorPwd.setText("");
				if (fieldUsr.getText().isEmpty()) {
					errorUsr.setText("Error. Introduce un nombre.");
				} else if (fieldPwd.getPassword().length == 0) {
					errorPwd.setText("Error. Introduce una contraseña.");
				} else if (consultaSql()) {
					
					/////// Inicializar como true para guardar si ha cerrado sin guardar
					PixelArt.setGuardado(true);
					BuscaMinas.setGuardado(true);
					Menu menu = new Menu(fieldUsr.getText());
					Panel_inicio p = (Panel_inicio) SwingUtilities.getWindowAncestor(Login.this);
					p.getContentPane().removeAll();
					p.getContentPane().add(menu);
					p.revalidate();
					p.repaint();

				}

			}
		});
	}

	public boolean consultaSql() {

		Connection c = Conexion.obtenerConexion();
//		Connection c = Conexion.obtenerConexionLocal();
		ResultSet r = null;
		boolean usrValidat = false;
		try {
			// Enviar una sentència SQL per recuperar els clients
			Statement cerca = c.createStatement();
			r = cerca.executeQuery(
					"SELECT usuarios.email, passwords.password, passwords.salto FROM usuarios JOIN passwords ON usuarios.id=passwords.idUsuario");
			try {
				boolean userTrobat = false;

				while (r.next() && !userTrobat) {
					String nom = r.getString("email");
					String pwd = r.getString("password");
					String salto = r.getString("salto");
					// login correcte
					if (fieldUsr.getText().equals(nom)) {
						userTrobat = true;
						if (!encriptarPassword(fieldPwd.getPassword(), salto).equals(pwd)) {
							JOptionPane.showMessageDialog(panel, "Contrasenya incorrecta per a l'usuari " + nom);
							usrValidat = false;
						} else {
							usrValidat = true;
						}
					}
				}

				if (!userTrobat) {
					JOptionPane.showMessageDialog(panel, "No s'ha trobat aquest usuari");
					usrValidat = false;
				}
			} catch (SQLException e1) {
				System.err.println("Error al recorrer la consulta.");
				e1.printStackTrace();
			}
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usrValidat;
	}

	public String encriptarPassword(char[] passWord, String saltoStr) {
		byte[] salto = null;
		String passwordEncriptada = "";

		try {
			salto = revertirSalto(saltoStr);

			KeySpec spec = new PBEKeySpec(passWord, salto, FORTALEZA, LONGITUD_HASH);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			byte[] hash = factory.generateSecret(spec).getEncoded();

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
