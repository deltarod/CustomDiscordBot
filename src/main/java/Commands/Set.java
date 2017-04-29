package Commands;

import Commands.Util.GuildCfg;
import Commands.Util.Message;
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
        else if (arg[0].equals("owner")) {
            cfg.setProp("owner", id);
        }
        else if (arg[0].equals("prefix")){
            cfg.setProp("prefix", arg[1]);
            new Message().builder(client, message.getChannel(), "Prefix set to: " + arg[1]);
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
    public String getDesc(String prefix) {
        return "Command to set varies configurations \n" +
                "Usage: \n" +
                prefix + "r9k - toggles r9k mode\n" +
                prefix + "r9k (number) - sets how far to look back\n" +
                prefix + "r9k limit - displays current r9k limit";
    }
}
