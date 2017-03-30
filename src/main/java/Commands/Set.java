package Commands;

import Commands.Util.GuildCfg;
import Commands.Util.StringSplit;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;


public class Set implements ICommand {
    GuildCfg cfg;


    @Override
    public void run(IDiscordClient client, GuildCfg cfg, String args, IMessage message) {
        this.cfg = cfg;
        String[] arg = new StringSplit().split(args);
        //some regex stuff to only get numbers, not honestly sure how it works
        String id = arg[1].replaceAll("[^0-9]", "");

        //checks first arg
        if (arg[0].equals("admin")) {
            cfg.setProp("admin", id);
        }
        if (arg[0].equals("owner")) {
            cfg.setProp("owner", id);
        }
    }

    @Override
    public String getRole() {
        try {
            cfg.getProp("owner");
        } catch (NullPointerException e) {
            return null;
        }
        return "owner";
    }

    @Override
    public String getDesc() {
        return "Command to set the privileges hierarchy of the guild";
    }
}
