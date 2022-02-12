package TwitchBot;


import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.events.ChannelChangeGameEvent;
import com.github.twitch4j.helix.domain.Game;
import com.github.twitch4j.helix.domain.GameList;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static TwitchBot.BotResources.*;
import static TwitchBot.Prepare.*;

public class RunBot{
    static ArrayList<String> Candidates = new ArrayList<>();
    static boolean GiveawayIsOpen=false;
    static List<String> Games = new ArrayList<>();
    static List<String> Games1 = new ArrayList<>();

    public static void main(String[] args){

     Initialize();
     Games.add("Overwatch");
     Games1.add("Overwatch 2");
        TC.getHelix().getGames("Overwatch",Games,Games1).execute();
        TC.getEventManager().onEvent(ChannelChangeGameEvent.class, game -> {
            TC.
        });
    TC.getEventManager().onEvent(ChannelMessageEvent.class, OnMessage ->{

            String Message = OnMessage.getMessage();
            String User = OnMessage.getUser().getName();
            String Channel = OnMessage.getChannel().getName();
            if(!Message.startsWith(Objects.requireNonNull(Prefix)))
            System.out.printf("[%s] %s: %s\n",Channel,User,Message);
            if(Scan.FindSI(Message) && Channel.equalsIgnoreCase(SpecialChannel))
                OnMessage.getTwitchChat().ban(Channel,User,"Said SI");
            if(Message.contains("followers, primes and views on")) {
                System.out.printf("A spamming bot called %s has been detected",User);
                OnMessage.getTwitchChat().ban(Channel, User, "Spamming bot");
            }if(Message.startsWith(Prefix)){
                String[] CommandContent = Message.replace(Prefix,"").split(" ");
                String UsedCommand = CommandContent[0];
                System.out.printf("[%s] %s used %s\n", Channel, User,UsedCommand);
                if (UsedCommand.equalsIgnoreCase("add")) {
                    try{
                        if(Prepare.ChannelExists(CommandContent[1])) {
                            JoinChannel(CommandContent[1]);
                            OnMessage.getTwitchChat().sendMessage(DefaultChannel, String.format("Joined %s channel successfully", CommandContent[1]));
                        }else
                    OnMessage.getTwitchChat().sendMessage(DefaultChannel,String.format("%s, the inserted channel doesn't seem to exist",User));
                }catch (ArrayIndexOutOfBoundsException e) {
                        OnMessage.getTwitchChat().sendMessage(DefaultChannel, String.format("%s, cannot add an empty channel", User));
                    }}if (UsedCommand.equalsIgnoreCase("remove")){
                    try{
                        LeaveChannel(CommandContent[1]);
                    }catch (ArrayIndexOutOfBoundsException e) {
                        OnMessage.getTwitchChat().sendMessage(DefaultChannel, String.format("%s, cannot remove an empty channel",User));
                    }}if(UsedCommand.equalsIgnoreCase("vanish"))
                        OnMessage.getTwitchChat().timeout(Channel,User, Duration.ofSeconds(1),"Vanished");
                if(UsedCommand.equalsIgnoreCase("enablegiveaway") && User.equalsIgnoreCase(DefaultChannel)){
                    GiveawayIsOpen = true;
                    OnMessage.getTwitchChat().sendMessage(Channel, "Giveaway has been enabled");
                }if(UsedCommand.equalsIgnoreCase("disablegiveaway") && User.equalsIgnoreCase(DefaultChannel)){
                    GiveawayIsOpen = false;
                    OnMessage.getTwitchChat().sendMessage(Channel, "Giveaway has been disabled");
                }if(UsedCommand.equalsIgnoreCase("participate") && GiveawayIsOpen){
                    if(!Candidates.contains(User)){
                        Candidates.add(User);
                        OnMessage.getTwitchChat().sendMessage(Channel,String.format("%s, you've participated!",User));
                    }else
                        OnMessage.getTwitchChat().sendMessage(Channel,String.format("%s, you've already participated in the giveaway",UsedCommand));
                }if(UsedCommand.equalsIgnoreCase("Participants")){
                    if(!Candidates.isEmpty())
                    OnMessage.getTwitchChat().sendMessage(Channel, Candidates.toString().replace('[', '\0').replace(']', '\0'));
                    else
                        OnMessage.getTwitchChat().sendMessage(Channel, String.format("%s, there aren't any participants to show", User));
                }if(UsedCommand.equalsIgnoreCase("pull") && User.equalsIgnoreCase(DefaultChannel) && GiveawayIsOpen){
                    if(!Candidates.isEmpty()){
                        System.out.print(Candidates);
                        int LuckyWinnerIndex = (int) Math.round(Math.random() * Candidates.size());
                        String Winner = Candidates.get(LuckyWinnerIndex);
                        OnMessage.getTwitchChat().sendMessage(Channel,String.format("Congratulations %s, you've just won the giveaway", Winner));
                        TC.getChat().sendPrivateMessage(Winner,"Congratulations here's your code: 1WUYT-EYFS2-MNF8F");
                        Candidates.clear();
                    }else
                        OnMessage.getTwitchChat().sendMessage(Channel, "Cannot pull a winner without participants");
                }
            }
     });
    }}

