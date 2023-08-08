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
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;


/*
 *
 *   Reactios are Emojis, taht add as a reaction. Those are defined by their unicode. there a lists online to
 *   get the right unicode for the emoji, or there are several libs around to have an easier usage.
 *
 *   Online Unicode List :   https://www.prosettings.com/emoji-list/
 * */

public class MessageWithButtonsMain extends ListenerAdapter
{
    public static void main(String[] args) throws IOException {

        String discordToken = new ExternalConfigReader("discordSettings.properties").readProperty("settings.discord.token");

        // We don't need any intents for this bot. Slash commands work without any intents!
        JDA jda = JDABuilder.createLight(discordToken, Collections.emptyList())
                .addEventListeners(new MessageWithButtonsMain())
                .setActivity(Activity.playing("Type /msgbtn"))
                .build();

        // Sets the global command list to the provided commands (removing all others)
        jda.updateCommands().addCommands(
                Commands.slash("msgbtn", "Shows a message with Buttons"),
                Commands.slash("msgbtnwemb", "Shows a embedded message with Buttons")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        // make sure we handle the right command
        switch (event.getName()) {
            case "msgbtn":

                // Create thre Buttons. Ok with Primary Style, and What and No with Secondary Style
                Button btnOk = Button.primary("btn-ok-id","OK");
                Button btnWhat = Button.secondary("btn-what-id","What?!?!");
                Button btnNo = Button.secondary("btn-no-id","NO");

                // Build the message, including Text and buttons
                MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
                messageCreateBuilder.addContent("This is an exaomle message with two buttons")
                        .setActionRow(btnOk,btnWhat,btnNo);

                // Create the final message object
                MessageCreateData theFinalMessageData = messageCreateBuilder.build();

                // Send the final Message back to slash command channel
                event.reply(theFinalMessageData).queue();

                break;
            case "msgbtnwemb":


                // Crate the main message builder. We will work with that up to now
                MessageCreateBuilder msgBuilder = new MessageCreateBuilder();
                msgBuilder.addContent("This is an example message with 4 buttons and some Empbed Message");

                // Create 4 buttons and add them to the MessageBuilder
                Button ButtonAllow = Button.primary("btn-allow-id","Allow");
                Button ButtonDeny = Button.secondary("btn-deny-id","Deny");
                Button ButtonDenyWithReason = Button.secondary("btn-denyreason-id","Deny Reason");
                Button ButtonAlert = Button.danger("btn-alert-id","ALERT!");

                msgBuilder.setActionRow(ButtonAllow,ButtonDeny,ButtonDenyWithReason,ButtonAlert);


                // Create an embed to include into the message and add it to the MessageBuilder
                EmbedBuilder eb = new EmbedBuilder();
                eb.setAuthor("Embedded Author")
                        .setColor(Color.RED)
                        .setTitle("The embed text")
                        .setDescription("The embed description");
                MessageEmbed theEmbed = eb.build();

                msgBuilder.addEmbeds(theEmbed);


                // Create the final message object
                MessageCreateData finalMessage = msgBuilder.build();

                // Send the final Message back to slash command channel, but we also add some Reactions :)
                event.reply("Sure").queue();
                event.getChannel().sendMessage(finalMessage).queue((msg)->{
                    Emoji reactionA = Emoji.fromUnicode("U+1F1E6");
                    Emoji reactionB = Emoji.fromUnicode("U+1F1E7");
                    msg.addReaction(reactionA).queue();
                    msg.addReaction(reactionB).queue();
                });

               break;
        }
    }
}