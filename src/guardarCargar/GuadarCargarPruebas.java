package guardarCargar;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

public class GuadarCargarPruebas extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuadarCargarPruebas frame = new GuadarCargarPruebas();
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
	public GuadarCargarPruebas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 3, 0, 0));

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);

		JPanel panel_4 = new JPanel();
		panel.add(panel_4);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setPreferredSize(new Dimension(panel.getPreferredSize().width, 150)); // Set height to 50, adjust as needed
		panel_1.setLayout(new GridLayout(1, 3, 0, 0));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		// Add some buttons
		for (int i = 0; i < 10; i++) {
		    buttonPanel.add(new JButton("Button " + i));
		}

		JScrollPane scrollPane = new JScrollPane(buttonPanel); // add buttonPanel to scrollPane
		panel_1.add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);

		JScrollPane scrollPane_2 = new JScrollPane();
		panel_1.add(scrollPane_2);


	}

}
