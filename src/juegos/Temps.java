package juegos;

import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Temps extends JPanel {
	private Timer timer;
	
	public Temps() {
		
	}
	public void iniciaComptador(int segonsIni, int velocitat) {
		timer = new Timer();
        TimerTask tarea = new TimerTask() {
        	public void run() {
        		
        	}
        };
        switch(velocitat) {
        case 1:
        	timer.scheduleAtFixedRate(tarea, 0, 2000);
        	break;
        case 2:
        	timer.scheduleAtFixedRate(tarea, 0, 1000);
        	break;
        case 3:
        	timer.scheduleAtFixedRate(tarea, 0, 500);
        	break;
        default:
        	timer.scheduleAtFixedRate(tarea, 0, 1000);
        }
        
	}
	public void paraComptador() {
//		segons=0;
		timer.cancel();
//		return labelTemps.getText();
		
	}

}
