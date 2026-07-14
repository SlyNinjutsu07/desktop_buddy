package com.desktopbuddy.data;

import java.io.IOException;
import java.nio.file.Files;

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
}
