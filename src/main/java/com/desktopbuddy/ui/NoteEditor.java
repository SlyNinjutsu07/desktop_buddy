package com.desktopbuddy.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

import com.desktopbuddy.data.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoteEditor {
    private JFrame window;
    private JTextPane textPane;

    private boolean isStyling = false;
    private List<SimpleAttributeSet> sets = new ArrayList<>();

    public NoteEditor(Note note){
        window = new JFrame("📝 " + note.getNoteName());
        window.setSize(400,400);
        initializeSets();

        textPane = new JTextPane();
        String content = NoteIO.getNoteContent(note);
        //check if null
        if (content == null) return;
        textPane.setText(content);

        //Add component
        window.add(new JScrollPane(textPane));
        if(note.getNoteName().endsWith(".md"))
            parseMarkdown(textPane.getStyledDocument());
        initAutoSave(note);

        window.setVisible(true);
    }


    //Continuously run a 1-second timer that resets after a new change occurs
    //After 1-second passes, save content to file
    private void initAutoSave(Note note){
        Timer saveTimer = new Timer(1000, e -> {
            NoteIO.saveNoteContent(note, textPane.getText());
            System.out.println("note saved");
        });
        saveTimer.setRepeats(false);

        textPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { //add/insert
                saveTimer.restart();
                if(note.getNoteName().endsWith(".md"))
                    parseMarkdown(textPane.getStyledDocument());
            }

            @Override
            public void removeUpdate(DocumentEvent e) { //deletion
                saveTimer.restart();
                if(note.getNoteName().endsWith(".md"))
                    parseMarkdown(textPane.getStyledDocument());
            }

            @Override
            public void changedUpdate(DocumentEvent e) { //styling
                //unimplemented because we don't want constant saves when styling is enabled
            }
        });
    }

    private void parseMarkdown(StyledDocument doc){
        if (isStyling) return;

        SwingUtilities.invokeLater(()-> {
            isStyling = true;
            try {
                String text = doc.getText(0, doc.getLength());

                doc.setCharacterAttributes(0, doc.getLength(), sets.get(0), true);

                // Match Bold: **text**
                Matcher boldMatcher = Pattern.compile("\\*\\*(.*?)\\*\\*").matcher(text);
                while (boldMatcher.find()) {
                    int start = boldMatcher.start();
                    int length = boldMatcher.end() - start;
                    doc.setCharacterAttributes(start, length, sets.get(1), false);
                }

                // Match Italic: *text*
                Matcher italicMatcher = Pattern.compile("\\*(.*?)\\*").matcher(text);
                while (italicMatcher.find()) {
                    int start = italicMatcher.start();
                    int length = italicMatcher.end() - start;
                    doc.setCharacterAttributes(start, length, sets.get(2), false);
                }

                // Match italics and bold: ***text***
                Matcher italicAndBoldMatcher = Pattern.compile("\\*\\*\\*(.*?)\\*\\*\\*").matcher(text);
                while (italicAndBoldMatcher.find()) {
                    int start = italicAndBoldMatcher.start();
                    int length = italicAndBoldMatcher.end() - start;
                    doc.setCharacterAttributes(start, length, sets.get(3), false);
                }

                // Match strike through: ~~text~~
                Matcher strikeThroughMatcher = Pattern.compile("~~(.*?)~~").matcher(text);
                while (strikeThroughMatcher.find()) {
                    int start = strikeThroughMatcher.start();
                    int length = strikeThroughMatcher.end() - start;
                    doc.setCharacterAttributes(start, length, sets.get(4), false);
                }

            } catch (BadLocationException e){
                e.printStackTrace();
            } finally {
                isStyling = false;
            }
        });
    }

    private void initializeSets(){
        SimpleAttributeSet defaultStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(defaultStyle, Color.WHITE);
        StyleConstants.setBold(defaultStyle, false);
        StyleConstants.setItalic(defaultStyle, false);

        SimpleAttributeSet boldStyle = new SimpleAttributeSet();
        StyleConstants.setBold(boldStyle, true);

        SimpleAttributeSet italicStyle = new SimpleAttributeSet();
        StyleConstants.setItalic(italicStyle, true);

        SimpleAttributeSet italicAndBoldStyle = new SimpleAttributeSet();
        StyleConstants.setItalic(italicAndBoldStyle, true);
        StyleConstants.setBold(italicAndBoldStyle, true);

        SimpleAttributeSet strikeThroughStyle = new SimpleAttributeSet();
        StyleConstants.isStrikeThrough(strikeThroughStyle);

        sets.add(defaultStyle);
        sets.add(boldStyle);
        sets.add(italicStyle);
        sets.add(italicAndBoldStyle);
        sets.add(strikeThroughStyle);;
    }
}
