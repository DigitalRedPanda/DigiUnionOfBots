package TwitchBot;

import com.github.philippheuer.credentialmanager.CredentialManager;
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.eventsub.domain.PredictionColor;
import com.github.twitch4j.eventsub.domain.PredictionOutcome;
import com.github.twitch4j.graphql.internal.type.CreatePredictionEventInput;
import com.github.twitch4j.graphql.internal.type.CreatePredictionOutcomeInput;
import com.github.twitch4j.helix.domain.Poll;
import com.github.twitch4j.helix.domain.Prediction;
import com.github.twitch4j.helix.domain.PredictionsList;


import java.util.ArrayList;
import java.util.List;
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
            .withEnableTMI(true)
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
        return false;
    }
    static void CreatePrediction(String Title,Integer Duration,String Channel,List<String> OutComes){
        List<CreatePredictionOutcomeInput> outcomesList = new ArrayList<>();
        for(String Outcome:OutComes) {
            CreatePredictionOutcomeInput outcome = CreatePredictionOutcomeInput.builder()
                    .title(Outcome)
                    .build();
            outcomesList.add(outcome);
        }
        CreatePredictionEventInput PredictionInput = CreatePredictionEventInput.builder()
                .predictionWindowSeconds(Duration)
                .title(Title)
                .outcomes(outcomesList)
                .channelID(Channel)
                .build();
        TC.getGraphQL().createPrediction(credential,PredictionInput);
    }static void CreatePoll(){


    }


}
