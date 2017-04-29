package Commands;

import Commands.Util.GuildCfg;
import Commands.Util.IntParse;
import Commands.Util.Message;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageHistory;


public class Purge implements ICommand {
    @Override
    public void run(IDiscordClient client, GuildCfg cfg, String args, IMessage message) {
        //gets the message the command comes from
        Message msg = new Message();
        IChannel channel = message.getChannel();
        IntParse parse = new IntParse();
        int parsedInt = parse.parseInt(args);
        //parses the args

        if (parsedInt >= 1) {
            MessageHistory history = channel.getMessageHistory(parsedInt + 1);
            history.bulkDelete();
        }
    }

    @Override
    public String getRole() {
        return "admin";
    }

    @Override
    public String getDesc(String prefix) {
        return "purges a supplied amount of messages\n" +
                "Usage:\n" +
                prefix + "purge (number)";
    }
}
