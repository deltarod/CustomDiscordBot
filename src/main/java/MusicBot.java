import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;


public class MusicBot {
    public IDiscordClient client;


    private MusicBot(String token, String owner, String prefix) {
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder

        try {
            client = clientBuilder.login(); // Creates the client instance and logs the client in
        } catch (DiscordException e) {
            e.printStackTrace();
        }
        EventDispatcher dispatcher = client.getDispatcher();
        AnnotationListener anno = new AnnotationListener(client, owner, prefix);
        dispatcher.registerListener(anno);
    }


    public static void main(String[] args) {
        Cfg config = new Cfg();

        String token = config.getProp("botLoginToken");
        String owner = config.getProp("botOwnerID");
        String prefix = config.getProp("defaultPrefix");
        new MusicBot(token, owner, prefix);
    }
}


