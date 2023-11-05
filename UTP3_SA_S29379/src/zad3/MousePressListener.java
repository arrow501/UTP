package zad3;


// interfejs MousePressListener
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface MousePressListener extends MouseListener {
    // metoda, która jest wywoływana, gdy naciśnięto klawisz myszki
    void mousePressed(MouseEvent e);

    // domyślne implementacje pozostałych metod interfejsu MouseListener, które nic nie robią
    default void mouseClicked(MouseEvent e) {}
    default void mouseReleased(MouseEvent e) {}
    default void mouseEntered(MouseEvent e) {}
    default void mouseExited(MouseEvent e) {}
}
