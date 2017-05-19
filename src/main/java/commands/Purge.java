package commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageHistory;


public class Purge implements ICommand {
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        //gets the message the command comes from
        IChannel channel = message.getChannel();
        int parsedInt = Integer.parseInt(args);
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
                "DISABLED UNTIL FURTHER NOTICE \n" +
                prefix + "purge (number)";
    }
}
