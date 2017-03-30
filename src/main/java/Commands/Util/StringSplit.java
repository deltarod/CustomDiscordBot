package Commands.Util;

public class StringSplit {
    //moved StringSplit
    public static String[] split(String string) {

        String[] returnArr = new String[2];
        //if command is 2 part, splits command and arg
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                returnArr[0] = string.substring(0, i);
                returnArr[1] = string.substring(i + 1);
                return returnArr;
            }
        }
        returnArr[0] = string;
        return returnArr;


    }
}
