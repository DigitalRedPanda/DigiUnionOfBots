package DiscordBot;


import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.util.concurrent.CompletableFuture;

import static DiscordBot.Resources.Token;

public interface Commence{
EmbedBuilder GiveawayConfirmation =  new EmbedBuilder()
        .setAuthor("DigitalRedPanda")
        .setTitle("Digi Giveaway");

DiscordApi client = new DiscordApiBuilder()
        .setToken(Token)
        .login().join();
     default void SendDM(User user, String Content){
        TextChannel channel;
        CompletableFuture<Message> messageBuilder = new MessageBuilder()
                .setEmbed(GiveawayConfirmation)
                .send(user);

    }

}

