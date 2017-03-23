package Commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;
import java.util.Map;


public class Command {
    // TODO: 3/22/2017 Some way of dealing with roles
    // TODO: 3/22/2017 implement r9k mode
    Map<String, ICommand> commandMap = new HashMap<>();
    R9K r9k;

    public Command(){
        r9k = new R9K();
        commandMap.put("purge",new Purge());
        commandMap.put("help",new Help());
        commandMap.put("eta",new Eta());
        commandMap.put("play",new Play());
        commandMap.put("r9k",r9k);
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
        String cmdString = "Here are all the getCommands: ";
        for(String command : commandMap.keySet()){
           cmdString += command + " ";
        }

       return cmdString;
    }
    public R9K getR9K(){
        return r9k;
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

