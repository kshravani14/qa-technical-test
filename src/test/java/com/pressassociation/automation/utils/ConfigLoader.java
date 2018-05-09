package com.pressassociation.automation.utils;

import com.pressassociation.automation.session.DataStore;

import java.io.*;
import java.util.Properties;

public class ConfigLoader {
    Properties prop = new Properties();
    InputStream input = null;

    //loading config from config files
    public void loadConfig(){
        try {
            input = new FileInputStream("src"+File.separator+"test"+ File.separator+"resources"+
                    File.separator+"config"+File.separator+"config.properties");
            prop.load(input);
            DataStore.setDataKey("baseURL", prop.getProperty("baseURL"));
            DataStore.setDataKey("VideoResource", prop.getProperty("VideoResource"));
            DataStore.setDataKey("PlaylistResource", prop.getProperty("PlaylistResource"));
        } catch (IOException io) {
            System.out.println( io.toString());
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
