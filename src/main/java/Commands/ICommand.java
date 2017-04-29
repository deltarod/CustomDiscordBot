package Commands;

import Commands.Util.GuildCfg;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by Tlap on 3/22/2017.
 */ //interface for getCommands
interface ICommand {
    void run(IDiscordClient client, GuildCfg cfg, String args, IMessage message);

    String getRole();

    String getDesc(String prefix);
}
