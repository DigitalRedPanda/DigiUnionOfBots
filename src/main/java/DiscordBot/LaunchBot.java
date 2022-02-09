package DiscordBot;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.rest.request.RouterOptions;

public class LaunchBot {
    public static void main(String[] args){
        DiscordClientBuilder<DiscordClient, RouterOptions> client = DiscordClientBuilder.create("TOKEN");

    }
}
