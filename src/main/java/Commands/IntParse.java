package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;

public class IntParse {

    private int parsedInt;
    public int parseInt(String args, Message msg, IDiscordClient client, IChannel channel){
        try{
            parsedInt = Integer.parseInt(args);
        }
        catch (NumberFormatException e){
            msg.builder(client,channel,"Please enter a number instead");
        }
        if (parsedInt<1){
            msg.builder(client,channel,"Please enter a number greater than or equal to 1");
        }
        return parsedInt;
    }
}
