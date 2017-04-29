package Commands;

import Commands.Util.GuildCfg;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public class Shutdown implements ICommand {
    //quickly made
    @Override
    public void run(IDiscordClient client, GuildCfg cfg, String args, IMessage message) {
        client.logout();
    }

    @Override
    public String getRole() {
        return "owner";
    }

    @Override
    public String getDesc(String prefix) {
        return "Shuts down the server.\n" +
                "Usage:\n" +
                prefix + "shutdown";
    }
}
