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
    private int limit;
    private GuildCfg cfg;
    private IntParse parse;
    private Message msg;

    R9K(GuildCfg cfg) {
        this.cfg = cfg;
        parse = new IntParse();
        msg = new Message();

        //checks if lim exists in properties
        try {
            limit = parse.parseInt(cfg.getProp("r9klim", "server"));
        } catch (Exception e) {
            //sets default prop to 5
            cfg.setProp("r9klim", "5", "server");

        }
    }

    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
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
            msg.builder(client, channel, "R9k limit is " + cfg.getProp("r9klim", "server"));
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
            cfg.setProp("r9klim", args, "server");
            if (limit < 1) {
                limit = parse.parseInt(cfg.getProp("r9klim", "server"));
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
    public String getDesc(String prefix) {
        return "Switches Server into Robot9k mode," +
                "Usage: \n" +
                prefix + "r9k - toggles r9k mode\n" +
                prefix + "r9k (number) - sets how far to look back\n" +
                prefix + "r9k limit - displays current r9k limit";
    }


    //handles the checking for nonunique messages
    public void handle(IMessage message) {
        //pulls latest messages on call
        MessageHistory history = message.getChannel().getMessageHistory(limit + 1);


        //sets capacity then loads


        for (IMessage loopMsg : history) {
            //dirty fix since you cant remove a message from a history for some reason?
            //#WORKINGASINTENDED
            if (!loopMsg.equals(message) && loopMsg.getContent().equals(message.getContent())) {
                try {
                    try {
                        //creates pm to original user
                        message
                                .getAuthor()
                                .getOrCreatePMChannel()
                                .sendMessage("This server is in Robot9K mode, Messages must be unique");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    message.delete();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


    }
}
