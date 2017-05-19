package commands;

import commands.util.GuildCfg;
import commands.util.Message;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;

public class Check implements ICommand{
    private GuildCfg cfg;

    Check(GuildCfg cfg){
        this.cfg = cfg;
    }
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        //split into array of each space
        String[] arg = args.split("\\s+");
        //if admin
        String roleID = cfg.getProp(arg[0], "server");
        if(roleID == null){
            Message.builder(client, message, arg[0] + " is not a role or is not set");
            //return;
        }
        else{
            IRole role = message.getGuild().getRoleByID(Long.parseLong(roleID));
            try {
                if(arg[1].equalsIgnoreCase("position")){
                    Message.builder(client, message, role.toString() + " position: " + role.getPosition());
                }
            }
            //if arg[1] is out of bounds, means no further args
            catch (ArrayIndexOutOfBoundsException e){
                Message.builder(client, message, role.toString());
            }
        }



    }

    @Override
    public String getRole() {
        return "mod";
    }

    @Override
    public String getDesc(String prefix) {
        return "Check various config settings" +
                "Usage:\n" +
                prefix + "check (role) - displays set role. \n" +
                prefix + "check (role) position - finds role position";
    }
}
