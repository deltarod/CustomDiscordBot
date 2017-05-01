import java.io.*;
import java.util.Properties;
import java.util.Scanner;

class Cfg {
    private Properties prop = new Properties();
    private String cfg = "cfg.properties";
    private OutputStream output;
    private InputStream input;


    //Reworked Cfg, now with common set and get properties
    private String setProp(String property){
        //gets needed value
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your " + property + ": ");
        String returnVal = scanner.nextLine();
        System.out.println(" you entered " + returnVal + ". Is this correct? (Y/N)");
        String yn = scanner.nextLine().toLowerCase();

        //checks if value is correct
        switch (yn){
            case "y":
                break;
            default:
                returnVal = setProp(property);
                break;
        }
        //just in case the cfg is already created
        read();
        finishRead();
        //loads output stream
        try {
            output = new FileOutputStream(cfg);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        prop.setProperty(property, returnVal);
        finishWrite();
        return returnVal;
    }

    //loads property
    String getProp(String property){
        read();
        String returnVal;

        returnVal = prop.getProperty(property);

        if(returnVal == null) {
            System.out.println("The " + property + " does not exist, please set it now");
            returnVal = setProp(property);
        }


        System.out.println("Your " + property + " is: " + returnVal);
        return returnVal;

    }


    private void read() {
        try {
            input = new FileInputStream(cfg);
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void finishWrite(){
        try {
            prop.store(output, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finishRead(){
        try {
            input.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}