package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

class Eta implements ICommand{
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        new Message().builder(client, message.getChannel(),"ETA until done?: Never");
    }

    @Override
    public String requiredPerms(IDiscordClient client, IMessage message) {
        return null;
    }
}
