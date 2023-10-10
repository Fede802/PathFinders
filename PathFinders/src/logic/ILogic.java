package logic;

import commons.*;

public interface ILogic {

    //    --------------------------------------------------------
    //                      DATA HANDLING
    //    --------------------------------------------------------
    void generatePreset(SimulationType currentSimulationType, AlgoType[] selectedAlgo);
    boolean generateData(RenderType currentRenderType, int[] options, AlgoType[] selectedAlgo);
    void generateData();
    int getNumNode();

    int getNumRows();

    int getNumColumns();

    NodeInfo getNodeInfo(int graphID, int nodeIndex);

    CellInfo getCellInfo(int graphID, int cellIndex);

    EdgeInfo getEdgeInfo(int graphID, int nodeIndex, int edgeIndex);
    NodeInfo getStartNode(int graphID);

    NodeInfo getEndNode(int graphID);


    //    --------------------------------------------------------
    //                      SIMULATION HANDLING
    //    --------------------------------------------------------
    void startSimulation();
    void pauseSimulation();
    void doSimulationStep();
    void nextSimulationStep();
    void resetSimulation();
    void saveSimulation(SimulationType currentSimulationType, int numSimulation, TimeType timeType);


    //    --------------------------------------------------------
    //                  SIMULATION DATA HANDLING
    //    --------------------------------------------------------
    double getPathCost(int id);

    int getNumStep(int id);

    double getTime(int id);

    String getName(int id);

    //    --------------------------------------------------------
    //                      WINDOW HANDLING
    //    --------------------------------------------------------
    void openMenu();
    void openApp();
    void openSimulation();
    void setUpScreen();

    int getNumGraph();
}
