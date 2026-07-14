package com.desktopbuddy.ui;

import javax.swing.*;
import com.desktopbuddy.data.*;

public class NoteEditor {
    private JFrame window;

    public NoteEditor(Note note){
        window = new JFrame();
        window.setSize(400,400);

        JTextArea textArea = new JTextArea();
        textArea.setText(NoteIO.getNoteContent(note));
        window.setVisible(true);

        window.add(new JScrollPane(textArea));
    }
}
