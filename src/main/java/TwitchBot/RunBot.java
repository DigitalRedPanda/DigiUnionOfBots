package TwitchBot;


import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import java.util.ArrayList;
import java.util.Objects;
import static TwitchBot.BotResources.DefaultChannel;
import static TwitchBot.BotResources.Prefix;
import static TwitchBot.Prepare.*;
import static TwitchBot.Scan.PullWinner;

public class RunBot {
    static ArrayList<String> Candidates = new ArrayList<>();
    static boolean GiveawayIsOpen=false;
    public static void main(String[] args){
     Initialize();
     TC.getEventManager().onEvent(ChannelMessageEvent.class, OnMessage ->{
            String Message = OnMessage.getMessage();
            String User = OnMessage.getUser().getName();
            String Channel = OnMessage.getChannel().getName();
            if(!Message.startsWith(Objects.requireNonNull(Prefix)))
            System.out.printf("[%s] %s: %s\n",Channel,User,Message);
            if(Message.equalsIgnoreCase("Yo"))
            OnMessage.getTwitchChat().sendMessage(DefaultChannel,String.format("%s, yo", User));
            if(Message.startsWith(Prefix)){
                String[] CommandContent = Message.replace(Prefix,"").split(" ");
                String UsedCommand = CommandContent[0];
                System.out.printf("[%s] %s used %s\n", Channel, User,UsedCommand);
                if (UsedCommand.equalsIgnoreCase("add")) {
                    try {
                        JoinChannel(CommandContent[1]);
                    }catch (ArrayIndexOutOfBoundsException e) {
                        OnMessage.getTwitchChat().sendMessage(DefaultChannel, String.format("%s, cannot add an empty channel", User));
                    }}if (UsedCommand.equalsIgnoreCase("remove")) {
                    try{
                        LeaveChannel(CommandContent[1]);
                    }catch (ArrayIndexOutOfBoundsException e) {
                        OnMessage.getTwitchChat().sendMessage(DefaultChannel, String.format("%s, cannot remove an empty channel",User));
                    }}if(UsedCommand.equalsIgnoreCase("enablegiveaway") && User.equalsIgnoreCase(DefaultChannel)){
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
                }if(UsedCommand.equalsIgnoreCase("Participants"))
                    OnMessage.getTwitchChat().sendMessage(Channel,Candidates.toString().replace('[','\0').replace(']','\0'));
                if(UsedCommand.equalsIgnoreCase("pull") && User.equalsIgnoreCase(DefaultChannel) && GiveawayIsOpen){
                    if(!Candidates.isEmpty()){
                        System.out.print(Candidates);
                        String LuckyWinner = PullWinner(Candidates);
                        OnMessage.getTwitchChat().sendMessage(Channel,String.format("Congratulations %s, you've just won the giveaway", LuckyWinner));
                        TC.getChat().sendPrivateMessage(LuckyWinner,"Congratulations here's your code: 1WUYT-EYFS2-MNF8F");
                        Candidates.clear();
                    }else
                        OnMessage.getTwitchChat().sendMessage(Channel, "Cannot pull a winner without participants");
                }
            }
     });
    }}

