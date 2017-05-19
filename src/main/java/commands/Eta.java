package commands;

import commands.util.Message;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

class Eta implements ICommand {
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        Message.builder(client, message, "ETA until done?: Never");
    }

    @Override
    public String getRole() {
        return null;
    }

    @Override
    public String getDesc(String prefix) {
        return "Gives the estimated time until the bot is completed\n" +
                "Usage:\n" +
                prefix + "eta - displays how long until the bot is finished";
    }
}
