import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MessageList;

import java.util.HashMap;
import java.util.Map;

public class Command {
    Map commandMap = new HashMap<String,ICommand>();

    Command(){
        commandMap.put("purge",new purge());
        commandMap.put("help",new help());
        commandMap.put("eta",new eta());
    }


    public void run(String input, IDiscordClient client, IMessage message){
        String[] commandVar = stringSplit(input.toLowerCase());

        ICommand command = (ICommand)commandMap.get(commandVar[0]);

        command.run(client,commandVar[1],message);

    }

    public String commands(){

       Object[] commands = commandMap.keySet().toArray();
       String cmdString = "Here are all the commands: ";
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
class message{
    public void builder(IDiscordClient client, IChannel channel,String contents){
        try {
            new MessageBuilder(client).withChannel(channel).withContent(contents).send();
        }
        catch (Exception e){}
    }
}
//interface for commands
interface ICommand{
    public void run(IDiscordClient client, String args,IMessage message);
}

class eta implements ICommand{
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        new message().builder(client, message.getChannel(),"ETA until done?: Never");
    }
}

class help implements ICommand{
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        String commands = new Command().commands();
        IChannel channel = message.getChannel();
        new message().builder(client,channel,commands);

    }
}

class purge implements ICommand{
    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        //gets messagelist from channel
        IChannel channel = message.getChannel();
        MessageList list = channel.getMessages();
        int total = 0;

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
        //if not number sends message out
        catch (NumberFormatException e) {
            new message().builder(client, channel, "Please enter a number not something else");
        }
    }
}

