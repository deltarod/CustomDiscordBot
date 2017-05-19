package commands;

import commands.util.GuildCfg;
import commands.util.Leaderboard;
import commands.util.Message;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.Set;

/*
 * Command to keep track of respects of users, will save users in a properties file
 */

public class F implements  ICommand{
    private int respectCounter;
    private String respectUser;
    private GuildCfg cfg;

    F(GuildCfg cfg){
        this.cfg = cfg;
        //gets last respect user
        respectUser = cfg.getProp("respectUser", "server");
        //gets the current users respect amount
        String respect = cfg.getProp(respectUser, "respect");

        //parses respect amount
        try {
            respectCounter = Integer.parseInt(respect);
        }
        catch (NumberFormatException e) {
            //if null on load, reset
            respectCounter = 0;
        }
        catch (NullPointerException e){
            //do nothing if user does not exist
        }
    }

    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        if(args == null){
            if(respectUser == null) {
                Message.builder(client, message, "User to pay respects to has not been set");

            }else {
                respectCounter++;
                cfg.setProp(respectUser, respectCounter + "", "respect");
                Message.builder(client, message, "Respects have been paid to " + respectUser + " " + respectCounter + " times");
            }
        }
        else if(args.startsWith("<")) {
            //stores current respects in properties file
            if(respectUser != null) {
                cfg.setProp(respectUser, respectCounter + "", "respect");
            }

            //sets current respect user
            respectUser = args;
            //sets current respect user in server config
            cfg.setProp("respectuser" , respectUser, "server");
            //recursively run run with a null arg, adding 1 respect to current user

            String count = cfg.getProp(respectUser, "respect");

            if(count == null){
                respectCounter = 0;
            }
            else {
                respectCounter = Integer.parseInt(count);
            }


            run(client, null, message);
        }
        else if(args.equalsIgnoreCase("leaderboard")){
            Leaderboard<Integer, IUser> leaderboard = new Leaderboard<>();
            Set<String> users = cfg.getAllKeys("respect");
            for(String userStr : users){

                int score = Integer.parseInt(cfg.getProp(userStr, "respect"));

                IUser user = message.getGuild().getUserByID(Long.parseLong(userStr.replaceAll("[^0-9]", "")));
                leaderboard.put(score, user);
            }

            StringBuilder sb = new StringBuilder("```Respects Leaderboard \n");
            //gets top 10 respect amounts or total respects, whatever comes first
            for(int i = 0; i < leaderboard.getSize() && i < 10; i++){
                sb.append(leaderboard.getCurrentValue().getName());
                sb.append(" : ");
                sb.append(leaderboard.getCurrentKey());
                sb.append("\n");
                leaderboard.next();
            }
            //close code bracket
            sb.append("```");
            Message.builder(client, message, sb.toString());

        }


    }



    @Override
    public String getDesc(String prefix) {
        return "Simple F to pay respects command \n" +
                "Usage: \n" +
                prefix + "F - f to pay respects\n" +
                prefix + "F @(user) - sets user to pay respects to";
    }

    @Override
    public String getRole() {
        return null;
    }
}
