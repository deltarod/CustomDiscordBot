import Commands.CommandHandler;
import Commands.R9K;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationListener {
    IDiscordClient client;
    IMessage currentMessage;
    String homeChannel;
    String startup;
    String prefix;
    Map<IGuild, CommandHandler> guildMap;

    AnnotationListener(IDiscordClient client, String HomeChannel, String startup, String prefix) {
        this.client = client;
        this.homeChannel = HomeChannel;
        this.startup = startup;
        this.prefix = prefix;
        guildMap = new HashMap<>();

    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        client.online("The Purge");
        System.out.println("Ready to go!");
        List<IGuild> list = client.getGuilds();

        //cant be in the initialization

        for (IGuild guild : list) {
            guildMap.put(guild, new CommandHandler(guild));
        }
    }

    //tells bot what to do when joining a new guild
    @EventSubscriber
    public void onGuildJoin(GuildCreateEvent event) {
        //adds new guild on join, not requiring a bot restart
        guildMap.put(event.getGuild(), new CommandHandler(event.getGuild()));
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {
        //added multiGuild capabilities!

        //gets current message and pulls the guild from the guild
        currentMessage = event.getMessage();
        CommandHandler command = guildMap.get(currentMessage.getGuild());

        R9K r9k = command.getR9K();
        //original code just using the new command pulled from the guildMap
        if (currentMessage.getContent().startsWith(prefix)) {
            command.run(currentMessage.getContent().toLowerCase(), client, currentMessage);
        }
        //if command skips the r9k check
        else if (r9k.isR9k) {
            r9k.handle(currentMessage);
        }
    }
}

