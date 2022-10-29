import java.util.*;
import java.nio.file.*;

/**
 * The aim of this project is to implement a bare-bones interpreter
 * Commands will be: incr var, clear var and decr var
 * The approach used is more procedural than object-oriented.
 * The program returns an error message if the code is wrong.
 *
 * space cadets 3 is an extension of the previous task.
 * The aim will be to extend the language. An object-oriented approach
 * shall be taken.
 * .variable names can start with a letter of the english language and contain alphanumeric
 * characters
 * .comments can be added by using the // line.
 * .subroutines
 * .switch case statements
 * .operators.
 */


public class Main {//sets up the main class where the entire program will be run.
    static Hashtable<String,Integer> vars = new Hashtable<String,Integer>();
    /*uses a hash table to store the variable and its value. This means the variable can be
    accessed very easily.*/
    static String[] commands={"clear","incr","decr","not","do","while","end"};
    /*Used to check for words that can't be used as variables.*/
    public static List<String[]> program=new ArrayList<String[]>();

    /*Initalizes the list where the instructions will be stored as arrays.
    * eg:[[[incr],[w]],[[decr],[x]]] */

    static Hashtable<String,BareProcs> procs = new Hashtable<String,BareProcs>();

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
        String[] parts = program.get(i);
        output();
        if (parts.length > 1) {
            if ((Arrays.binarySearch(commands, (parts[1])) >= 0)&&!parts[0].equals("end"))//if the value is found in commands.
                throw new Exception("Cannot use a command \"" + parts[1] + "\" as a variable.");
        }
        if (parts.length == 1 )//catches less than one length arrays.
            throw new Exception(parts[0] + "; is not a valid line.");
        else if (parts[0].equals("clear")){
            clear(parts[1]);
    }
        else if (parts[0].equals("incr"))
            incr(parts[1]);
        else if (parts[0].equals("decr"))
            decr(parts[1]);
        
        else if (parts[0].equals("procedure")) {
            BareProcs newProcedure= new BareProcs(i);
            newProcedure.checkValid();
            procs.put(parts[1],newProcedure);
            return newProcedure.getEndIndex();

        }
        else if (parts[0].equals("call")){
            procs.get(parts[1]).runProc();
            return i+1;
        }
        else if (parts[0].equals("while")) {
            BareWhile newWhileLoop= new BareWhile(i);
            newWhileLoop.checkValid();
            newWhileLoop.runWhile();
            return newWhileLoop.getEndIndex();

        }

        else if (parts[0].equals("switch")) {
            BareSwitch newSwitch= new BareSwitch(i);
            newSwitch.checkValid();
            newSwitch.runSwitch();
            return newSwitch.getEndIndex();

        }
        else if (parts[0].equals("end"))
            return i+1;
        else {
            throw new Exception(parts[0] + " is not a valid command.");
        }
        output();
        return i+1;

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
