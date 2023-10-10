package logic;

import commons.*;
import utils.FileUtils;
import view.View;

import java.util.Map;

public class Logic implements ILogic{

    private Map<AlgoType,PathFinder> algoList = Map.of(
            AlgoType.AE,new AE(),
            AlgoType.AMAN,new AMan(),
            AlgoType.DFS,new DFS(),
            AlgoType.DIJKSTRA,new Dijkstra(),
            AlgoType.BFS,new BFS()
    );

    private Graph[] graphs;
    private PathFinder[] pathFinders;
    private RenderType currRenderType;
    private ShowState currShowState;
    private boolean simulationStarted;

    //    --------------------------------------------------------
    //                      DATA HANDLING
    //    --------------------------------------------------------

    @Override
    public void generatePreset(SimulationType currentSimulationType, AlgoType[] selectedAlgo) {
        //generate data in case a preset is selected, makeset has to be already generated to check data input
        int numCopy = selectedAlgo.length;

        graphs = Graph.load(currentSimulationType,numCopy);
        initPathfinders(selectedAlgo, numCopy);

    }

    @Override
    public boolean generateData(RenderType currentRenderType, int[] options, AlgoType[] selectedAlgo) {
        resetSimulation();//if simulation is running reset simulationStarted and notify view

        int numCopy = selectedAlgo.length;
        if(numCopy == 1){
            currShowState = ShowState.SINGLE;
        }else{
            currShowState = ShowState.ALL;
        }

        boolean accepted = true;
        switch (currentRenderType){
            case GRAPH -> graphs = Graph.generateRandomGraph(options[0],options[1],options[2],options[3],numCopy);
            case LAB -> graphs = Lab.generateRandomLab(options[0],options[1],numCopy);
            case MAP -> graphs = ObstacleMap.generateRandomMap(options[0],options[1],options[2],options[3],numCopy);
        }
        // in view is checked if options are numbers,
        // in Graph, Lab and ObstacleMap should be checked if these numbers are in a good range
        // at the moment this check is not done
        if(graphs == null){//data not valid
            this.currRenderType = null;// if some graph is shown in screen will be removed
            accepted = false;
        }else{
            this.currRenderType = currentRenderType; //set render type
            initPathfinders(selectedAlgo,numCopy);
        }
        return accepted;
    }



    @Override
    public void generateData() {
        resetSimulation(); //if simulation is running reset simulationStarted and notify view
        if(currRenderType != null) {
            switch (currRenderType) {
                case GRAPH -> graphs = Graph.generateRandomGraph();
                case LAB -> graphs = Lab.generateRandomLab();
                case MAP -> graphs = ObstacleMap.generateRandomMap();
            }
            for (int i = 0; i < graphs.length; i++) {
                //same pathfinders list only setup with new graphs
                pathFinders[i].setup(graphs[i]);
            }

        }
    }

    @Override
    public int getNumNode() {
        return graphs[0].getNumNode();
    }

    @Override
    public int getNumRows() {
        return Graph.numRows;
    }

    @Override
    public int getNumColumns() {
        return Graph.numCols;
    }

    @Override
    public NodeInfo getNodeInfo(int graphID, int nodeIndex) {
        return graphs[graphID].getNode(nodeIndex).getNodeInfo();
    }

    @Override
    public CellInfo getCellInfo(int graphID, int cellIndex) {
        //request only from lab panel that effectively render labs
        return ((Cell)graphs[graphID].getNode(cellIndex)).getNodeInfo();
    }

    @Override
    public EdgeInfo getEdgeInfo(int graphID, int nodeIndex, int edgeIndex) {
        return graphs[graphID].getNode(nodeIndex).getEdges().get(edgeIndex).getEdgeInfo();
    }

    @Override
    public NodeInfo getStartNode(int graphID) {
        return graphs[graphID].getNode(Graph.startNode).getNodeInfo();
    }

    @Override
    public NodeInfo getEndNode(int graphID) {
        return graphs[graphID].getNode(Graph.endNode).getNodeInfo();
    }

    //    --------------------------------------------------------
    //                      SIMULATION HANDLING
    //    --------------------------------------------------------

