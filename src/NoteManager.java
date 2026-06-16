import javax.swing.*;
import java.awt.*;

public class NoteManager {
    private JFrame window;

    //ADD BUTTON
    private JButton addButton;
    private JPopupMenu addButtonOptions;

    public NoteManager(){
        window = new JFrame("desktop buddy notes");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension desktop_size = Toolkit.getDefaultToolkit().getScreenSize();

        window.setSize(400, 400);
        window.setLocation(desktop_size.width / 2 - 600, desktop_size.height / 2 - 200);

        window.setLayout(null);

        initializeComponents();

        //window.add(new JScrollPane(pane));

        addButton.setVisible(true);

        window.setVisible(true);
    }

    private void initializeComponents(){
        addInterface();
        addListeners();
    }

    private void addInterface(){
        addButton = new JButton();

        ImageIcon addIcon = new ImageIcon("add-icon.png");
        Image scaler = addIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledAddIcon = new ImageIcon(scaler);
        addButton.setIcon(scaledAddIcon);
        addButton.setBounds(10,10,30,30);


        window.add(addButton);
        addButtonOptions = new JPopupMenu();
        addButton.add(addButtonOptions);
    }

    private void addListeners(){

        /* ADD BUTTON FUNCTIONALITY */
        JMenuItem addFolder = new JMenuItem("Create Folder");
        JMenuItem addNote = new JMenuItem("Create Note");

        addButtonOptions.add(addFolder);
        addButtonOptions.add(addNote);

        addFolder.addActionListener(e->{
            //ADD A FOLDER
        });
        addNote.addActionListener(e->{
            //ADD A NOTE
        });

        addButton.addActionListener(e->{
            addButtonOptions.show(addButton, addButton.getWidth(), 0);
        });
        /* END */
    }
}
