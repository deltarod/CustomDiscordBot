package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageList;


public class Purge implements ICommand {
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        //gets the message the command comes from
        Message msg = new Message();
        IChannel channel = message.getChannel();
        IntParse parse = new IntParse();
        int parsedInt = parse.parseInt(args,msg,client,channel);
        //parses the args


        if (parsedInt<1&&parsedInt!=0){
            msg.builder(client,channel,"Please enter a number over 1");

        }
        else if(parsedInt==0) {
            msg.builder(client,channel,"Enter a number not 0");
        }
        else {
            MessageList list = channel.getMessages();
            try {
                parsedInt+=1;
                //gets latest x amount of messages, not sure bot both set and load are needed.
                list.setCacheCapacity(parsedInt);
                list.load(parsedInt);
                list.bulkDelete(list);
            } catch (Exception e) {}

        }
    }
}
