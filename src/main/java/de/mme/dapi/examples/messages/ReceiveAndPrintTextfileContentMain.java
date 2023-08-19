package de.mme.dapi.examples.messages;

import de.mme.dapi.utils.ExternalConfigReader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


/*
 *
 *   Reactios are Emojis, taht add as a reaction. Those are defined by their unicode. there a lists online to
 *   get the right unicode for the emoji, or there are several libs around to have an easier usage.
 *
 *   Online Unicode List :   https://www.prosettings.com/emoji-list/
 * */

public class ReceiveAndPrintTextfileContentMain extends ListenerAdapter
{
    public static void main(String[] args) throws IOException {

        String discordToken = new ExternalConfigReader("discordSettings.properties").readProperty("settings.discord.token");

        // We don't need any intents for this bot. Slash commands work without any intents!
        JDA jda = JDABuilder.createLight(discordToken, Collections.emptyList())
                .addEventListeners(new ReceiveAndPrintTextfileContentMain())
                .setActivity(Activity.playing("Type /msgbtn"))
                .build();

        // Sets the global command list to the provided commands (removing all others)
        jda.updateCommands().addCommands(
                Commands.slash("rtxt", "Print the content of the given textfile")
                        .addOption(OptionType.ATTACHMENT,
                                "fileattachment",
                                "File with content to print")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        // make sure we handle the right command
        switch (event.getName()) {
            case "rtxt":
                
                // Get the File from property
                Message.Attachment theAttachment = event.getOptions()
                        .stream()
                        .filter((optMap)->optMap.getName().equals("fileattachment"))
                        .findFirst()
                        .get()
                        .getAsAttachment();
                
                // Collect Infos abou the file
                StringBuilder attachmentInfo = new StringBuilder();
                attachmentInfo.append("Attachment Info:");
                attachmentInfo.append("\n" + "ContentType=" + theAttachment.getContentType());
                attachmentInfo.append("\n" + "Url=" + theAttachment.getUrl());
                attachmentInfo.append("\n" + "Description=" + theAttachment.getDescription());
                attachmentInfo.append("\n" + "FileExtension=" + theAttachment.getFileExtension());
                attachmentInfo.append("\n" + "FileName=" + theAttachment.getFileName());
                attachmentInfo.append("\n" + "Size=" + theAttachment.getSize());

                // read the content of the file and put it into the target string
                try {
                    // get teh Inputstream from the uploaded file on discord and get its content as string
                    String fileContent = new BufferedReader(new InputStreamReader(theAttachment.getProxy().download().get()))
                            .lines().collect(Collectors.joining("\n"));

                    attachmentInfo.append("\n\n" + "FILES CONTENT:");
                    attachmentInfo.append(fileContent);

                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Build the message, including Text and buttons
                MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
                messageCreateBuilder.addContent(attachmentInfo.toString());

                // Create the final message object
                MessageCreateData theFinalMessageData = messageCreateBuilder.build();

                // Send the final Message back to slash command channel
                event.reply(theFinalMessageData).queue();

                break;

        }
    }
}