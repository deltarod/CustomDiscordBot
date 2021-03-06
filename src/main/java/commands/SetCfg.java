package commands;

import commands.util.GuildCfg;
import commands.util.Message;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

/*
 * Used to set various config settings, like bot roles
 */
public class SetCfg implements ICommand {
    private GuildCfg cfg;

    SetCfg(GuildCfg cfg) {
        this.cfg = cfg;
    }

    @Override
    public void run(IDiscordClient client, String args, IMessage msg) {
        String[] arg = args.split("\\s+");
        //some regex stuff to only get numbers, not honestly sure how it works
        String id = arg[1].replaceAll("[^0-9]", "");
        //set admin role
        if (arg[0].equalsIgnoreCase("admin")) {
            System.out.println(msg.getGuild().getRoleByID(Long.parseLong(id)).getPosition()+"");
            cfg.setProp("admin", id, "server");
        }
        //set mod role
        if (arg[0].equalsIgnoreCase("mod")) {
            cfg.setProp("mod", id, "server");
        }

        //set the prefix
        else if (arg[0].equals("prefix")) {
            cfg.setProp("prefix", arg[1], "server");
            Message.builder(client, msg, "Prefix set to: " + arg[1]);
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
