import java.awt.*;

import javax.swing.*;

public class Settings {
    private JFrame window;
    private String directoryPath;

    public Settings(){
        window = new JFrame("settings");
        Container content = window.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        //ADD INPUT COMPONENT FOR DIRECTORY
        addPathField();

        window.pack();
        window.setLocation(new Point((int) size.getWidth() / 2 - window.getWidth() / 2, (int) size.getHeight() / 2 - window.getHeight() / 2));
        window.setVisible(true);
    }

    private void addPathField(){
        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new BorderLayout(8, 0));         // 8px gap between label and field
        pathPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel pathLabel = new JLabel("Path to directory:");

        JTextField pathField = new JTextField(15);
        pathField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        pathPanel.add(pathLabel, BorderLayout.WEST);         // label on the left
        pathPanel.add(pathField, BorderLayout.CENTER);       // field fills the rest of the width

        // Keep the row skinny: stop the Y_AXIS BoxLayout from stretching it vertically.
        pathPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, pathPanel.getPreferredSize().height));

        window.add(pathPanel);
        window.add(Box.createVerticalGlue());                // pin the row to the top
    }
}
