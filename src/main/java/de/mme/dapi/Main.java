package de.mme.dapi;

import de.mme.dapi.utils.ExternalConfigReader;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {


     public static Logger logger;
     public static ExternalConfigReader discordConfigs;


    public static void main(String[] args){

        // INIT the Application
        initLogger();
        initExternalConfigReaders();

        logger.info("...Application Initialized");

        // Get Configurations
        String discordToken = discordConfigs.readProperty("settings.discord.token");
        discordToken = discordConfigs.readProperty("settings.discord.tokenXX");



        JDABuilder builder = JDABuilder.createDefault(discordToken);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("TV"));

        builder.build();
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
