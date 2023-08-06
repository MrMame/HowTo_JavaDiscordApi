package de.mme.dapi.discord;

import de.mme.dapi.discord.events.ReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDAFactory {

    static Logger logger = LoggerFactory.getLogger(JDAFactory.class);

    public static JDA CreateJda(String discordToken)  {

        JDA returnJda = null;

        JDABuilder builder = JDABuilder.createDefault(discordToken);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("TV"));

        // Add all Event Releated code here
        builder.addEventListeners(new ReadyListener());

        returnJda =  builder.build();

        // Wait for the JDA Object to be ready for use
        try {
            returnJda.awaitReady();
            logger.info("...JDA Object was created and is ready to use.");
        } catch (InterruptedException e) {
            logger.warn("Abort waiting for JDA Object to get ready. There is no JDA Object available.");
            throw new RuntimeException(e);
        }

        return returnJda;
    }



}
