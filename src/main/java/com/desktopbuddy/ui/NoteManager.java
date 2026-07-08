package com.desktopbuddy.ui;

import com.desktopbuddy.data.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;

public class NoteManager {
    private JFrame window;

    //ADD BUTTON
    private JButton addButton;
    private JPopupMenu addButtonMenu;

    //SETTINGS BUTTON
    private JButton settingsButton;
    private SettingsData settingsData;

    //CONTENT
    //TODO: add field for the current path that is being viewed
    private ArrayList<Folder> folders = new ArrayList<>();
    private ArrayList<Note> notes = new ArrayList<>();

    public NoteManager(){
        this.settingsData = ConfigManager.load(); //load pre-existing settings or create new json
        Path dirPath = Path.of(settingsData.getDirectoryPath());
        Folder root = new Folder("root", dirPath);
        listItems(root);

        //System.out.println(folders.size() + " " + notes.size());

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

        window.add(toolbar, BorderLayout.NORTH);
        window.add(initUI(), BorderLayout.CENTER);
    }

    private void listItems(Folder root){
        //clear current view
        folders.clear();
        notes.clear();

        //read from the path and figure out the folders and notes and add them
        try (var stream = Files.list(root.getFolderPath())){
            stream.forEach(itemPath -> {
                String itemName = itemPath.getFileName().toString();
                if(Files.isDirectory(itemPath)){
                    if(itemName.equals(".obsidian")) return;
                    folders.add(new Folder(itemName, itemPath));
                } else if (itemName.endsWith(".md")) {
                    notes.add(new Note(itemName, itemPath));
                }
            });
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not read directory");
        }
    }

    private JPanel initUI(){
        JPanel view = new JPanel();
        view.setLayout(new BorderLayout());

        view.add(initFolders(), BorderLayout.NORTH);
        view.add(initNotes(), BorderLayout.CENTER);

        return view;
    }

    private JPanel initFolders(){
        JPanel folderView = new JPanel();
        folderView.setLayout(new BoxLayout(folderView, BoxLayout.Y_AXIS));

        for(Folder folder : folders){
            JButton folderButton = new JButton(folder.getFolderName());
            folderButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //TODO: refresh NoteManager with new contents of that folder
                }
            });
            folderView.add(folderButton);
        }

        return folderView;
    }

    private JPanel initNotes(){
        JPanel notesView = new JPanel();
        notesView.setLayout(new FlowLayout());

        for (Note note : notes){
            JButton noteButton = new JButton(note.getNoteName());
            noteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //TODO: instantaite a new NoteWindow
                }
            });
            notesView.add(noteButton);
        }


        return notesView;
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

    //TODO: add an init function for showing the current directory path (JPanel)
}
