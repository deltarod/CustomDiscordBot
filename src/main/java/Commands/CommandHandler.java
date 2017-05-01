package Commands;

import Commands.Util.GuildCfg;
import Commands.Util.Message;
import Commands.Util.StringSplit;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommandHandler {
    private Map<String, ICommand> commandMap = new HashMap<>();
    private GuildCfg cfg;
    private String defaultPrefix;
    private String owner;


    public CommandHandler(GuildCfg cfg, String defaultPrefix, String owner) {
        this.cfg = cfg;
        this.owner = owner;
        this.defaultPrefix = defaultPrefix;

        //checks if a prefix has been set

        // TODO: 3/29/2017 More commands, expand upon current
        // TODO: 3/29/2017 Audio clip player on command maybe
        //puts all base commands into a map to call from later
        commandMap.put("purge", new Purge());
        commandMap.put("help", new Help(getPrefix(), owner, cfg));
        commandMap.put("eta", new Eta());
        commandMap.put("r9k", new R9K(cfg));
        commandMap.put("set", new Set(cfg));
        commandMap.put("shutdown",new Shutdown());
        commandMap.put("f", new F(cfg));


        // TODO: 4/30/2017 Todo command
        //commandMap.put("todo", new Todo());

    }


    public void run(String input, IDiscordClient client, IMessage message) {
        input = input.substring(1);
        //contains [0]command and [1]args
        String[] commandVar = StringSplit.split(input);
        ICommand command;
        //checks if command exists, if not returns breaking the function

        //gets current command executed
        try {
            command = commandMap.get(commandVar[0]);
        } catch (Exception e) {
            new Message().builder(client, message.getChannel(), "Invalid Command");
            return;
        }
        //checks if perms are good, if so runs command
        if (checkPerms(command, message)) {
            command.run(client, commandVar[getPrefix().length()], message);
        }
        //if perms are not good tells them what they need
        else {
            new Message().builder(client,
                    message.getChannel(),
                    "You do not have the permissions required for " + commandVar[0]);
        }


    }

    //gets required role for command
    private boolean checkPerms(ICommand command, IMessage message) {
        //admin

        String roleID = null;
        List<IRole> list = message.getAuthor().getRolesForGuild(message.getGuild());
        //gets roleID and owner, might be null if not configured.
        try {
            roleID = cfg.getProp(command.getRole(), "server");
        } catch (Exception e) {
            System.out.println(command.getRole() + " not set");
        }
        if (command.getRole() == null) {
            return true;
        }
        else if (owner.equals(message.getAuthor().getStringID())) {
            return true;
        }
        else {
            for (IRole role : list) {
                if (role.getStringID().equals(roleID)) {
                    return true;
                }
            }
        }
        return false;

    }

    // TODO: 3/29/2017 add an identifier that shows if a command requires something more than the average user
    String getCommands(IMessage message) {
        //pulls all commands from the commandMap
        StringBuilder builder = new StringBuilder("Here are all the commands available to your rank:\n ```");
        for (String command : commandMap.keySet()) {
            ICommand cmd = commandMap.get(command);
            if (checkPerms(cmd, message)) {
                builder.append(command);
                builder.append(" - ");
                builder.append(cmd.getDesc(getPrefix()));
                builder.append("\n--------------------------------\n");
            }
        }
        builder.append("```");
        return builder.toString();
    }
    public String getPrefix(){
        String guildPrefix = cfg.getProp("prefix", "server");
        if(guildPrefix != null){
            return guildPrefix;
        }
        else {
            return defaultPrefix;
        }
    }

    //gets r9k so the listener can react to r9k
    public R9K getR9K() {
        return (R9K) commandMap.get("r9k");
    }

}

