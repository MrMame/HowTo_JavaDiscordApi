package de.mme.dapi;

import de.mme.cfm.configurations.Configuration;
import de.mme.cfm.repositories.ConfigurationRepository;
import de.mme.cfm.repositories.TextFileRepository;
import de.mme.dapi.utils.ExternalConfigReader;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {


    static private Configuration cfg;

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args){

        LOGGER.info("Start main Method");


        String token = "";
        try {
            token = new ExternalConfigReader("discordSettings.properties").readProperty("settings.discord.token");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        JDABuilder builder = JDABuilder.createDefault(token);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("TV"));

        builder.build();
    }




}
