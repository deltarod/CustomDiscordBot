import Commands.CommandHandler;
import Commands.R9K;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationListener{
    IDiscordClient client;
    IMessage currentMessage;
    String homeChannel;
    String startup;
    String prefix;
    Map<String, CommandHandler> guildMap;

    AnnotationListener(IDiscordClient client, String HomeChannel, String startup, String prefix){
        this.client = client;
        this.homeChannel = HomeChannel;
        this.startup = startup;
        this.prefix = prefix;
        guildMap = new HashMap<>();
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event){
        client.streaming("The Purge","");
        System.out.println("Ready to go!");
        List<IGuild> list = client.getGuilds();
        System.out.println(list.size());
        //cant be in the initialization

        for(IGuild guild : list){
            guildMap.put(guild.getID(),new CommandHandler());
        }
    }
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
        //added multiGuild capabilities!

        //gets current message and pulls the guild from the guild
        currentMessage=event.getMessage();
        CommandHandler command = guildMap.get(currentMessage.getGuild().getID());

        //original code just using the new command pulled from the guildMap
        R9K r9k = command.getR9K();
        if(currentMessage.getContent().startsWith(prefix)){
            command.run(currentMessage.getContent(),client,currentMessage);
        }
        else if(r9k.isR9k){
            r9k.handle(currentMessage);
        }

    }

}

