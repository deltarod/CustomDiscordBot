package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

// TODO: 3/22/2017 MORE COMMANDS!!!
class Eta implements ICommand{
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        new Message().builder(client, message.getChannel(),"ETA until done?: Never");
    }
}
