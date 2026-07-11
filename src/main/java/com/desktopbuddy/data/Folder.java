package com.desktopbuddy.data;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Folder {
    private String folderName;
    private Path folderPath; //the actual directory on disk

    private List<Folder> folders;
    private List<Note> notes;

    public Folder() {
        this.folders = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    public Folder(String folderName, Path folderPath) {
        this.folderName = folderName;
        this.folderPath = folderPath;
        this.folders = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    public String getFolderName() { return folderName; }
    public void setFolderName(String folderName) { this.folderName = folderName; }

    public Path getFolderPath() { return folderPath; }
    public void setFolderPath(Path folderPath) { this.folderPath = folderPath; }

    public List<Folder> getFolders() { return folders; }
    public List<Note> getNotes() { return notes; }

    public void addFolder(Folder folder) { this.folders.add(folder); }
    public void addNote(Note note) { this.notes.add(note); }

    public String toString() {return getFolderName(); }
}
