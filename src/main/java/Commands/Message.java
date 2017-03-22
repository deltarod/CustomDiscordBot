package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.MessageBuilder;

/**
 * Created by Tlap on 3/22/2017.
 */
class Message {
    public void builder(IDiscordClient client, IChannel channel, String contents){
        try {
            new MessageBuilder(client)
                    .withChannel(channel)
                    .withContent(contents)
                    .send();
        }
        catch (Exception e){}
    }
}
