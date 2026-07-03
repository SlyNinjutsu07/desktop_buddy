package com.desktopbuddy.data;

public class SettingsData {
    private String directoryPath;

    //for default values
    public SettingsData() {
        this.directoryPath = "";
    }

    public String getDirectoryPath() { return directoryPath; }
    public void setDirectoryPath(String directoryPath) { this.directoryPath = directoryPath; }
}
