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
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
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

    public static final String BTN_OK_ID = "btn-ok-id";
    public static final String BTN_WHAT_ID = "btn-what-id";
    public static final String BTN_NO_ID = "btn-no-id";
    public static final String BTN_ALLOW_ID = "btn-allow-id";
    public static final String BTN_DENY_ID = "btn-deny-id";
    public static final String BTN_DENYREASON_ID = "btn-denyreason-id";
    public static final String BTN_ALERT_ID = "btn-alert-id";

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
    public void onButtonInteraction (ButtonInteractionEvent event){
        if (event.getComponentId().equals(BTN_OK_ID)){
            event.reply("Button pressed: " + "OK").queue();
        }
        if (event.getComponentId().equals(BTN_ALERT_ID)) {
            event.reply("Button pressed: " + "ALERT").queue();
        }
        if (event.getComponentId().equals(BTN_DENYREASON_ID)) {
            event.reply("Button pressed: " + "DENY REASON").queue();
        }
        if (event.getComponentId().equals(BTN_DENY_ID)) {
            event.reply("Button pressed: " + "DENY").queue();
        }
        if (event.getComponentId().equals(BTN_ALLOW_ID)) {
            event.reply("Button pressed: " + "ALLOW").queue();
        }
        if (event.getComponentId().equals(BTN_NO_ID)) {
            event.reply("Button pressed: " + "NO").queue();
        }
        if (event.getComponentId().equals(BTN_WHAT_ID)) {
            event.reply("Button pressed: " + "WHAT").queue();
        }


    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        // make sure we handle the right command
        switch (event.getName()) {
            case "msgbtn":

                // Create thre Buttons. Ok with Primary Style, and What and No with Secondary Style
                Button btnOk = Button.primary(BTN_OK_ID,"OK");
                Button btnWhat = Button.secondary(BTN_WHAT_ID,"What?!?!");
                Button btnNo = Button.secondary(BTN_NO_ID,"NO");

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

                // ADD BUTTONS --------------------------------------------------------------------------------
                // Create 4 buttons and add them to the MessageBuilder
                Button ButtonAllow = Button.primary(BTN_ALLOW_ID,"Allow");
                Button ButtonDeny = Button.secondary(BTN_DENY_ID,"Deny");
                Button ButtonDenyWithReason = Button.secondary(BTN_DENYREASON_ID,"Deny Reason");
                Button ButtonAlert = Button.danger(BTN_ALERT_ID,"ALERT!");

                msgBuilder.setActionRow(ButtonAllow,ButtonDeny,ButtonDenyWithReason,ButtonAlert);


                // ADD EMBEDS - Create an embed to include into the message and add it to the MessageBuilder
                // ----------------------------------------------------------------------------
                // Embed A
                EmbedBuilder eb = new EmbedBuilder();
                eb.setAuthor("Embedded Author")
                        .setColor(Color.RED)
                        .setTitle("The embed text")
                        .setDescription("The embed description");
                MessageEmbed theEmbed = eb.build();
                // Embed B
                EmbedBuilder ebB = new EmbedBuilder();
                ebB.setAuthor("Kein Author notwendig")
                        .setColor(Color.GREEN)
                        .setTitle("This is the title of Embed B")
                        .setDescription("Description of embed B");
                MessageEmbed theEmbedB = eb.build();


                msgBuilder.addEmbeds(theEmbed,theEmbedB);


                // Create the final message object
                MessageCreateData finalMessage = msgBuilder.build();

                // Send the final Message back to slash command channel, but we also add some Reactions :)
                event.reply("Sure").queue();
                event.getChannel().sendMessage(finalMessage).queue((msg)->{
                    // ADD REACTIONS ---------------------------------------------------------------
                    Emoji reactionA = Emoji.fromUnicode("U+1F1E6");
                    Emoji reactionB = Emoji.fromUnicode("U+1F1E7");
                    msg.addReaction(reactionA).queue();
                    msg.addReaction(reactionB).queue();
                });

               break;
        }
    }
}