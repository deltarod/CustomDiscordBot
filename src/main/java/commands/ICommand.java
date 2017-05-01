package commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

//interface for commands
interface ICommand {

    /**
     * Runs the command
     *
     * @param client  The client instance
     * @param args    The arguments
     * @param message The IMessage
     */
    void run(IDiscordClient client, String args, IMessage message);

    /**
     * Gets the role needed to run the command
     *
     * @return The role's name
     */
    String getRole();

    /**
     * Gets the description of the command
     *
     * @param prefix The prefix
     * @return The description
     */
    String getDesc(String prefix);
}
