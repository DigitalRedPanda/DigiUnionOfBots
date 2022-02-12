package DiscordBot;


import static DiscordBot.Commence.client;

public class LaunchBot {
    public static void main(String[] args) {
client.addMessageCreateListener(messageCreateEvent -> {
    if(!messageCreateEvent.isPrivateMessage() && (messageCreateEvent.getMessage().getContent().equalsIgnoreCase("Hello") || messageCreateEvent.getMessage().getContent().equalsIgnoreCase("Hi") || messageCreateEvent.getMessage().getContent().equalsIgnoreCase("Hey") || messageCreateEvent.getMessage().getContent().equalsIgnoreCase("هلا") || messageCreateEvent.getMessage().getContent().equalsIgnoreCase("yo")))
        messageCreateEvent.getMessage().getChannel().sendMessage(String.format("%s, hello",messageCreateEvent.getMessage().getAuthor().getDisplayName()));
});

    }}