import java.io.*;
import java.util.Properties;
import java.util.Scanner;

class Cfg {
    private Properties prop = new Properties();
    private String cfg = "cfg.properties";
    OutputStream output;
    InputStream input;
    //creates new cfg.properties

    // TODO: 3/22/2017 read, write, and a common propertyReader
    public void cfgCreate() {
        Scanner scanner = new Scanner(System.in);

        try {
            output = new FileOutputStream(cfg);
            System.out.println("Please enter your token: ");
            //takes in token from console
            String token = scanner.nextLine();
            //repeats token and stores
            System.out.println("You entered: " + token);
            prop.setProperty("Token", token);

            //gets home text channel
            System.out.println("Please enter the home text channel: ");
            String homeChannel = scanner.nextLine();
            System.out.println("Home Channel: " + homeChannel);
            prop.setProperty("HomeChannel", homeChannel);
            //gets startup Message
            System.out.println("Please enter the startup Message: ");
            String startup = scanner.nextLine();
            System.out.println("Startup Message: " + startup);
            prop.setProperty("Startup", startup);
            //gets command prefix
            System.out.println("Please enter the default command prefix: ");
            String prefix = scanner.nextLine();
            System.out.println("Command prefix: " + prefix);
            prop.setProperty("prefix", prefix);


            prop.store(output, null);
        } catch (IOException e) {
        }

    }

    private void read() {
        try {
            input = new FileInputStream(cfg);
            prop.load(input);
        } catch (IOException e) {
        }
    }
    //loads token

    public String tokenRead() {
        read();
        String token;
        token = prop.getProperty("Token");
        //repeats token
        System.out.println("Your Token is: " + token);

        return token;
    }

    public String homeChannelRead() {
        read();
        String channel = prop.getProperty("HomeChannel");
        //repeats Channel
        System.out.println("Your Home Channel is: " + channel);

        return channel;
    }

    public String startupRead() {
        read();
        String startup = prop.getProperty("Startup");

        System.out.println("Your startup Message is: " + startup);

        return startup;
    }

    public String prefixRead() {
        read();
        String prefix = prop.getProperty("prefix");

        System.out.println("Your prefix is: " + prefix);

        return prefix;
    }

}