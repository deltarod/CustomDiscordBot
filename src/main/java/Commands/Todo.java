package Commands;


import Commands.Util.GuildCfg;
import Commands.Util.Message;
import Commands.Util.StringSplit;
import Commands.Util.UserTodo;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public class Todo implements ICommand{
    private Message msg = new Message();
    @Override
    public void run(IDiscordClient client, GuildCfg cfg, String args, IMessage message) {
        String[] arg = null;
        UserTodo todo = null;
        //if get
        if(args==null){
            msg.builder(client,message.getChannel(),"Invalid Format please use get to get your todos," +
                    " or set (todo) to set one.");
            return;
        }
        arg = StringSplit.split(args);
        if(arg[0].equals("get")){
            //trys to get the to do list, if throws null error  sends message
            try {
                todo = (UserTodo)cfg.getObj(message.getAuthor().getID() + "todo");
            }
            catch (NullPointerException e){
                msg.builder(client,message.getChannel() , "No todos set");
            }
            message.getAuthor().getOrCreatePMChannel().sendMessage(getTodos(todo));
        }


        //if set
        else if(arg[0].equals("add")){
            //if can get todos, keeps going, else creates todos then continues
            try {
                todo = (UserTodo)cfg.getObj(message.getAuthor().getID() + "todo");
            }catch (NullPointerException e){
                todo = new UserTodo();
            }
            //sets todos then saves to cfg
            todo.set(arg[1]);
            System.out.println(todo.get());
            // TODO: 3/29/2017 throwing nullpointer?
            cfg.setObj(message.getAuthor().getID()+"todo",todo);
            msg.builder(client,message.getChannel(),"Todo set!");
        }
        else {
            msg.builder(client,message.getChannel(),"Invalid Format please use get to get your todos," +
                    " or set (todo) to set one.");
        }
    }

    private String getTodos(UserTodo todo){
        String split = "\n--------------------------------\n";
        String rtnString = "Todo list \n" + split;
        for(String str : todo.get()){
            rtnString += str + split;
        }
        return rtnString;
    }

    @Override
    public String getRole() {
        return null;
    }

    @Override
    public String getDesc() {
        return "simple command that can store or get a todo list" +
                "\n add(Adds todo to list) " +
                "\nget(gets list of all todos)" +
                "\nremove(removes latest command)";
    }
}
