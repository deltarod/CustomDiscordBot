package Commands;

import Commands.Util.GuildCfg;
import Commands.Util.IntParse;
import Commands.Util.Message;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageHistory;


public class R9K implements ICommand {
    //enables robot9k mode, preventing messages to be similar
    public boolean isR9k = false;
    public int limit;
    IntParse parse = new IntParse();
    Message msg = new Message();

    R9K(GuildCfg cfg) {

        //checks if lim exists in properties
        try {
            limit = parse.parseInt(cfg.getProp("r9klim"));
        } catch (Exception e) {
            //sets default prop to 5
            cfg.setProp("r9klim", "5");

        }
    }

    @Override
    public void run(IDiscordClient client, GuildCfg cfg, String args, IMessage message) {
        IChannel channel = message.getChannel();

        //if args is null, toggles r9k mode
        if (args == null) {
            isR9k = !isR9k;
            String returnMessage;
            //sets r9k message
            if (isR9k) returnMessage = "R9K is enabled.";
            else returnMessage = "R9K is disabled.";

            msg.builder(client, channel, returnMessage);
        }
        //if args is limit returns the limit
        else if (args.equals("limit")) {
            msg.builder(client, channel, "R9k limit is " + cfg.getProp("r9klim"));
        }
        //sets limit to args
        else {
            //if args is not an int sends message
            try {
                limit = parse.parseInt(args);
            } catch (NumberFormatException e) {
                msg.builder(client, channel, "Invalid Number Format");
                return;
            }

            //sets proper limit
            cfg.setProp("r9klim", args);
            if (limit < 1) {
                limit = parse.parseInt(cfg.getProp("r9klim"));
                msg.builder(client, channel, "Limit defaulted to " + limit + " since number less than 1");
            } else {
                msg.builder(client, channel, "Limit set to " + limit);
            }

        }

    }


    @Override
    public String getRole() {
        return "admin";
    }

    @Override
    public String getDesc() {
        return "Switches Server into Robot9k mode," +
                " Where messages must be unique." +
                " Use (Command Prefix)r9k X, X being amount of messages," +
                " To set how far to look back per message.";
    }


    //handles the checking for nonunique messages
    public void handle(IMessage message) {
        //pulls latest messages on call
        MessageHistory history = message.getChannel().getMessageHistory(limit + 1);


        //sets capacity then loads


        for (IMessage loopMsg : history) {
            //dirty fix since you cant remove a message from a history for some reason?
            //#WORKINGASINTENDED
            if (loopMsg.equals(message)) {
                //do nothing
            } else if (loopMsg.getContent().equals(message.getContent())) {
                try {
                    try {
                        //creates pm to original user
                        message
                                .getAuthor()
                                .getOrCreatePMChannel()
                                .sendMessage("This server is in Robot9K mode, Messages must be unique");
                    } catch (Exception ex) {
                    }
                    message.delete();
                    break;
                } catch (Exception e) {
                }
            }

        }


    }
}
