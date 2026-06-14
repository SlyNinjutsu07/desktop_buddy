import javax.swing.*;
import java.awt.*;

public class NoteManager {
    private JFrame window;

    //BUTTONS
    private JButton addNoteButton;

    public NoteManager(){
        window = new JFrame("desktop buddy notes");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension desktop_size = Toolkit.getDefaultToolkit().getScreenSize();

        window.setSize(400, 400);
        window.setLocation(desktop_size.width / 2 - 600, desktop_size.height / 2 - 200);

        window.setLayout(null);


        addButtons();
        addListeners();

        //window.add(new JScrollPane(pane));

        addNoteButton.setVisible(true);

        window.setVisible(true);
    }

    private void addButtons(){
        addNoteButton = new JButton();

        ImageIcon addIcon = new ImageIcon("add-icon.png");
        Image scaler = addIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledAddIcon = new ImageIcon(scaler);
        addNoteButton.setIcon(scaledAddIcon);
        addNoteButton.setBounds(10,10,30,30);


        window.add(addNoteButton);
    }

    private void addListeners(){
        addNoteButton.addActionListener(e->{
            System.out.println("Can't do anything right now!");
        });
    }
}
