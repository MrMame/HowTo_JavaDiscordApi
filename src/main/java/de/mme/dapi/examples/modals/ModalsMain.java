package de.mme.dapi.examples.modals;

import de.mme.dapi.utils.ExternalConfigReader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;


/*
 *
 *      A Modal is an input form like a windows-dialog to get user input values.
 *      First you have to create the modal object, containing all input elements.
 *      Then you have to reply to the slashCommand with this modal object.
 *
 *      Two events to handle ----
 *        onSlashCommandInteraction
 *          Handle to react to the Slash command itself.
 *        onModalInteraction
 *          Handle to react when the submit button of the modal gets pressed
 * */

public class ModalsMain extends ListenerAdapter
{


    static final String SLASHCOMMAND_NAME = "showmod";
    static final String SLASHCOMMAND_DESCRIPTION = "Shows a modal message";


    static final String MODAL_ID = "modmail-id";
    static final String MODAL_TITLE = "Modmail Title";
    static final String MODAL_INPUTFIELD_ID_SUBJECT = "subject";
    static final String MODAL_INPUTFIELD_ID_BODY = "body";




    public static void main(String[] args) throws IOException {

        String discordToken = new ExternalConfigReader("discordSettings.properties").readProperty("settings.discord.token");

        // We don't need any intents for this bot. Slash commands work without any intents!
        JDA jda = JDABuilder.createLight(discordToken, Collections.emptyList())
                .addEventListeners(new ModalsMain())
                .setActivity(Activity.playing("Type /" + SLASHCOMMAND_NAME))
                .build();

        // Sets the global command list to the provided commands (removing all others)
        jda.updateCommands().addCommands(
                Commands.slash(SLASHCOMMAND_NAME, SLASHCOMMAND_DESCRIPTION)
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        if (event.getName().equals(SLASHCOMMAND_NAME))
        {
            /* First create the parts of the modal window. In this example there are two elements :
                A Subject Textinput field and a Body Textinput field.
                After cerateing both, we will create a modal object and add both of them to it.
                At least we reply the finished modal object.
            * */

            // Subject Textinput field
            TextInput subject = TextInput.create(MODAL_INPUTFIELD_ID_SUBJECT, "Subject", TextInputStyle.SHORT)
                    .setPlaceholder("Subject of this ticket")
                    .setMinLength(10)
                    .setMaxLength(100) // or setRequiredRange(10, 100)
                    .build();

            // Body Textinput field
            TextInput body = TextInput.create(MODAL_INPUTFIELD_ID_BODY, "Body", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Your concerns go here")
                    .setMinLength(30)
                    .setMaxLength(1000)
                    .build();
            // Create the Modal object
            Modal modal = Modal.create(MODAL_ID, MODAL_TITLE)
                    .addActionRows(ActionRow.of(subject), ActionRow.of(body))
                    .build();
            // the slash command with the finished modal
            event.replyModal(modal).queue();
        }
    }

    /*
    * As soon as the modals submit button gets pressed, a ModalInteractionEvent gets fired.
    * So we have to react on that event to do something.
    * */
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
//        super.onModalInteraction(event);

        if(event.getModalId().equals(MODAL_ID)){
            // Getting both modal values by using the inputfields ids
            String subjectText = event.getValue(MODAL_INPUTFIELD_ID_SUBJECT).getAsString();
            String bodyText = event.getValue(MODAL_INPUTFIELD_ID_BODY).getAsString();

            // Send back to show that it works
            event.reply("Modal Values are subject=" + subjectText + "\r\n\r\n" + "bodyText=" + bodyText).queue();
        }

    }

}