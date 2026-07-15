package com.desktopbuddy.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.desktopbuddy.data.*;

public class NoteEditor {
    private JFrame window;
    private JTextPane textPane;

    public NoteEditor(Note note){
        window = new JFrame();
        window.setSize(400,400);

        textPane = new JTextPane();
        String content = NoteIO.getNoteContent(note);
        //check if null
        if (content == null) return;
        textPane.setText(content);

        //Add component
        window.add(new JScrollPane(textPane));
        initAutoSave(note, content);

        window.setVisible(true);
    }


    //Continuously run a 1-second timer that resets after a new change occurs
    //After 1-second passes, save content to file
    private void initAutoSave(Note note, String content){
        Timer saveTimer = new Timer(1000, e -> {
            NoteIO.saveNoteContent(note, NoteIO.getNoteContent(note));
        });
        saveTimer.setRepeats(false);

        textPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { //add/insert
                saveTimer.restart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) { //deletion
                saveTimer.restart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) { //styling
                //unimplemented because we don't want constant saves when styling is enabled
            }
        });

    }
}
