package Commands.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ObjectMapper mapper;

    //used to save id's, command handler compares strings
    public GuildCfg(IGuild guild) {
        //gets file location for where properties will be stored based on guild
        this.guildId = guild.getID();
        mapper = new ObjectMapper();
        path = "GuildCFG/" + guildId + ".properties";
        cfg = new File(path);
    }

    //reads an ID from the .prop file
    public String getProp(String key) {
        read();
        return prop.getProperty(key.toLowerCase());

    }

    //sets ID
    public void setProp(String key, String value) {
        write();
        prop.setProperty(key.toLowerCase(), value);
        try {
            prop.store(output, null);
        } catch (Exception e) {
        }
    }

    //Gets Object
    public Object getObj(String key) {
        read();
        Object obj = null;

        try {
            obj = mapper.readValue(prop.getProperty(key.toLowerCase()), Object.class);
        } catch (IOException e) {
        }
        return obj;
    }

    //sets Object
    public void setObj(String key, Object obj) {
        String objAsString = null;
        write();
        // TODO: 3/29/2017 fix error
        //objAsString is null
        try {
            objAsString = mapper.writeValueAsString(obj);
        } catch (Exception e) {
        }

        prop.setProperty(key.toLowerCase(), objAsString);
        try {
            prop.store(output, null);
        } catch (Exception e) {
        }
    }


    //write prereqs
    private void write() {
        File dir = new File("GuildCFG/");
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            output = new FileOutputStream(cfg);
        } catch (FileNotFoundException e) {

        }
    }

    //creates a class to setup the reading of a file
    private void read() {
        try {
            input = new FileInputStream(cfg);
            prop.load(input);
        } catch (IOException e) {
        }
    }
}
