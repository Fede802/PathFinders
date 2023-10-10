package utils;

import enums.RenderType;
import enums.SimulationType;
import enums.TimeType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Config {

    public static final int WEIGHT_BOUND = 5;
    private static final Map<String, SimulationType> simulationTypeMap = Map.of(
            "preset1",SimulationType.PRESET1,
            "preset2",SimulationType.PRESET2,
            "preset3",SimulationType.PRESET3,
            "preset4",SimulationType.PRESET4,
            "preset5",SimulationType.PRESET5,
            "makesetGraph",SimulationType.MAKESET,
            "makesetLab",SimulationType.MAKESET,
            "makesetMap",SimulationType.MAKESET);

    private static final Map<String, RenderType> renderTypeMap = Map.of(
            "makesetGraph",RenderType.GRAPH,
            "makesetLab",RenderType.LAB,
            "makesetMap",RenderType.MAP);

    private static final Map<RenderType,String> renderTypeConfig = Map.of(
            RenderType.GRAPH, "config/makesetGraph.txt",
            RenderType.LAB, "config/makesetLab.txt",
            RenderType.MAP, "config/makesetMap.txt");

    private static final Map<String, TimeType> timeTypeMap = Map.of(
            "sec",TimeType.SEC,
            "milli",TimeType.MILLI,
            "micro",TimeType.MICRO,
            "nano",TimeType.NANO);



    public static SimulationType simulationType;
    public static RenderType renderType;
    public static int numSimulation;
    public static TimeType timeType;
    public static boolean includeDFS;


    public static void loadConfig(){
            ArrayList<String> data = new ArrayList<>();
            try {
                BufferedReader in = new BufferedReader (new FileReader("config/activeSimulation.txt"));
                String line = in.readLine ();
                while (line != null){
                    if(!line.contains("#") && !(line.length() == 0))
                        data.add(line);
                    line = in.readLine();
                }
                in.close();
                simulationType = simulationTypeMap.get(data.get(0));
                if(simulationType == SimulationType.MAKESET)
                    renderType = renderTypeMap.get(data.get(0));
                else
                    renderType = null;
                numSimulation = Integer.parseInt(data.get(1).split("=")[1].trim());
                timeType = timeTypeMap.get(data.get(2));
                includeDFS = data.get(3).split("=")[1].trim().equals("true");
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public static int[] loadOptions(){
        ArrayList<String> data = new ArrayList<>();
        int[] parsedData = null;
        try {
            BufferedReader in = new BufferedReader (new FileReader(renderTypeConfig.get(renderType)));
            String line = in.readLine();
            while (line != null){
                if(!line.contains("#") && !(line.length() == 0))
                    data.add(line);
                line = in.readLine();
            }
            in.close();
            parsedData = new int[data.size()];
            for (int i = 0; i < data.size(); i++) {
                parsedData[i] = Integer.parseInt(data.get(i).split("=")[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parsedData;
    }



}
