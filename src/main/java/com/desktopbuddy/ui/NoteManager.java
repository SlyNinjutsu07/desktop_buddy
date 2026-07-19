package com.desktopbuddy.ui;

import com.desktopbuddy.data.*;

import javax.swing.*;
import javax.swing.tree.*;
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

    //SETTINGS
    private SettingsData settingsData;

    //TREE
    private JTree tree;
    private DefaultMutableTreeNode rootNode;

    public NoteManager(){
        this.settingsData = ConfigManager.load(); //load pre-existing settings or create new json
        Path dirPath = Path.of(settingsData.getDirectoryPath());
        rootDir = new Folder(dirPath.getFileName().toString(), dirPath);

        rootNode = TreeManager.buildTree(rootDir);
        tree = new JTree(rootNode);


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

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                    if (path == null) return;

                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    Object obj = node.getUserObject();

                    if (obj instanceof Note note) {
                        new NoteEditor(note);
                    }
                } else if(e.getButton() == MouseEvent.BUTTON3){
                    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                    if(path == null) return;

                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                    Object obj = node.getUserObject(); //check if folder or note

                    JPopupMenu deleteMenu = new JPopupMenu();
                    JMenuItem delete = new JMenuItem("Delete");

                    delete.addActionListener(d -> {
                        //check if note
                        if(obj instanceof Note){
                            model.removeNodeFromParent(node);
                        }
                        //TODO: check if folder (harder)
                    });

                    deleteMenu.show(tree, e.getX(), e.getY());
                }
            }
        });

        //add content to manager window
        window.add(toolbar, BorderLayout.NORTH);

        window.add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    private void initializeTreeRenderer(){
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        ((DefaultTreeModel) tree.getModel()).setAsksAllowsChildren(true);

        //get icons
        ImageIcon folderIcon = new ImageIcon("src/main/resources/folder-logo.png");
        Image scaledFolderIcon = folderIcon.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);

        ImageIcon mdIcon = new ImageIcon("src/main/resources/doc-logo.png");
        Image scaledMDIcon = mdIcon.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);

        renderer.setLeafIcon(new ImageIcon(scaledMDIcon));
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

        //Action listeners for add folder and note
        initAddFolderActionListener(addFolder);
        initAddNoteActionListener(addNote);

        addButton.addActionListener(e -> {
            addButtonMenu.show(addButton, addButton.getWidth(), 0);
        });

        return addButton;
    }

    private void initAddNoteActionListener(JMenuItem addNote){
        addNote.addActionListener(e -> {
            String noteName = JOptionPane.showInputDialog(window, "Note name: ");

            //Check if no name entered
            if(noteName == null || noteName.isBlank()) return;

            //Check if name is invalid
            if(noteName.contains("\\") || noteName.contains("/") || noteName.contains("..")){
                JOptionPane.showMessageDialog(window, "Invalid name");
                return;
            }

            //Check if the user did not already enter ".md"
            if(!noteName.toLowerCase().endsWith(".md")){
                noteName += ".md";
            }

            Path newNotePath = null;
            Note newNote = null;
            DefaultMutableTreeNode noteNode = null;

            //check to see if you selected a subfolder of the main dir
            Object selectedNode = tree.getLastSelectedPathComponent();
            if(selectedNode == null){
                newNotePath = rootDir.getFolderPath().resolve(noteName);
                newNote = new Note(noteName, newNotePath);
                noteNode = new DefaultMutableTreeNode(newNote, false);

                //Attempt to create note
                try {
                    Files.createFile(newNotePath);
                } catch (FileAlreadyExistsException ex){
                    JOptionPane.showMessageDialog(window, "A note of name \"" + newNote.getNoteName() + "\" already exists");
                    return; //quit creating the note
                } catch (IOException ex){
                    JOptionPane.showMessageDialog(window, "Couldn't create folder");
                    return;
                }

                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                model.insertNodeInto(noteNode, rootNode, rootNode.getChildCount());

            } else if (selectedNode instanceof DefaultMutableTreeNode node) {
                if (node.getUserObject() instanceof Folder folder) {
                    newNotePath = folder.getFolderPath().resolve(noteName);
                    newNote = new Note(noteName, newNotePath);
                    noteNode = new DefaultMutableTreeNode(newNote, false);

                    //Attempt to create note
                    try {
                        Files.createFile(newNotePath);
                    } catch (FileAlreadyExistsException ex) {
                        JOptionPane.showMessageDialog(window, "A note of name \"" + newNote.getNoteName() + "\" already exists");
                        return; //quit creating the note
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(window, "Couldn't create folder");
                        return;
                    }

                    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                    model.insertNodeInto(noteNode, node, node.getChildCount());
                }
            }
        });
    }

    private void initAddFolderActionListener(JMenuItem addFolder){
        addFolder.addActionListener(e -> {
            String folderName = JOptionPane.showInputDialog(window, "Folder name: ");
            if(folderName == null || folderName.isBlank()) return;

            Path newFolderPath = null;
            Folder newFolder = null;
            DefaultMutableTreeNode folderNode = null;

            //check to see if you selected a subfolder of the main dir
            Object selectedNode = tree.getLastSelectedPathComponent();
            if(selectedNode == null){
                newFolderPath = rootDir.getFolderPath().resolve(folderName);
                newFolder = new Folder(folderName, newFolderPath);
                folderNode = new DefaultMutableTreeNode(newFolder, true);

                //Attempt to create folder
                try {
                    Files.createDirectory(newFolderPath);
                } catch (FileAlreadyExistsException ex){
                    JOptionPane.showMessageDialog(window, "A folder of name \"" + newFolder.getFolderName() + "\" already exists");
                    return; //quit creating the folder
                } catch (IOException ex){
                    JOptionPane.showMessageDialog(window, "Couldn't create folder");
                    return;
                }

                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                model.insertNodeInto(folderNode, rootNode, rootNode.getChildCount());

            } else if (selectedNode instanceof DefaultMutableTreeNode node){
                if (node.getUserObject() instanceof Folder folder) {
                    newFolderPath = folder.getFolderPath().resolve(folderName);
                    newFolder = new Folder(folderName, newFolderPath);
                    folderNode = new DefaultMutableTreeNode(newFolder, true);

                    //Attempt to create folder
                    try {
                        Files.createDirectory(newFolderPath);
                    } catch (FileAlreadyExistsException ex){
                        JOptionPane.showMessageDialog(window, "A folder of name \"" + newFolder.getFolderName() + "\" already exists");
                        return; //quit creating the folder
                    } catch (IOException ex){
                        JOptionPane.showMessageDialog(window, "Couldn't create folder");
                        return;
                    }

                    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                    model.insertNodeInto(folderNode, node, node.getChildCount());
                }
            }




        });
    }

    private JButton initSettingsButton(){
        JButton settingsButton = new JButton();

        ImageIcon settingsIcon = new ImageIcon("src/main/resources/settings-icon.png");
        Image scaler = settingsIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        settingsButton.setIcon(new ImageIcon(scaler));

        settingsButton.addActionListener(e -> {
            new SettingsWindow(settingsData);
        });

        return settingsButton;
    }

}
