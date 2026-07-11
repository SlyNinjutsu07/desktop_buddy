package com.desktopbuddy.ui;

import com.desktopbuddy.data.*;
import com.sun.source.tree.Tree;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
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

    //CONTENT
    private JPanel contentPanel;
    //TODO: add field for the current path that is being viewed
    private ArrayList<Folder> folders = new ArrayList<>();
    private ArrayList<Note> notes = new ArrayList<>();

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

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(initFolders());
        contentPanel.add(initNotes());

        //add content to manager window
        window.add(toolbar, BorderLayout.NORTH);

        Folder dir = new Folder(rootDir.getFolderName(), rootDir.getFolderPath());
        window.add(new JScrollPane(tree), BorderLayout.CENTER);
        //window.add(contentPanel, BorderLayout.CENTER);
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


    /*

        EVERYTHING BELOW IS DEAD CODE BECAUSE I'M USING A JTREE INSTEAD

     */



    //refresh the contentPanel with new content after accessing root
    private void refreshWindow(Folder newRoot){
        fillItems(newRoot);
        contentPanel.removeAll();
        contentPanel.add(initFolders(), BorderLayout.NORTH);
        contentPanel.add(initNotes(), BorderLayout.CENTER);

        //For re-orienting and re-drawing everything live
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    //Fills the array lists with the current view (root) of contents
    private void fillItems(Folder root){

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

    private JPanel initFolders(){
        JPanel folderView = new JPanel();
        folderView.setLayout(new BoxLayout(folderView, BoxLayout.Y_AXIS));



        for(Folder folder : folders){
            JButton folderButton = new JButton(folder.getFolderName());


            //folderButton.setIcon(new ImageIcon(scaler));

            folderButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2){
                        refreshWindow(folder);
                    }
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

            ImageIcon imageIcon = new ImageIcon("src/main/resources/md-logo.png");
            Image scaler = imageIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
            noteButton.setIcon(new ImageIcon(scaler));

            noteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2){
                        new NoteEditor(note);
                    }
                }
            });
            notesView.add(noteButton);
        }


        return notesView;
    }

    /*
    DEAD CODE ENDS HERE
     */

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
