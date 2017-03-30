package Commands.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class UserTodo {
    LinkedList<String>list = new LinkedList<>();

    public void set(String todo){
        String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
        list.add(time + ": " + todo);
    }
    public LinkedList<String> get(){
        return list;
    }
    public String remove(){
        return list.remove();
    }
}
