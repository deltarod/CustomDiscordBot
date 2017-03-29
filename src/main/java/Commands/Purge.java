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
        int parsedInt = parse.parseInt(args, msg, client, channel);
        //parses the args

        if(parsedInt >= 1){
            MessageHistory history = channel.getMessageHistory(parsedInt+1);
            history.bulkDelete();
        }
    }

    @Override
    public String getRole() {
        return "admin";
    }

    @Override
    public String getDesc() {
        return "Purges X amount of lines. Usage: (Command prefix)purge X," +
                " X being the amount of messages that you want deleted.";
    }
}
