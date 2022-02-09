package TwitchBot;

import io.github.cdimascio.dotenv.Dotenv;

public interface BotResources {
Dotenv dotenv = Dotenv.configure()
        .directory("src\\main\\resources")
        .filename("TwitchBot.env")
        .load();
String ClientID = dotenv.get("CLIENT_ID");
String ClientSecret = dotenv.get("CLIENT_SECRET");
String Token = dotenv.get("TOKEN");
String DefaultChannel = dotenv.get("DEFAULT_CHANNEL");
String RedirectURL = dotenv.get("REDIRECT_URL");
String SpecialGuest = dotenv.get("SPECIAL_GUEST");
String SpecialChannel = dotenv.get("SPECIAL_CHANNEL");
String NickName = DefaultChannel.substring(0,4);
String Prefix = dotenv.get("PREFIX");
}
