package com.desktopbuddy.ui;

import com.desktopbuddy.data.*;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.*;

public class NoteManager {
    private JFrame window;
    private Folder rootDir;

    //ADD BUTTON
    private JButton addButton;
    private JPopupMenu addButtonMenu;

    //SETTINGS BUTTON
    private JButton settingsButton;
    private SettingsData settingsData;

    //JTREE
    private JTree tree;

    public NoteManager(){
        this.settingsData = ConfigManager.load(); //load pre-existing settings or create new json
        Path dirPath = Path.of(settingsData.getDirectoryPath());
        rootDir = new Folder(dirPath.getFileName().toString(), dirPath);

        tree = new JTree(TreeManager.buildTree(rootDir));


        window = new JFrame("📝notes");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension desktop_size = Toolkit.getDefaultToolkit().getScreenSize();

        window.setSize(400, 400);
        window.setLocation(desktop_size.width / 2 - 600, desktop_size.height / 2 - 200);

        initializeComponents();

        window.setVisible(true);
    }

    private void initializeComponents(){
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.add(initAddButton(), BorderLayout.WEST);
        toolbar.add(initSettingsButton(), BorderLayout.EAST);

        //tree renderer
        initializeTreeRenderer();

        //add content to manager window
        window.add(toolbar, BorderLayout.NORTH);

        Folder dir = new Folder(rootDir.getFolderName(), rootDir.getFolderPath());
        window.add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    private void initializeTreeRenderer(){
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

        //get icons
        ImageIcon folderIcon = new ImageIcon("src/main/resources/folder-logo.png");
        Image scaledFolderIcon = folderIcon.getImage().getScaledInstance(10,10,Image.SCALE_SMOOTH);

        ImageIcon noteIcon = new ImageIcon("src/main/resources/md-logo.png");
        Image scaledNoteIcon = noteIcon.getImage().getScaledInstance(10,10,Image.SCALE_SMOOTH);


        renderer.setLeafIcon(new ImageIcon(scaledNoteIcon));
        renderer.setOpenIcon(new ImageIcon(scaledFolderIcon));
        renderer.setClosedIcon(new ImageIcon(scaledFolderIcon));
        tree.setCellRenderer(renderer);
    }

    private JButton initAddButton(){
        addButton = new JButton();
        addButtonMenu = new JPopupMenu();

        ImageIcon addIcon = new ImageIcon("src/main/resources/add-icon.png");
        Image scaler = addIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        addButton.setIcon(new ImageIcon(scaler));

        JMenuItem addFolder = new JMenuItem("Create Folder");
        JMenuItem addNote = new JMenuItem("Create Note");

        addButtonMenu.add(addFolder);
        addButtonMenu.add(addNote);

        addFolder.addActionListener(e -> {
            //TODO: Add functionality for adding a folder
        });
        addNote.addActionListener(e -> {
            //TODO: Add functionality for adding a note
        });

        addButton.addActionListener(e -> {
            addButtonMenu.show(addButton, addButton.getWidth(), 0);
        });

        return addButton;
    }

    private JButton initSettingsButton(){
        settingsButton = new JButton();

        ImageIcon settingsIcon = new ImageIcon("src/main/resources/settings-icon.png");
        Image scaler = settingsIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        settingsButton.setIcon(new ImageIcon(scaler));

        settingsButton.addActionListener(e -> {
            new SettingsWindow(settingsData);
        });

        return settingsButton;
    }

}
