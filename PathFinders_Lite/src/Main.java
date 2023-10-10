import enums.AlgoType;
import enums.SimulationType;
import utils.Config;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.Map;


public class Main {

    private static Graph[] graphs;
    private static PathFinder[] pathFinders;
    private static final Map<AlgoType,PathFinder> algoList = Map.of(
            AlgoType.AE,new AE(),
            AlgoType.AMAN,new AMan(),
            AlgoType.DFS,new DFS(),
            AlgoType.DIJKSTRA,new Dijkstra(),
            AlgoType.BFS,new BFS()
    );

    public static void main(String[] args) {
        Config.loadConfig();
        saveSimulation();
    }

    public static void saveSimulation() {
        AlgoType[] selectedAlgo = getAlgo();
        int numCopy = selectedAlgo.length;
        if(Config.simulationType != SimulationType.MAKESET){
            graphs = Graph.load(Config.simulationType,numCopy);
        }else{
            int[] options = Config.loadOptions();
            switch (Config.renderType){
                case GRAPH -> graphs = Graph.generateRandomGraph(options[0],options[1],options[2],options[3],numCopy);
                case LAB -> graphs = Lab.generateRandomLab(options[0],options[1],numCopy);
                case MAP -> graphs = ObstacleMap.generateRandomMap(options[0],options[1],options[2],options[3],numCopy);
            }
        }


        initPathfinders(selectedAlgo, numCopy);
        double timeDivisor = -1; //nano case
        switch (Config.timeType){
            case SEC -> timeDivisor = 1E9;
            case MILLI -> timeDivisor = 1E6;
            case MICRO -> timeDivisor = 1E3;
        }
        FileUtils.setSimFolder();
        String time = "";
        String pathCost = "";
        String numStep = "";
        for(PathFinder a: pathFinders){
            time+="\t"+a.getName();
            pathCost+="\t"+a.getName();
            numStep+="\t"+a.getName();
        }
        time+="\n";
        pathCost+="\n";
        numStep+="\n";

        long t0, t1;
        t0=System.nanoTime();
        for (int i = 0; i < Config.numSimulation; i++) {
            if(Config.simulationType != SimulationType.MAKESET){
                resetSimulation();
            }else{
                generateData();
            }
            time+=""+i;
            pathCost+=""+i;
            numStep+=""+i;
            for (PathFinder pf :
                    pathFinders) {
                if(pf.simulate()) {
                    if (timeDivisor == -1) {
                        time += "\t" + pf.getSimDurationNanoSec();
                    } else {
                        time += "\t" + pf.getSimDurationNanoSec() / timeDivisor;
                    }
                    pathCost += "\t" + pf.getPathCost();
                    numStep += "\t" + pf.getNumStep();
                }
                FileUtils.writeSim(time,pathCost,numStep);
            }
            time+="\n";
            pathCost+="\n";
            numStep+="\n";
        }
        t1=System.nanoTime();
        System.out.println("Logic: total sec sim duration "+(t1-t0)/1E6);

        FileUtils.writeSim(time,pathCost,numStep);

    }


    private static AlgoType[] getAlgo() {
        ArrayList<AlgoType> algoTypes = new ArrayList<>();
        algoTypes.add(AlgoType.AE);
        algoTypes.add(AlgoType.AMAN);
        algoTypes.add(AlgoType.DIJKSTRA);
        algoTypes.add(AlgoType.BFS);
        if(Config.includeDFS)
            algoTypes.add(AlgoType.DFS);
        return algoTypes.toArray(new AlgoType[]{});
    }

    private static void initPathfinders(AlgoType[] selectedAlgo, int numCopy) {
        pathFinders = new PathFinder[numCopy];
        for (int i = 0; i < numCopy; i++) {
            PathFinder temp = algoList.get(selectedAlgo[i]);
            temp.setup(graphs[i]);
            pathFinders[i] = temp;
        }
    }

    public static void resetSimulation() {
        if (graphs != null) {
            for (Graph g :
                    graphs) {
                g.reset();
            }
            Graph.setupPoints(graphs[0]);
        }
    }

    public static void generateData() {
        switch (Config.renderType) {
            case GRAPH -> graphs = Graph.generateRandomGraph();
            case LAB -> graphs = Lab.generateRandomLab();
            case MAP -> graphs = ObstacleMap.generateRandomMap();
        }
        for (int i = 0; i < graphs.length; i++) {
            pathFinders[i].setup(graphs[i]);
        }
    }










}