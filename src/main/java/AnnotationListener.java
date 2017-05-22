import commands.CommandHandler;
import commands.R9K;
import commands.util.GuildCfg;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;
import java.util.Map;

public class AnnotationListener {
    private IDiscordClient client;
    private IMessage currentMessage;
    private String prefix;
    private Map<IGuild, CommandHandler> guildMap;
    private String owner;

    AnnotationListener(IDiscordClient client, String owner, String prefix) {
        this.client = client;
        this.prefix = prefix;
        this.owner = owner;
        guildMap = new HashMap<>();

    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        client.online("Respect the leaderboard!");
        System.out.println("Ready to go!");
    }

    //tells bot what to do when joining a new guild
    @EventSubscriber
    public void onGuildJoin(GuildCreateEvent event) {
        //adds new guild on join, not requiring a bot restart
        guildMap.put(event.getGuild(), new CommandHandler(new GuildCfg(event.getGuild()), prefix, owner));
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {
        //added multiGuild capabilities!
        currentMessage = event.getMessage();
        //gets command handler of message's guild
        CommandHandler command = guildMap.get(currentMessage.getGuild());

        //gets current guild prefix
        String guildPrefix = command.getPrefix();

        R9K r9k = command.getR9K();
        //r9k gets handled before commands now
        if (r9k.isR9k) {
            r9k.handle(currentMessage);
        }
        //original code just using the new command pulled from the guildMap
        if (currentMessage.getContent().startsWith(guildPrefix) && !currentMessage.isDeleted()) {
            command.run(currentMessage.getContent().toLowerCase(), client, currentMessage);
        }
        //if command skips the r9k check

    }
}

