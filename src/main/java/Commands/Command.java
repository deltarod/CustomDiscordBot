package Commands;

import Commands.ICommand;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;


import java.util.HashMap;
import java.util.Map;


public class Command {
    Map<String, ICommand> commandMap = new HashMap<>();

    public Command(){
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
            new Message().builder(client, message.getChannel(), "Invalid Commands.Command");
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

