package commands.util;

import sx.blah.discord.handle.obj.IGuild;

import java.io.*;
import java.util.Properties;

public class GuildCfg {
    private String path;

    //used to save id's, command handler compares strings
    public GuildCfg(IGuild guild) {
        //gets file location for where properties will be stored based on guild
        String guildId = guild.getStringID();
        path = "GuildCFG/" + guildId + "/";
        File dir = new File(path);
        boolean isMade = false;
        if(!dir.exists()){
            isMade = dir.mkdirs();
        }
        if(isMade){
            System.out.println("Successfully created " + dir.getPath());
        }

    }

    //reads an ID from the .prop file
    public String getProp(String key, String config) {
        Properties prop = new Properties();
        File cfg = new File(path + config + ".properties");
        FileInputStream input = read(cfg, prop);
        String value = prop.getProperty(key.toLowerCase());
        finishRead(input);

        return value;


    }

    //sets ID
    public void setProp(String key, String value, String config) {
        File cfg = new File(path + config + ".properties");
        Properties prop = new Properties();
        FileOutputStream output;
        //if file exists, opens input stream first to not delete it
        if(cfg.exists()){
            //loads file into prop so it doesnt delete contents
            InputStream input = read(cfg, prop);
            finishRead(input);
            output = write(cfg);
        }else {
            output = write(cfg);
        }
        prop.setProperty(key.toLowerCase(), value);
        finishWrite(output, prop);
    }


    //opens new input stream
    private FileInputStream read(File file, Properties prop){
        FileInputStream input;
        //trys to load file
        try{
            input = new FileInputStream(file);
            prop.load(input);
        }
        catch (IOException e){
            //if file does not exists, creates it and recursively call read
            fileCreate(file);
            input = read(file, prop);
        }
        return input;
    }


    //closes input stream
    private void finishRead(InputStream input){
        try {
            input.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //opens output steam
    private FileOutputStream write(File file){
        FileOutputStream output = null;
        try{
            output = new FileOutputStream(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return output;
    }

    //close output stream
    private void finishWrite(FileOutputStream output, Properties prop){
        try {
            prop.store(output, null);
            output.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    private void fileCreate(File file){
        //creates file, used in read in case file does not exist
        boolean isCreated = false;
        try {
            isCreated = file.createNewFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        if(isCreated){
            System.out.println(file.getName() + " created successfully");
        }
    }

}
