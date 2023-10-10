package logic;

import java.util.ArrayList;

public abstract class PathFinder {

    //pathfinder config
    protected Graph g;
    protected Node startNode, endNode;
    protected boolean simulate = false;


    //simulation info
    protected String name;
    protected int numStep = 0;
    protected double pathCost = -1;
    protected long simDurationNanoSec = -1;


    public PathFinder() {}
    public PathFinder(Graph g) {
        setup(g);
    }
    public void setup(Graph g){
        this.g = g;
        setupSimulation();
    };




    public boolean simulate(){
        long t0, t1;
        setupSimulation();
        numStep = 0;
        simDurationNanoSec = -1;
        t0=System.nanoTime();
        boolean result = simulation();
        t1=System.nanoTime();
        simDurationNanoSec = t1-t0;

        setPath();
        return result;
    }

    public void nextStep(){
        if(simulate)
            desSimulation();
    }

    public void setupSimulation(){
        g.reset();
        this.startNode = g.getNode(Graph.startNode);
        this.endNode = g.getNode(Graph.endNode);
        pathCost = 0;
        simulate = true;
    }



    protected void setPath() {
        Node currentNode = endNode;
        pathCost = 0;
        findEdgePath(currentNode);

        while (currentNode.getParent() != null) {
            currentNode.getNodeInfo().setPath(true);
            currentNode = currentNode.getParent();

            findEdgePath(currentNode);
        }
    }

    private void findEdgePath(Node currentNode) {
        ArrayList<Edge> temp = currentNode.getEdges();
        for (Edge e:
                temp) {
            if(e.getDestination() == currentNode.getParent()) {
                e.getEdgeInfo().setPath(true);
                pathCost+=e.getEdgeInfo().getWeight();
            }
        }
    }

    public boolean isSimulationEnded() {
        return !simulate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getNumStep() {
        return numStep;
    }
    public double getPathCost() {
        return pathCost;
    }
    public long getSimDurationNanoSec() {
        return simDurationNanoSec;
    }

    abstract boolean simulation();
    abstract void desSimulation();
}
