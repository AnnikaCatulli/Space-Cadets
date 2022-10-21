import java.util.*;
import java.nio.file.*;

/**
 * The aim of this project is to implement a bare-bones interpreter
 * Commands will be: incr var, clear var and decr var
 * The approach used is more procedural than object-oriented.
 * The program returns an error message if the code is wrong.
 */


public class Main {//sets up the main class where the entire program will be run.
    static Hashtable<String,Integer> vars = new Hashtable<String,Integer>();
    /*uses a hash table to store the variable and its value. This means the variable can be
    accessed very easily.*/
    static String[] commands={"clear","incr","decr","not","do","while","end"};
    /*Used to check for words that can't be used as variables.*/
    static List<String[]> program=new ArrayList<String[]>();
    /*Initalizes the list where the instructions will be stored as arrays.
    * eg:[[[incr],[w]],[[decr],[x]]] */


    /**
     * Running the key methods
     */
    public static void main(String[] args) throws Exception {

        readString();
        run();
        output();

    }

    /**
     * Iterates the code an executes each line unless in a while loop.
     */
    static public void run() throws Exception {
        int index=0;

        while (index < program.size()){
            index =excecute(index);/*execute will return the index
            of the next instruction to be executed.*/
        }
    }
    /**
     * A function which reads the whole file as a string, removes whitespace
     * between lines of code, splits the lines apart then splits the spaces.
     */
    public static void readString()throws Exception{
        //reads in the whole code as a string.

        String file = "src/bareCode.txt";
        Path path = Paths.get(file);
        String programString= Files.readString(path);


        programString=programString.strip().replaceAll("(?<=;)\\s+(?=\\w|\\-)", "");

        String[] listOfStrings=programString.split(";");

        for (int i=0; i<listOfStrings.length;i++)
            program.add(i,listOfStrings[i].split(" "));

    }
    static public void output() {
        for (Map.Entry<String, Integer> entry : vars.entrySet())
            System.out.print(entry.getKey() + ": " + entry.getValue()+" ");
        System.out.println(" ");
    }
    /**
     * Branching is used to run the correct method depending on the first
     * part of the line which should be the command.
     */

    static public int excecute(int i) throws Exception {
        String[] parts=program.get(i);

        if (parts.length>1){
            if (Arrays.binarySearch(commands, (parts[1])) >= 0)//if the value is found in commands.
                throw new Exception("Cannot use a command \"" + parts[1] + "\" as a variable.");
        }
        if (parts.length==1 && !parts[0].equals("end"))//catches less than one length arrays.
            throw new Exception(parts[0] +"; is not a valid line.");
        else if (parts[0].equals("clear"))
            clear(parts[1]);
        else if (parts[0].equals("incr"))
            incr(parts[1]);
        else if (parts[0].equals("decr"))
            decr(parts[1]);
        else if (parts[0].equals("while"))
            return newWhile(i); //returns the index after the end of the loop.
        else if (parts[0].equals("end"))
            return i+1;
        else
            throw new Exception(parts[0] + " is not a valid command.");
        output();
        return i+1;

    }
    /**
     * The class used to make the while loop. In an object-oriented
     * approach, the while loop would be a class with the start and
     * end index as attributes.
     */
    static public int newWhile(int i) throws Exception{
        String[] parts=program.get(i);

        if (parts[0].equals("while") &&
                Arrays.binarySearch(commands, parts[1]) < 0 &&
                parts[2].equals("not") &&
                parts[3].equals("0") &&
                parts[4].equals("do") ) {
            //checking if it's a valid while loop.

            int startIndex=i+1;
            int endIndex=i+1;
            int nested=1;/*used to store how many loops have been
            started and not finished. When 0 is reached, the current loop is
            over.
            */
            while (nested>0) {
                String command=program.get(endIndex)[0];
                if (command.equals("end"))
                    nested -= 1;
                else if (command.equals("while"))
                    nested += 1;
                endIndex+=1;
            }

            //The while loop below runs the indexed lines conditionally.
            while (vars.get(parts[1]) != 0) { //parts 1 is the variable
                i=startIndex;
                while (i<endIndex) {
                    i=excecute(i); //this is indirectly recursive to allow nested while loops.
                }
            }
            return endIndex;//returns next line.
        }
        else
            throw new Exception("Invalid while loop.");

    }
    /**
     * The methods below are for the commands except the while loop.
     */
    static public void incr(String var){
                if (vars.get(var) == null)
                    vars.put(var, Integer.valueOf(0));
                vars.put(var, vars.get(var) + 1);
            }


    static public void decr(String var) {
        if (vars.get(var)==null)
            vars.put(var,Integer.valueOf(0));
        vars.put(var, vars.get(var)-1);

    }
    static public void clear(String var){
        vars.put(var,Integer.valueOf(0));

    }
}
