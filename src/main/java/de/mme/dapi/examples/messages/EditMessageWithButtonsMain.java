package de.mme.dapi.examples.messages;

import de.mme.dapi.utils.ExternalConfigReader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
 *
 *
 *
 *  https://www.reddit.com/r/Discordjs/comments/q8x4if/v13_silently_end_an_interaction_without_replying/
 * */

public class EditMessageWithButtonsMain extends ListenerAdapter
{

    public static final String BTN_ADDUSER_A_ID = "btn-adduser-A-id";
    public static final String BTN_ADDUSER_B_ID = "btn-adduser-B-id";
    public static final String BTN_ADDUSER_C_ID = "btn-adduser-C-id";

    public static final String EMBED_A_TITLE = "A";
    public static final String EMBED_B_TITLE = "B";
    public static final String EMBED_C_TITLE = "C";


    public static final String SLASH_COMMAND_NAME = "msgedit";
    public static final String SLASH_COMMAND_DESCRIPTION = "Add the username to the Embed " + EMBED_A_TITLE + " by buttonpress ";


    public static void main(String[] args) throws IOException {

        String discordToken = new ExternalConfigReader("discordSettings.properties").readProperty("settings.discord.token");

        // We don't need any intents for this bot. Slash commands work without any intents!
        JDA jda = JDABuilder.createLight(discordToken, Collections.emptyList())
                .addEventListeners(new EditMessageWithButtonsMain())
                .setActivity(Activity.playing("Type /" + SLASH_COMMAND_NAME))
                .build();

        // Sets the global command list to the provided commands (removing all others)
        jda.updateCommands().addCommands(
                Commands.slash(SLASH_COMMAND_NAME, SLASH_COMMAND_DESCRIPTION)
        ).queue();
    }


    @Override
    public void onButtonInteraction (ButtonInteractionEvent event) {
        if (event.getComponentId().equals(BTN_ADDUSER_A_ID)) {
            List<MessageEmbed> oldEmbeds = event.getMessage().getEmbeds();
            List<MessageEmbed> newEmbeds = new ArrayList<>();
            for (MessageEmbed emb : oldEmbeds) {
                EmbedBuilder neb = new EmbedBuilder();
                neb.setTitle(emb.getTitle());
                neb.appendDescription(emb.getDescription());
                if (emb.getTitle().equals(EMBED_A_TITLE)) {
                    neb.appendDescription("\r\n" + event.getUser().getAsMention());
                }
                newEmbeds.add(neb.build());
            }
            event.editMessageEmbeds(newEmbeds).queue();
        }
        // Or using Streams
        // todo
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        // make sure we handle the right command
        switch (event.getName()) {
            case SLASH_COMMAND_NAME:

                // Create thre Buttons. Ok with Primary Style, and What and No with Secondary Style
                Button btnAddUsername = Button.primary(BTN_ADDUSER_A_ID,"Add Username");

                // Build the message, including Text and buttons
                MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
                messageCreateBuilder.addContent("This is the Target message. Everytime you click the Button, you will add your username to the message")
                        .setActionRow(btnAddUsername);

                // Add Target Embeds
                EmbedBuilder ebA = new EmbedBuilder();
                ebA.setTitle(EMBED_A_TITLE);
                ebA.setDescription("Description of Embed " + EMBED_A_TITLE);
                EmbedBuilder ebB = new EmbedBuilder();
                ebB.setTitle(EMBED_B_TITLE);
                ebB.setDescription("Description of Embed " + EMBED_B_TITLE);
                EmbedBuilder ebC = new EmbedBuilder();
                ebC.setTitle(EMBED_C_TITLE);
                ebC.setDescription("Description of Embed " + EMBED_C_TITLE);
                messageCreateBuilder.addEmbeds(ebA.build());
                messageCreateBuilder.addEmbeds(ebB.build());
                messageCreateBuilder.addEmbeds(ebC.build());

                // Create the final message object
                MessageCreateData theFinalMessageData = messageCreateBuilder.build();

                // Send the final Message back to slash command channel
                event.reply(theFinalMessageData).queue();

                break;

        }
    }



}