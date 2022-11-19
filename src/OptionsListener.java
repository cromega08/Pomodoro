import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionsListener extends WindowAdapter {

    private final Body pomodoroWindow;

    OptionsListener(Body body) {pomodoroWindow = body;}

    public void windowOpened(WindowEvent e) {pomodoroWindow.disableMainWindow();}

    public void windowClosed(WindowEvent e) {pomodoroWindow.enableMainWindow();}
}
