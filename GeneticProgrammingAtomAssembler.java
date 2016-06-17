import java.io.Serializable;

/**
 * Created by m088378 on 6/16/16.
 */
public class GeneticProgrammingAtomAssembler {
    // My attempt at an Genetic Programming creator for Tank Bots
    String inFile = "/Users/m088378/robocode/robots/raymondBots/BlankBot.java";
    String serialFile = "/Users/m088378/robocode/robots/raymondBots/gp.ser";

    public static void main(String[] args)
    {

    }

 //   public GenerationInfo readSerializedInfo(){
    //  }

    public void writeSerializedInfo(){

    }

}

class GenerationInfo implements Serializable{
    public int generation;
}