package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by Tlap on 3/22/2017.
 */ //interface for getCommands
interface ICommand{
    void run(IDiscordClient client, String args, IMessage message);
    String requiredPerms(IDiscordClient client, IMessage message);
}
