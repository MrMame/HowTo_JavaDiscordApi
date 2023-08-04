import de.mme.cfm.configurations.Configuration;
import de.mme.cfm.repositories.ConfigurationRepository;
import de.mme.cfm.repositories.TextFileRepository;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.nio.file.Path;

public class main {

    static private final ConfigurationRepository cfg = new TextFileRepository(Path.of("configs/discordSettings.cfg"));;





    static{

        Configuration config = cfg.load();

        // Use some settings
        String settingName = config.getEntry("MySetting2").getName();
        String settingValue = config.getEntry("MySetting2").getValue();

        System.out.println("Setting Name is " + settingName + " and it's value is " + settingValue);
    }



    public static void main(String[] args){
        JDABuilder builder = JDABuilder.createDefault(args[0]);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("TV"));

        builder.build();
    }


    private void intiConfig(){
        // Create textfile repository to get access to the configuration textfile
        ConfigurationRepository fsr = new TextFileRepository(Path.of("exampleConfig.cfg"));


    }




}
