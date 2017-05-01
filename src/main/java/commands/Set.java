package commands;

import commands.util.GuildCfg;
import commands.util.Message;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;


public class Set implements ICommand {
    private GuildCfg cfg;

    Set(GuildCfg cfg) {
        this.cfg = cfg;
    }

    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        String[] arg = args.split("\\s+");
        //some regex stuff to only get numbers, not honestly sure how it works
        String id = arg[1].replaceAll("[^0-9]", "");

        //set admin role
        if (arg[0].equals("admin")) {
            cfg.setProp("admin", id, "server");
        }
        //set mod role
        // TODO: 4/30/2017 learn how roles are stored to the list
        if (arg[0].equals("mod")) {
            cfg.setProp("mod", id, "server");
        }
        //set the prefix
        else if (arg[0].equals("prefix")) {
            cfg.setProp("prefix", arg[1], "server");
            new Message().builder(client, message.getChannel(), "Prefix set to: " + arg[1]);
        }
    }

    @Override
    public String getRole() {
        return "admin";
    }

    // TODO: 4/30/2017 permission based getDesc
    @Override
    public String getDesc(String prefix) {
        return "Command to set various configurations \n";

    }
}
