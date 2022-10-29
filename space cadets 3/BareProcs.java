import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class BareProcs extends BareStructure {

    public BareProcs(int i ){
        super(i);
    }
    static Hashtable<String,Integer> localVars = new Hashtable<String,Integer>();

    public void checkValid() throws Exception{
        if (!(parts[0].equals("procedure") &&
                Arrays.binarySearch(Main.commands, parts[1]) < 0 ))
        {
            throw new Exception("Invalid procedure.");
        }
        //checking if it's a valid while loop.
    }
    public void runProc() throws Exception{
        int i;
        i = startIndex;
        while (i < endIndex) {
            i = Main.excecute(i); //this is indirectly recursive to allow nested while loops.
        }
    }
    public int getEndIndex(){
        return endIndex;
    }

}
