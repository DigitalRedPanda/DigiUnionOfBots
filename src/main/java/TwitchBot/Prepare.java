package TwitchBot;

import com.github.philippheuer.credentialmanager.CredentialManager;
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import java.util.Objects;

import static TwitchBot.BotResources.*;

public interface Prepare {
     CredentialManager credentialManager = CredentialManagerBuilder.builder().build();
    OAuth2Credential credential = new OAuth2Credential("twitch", Objects.requireNonNull(Token));
    TwitchClient TC = TwitchClientBuilder.builder()
            .withClientId(ClientID)
            .withClientSecret(ClientSecret)
            .withEnableChat(true)
            .withEnableGraphQL(true)
            .withEnablePubSub(true)
            .withDefaultAuthToken(credential)
            .withEnableHelix(true)
            .withChatAccount(credential)
            .withCommandTrigger(Prefix)
            .build();
    static void JoinChannel(String ChannelToJoin){
        if(!TC.getChat().isChannelJoined(ChannelToJoin)){
            TC.getChat().joinChannel(ChannelToJoin);
            TC.getChat().sendMessage(ChannelToJoin,String.format("Hello %s, %s has added your channel",ChannelToJoin,NickName));
        }else
            TC.getChat().sendMessage(DefaultChannel,String.format("%s is already joined",ChannelToJoin));

    }static void LeaveChannel(String ChannelToLeave){
        if(TC.getChat().isChannelJoined(ChannelToLeave)){
            TC.getChat().leaveChannel(ChannelToLeave);
            TC.getChat().sendMessage(ChannelToLeave,String.format("Hello %s, %s has removed your channel",ChannelToLeave,NickName));
            TC.getChat().sendMessage(DefaultChannel,String.format("%s has been removed successfully",ChannelToLeave));
        }else
            TC.getChat().sendMessage(DefaultChannel,String.format("%s isn't joined", ChannelToLeave));
    }static void Initialize(){
     TC.getChat().connect();
     credentialManager.registerIdentityProvider(new TwitchIdentityProvider(ClientID,ClientSecret,RedirectURL));
     TC.getChat().sendMessage(DefaultChannel,"Bot has been initialized");
     JoinChannel(DefaultChannel);
    }static boolean ChannelExists(String ChannelToConfirm){
        boolean ChannelDoesExist;
        if(!TC.getChat().isChannelJoined(ChannelToConfirm)){
            TC.getChat().joinChannel(ChannelToConfirm);
            ChannelDoesExist = TC.getChat().isChannelJoined(ChannelToConfirm);
            TC.getChat().leaveChannel(ChannelToConfirm);
        }else{
            ChannelDoesExist = TC.getChat().isChannelJoined(ChannelToConfirm);
        } return ChannelDoesExist;
    }
}
