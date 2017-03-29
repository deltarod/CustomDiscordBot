package Commands.Util;

public class IntParse {

    private int parsedInt;

    public int parseInt(String args) {
        parsedInt = Integer.parseInt(args);

        return parsedInt;
    }
}
