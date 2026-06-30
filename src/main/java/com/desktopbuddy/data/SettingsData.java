package com.desktopbuddy.data;

public class SettingsData {
    private String directoryPath;
    private boolean isPathValid;

    public String getDirectoryPath() { return directoryPath; }
    public void setDirectoryPath(String directoryPath) { this.directoryPath = directoryPath; }

    public boolean isPathValid() { return isPathValid; }
    public void setPathValid(boolean isPathValid) { this.isPathValid = isPathValid; }
}
