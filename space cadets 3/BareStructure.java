import java.util.List;

public abstract class BareStructure {
    int startIndex;
    int endIndex;
    String theRegex;
    String[] parts;
    public static List<String[]> program =Main.program;

    public BareStructure(int i){
        parts=program.get(i);
        startIndex=i+1;
        endIndex=i+1;
        findEnd();
        //return parts,startIndex,endIndex ;

    }
    public void findEnd(){
        int nested=1;
        while (nested>0) {
            String[] line=program.get(endIndex);
            if (line[0].equals("end") && line[1].equals(parts[0]))
                nested -= 1;
            else if (line[0].equals(parts[0]))
                nested += 1;
            endIndex+=1;

        }
    }

}
