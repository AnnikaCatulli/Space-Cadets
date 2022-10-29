import java.util.Arrays;
import java.util.List;

public class BareWhile extends BareStructure {
    private String whileRegex;


    public BareWhile(int i ){
        super(i);
    }

    public void checkValid() throws Exception{
        if (!(parts[0].equals("while") &&
                Arrays.binarySearch(Main.commands, parts[1]) < 0 &&
                parts[2].equals("not") &&
                parts[3].equals("0") &&
                parts[4].equals("do") )) {
            throw new Exception("Invalid while loop.");
        }
            //checking if it's a valid while loop.
    }
    public void runWhile() throws Exception{
        int i;
        while (Main.vars.get(parts[1]) != 0) { //parts 1 is the variable
            i = startIndex;
            while (i < endIndex) {
                i = Main.excecute(i); //this is indirectly recursive to allow nested while loops.

            }
        }
    }
    public int getEndIndex(){
        return endIndex;
    }

}
