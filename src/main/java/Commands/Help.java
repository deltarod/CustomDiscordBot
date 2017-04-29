package Commands;

import Commands.Util.GuildCfg;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;


class Help implements ICommand {
    String defaultPrefix;

    Help(String defaultPrefix){
        this.defaultPrefix = defaultPrefix;
    }
    @Override
    public void run(IDiscordClient client, GuildCfg cfg, String args, IMessage message) {
        String commands = new CommandHandler(message.getGuild(), cfg, defaultPrefix).getCommands(message);
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
