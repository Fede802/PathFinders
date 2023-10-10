package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FileUtils {

    private static String dir;

    public static ArrayList<String> loadPreset(String fileName){
        ArrayList<String> seq=new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader (new FileReader(fileName));
            String line = in.readLine ();
            while (line != null){
                seq.add(line);
                line = in.readLine ();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return seq;
    }

    public static void setSimFolder(){
        int numSim = Objects.requireNonNull(new File("sim").listFiles()).length;
        dir = "sim/simulation"+numSim+"/";
        new File(dir).mkdirs();
    }

    public static void writeSim(String time, String pathCost, String numStep){
        try {
            PrintWriter out = new PrintWriter (new FileWriter(dir+"time.txt"));
            out.println(time);
            out.close();
            out = new PrintWriter (new FileWriter(dir+"pathCost.txt"));
            out.println(pathCost);
            out.close();
            out = new PrintWriter (new FileWriter(dir+"numStep.txt"));
            out.println(numStep);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
