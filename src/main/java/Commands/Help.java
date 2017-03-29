package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by Tlap on 3/22/2017.
 */
class Help implements ICommand{
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        String commands = new CommandHandler().getCommands();
        IChannel channel = message.getChannel();
        new Message().builder(client,channel,commands);

    }

    @Override
    public String requiredPerms(IDiscordClient client, IMessage message) {
        return null;
    }
}
