package com.desktopbuddy.data;

import javax.swing.*;
import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NoteIO {

    public static String getNoteContent(Note note){
        try {
            String content = Files.readString(note.getFilePath());
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; //if returning null, let save() function save nothing
    }

    public static void saveNoteContent(Note note, String content){
        // never overwrite a real note with nothing (e.g. a failed read upstream)
        if (content == null) return;

        Path target = note.getFilePath();
        Path dir = target.getParent();
        Path temp = null;

        try {
            // write to a temp file IN THE SAME DIRECTORY as the target
            temp = Files.createTempFile(dir, target.getFileName().toString(), ".tmp");
            Files.writeString(temp, content);

            // swap the temp file into place. ATOMIC_MOVE means the target is
            //    either the old file or the fully-written new one, never a half-write.
            try {
                Files.move(temp, target,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ex) {
                // some filesystems can't do atomic moves; fall back to a plain replace
                Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // clean up the temp file if we never managed to move it into place
            if (temp != null) {
                try { Files.deleteIfExists(temp); } catch (IOException ignored) {}
            }
        }
    }

    public static boolean deleteNote(Path path){
        try{
            Files.deleteIfExists(path);
            return true;
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Could not delete " + path.getFileName());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFolder(Path path){
        // moveToTrash sends the whole folder (contents included) to the OS trash in
        // ONE call -- recoverable, and no manual recursion needed.
        if (!Desktop.isDesktopSupported()) return false;

        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.MOVE_TO_TRASH)) return false;

        try {
            return desktop.moveToTrash(path.toFile());
        } catch (IllegalArgumentException | SecurityException e) {
            // file doesn't exist, or we don't have permission to remove it
            e.printStackTrace();
            return false;
        }
    }
}
