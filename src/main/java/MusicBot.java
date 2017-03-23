import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

import java.io.File;


public class MusicBot {
    public IDiscordClient client;
    public String channel;
    public String startup;


    public MusicBot(String token, String channel, String startup,String prefix){
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        this.channel = channel;
        this.startup = startup;

        try {
            client = clientBuilder.login(); // Creates the client instance and logs the client in
        } catch (DiscordException e) {
            e.printStackTrace();
        }
        EventDispatcher dispatcher = client.getDispatcher();
        AnnotationListener anno = new AnnotationListener(client,channel,startup,prefix);
        dispatcher.registerListener(anno);
    }


    public static void main(String[] args) {
        Cfg config = new Cfg();
        File cfg = new File("cfg.properties");
        if(!cfg.exists()){
            config.cfgCreate();
        }
        String token = config.tokenRead();
        String homeChannel = config.homeChannelRead();
        String startup = config.startupRead();
        String prefix = config.prefixRead();
        new MusicBot(token,homeChannel,startup,prefix);

    }
}


