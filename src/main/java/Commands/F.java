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

    F(GuildCfg cfg){

        //loads respect amounts
        String respect = cfg.getProp("respectCounter");
        respectUser = cfg.getProp("respectUser");
        IntParse parse = new IntParse();
        msg = new Message();
        //parses respectamount
        try {
            respectCounter = parse.parseInt(respect);
        } catch (NumberFormatException e) {}
    }

    private void counterReset(){
        respectCounter = 0;
        cfg.setProp("respectcounter", respectCounter + "");
    }
    @Override
    public void run(IDiscordClient client, GuildCfg cfg, String args, IMessage message) {
        this.cfg = cfg;
        if(args == null){
            if(respectUser == null) {
                msg.builder(client, message.getChannel(), "User to pay respects to has not been set");
                counterReset();
            }else {
                respectCounter++;
                cfg.setProp("respectcounter", respectCounter + "");
                msg.builder(client, message.getChannel(), "Respects have been paid to " + respectUser + " " + respectCounter + " times");
            }
        }
        else if(args.startsWith("<")) {
            //String id = args.replaceAll("[^0-9]", "");
            respectUser = args;
            cfg.setProp("respectuser", respectUser);
            counterReset();
            run(client, cfg, null, message);
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
