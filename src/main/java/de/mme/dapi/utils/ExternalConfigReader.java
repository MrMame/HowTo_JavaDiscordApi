package de.mme.dapi.utils;

import de.mme.dapi.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Properties;

public class ExternalConfigReader {


    private static final String CONFIGFOLDER = "externalConfigs/";
    private Properties configProps = null;





    public ExternalConfigReader(String Configfilename) throws IOException {
        this.configProps = readExternalConfigProperties(Configfilename);
    }

    public String readProperty(String propertyName){
        return configProps.getProperty(propertyName);
    }




    private Properties readExternalConfigProperties(String Filename) throws IOException {
        String configFilename = getJarPath() + CONFIGFOLDER + Filename;

        Properties discordProps = new Properties();
        try(FileInputStream fis = new FileInputStream(configFilename)){
            discordProps.load(fis);
        }
        return discordProps;
    }




    private String getJarPath(){

        String jarPath="";

        try {
                // Get path of the JAR file
                jarPath = Main.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()
                        .getPath();
                System.out.println("JAR Path : " + jarPath + "\n");
                // Get Folder Name
                jarPath = "/" + jarPath.substring(1,jarPath.lastIndexOf("/")+1);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        return jarPath;
    }

}
