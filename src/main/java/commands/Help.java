package commands;

import commands.util.GuildCfg;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;


class Help implements ICommand {
    private String defaultPrefix;
    private String owner;
    private GuildCfg cfg;



    Help(String defaultPrefix, String owner, GuildCfg cfg){
        this.defaultPrefix = defaultPrefix;
        this.cfg = cfg;
        this.owner = owner;
    }
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        String commands = new CommandHandler(cfg, defaultPrefix, owner).getCommands(message);
        IChannel channel = message.getChannel();
        //sends PM's with command
        message.getAuthor()
                .getOrCreatePMChannel()
                .sendMessage(commands);

    }

    @Override
    public String getRole() {
        return null;
    }

    @Override
    public String getDesc(String prefix) {
        return "Displays all commands available for current rank\n" +
                "Usage: \n" +
                prefix + "help - displays commands";
    }
}
