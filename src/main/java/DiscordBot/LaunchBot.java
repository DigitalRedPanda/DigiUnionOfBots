package DiscordBot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;
import java.util.Objects;
import static DiscordBot.Resources.Token;
public class LaunchBot {
    public static void main(String[] args){

        DiscordClient client = DiscordClient.create(Objects.requireNonNull(Token));
        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) ->
                gateway.on(ReadyEvent.class, event ->
                                Mono.fromRunnable(() -> {
            final User self = event.getSelf();
            System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
        })

        ));
        login.block();
        Mono<Void> message = client.withGateway((GatewayDiscordClient gateway) ->{
        gateway.on(MessageCreateEvent.class, messageCreateEvent -> {
            Message SentMessage = messageCreateEvent.getMessage();
            User user = messageCreateEvent.getMember().get();
            System.out.printf("[%s] %s: %s",SentMessage.getChannel(), user, SentMessage.getContent());
            if (SentMessage.getContent().equalsIgnoreCase("!ping")) {
                return SentMessage.getChannel()
                    .flatMap(channel -> channel.createMessage("pong!"));
            }
            return Mono.empty();
        });

            return Mono.empty();
        });
    message.block();
    }

}