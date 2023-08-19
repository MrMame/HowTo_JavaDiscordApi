package de.mme.dapi.examples.messages;

import de.mme.dapi.utils.ExternalConfigReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.io.*;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


/*
 *
 *   Reactios are Emojis, taht add as a reaction. Those are defined by their unicode. there a lists online to
 *   get the right unicode for the emoji, or there are several libs around to have an easier usage.
 *
 *   Online Unicode List :   https://www.prosettings.com/emoji-list/
 * */

public class CreateTextfileAndSendToDiscordUserMain extends ListenerAdapter
{
    public static void main(String[] args) throws IOException {

        String discordToken = new ExternalConfigReader("discordSettings.properties").readProperty("settings.discord.token");

        // We don't need any intents for this bot. Slash commands work without any intents!
        JDA jda = JDABuilder.createLight(discordToken, Collections.emptyList())
                .addEventListeners(new CreateTextfileAndSendToDiscordUserMain())
                .setActivity(Activity.playing("HowToBot"))
                .build();

        // Sets the global command list to the provided commands (removing all others)
        jda.updateCommands().addCommands(
                Commands.slash("stxt", "Print the content of the given textfile")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        // make sure we handle the right command
        if(event.getName().equals("stxt")) {


            // Create a new Message
            MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
            messageCreateBuilder.addContent("My Message");

            // get the Content as an InputStream, so we can use that instead of a file object and add it to the message
            InputStream targetStream = new ByteArrayInputStream("That's the content, my friend.".getBytes());
            messageCreateBuilder.addFiles(FileUpload.fromData(targetStream,"output.txt"));

            // Create the final message object and reply it to the command
            MessageCreateData theFinalMessageData = messageCreateBuilder.build();
            event.reply(theFinalMessageData).queue();

        }
    }
}