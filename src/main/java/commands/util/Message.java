package commands.util;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;

public class Message {

    //sends message using sent message, using message.getChannel()
    public static void builder(IDiscordClient client, IMessage message, String contents) {
        try {
            new MessageBuilder(client)
                    .withChannel(message.getChannel())
                    .withContent(contents).build();
        } catch (Exception e) {
            //TODO: Add some error handling here
        }
    }
}
