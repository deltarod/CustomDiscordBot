package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageHistory;


public class R9K implements ICommand {
    //enables robot9k mode, preventing messages to be similar
    public boolean isR9k = false;
    public int limit = 5;
    IntParse parse = new IntParse();
    Message msg = new Message();

    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        IChannel channel = message.getChannel();

        if (args == null) {
            isR9k = !isR9k;
            String returnMessage;
            //sets r9k message
            if (isR9k) returnMessage = "R9K is enabled.";
            else returnMessage = "R9K is disabled.";

            msg.builder(client, channel, returnMessage);
        }
        else{

            limit = parse.parseInt(args, msg, client, message.getChannel());
            if(limit<1){
                limit = 5;
                msg.builder(client, channel, "Limit defaulted to 5");
            }
            else {
                msg.builder(client, channel, "Limit set to " + limit);
            }

        }

    }


    public void handle(IMessage message){
        //pulls latest messages on call
        MessageHistory history = message.getChannel().getMessageHistory(limit+1);


        //sets capacity then loads


        for(IMessage loopMsg : history){
            //dirty fix since you cant remove a message from a history for some reason?
            //#WORKINGASINTENDED
            if(loopMsg.equals(message)){
                //do nothing
            }
            else if(loopMsg.getContent().equals(message.getContent())) {
                try {
                    try {
                        //creates pm to original user
                        message
                                .getAuthor()
                                .getOrCreatePMChannel()
                                .sendMessage("This server is in Robot9K mode, Messages must be unique");
                    }
                    catch (Exception ex) {}
                    message.delete();
                    break;
                }
                catch (Exception e) {}
            }

        }
    }


}
