import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.*;

public class Settings {
    private JFrame window;
    private String directoryPath;

    public Settings(){
        window = new JFrame("settings");
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        window.setLocation(new Point((int) size.getWidth() / 2 - 200, (int) size.getHeight() / 2 - 200));
        window.setSize(300,300);
        
        //ADD INPUT COMPONENT FOR DIRECTORY
        JTextField pathField = new JTextField("Path to dir...");
        window.add(pathField);
        
        
        window.setVisible(true);
    }
}
