package DiscordBot;

import io.github.cdimascio.dotenv.Dotenv;

public interface Resources {
    Dotenv dotenv = Dotenv.configure()
            .directory("src\\main\\resources\\")
            .filename("DiscordBot.env")
            .load();
    String Token = dotenv.get("TOKEN");
}
