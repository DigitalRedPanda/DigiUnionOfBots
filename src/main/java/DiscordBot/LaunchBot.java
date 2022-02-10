package DiscordBot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Objects;

import static DiscordBot.Resources.Token;
public class LaunchBot {
    public static void main(String[] args){

        DiscordClient client = DiscordClient.create(Objects.requireNonNull(Token));
        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) ->{
           Mono<Void> printreadiness = gateway.on(ReadyEvent.class, readyEvent ->{
                Mono.fromRunnable(() ->{
                   final User self = readyEvent.getSelf();
                   System.out.printf("Logged in as %s#%s%n",self.getUsername(),self.getDiscriminator());
                       });
                return Mono.empty();
                        }).then();
            Mono<Void> CreateMessage = gateway.on(MessageCreateEvent.class, messageCreateEvent -> {
                if(messageCreateEvent.getMember().get().isBot())
                    return Mono.empty();

                Message message = messageCreateEvent.getMessage();
                if(message.getContent().equalsIgnoreCase("Yo")){
                    return message.getChannel()
                            .flatMap(channel -> channel.createMessage("yo"));
                }
                return Mono.empty();
            }).then();

            return printreadiness.and(CreateMessage);
        });

        login.block();
    }

}