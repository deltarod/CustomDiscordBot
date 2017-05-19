package commands;

import commands.util.GuildCfg;
import commands.util.Message;
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
    private IDiscordClient client;
    private IMessage message;


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
        commandMap.put("check", new Check(cfg));


    }


    public void run(String input, IDiscordClient client, IMessage message) {
        this.client = client;
        this.message = message;
        input = input.substring(1);
        //splits command and args
        String[] commandVar = input.split("\\s+", 2);


        //gets current command executed
        ICommand command;
        try {
            command = commandMap.get(commandVar[0]);
        } catch (Exception e) {
            Message.builder(client, message, "Invalid Command");
            return;
        }
        //checks if perms are good, if so runs command
        if (checkPerms(command, message)){
            command.run(client, commandVar[1], message);
        }
        //if perms are not good tells them what they need
        else {
            Message.builder(client,
                    message,
                    "You do not have the permissions required for " + commandVar[0]);
        }


    }

    //gets required role for command
    private boolean checkPerms(ICommand command, IMessage message) {
        List<IRole> list = message.getAuthor().getRolesForGuild(message.getGuild());

        //if the role requires no rank, dont do other stuff
        if (command.getRole() == null) {
            return true;
        }
        long roleID = 0;
    //gets roleID and owner, might be null if not configured.
        try {
            String stringID = cfg.getProp(command.getRole(), "server");
            roleID = Long.parseLong(stringID);
        } catch (Exception e) {
            Message.builder(client, message, command.getRole() + " not set");
        }

        if (owner.equals(message.getAuthor().getStringID())) {
            return true;
        }
        else {
            try {
                IRole currentRole = message.getGuild().getRoleByID(roleID);
                for (IRole role : list) {
                    //System.out.println("Role: " + role.getPosition() + " | Current Role: " + currentRole.getPosition());
                    try {
                        if (role.getPosition() >= currentRole.getPosition()) {
                            return true;
                        }
                    }
                    catch (Exception e){
                        //do nothing, catches null in getRole
                    }
                }
            }
            catch (NullPointerException e){
                e.printStackTrace();
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

