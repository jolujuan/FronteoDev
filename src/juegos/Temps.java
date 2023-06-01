package juegos;

import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Temps extends JPanel {
	//Variables del temps
	private int segons;
	private JLabel labelTemps=new JLabel();
	private Timer timer;
	/**
	 * Create the panel.
	 */
	public Temps() {
		
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel Text = new JLabel("Temps transcurrit");
		Text.setHorizontalAlignment(SwingConstants.CENTER);
		add(Text);
		
		labelTemps.setText("00:00:00");
		labelTemps.setHorizontalAlignment(SwingConstants.CENTER);
		add(labelTemps);

	}
	public void iniciaComptador(int segonsIni) {
		segons=segonsIni;
		timer = new Timer();
        TimerTask tarea = new TimerTask() {
        	public void run() {
        		segons++;
        		labelTemps.setText(String.format("%03d",segons));
        	}
        };
        timer.scheduleAtFixedRate(tarea, 0, 1000);
	}
	public String paraComptador() {
		segons=0;
		timer.cancel();
		return labelTemps.getText();
		
	}

}
