package Commands;

import Commands.Util.GuildCfg;
import Commands.Util.IntParse;
import Commands.Util.Message;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public class F implements  ICommand{
    private int respectCounter;
    private String respectUser;
    private Message msg;
    private GuildCfg cfg;
    private String respect;

    F(GuildCfg cfg){
        this.cfg = cfg;
        //gets last respect user
        respectUser = cfg.getProp("respectUser", "server");
        //gets the current users respect amount
        try {
            respect = cfg.getProp(respectUser, "respect");
        }
        catch (NullPointerException e){
            respect = null;
        }
        IntParse parse = new IntParse();
        msg = new Message();
        //parses respect amount
        try {
            respectCounter = parse.parseInt(respect);
        } catch (NumberFormatException e) {
            //if null on load, reset
            respectCounter = 0;
        }
        catch (NullPointerException e){
            System.out.println("lmao");
        }
    }

    @Override
    public void run(IDiscordClient client, String args, IMessage message) {
        if(args == null){
            if(respectUser == null) {
                msg.builder(client, message.getChannel(), "User to pay respects to has not been set");

            }else {
                respectCounter++;
                cfg.setProp(respectUser, respectCounter + "", "respect");
                msg.builder(client, message.getChannel(), "Respects have been paid to " + respectUser + " " + respectCounter + " times");
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
            //recursivly run run with a null arg, adding 1 respect to current user

            String count = cfg.getProp(respectUser, "respect");

            if(count == null){
                respectCounter = 0;
            }
            else {
                respectCounter = new IntParse().parseInt(count);
            }


            run(client, null, message);
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
