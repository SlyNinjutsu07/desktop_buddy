package com.desktopbuddy.ui;

import com.desktopbuddy.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sprite {
    private JFrame window;
    private JPopupMenu menu;
    private JLabel label;

    public Sprite(int width, int height){
        window = new JFrame();
        window.setUndecorated(true);
        window.setBackground(new Color(0,0,0,0));

        initializeMenu();
        window.setAlwaysOnTop(true);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon icon = new ImageIcon("src/main/resources/pixel_frog.png");
        Image scaledImg = icon.getImage().getScaledInstance(width, height, Image.SCALE_REPLICATE);
        label = new JLabel(new ImageIcon(scaledImg));
        window.add(label);

        window.setTitle("desktop_buddy");
        window.setSize(width, height);

        window.setLocation((int) size.getWidth()/2 - 50, (int) size.getHeight()/2 - 50);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    menu.show(label, e.getX(), e.getY());
                    System.out.println("Clicked");
                }
            }
        });
        System.out.println("Running");
    }

    private void initializeMenu(){
        menu = new JPopupMenu();

        JMenuItem notes_window = new JMenuItem("Notes");
        notes_window.addActionListener(e -> {
            System.out.println("Feature still being implemented");
            new NoteManager();
        });

        JMenuItem new_window = new JMenuItem("New Window");
        new_window.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Not implemented yet");
        });

        JMenuItem print_text = new JMenuItem("Say Something..");
        print_text.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter something...");
            JOptionPane.showMessageDialog(null, "You said: \"" + input + "\"");
        });

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e->System.exit(0));


        menu.add(notes_window);
        menu.add(new_window);
        menu.add(print_text);
        menu.add(exit);
    }
}
