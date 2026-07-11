package com.desktopbuddy.data;

import java.nio.file.Path;

public class Note {
    private String noteName;
    private Path filePath; //the actual .md file on disk

    public Note() {}

    public Note(String noteName, Path filePath) {
        this.noteName = noteName;
        this.filePath = filePath;
    }

    public String getNoteName() { return noteName; }
    public void setNoteName(String noteName) { this.noteName = noteName; }

    public Path getFilePath() { return filePath; }
    public void setFilePath(Path filePath) { this.filePath = filePath; }

    public String toString() {return getNoteName(); }
}
