package com.desktopbuddy;

import com.desktopbuddy.data.SettingsData;
import com.desktopbuddy.ui.SettingsWindow;
import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        //Sprite buddy = new Sprite(50, 50);
        //NoteManager nw = new NoteManager();
        new SettingsWindow(new SettingsData());
    }
}
