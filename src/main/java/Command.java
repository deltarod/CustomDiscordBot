import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MessageList;

import java.util.HashMap;
import java.util.Map;


// TODO: 3/22/2017 Separate getCommands into seperate files
public class Command {
    Map<String,ICommand> commandMap = new HashMap<>();

    Command(){
        commandMap.put("purge",new Purge());
        commandMap.put("help",new Help());
        commandMap.put("eta",new Eta());
    }


    public void run(String input, IDiscordClient client, IMessage message){
        String[] commandVar = stringSplit(input.toLowerCase());

        ICommand command = commandMap.get(commandVar[0]);

        //checks if command is invalid
        try {
            command.run(client, commandVar[1], message);
        }
        catch (NullPointerException e){
            new Message().builder(client, message.getChannel(), "Invalid Command");
        }

    }

    public String getCommands(){
        //pulls all commands from the commandMap
        // TODO: 3/22/2017 Change to foreach loop
       Object[] commands = commandMap.keySet().toArray();
       String cmdString = "Here are all the getCommands: ";
       for(int i = 0; i < commands.length;i++){
           cmdString += commands[i] + " ";
       }
       return cmdString;
    }


    private String[] stringSplit(String string){

        String[] returnArr = new String[2];
        //if command is 2 part, splits command and arg
        for(int i = 0; i < string.length();i++){
            if(string.charAt(i) == ' '){
                returnArr[0] = string.substring(1,i);
                returnArr[1] = string.substring(i+1);
                return returnArr;
            }
        }
        returnArr[0] = string.substring(1);
        return returnArr;

    }

}
class Message {
    public void builder(IDiscordClient client, IChannel channel, String contents){
        try {
            new MessageBuilder(client).withChannel(channel).withContent(contents).send();
        }
        catch (Exception e){}
    }
}
//interface for getCommands
interface ICommand{
    void run(IDiscordClient client, String args, IMessage message);
}

// TODO: 3/22/2017 MORE COMMANDS!!!
class Eta implements ICommand{
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        new Message().builder(client, message.getChannel(),"ETA until done?: Never");
    }
}

class Help implements ICommand{
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        String commands = new Command().getCommands();
        IChannel channel = message.getChannel();
        new Message().builder(client,channel,commands);

    }
}

class Purge implements ICommand{
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        //gets messagelist from channel
        IChannel channel = message.getChannel();
        MessageList list = channel.getMessages();
        int total = 0;
        // TODO: 3/22/2017 bulkDelete?
        try{
            //parses int
            total = Integer.parseInt(args);
            //uses parsed int to delete numbers
            for(int i = 0;i<= total+1;i++) {

                try {
                    list.getLatestMessage().delete();
                } catch (Exception err) {
                    err.printStackTrace();

                }
            }
        }
        //if not number sends Message out
        catch (NumberFormatException e) {
            new Message().builder(client, channel, "Please enter a number not something else");
        }
    }
}

