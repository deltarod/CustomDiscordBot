package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;


// TODO: 3/22/2017 Implement a stack to use for song Queuing. Shuffle can come later 
// TODO: 3/22/2017 implement play,create pause, skip, and volume. Base player is required before these things probably
public class Play implements ICommand {
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        new Message().builder(client, message.getChannel(), "Not Yet Implemented ");
    }
}
