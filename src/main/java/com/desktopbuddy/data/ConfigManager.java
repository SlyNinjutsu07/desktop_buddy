package com.desktopbuddy.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static final Path CONFIG_DIR = Path.of(System.getProperty("user.home"), ".desktop_buddy");
    private static final Path CONFIG_FILE = CONFIG_DIR.resolve("config.json");
    private static final ObjectMapper mapper = new ObjectMapper();

    public static SettingsData load() {
        // TODO: if CONFIG_FILE doesn't exist, create the directory + file with a default SettingsData and return it
        // TODO: if CONFIG_FILE exists, read it and return the deserialized SettingsData
        return new SettingsData();
    }

    public static void save(SettingsData data) {
        // TODO: write data to CONFIG_FILE as JSON
    }
}
