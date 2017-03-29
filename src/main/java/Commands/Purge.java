package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageHistory;


public class Purge implements ICommand {
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        //gets the message the command comes from
        Message msg = new Message();
        IChannel channel = message.getChannel();
        IntParse parse = new IntParse();
        int parsedInt = parse.parseInt(args,msg,client,channel);
        //parses the args

        if(parsedInt >= 1){
            MessageHistory history = channel.getMessageHistory(parsedInt+1);
            history.bulkDelete();
        }
    }

    @Override
    public String requiredPerms(IDiscordClient client, IMessage message) {
        return "admin";
    }
}
