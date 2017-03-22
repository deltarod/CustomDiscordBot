import Commands.Command;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Status;
import Commands.*;
/**
 * Created by Tristan on 3/21/2017.
 */
public class AnnotationListener{
    IDiscordClient client;
    IMessage currentMessage;
    String homeChannel;
    String startup;
    String prefix;
    Command command;
    AnnotationListener(IDiscordClient client, String HomeChannel, String startup, String prefix){
        this.client = client;
        this.homeChannel = HomeChannel;
        this.startup = startup;
        this.prefix = prefix;
        command = new Command();
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event){
        client.changeStatus(Status.game("The Purge"));
        System.out.println("Ready to go!");
    }
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
        currentMessage=event.getMessage();
        if(currentMessage.getContent().startsWith(prefix)){
            command.run(currentMessage.getContent(),client,currentMessage);
        }

    }

}

