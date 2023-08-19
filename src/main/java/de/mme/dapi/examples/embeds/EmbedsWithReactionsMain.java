package de.mme.dapi.examples.embeds;

import de.mme.dapi.utils.ExternalConfigReader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

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

public class EmbedsWithReactionsMain extends ListenerAdapter
{
    public static void main(String[] args) throws IOException {

        String discordToken = new ExternalConfigReader("discordSettings.properties").readProperty("settings.discord.token");

        // We don't need any intents for this bot. Slash commands work without any intents!
        JDA jda = JDABuilder.createLight(discordToken, Collections.emptyList())
                .addEventListeners(new EmbedsWithReactionsMain())
                .setActivity(Activity.playing("Type /embedreact"))
                .build();

        // Sets the global command list to the provided commands (removing all others)
        jda.updateCommands().addCommands(
                Commands.slash("embedreact", "Shows a embed message with reactions on it")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        // make sure we handle the right command
        switch (event.getName()) {
            case "embedreact":
                /* If the slash command /embed1 gets received, a new embed is created and postet by the bot into
                 the same channel.*/
                // Build the embed
                EmbedBuilder eb = new EmbedBuilder();

                eb.setAuthor("embedreact")
                        .setColor(Color.RED)
                        .setTitle("Embed with Reactions")
                        .setDescription("there should be some reactions beneath this embed");

                MessageEmbed theEmbed = eb.build();

                /* send it into the channel
                    The Queu methode let you access the message , so you can change it again
                * */
                event.replyEmbeds(theEmbed).queue((msg)->{
                    msg.retrieveOriginal().queue((rMsg)-> {
                        Emoji reactionROFL = Emoji.fromUnicode(":rofl:");
                        Emoji reactionA = Emoji.fromUnicode("U+1F1E6");
                        Emoji reactionB = Emoji.fromUnicode("U+1F1E7");
                        Emoji reactionC = Emoji.fromUnicode("U+1F1E8");
                        Emoji reactionD = Emoji.fromUnicode("U+1F1E9");
                        Emoji reactionE = Emoji.fromUnicode("U+1F1EA");

                    /* PROBLEM : This will senda message with delay on each queue. You can see the reactions popping
                     each step by step over time. very annoying. But Thats the way it goes. there is no mass addReaction
                     function available. Other bots are doing it the same way.... Step by Step, each time an own queue()
                     */
                        rMsg.addReaction(reactionROFL).queue();
                        rMsg.addReaction(reactionA).queue();
                        rMsg.addReaction(reactionB).queue();
                        rMsg.addReaction(reactionC).queue();
                        rMsg.addReaction(reactionD).queue();
                        rMsg.addReaction(reactionE).queue();
                    });
                });


//                /* send it into the channel
//                    The Queu methode let you access the message , so you can change it again
//                * */
//                event.getChannel().sendMessageEmbeds(theEmbed).queue((msg)->{
//                    Emoji reactionROFL = Emoji.fromUnicode(":rofl:");
//                    Emoji reactionA = Emoji.fromUnicode("U+1F1E6");
//                    Emoji reactionB = Emoji.fromUnicode("U+1F1E7");
//                    Emoji reactionC = Emoji.fromUnicode("U+1F1E8");
//                    Emoji reactionD = Emoji.fromUnicode("U+1F1E9");
//                    Emoji reactionE = Emoji.fromUnicode("U+1F1EA");
//
//                    /* PROBLEM : This will senda message with delay on each queue. You can see the reactions popping
//                     each step by step over time. very annoying. But Thats the way it goes. there is no mass addReaction
//                     function available. Other bots are doing it the same way.... Step by Step, each time an own queue()
//                     */
//                    msg.addReaction(reactionROFL).queue();
//                    msg.addReaction(reactionA).queue();
//                    msg.addReaction(reactionB).queue();
//                    msg.addReaction(reactionC).queue();
//                    msg.addReaction(reactionD).queue();
//                    msg.addReaction(reactionE).queue();
//                });
                break;
        }
    }
}