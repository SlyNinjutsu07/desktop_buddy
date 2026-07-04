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

    public static SettingsData load(){
        try{
            //if file location doesn't exist, make a new json
            if(!Files.exists(CONFIG_FILE)){
                SettingsData data = new SettingsData();
                save(data);
                return data;
            }

            //alternatively, just read it
            return mapper.readValue(CONFIG_FILE.toFile(), SettingsData.class);
        } catch (IOException e){ //in case something breaks
            e.printStackTrace();
            return new SettingsData();//return defaults for the program to keep running
        }

    }

    public static void save(SettingsData data) {
        try{
            Files.createDirectories(CONFIG_DIR); //double-checks existence of directory
            mapper.writeValue(CONFIG_FILE.toFile(), data);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