    @Override
    public void startSimulation() {
        if(graphs != null) {
            if (!simulationStarted) {
                for (PathFinder pf :
                        pathFinders) {
                    pf.simulate(); //need to generate simulation data
                    pf.setupSimulation();
                }
                simulationStarted = true;
            }
            View.getInstance().setSimulation(true);
        }else{
            View.getInstance().setSimulation(false); //notify view that start simulation fail to reset to pause mode
        }
    }

    @Override
    public void pauseSimulation() {
        if(simulationStarted) {
            View.getInstance().setSimulation(false);
        }
    }

    @Override
    public void doSimulationStep() {
        if (graphs != null) {
            if (!simulationStarted) { //needed because simulation could be started directly in step mode
                for (PathFinder pf :
                        pathFinders) {
                    pf.simulate();
                    pf.setupSimulation();
                }
                simulationStarted = true;
            }
            View.getInstance().setSimulation(false); //set or maintain view in pause mode
            nextSimulationStep();
        }
    }

    @Override
    public void nextSimulationStep() {
        int numSimEnded = 0;

        for (int i = 0; i < graphs.length; i++) {
            pathFinders[i].nextStep();

            if (pathFinders[i].isSimulationEnded()) {
                numSimEnded++;
                if (numSimEnded == graphs.length) {
                    simulationStarted = false;
                    View.getInstance().setSimulation(false);//reset to pause mode
                    View.getInstance().simulationEnded();//and notify view to show stats
                }
            }
        }
    }

    @Override
    public void resetSimulation() {
        if(graphs != null){
            for (Graph g:
                    graphs) {
                g.reset();
            }
            View.getInstance().setSimulation(false);//reset to pause mode
        }
        //simulation started overlaps to restart also with play btn
        if(simulationStarted){
            simulationStarted = false;
        }else{
            if(graphs != null)
                Graph.setupPoints(graphs[0]);
        }

    }

    @Override
    public void saveSimulation(SimulationType currentSimulationType, int numSimulation, TimeType timeType) {

        double timeDivisor = -1; //nano case
        switch (timeType){
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
        for (int i = 0; i < numSimulation; i++) {
            if(currentSimulationType != SimulationType.MAKESET){
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

    private void initPathfinders(AlgoType[] selectedAlgo, int numCopy) {
        pathFinders = new PathFinder[numCopy];
        for (int i = 0; i < numCopy; i++) {
            PathFinder temp = algoList.get(selectedAlgo[i]);
            temp.setup(graphs[i]);
            pathFinders[i] = temp;
        }
    }

    //    --------------------------------------------------------
    //                  SIMULATION DATA HANDLING
    //    --------------------------------------------------------

    @Override
    public double getPathCost(int id) {
        return pathFinders[id].getPathCost();
    }

    @Override
    public int getNumStep(int id) {
        return pathFinders[id].getNumStep();
    }

    @Override
    public double getTime(int id) {
        return pathFinders[id].getSimDurationNanoSec()/1E6; //for design view shows only time in millisec
    }

    @Override
    public String getName(int id) {
        return pathFinders[id].getName();
    }

    //    --------------------------------------------------------
    //                      WINDOW HANDLING
    //    --------------------------------------------------------

    @Override
    public void openMenu() {
        resetSimulation(); //if simulation is running reset simulationStarted and notify view
        graphs = null; //prevent simulation button click
        View.getInstance().setUpScreen(null,null);//special state that remove rendering content and reset initial screen setup
        View.getInstance().openMenu();
    }

    @Override
    public void openApp() {
        View.getInstance().openApp();
    }

    @Override
    public void openSimulation() {
        View.getInstance().openSimulation();
    }

    @Override
    public void setUpScreen() {
        View.getInstance().setUpScreen(currRenderType, currShowState);
    }

    @Override
    public int getNumGraph() {
        return graphs.length;
    }

    //    --------------------------------------------------------
    //                      SINGLETON PATTERN
    //    --------------------------------------------------------
    private static ILogic instance = null;
    public static ILogic getInstance() {
        if (instance == null) {
            instance = new Logic();
        }
        return instance;
    }
    private Logic(){};



}
