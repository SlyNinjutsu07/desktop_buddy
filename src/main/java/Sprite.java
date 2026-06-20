import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sprite{
    private JFrame window;
    private JPopupMenu menu;
    private JLabel label;

    //private NoteManager manager;

    public Sprite(int width, int height){
        window = new JFrame();
        initializeMenu();
        window.setAlwaysOnTop(true);

        //To get monitor's width and height
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon icon = new ImageIcon("src/main/resources/pixel_frog.png");
        Image scaledImg = icon.getImage().getScaledInstance(width, height, Image.SCALE_REPLICATE);
        label = new JLabel(new ImageIcon(scaledImg));
        window.add(label);

        window.setTitle("desktop_buddy");
        window.setSize(width, height);

        //centers the window
        window.setLocation((int) size.getWidth()/2 - 50, (int) size.getHeight()/2 - 50);
        window.pack(); //removes extra space
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //end task on exing out
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
            System.out.println("Can't do that");    
        });

        JMenuItem print_text = new JMenuItem("Print Input");
        print_text.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter something...");
            System.out.println(input);
        });

        menu.add(notes_window);
        menu.add(new_window);
        menu.add(print_text);
    }
}
