import java.awt.*;

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
        pathField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        window.add(pathField);
        
        
        window.setVisible(true);
    }
}
