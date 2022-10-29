import java.util.Arrays;
import java.util.List;

public class BareSwitch extends BareStructure {

    private String switchRegex;
    private int realEnd;
    public BareSwitch(int i ){
        super(i);
    }

    public void checkValid() throws Exception{
        if (!(parts[0].equals("switch") &&
                Main.vars.containsKey(parts[1])))
            throw new Exception("Switch Statement.");

        //checking if it's a valid while loop.
    }
    public void runSwitch() throws Exception{
        int i;
        i = startIndex;
        while (i < endIndex) {
            i = Main.excecute(i); //this is indirectly recursive to allow nested while loops.

        }
        }

    public void findEnd() {
        int nested = 1;
        boolean startChanged = false;
        int currentIndex = startIndex;
        while (nested > 0) {
            String[] line = program.get(currentIndex);
            if (line[0].equals("case") && startChanged)
                endIndex = currentIndex;

            if (line[0].equals("end") && line[1].equals(parts[0]))
                nested -= 1;
                if (endIndex<=startIndex){
                    endIndex = currentIndex;
                }
            else if (line[0].equals("switch"))
                nested += 1;

            if (line[0].equals("case") && nested == 1  ){

                if (Main.vars.get(parts[1])==Integer.valueOf(line[1])) {
                    startIndex = currentIndex + 1;
                    startChanged = true;
                }
            }
            if (line[0].equals("else") && nested == 1  ){
                startIndex = currentIndex + 1;
                startChanged = true;
                }





            currentIndex += 1;

        }
        realEnd = currentIndex;

    }
    public int getEndIndex(){
        return realEnd;
    }

}
