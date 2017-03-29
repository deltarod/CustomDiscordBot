package Commands;

import Commands.Util.GuildCfg;
import Commands.Util.Message;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

class Eta implements ICommand{
    @Override
    public void run(IDiscordClient client, GuildCfg cfg, String args, IMessage message) {
        new Message().builder(client, message.getChannel(),"ETA until done?: Never");
    }

    @Override
    public String getRole() {
        return null;
    }

    @Override
    public String getDesc() {
        return "Gives the estimated time until the bot is completed";
    }
}
