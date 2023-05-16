package juegos;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Casilla extends JPanel {
    private static final Color COLOR_CASILLA = Color.WHITE;
    private static final Color COLOR_BORDE = Color.BLACK;
    private static final int GROSOR_BORDE = 1;

    public Casilla() {
        setBackground(COLOR_CASILLA);
        setBorder(BorderFactory.createLineBorder(COLOR_BORDE, GROSOR_BORDE));
    }
}
