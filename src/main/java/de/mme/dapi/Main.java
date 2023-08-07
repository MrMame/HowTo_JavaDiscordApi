package de.mme.dapi;

import de.mme.dapi.discord.JDAFactory;
import de.mme.dapi.utils.ExternalConfigReader;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {


     public static Logger logger;
     public static ExternalConfigReader discordConfigs;


    public static void main(String[] args){

        // INIT the Application
        initApplication();

        // Get Configurations
        String discordToken = discordConfigs.readProperty("settings.discord.token");

        // get Main Discord Api Object, this also will start all Listeners
        JDA jda = JDAFactory.CreateExampleJda(discordToken);

    }


    private static void initApplication(){
        initLogger();
        initExternalConfigReaders();
        logger.info("...Application Initialized");
    }
    private static void initLogger(){
       logger = LoggerFactory.getLogger(Main.class);
       logger.info("...logger initialized...");
    }
    private static void initExternalConfigReaders(){
        try {
            discordConfigs = new ExternalConfigReader("discordSettings.properties");
            logger.info("...external config readers initialized...");
        } catch (IOException e) {
            logger.error("Error while trying to init external config readers");
            throw new RuntimeException(e);
        }
    }




}
