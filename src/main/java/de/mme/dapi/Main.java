package de.mme.dapi;

import de.mme.cfm.configurations.Configuration;
import de.mme.cfm.repositories.ConfigurationRepository;
import de.mme.cfm.repositories.TextFileRepository;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Main {


    private static String discordConfigFilename = "configs/discordSettings.cfg";
    static private Configuration cfg;

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    static{
        ConfigurationRepository cfgFile = new TextFileRepository(Path.of(discordConfigFilename));;
        cfg = cfgFile.load();


    }



    public static void main(String[] args){

        LOGGER.info("a test message");


        JDABuilder builder = JDABuilder.createDefault(
                cfg.getEntry("settings.discord.token").getValue());

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("TV"));

        builder.build();
    }




}
