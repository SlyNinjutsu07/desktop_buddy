package com.desktopbuddy.ui;
import com.desktopbuddy.data.SettingsData;

import java.awt.*;
import javax.swing.*;
import java.nio.file.*;

public class SettingsWindow {
    private JFrame window;
    private SettingsData settings;

    public SettingsWindow(SettingsData settings) {
        this.settings = settings;

        window = new JFrame("settings");
        Container content = window.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        buildUI();

        window.pack();
        window.setLocation(new Point((int) size.getWidth() / 2 - window.getWidth() / 2, (int) size.getHeight() / 2 - window.getHeight() / 2));
        window.setVisible(true);
    }


    //main function for building all UI components into Settings window
    private void buildUI(){
        addDirPathSetting();
    }

    private void addDirPathSetting(){
        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new BorderLayout(8, 0));
        pathPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel pathLabel = new JLabel("Path to directory:");

        JTextField pathField = new JTextField(15);
        pathField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        pathField.addActionListener(e -> updateDirectoryPath(pathField));

        pathPanel.add(pathLabel, BorderLayout.WEST);
        pathPanel.add(pathField, BorderLayout.CENTER);

        pathPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, pathPanel.getPreferredSize().height));

        window.add(pathPanel);
        window.add(Box.createVerticalGlue());
    }

    private void updateDirectoryPath(JTextField inputField){
        String loc = inputField.getText().trim(); //.trim() removes whitespace
        Path dir = Path.of(loc);
        if(Files.isDirectory(dir)){
            settings.setDirectoryPath(loc);
            settings.setPathValid(true);
        } else{
            //Flashing error
            inputField.setEditable(false);
            inputField.setText("Invalid directory...");
            Timer timer = new Timer(1500, e -> {
                inputField.setText(loc);
                inputField.setEditable(true);
            });
            timer.setRepeats(false);
            timer.start();
            settings.setPathValid(false);
        }
    }
}
