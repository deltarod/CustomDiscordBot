package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
//interface for commands
interface ICommand {



    void run(IDiscordClient client, String args, IMessage message);

    String getRole();

    String getDesc(String prefix);
}
