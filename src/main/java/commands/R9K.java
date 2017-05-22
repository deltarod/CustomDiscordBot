package commands;

import commands.util.GuildCfg;
import commands.util.Message;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageHistory;


public class R9K implements ICommand {
    //enables robot9k mode, preventing messages to be similar
    public boolean isR9k = false;
    private int limit;
    private GuildCfg cfg;

    R9K(GuildCfg cfg) {
        this.cfg = cfg;

        //checks if lim exists in properties
        try {
            limit = Integer.parseInt(cfg.getProp("r9klim", "server"));
        } catch (Exception e) {
            //sets default prop to 5
            cfg.setProp("r9klim", "5", "server");

        }
    }

    @Override
    public void run(IDiscordClient client, String args, IMessage message) {

        //if args is null, toggles r9k mode
        if (args == null) {
            isR9k = !isR9k;
            String returnMessage;
            //sets r9k message
            if (isR9k) returnMessage = "R9K is enabled.";
            else returnMessage = "R9K is disabled.";

            Message.builder(client, message, returnMessage);
        }
        //if args is limit returns the limit
        else if (args.equals("limit")) {
            Message.builder(client, message, "R9k limit is " + cfg.getProp("r9klim", "server"));
        }
        //sets limit to args
        else {
            //if args is not an int sends message
            try {
                limit = Integer.parseInt(args);
            } catch (NumberFormatException e) {
                Message.builder(client, message, "Invalid Number Format");
                return;
            }

            //sets proper limit
            cfg.setProp("r9klim", args, "server");
            if (limit < 1) {
                limit = Integer.parseInt(cfg.getProp("r9klim", "server"));
                Message.builder(client, message, "Limit defaulted to " + limit + " since number less than 1");
            } else {
                Message.builder(client, message, "Limit set to " + limit);
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
    public boolean handle(IMessage message) {
        //pulls latest messages on call
        if(message.getContent().contains("r9k")){
            return false;
        }
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
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return false;
    }
}
