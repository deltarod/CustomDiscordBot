package Commands.Util;

import sx.blah.discord.handle.obj.IGuild;

import java.io.*;
import java.util.Properties;

public class GuildCfg {
    private Properties prop = new Properties();
    private String guildId;
    private File cfg;
    private OutputStream output;
    private InputStream input;
    private String path;

    //used to save id's, command handler compares strings
    public GuildCfg(IGuild guild){
        //gets file location for where properties will be stored based on guild
        this.guildId=guild.getID();
        path = "GuildCFG/" + guildId + ".properties";
        cfg = new File(path);
    }
    //reads an ID from the .prop file
    public String getId(String role){
        read();
        return prop.getProperty(role.toLowerCase());

    }
    //sets ID
    public void setId(String role, String roleId){
        write();
        prop.setProperty(role.toLowerCase(), roleId);
        try {
            prop.store(output, null);
        }catch (Exception e){}
    }




    //write prereqs
    private void write(){
        File dir = new File("GuildCFG/");
        if(!dir.exists()){
            dir.mkdir();
        }
        try {
            output = new FileOutputStream(cfg);
        }
        catch (FileNotFoundException e){

        }
    }

//creates a class to setup the reading of a file
    private void read(){
        try{
            input = new FileInputStream(cfg);
            prop.load(input);
        }
        catch (IOException e){}
    }
}
