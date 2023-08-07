package de.mme.dapi.examples.embeds;

import de.mme.dapi.utils.ExternalConfigReader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;

public class EmbedsExampleSimpleMain extends ListenerAdapter
{
    public static void main(String[] args) throws IOException {

        String discordToken = new ExternalConfigReader("discordSettings.properties").readProperty("settings.discord.token");

        // We don't need any intents for this bot. Slash commands work without any intents!
        JDA jda = JDABuilder.createLight(discordToken, Collections.emptyList())
                .addEventListeners(new EmbedsExampleSimpleMain())
                .setActivity(Activity.playing("Type /embed1"))
                .build();

        // Sets the global command list to the provided commands (removing all others)
        jda.updateCommands().addCommands(
                Commands.slash("embed1", "Shows the first embed example")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        // make sure we handle the right command
        switch (event.getName()) {
            case "embed1":
                /* If the slash command /embed1 gets received, a new embed is created and postet by the bot into
                 the same channel.*/
                // Build the embed
                EmbedBuilder eb = new EmbedBuilder();

                eb.setAuthor("Dissi")
                        .setColor(Color.ORANGE)
                        .addField("This embed was requested by user", event.getUser().getName(),false)
                        .addField("Users avatar url", event.getUser().getAvatar().getUrl(),false)
                        .addField("NameFieldA", "NameFieldA's value to read, and it's inline!", true)
                        .addField("NameFieldB", "NameFieldB's value to read, and it's inline!", true)
                        .addField("NameFieldC", "NameFieldC's value to read, and it's NOT inline!", false)
                        .addField("NameFieldD", "NameFieldD's value to read, and it's NOT inline!", false)
                        .setDescription("A Nice Description text for you :)")
                        .setFooter("here is a footer without a link")
                        .setImage("https://img.freepik.com/vektoren-kostenlos/niedliche-monster-kind-karikatur-vektor-symbol-illustration-monster-holiday-icon-konzept-isoliert-premium-vektor-flacher-cartoon-stil_138676-3995.jpg")
                        .setThumbnail("https://img.freepik.com/vektoren-kostenlos/nette-pizza-cartoon-vektor-icon-illustration-fast-food-symbol-konzept-flacher-cartoon-stil_138676-2588.jpg")
                        .setTitle("Thats my first embed example with a link", "http://www.google.com")
                        .appendDescription("Something to append the description text");
                // send it into the channel
                event.getChannel().sendMessageEmbeds(eb.build()).queue();
                break;
        }
    }
}