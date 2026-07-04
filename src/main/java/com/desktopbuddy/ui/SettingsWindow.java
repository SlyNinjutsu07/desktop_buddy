package com.desktopbuddy.ui;

import com.desktopbuddy.data.*;

import java.awt.*;
import javax.swing.*;
import java.nio.file.*;

public class SettingsWindow {
    private JFrame window;
    private SettingsData settingsData;

    public SettingsWindow(SettingsData data) {
        this.settingsData = data;

        window = new JFrame("⚙️ settings");
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

        window.add(Box.createVerticalGlue());
    }

    private void addDirPathSetting(){
        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new BorderLayout(8, 0));
        pathPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel pathLabel = new JLabel("Path to directory:");

        JTextField pathField = new JTextField(15);
        pathField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        pathField.setEditable(false);

        JButton browseButton = new JButton("Browse...");
        browseButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            int result = chooser.showOpenDialog(window);
            //if selected save it to file
            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedPath = chooser.getSelectedFile().getAbsolutePath();
                pathField.setText(selectedPath);
                settingsData.setDirectoryPath(selectedPath);
                ConfigManager.save(settingsData);
            }
        });

        pathPanel.add(pathLabel, BorderLayout.WEST);
        pathPanel.add(browseButton, BorderLayout.CENTER);
        pathPanel.add(pathField, BorderLayout.EAST);

        pathPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, pathPanel.getPreferredSize().height));

        window.add(pathPanel);
    }
}
