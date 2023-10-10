package logic;

import commons.SimulationType;
import utils.Config;
import utils.FileUtils;

import java.util.*;

public class Graph {

    protected static int numCopy, numRows, numCols,startNode,endNode; //general setup data
    protected static final Random r = new Random();
    private static int completeness, numNode; //graph setup data
    private static final int dirLimit = 3; //inner graph config data
    private static Map<SimulationType,ArrayList<String>> nodePresets;
    private static Map<SimulationType,ArrayList<String>> edgePresets;

    protected ArrayList<Node> nodes = new ArrayList<>();
    public Graph(){}



    public void addNode(Node n){
        nodes.add(n);
    }
    public Node getNode(int index) {
        return nodes.get(index);
    }
    public int getNumNode(){
        return nodes.size();
    }
    public void addBidirectionalArc(Node n1, Node n2, double weight){
        n2.addEdge(n1,n1.addEdge(n2,weight));
    }
    public void reset(){
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).reset();
        }
    }

    //    --------------------------------------------------------
    //                      GRAPH GENERATION
    //    --------------------------------------------------------
    public static Graph[] generateRandomGraph(){
        return generateRandomGraph(numRows, numCols,completeness, numNode, numCopy);
    }

    public static Graph[] generateRandomGraph(int numRows, int numCols, int completeness, int numNode, int numCopy){
        Graph.numRows = numRows;
        Graph.numCols = numCols;
        Graph.completeness = completeness;
        Graph.numNode = numNode;
        Graph.numCopy = numCopy;

        //init graph list
        Graph[] graphs = new Graph[numCopy];
        for (int i = 0; i < numCopy; i++) {
            graphs[i] = new Graph();
        }

        //position random nodes
        int[][] nodePosition = positionRandomNodes(graphs);

        //generateEdges
        connectNodes(graphs, nodePosition);
        connectGraph(graphs);

        //reset Graphs
        for (int i = 0; i < numCopy; i++) {
            graphs[i].reset();
        }
        setupPoints(graphs[0]);
        return graphs;
    }

    private static int[][] positionRandomNodes(Graph[] graphs){

        boolean[][] grid = new boolean[numRows][numCols];
        int[][] nodePosition = new int[numRows][numCols];
        int currentNumNode = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                nodePosition[i][j] = -1;
            }
        }
        while(currentNumNode < numNode){
            int row = r.nextInt(numRows);
            int column = r.nextInt(numCols);
            if(!grid[row][column]){
                grid[row][column] = true;
                nodePosition[row][column] = currentNumNode;
                currentNumNode++;
                for (int i = 0; i < numCopy; i++) {
                    graphs[i].addNode(new Node(row, column));
                }
            }
        }
        return nodePosition;
    }
    private static void connectNodes(Graph[] graphs, int[][] nodePosition) {

        //adjacent matrix like struct to make easier check already existing connections
        boolean[][] grid = new boolean[numNode][numNode];
        for (int i = 0; i < numNode; i++) {
            grid[i][i] = true;
        }

        for (int i = 0; i < numNode; i++) { //for every node
            int nodeX = (int) graphs[0].getNode(i).getNodeInfo().getX();
            int nodeY = (int) graphs[0].getNode(i).getNodeInfo().getY();
            boolean connectedTop = false;
            boolean connectedBottom = false;
            boolean connectedRight = false;
            boolean connectedLeft = false;
            boolean connectedTopRight = false;
            boolean connectedTopLeft = false;
            boolean connectedBottomRight = false;
            boolean connectedBottomLeft = false;
            //search neighbours inside this distance range proceeding inside out to avoid edge visual overlap
            for (int j = 1; j <= dirLimit; j++) {

                for (int k = nodeX-j; k <= nodeX+j; k++) {
                    if( k>0 && k < numRows){//valid row
                        for (int l = nodeY-j; l <= nodeY+j ; l++) {
                            if (l > 0 && l < numCols) {//valid column
                                int neighPosition = nodePosition[k][l];
                                if (neighPosition != -1) { // neigh exist
                                    //try to connect
                                    boolean connectionAvailable = true;
                                    //on main diag
                                    if (nodeX - k == nodeY - l) {
                                        if (k < nodeX && !connectedTopLeft) {
                                            connectedTopLeft = true;
                                        } else if (k > nodeX && !connectedBottomLeft) {
                                            connectedBottomLeft = true;
                                        } else {
                                            connectionAvailable = false;
                                        }
                                    }
                                    //on anti-main diag
                                    if (k - nodeX == nodeY - l) {
                                        if (k < nodeX && !connectedTopRight) {
                                            connectedTopRight = true;
                                        } else if (k > nodeX && !connectedBottomRight) {
                                            connectedBottomRight = true;
                                        } else {
                                            connectionAvailable = false;
                                        }
                                    }
                                    //same row
                                    if (k == nodeX) {
                                        if (l < nodeY && !connectedLeft) {
                                            connectedLeft = true;
                                        } else if (l > nodeY && !connectedRight) {
                                            connectedRight = true;
                                        } else {
                                            connectionAvailable = false;
                                        }
                                    }
                                    //same col
                                    if (l == nodeY) {
                                        if (k < nodeX && !connectedTop) {
                                            connectedTop = true;
                                        } else if (k > nodeX && !connectedBottom) {
                                            connectedBottom = true;
                                        } else {
                                            connectionAvailable = false;
                                        }
                                    }

                                    if (connectionAvailable) {
                                        if (r.nextInt(100) < completeness) {
                                            connectNodes(graphs, grid, nodePosition[nodeX][nodeY], neighPosition);
                                        }
                                    }


                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void connectNodes(Graph[] graphs, boolean[][] grid, int i, int j) {
        if(!grid[i][j]) {
            grid[i][j] = true;
            grid[j][i] = true;
            double weight = r.nextInt(Config.WEIGHT_BOUND);
            for (int k = 0; k < graphs.length; k++) {
                //todo check this weight adjustment
                graphs[k].addBidirectionalArc(graphs[k].getNode(i), graphs[k].getNode(j), weight+1);
            }
        }
    }


    private static void connectGraph(Graph[] graphs) {
        ArrayList<Node> visitedNodes = new ArrayList<>();
        for (int i = 0; i < graphs[0].getNumNode(); i++) {
            //check unconnected nodes
            if (!graphs[0].getNode(i).getNodeInfo().isVisited()) {
                if (!visitedNodes.isEmpty()) {
                    //connect
                    int nodeIndex = r.nextInt(visitedNodes.size());
                    double weight = r.nextInt(Config.WEIGHT_BOUND);
                    for (int j = 0; j < graphs.length; j++) {
                        graphs[j].addBidirectionalArc(graphs[j].getNode(i),graphs[j].getNode(nodeIndex),weight);
                    }
                }


                //do bfs for this node
                for (int j = 0; j < graphs.length; j++) {
                    graphs[j].getNode(i).getNodeInfo().setVisited(true);
                }
                Node startNode = graphs[0].getNode(i);
                visitedNodes.add(startNode);

                ArrayDeque<Node> bfsQueue = new ArrayDeque<>();
                bfsQueue.add(startNode);
                while (!bfsQueue.isEmpty()) {
                    Node current = bfsQueue.remove();
                    for (Edge e :
                            current.getEdges()) {
                        Node temp = e.getDestination();
                        if (!temp.getNodeInfo().isVisited()) {
                            temp.getNodeInfo().setVisited(true);
                            temp.setParent(current);
                            bfsQueue.add(temp);
                        }
                    }
                }
            }
        }
    }


    protected static void setupPoints(Graph g){
        startNode = r.nextInt(g.getNumNode());
        do {
            endNode = r.nextInt(g.getNumNode());
        }while(startNode == endNode);
    }

    public static Graph[] load(SimulationType simulationType, int numCopy) {
        if(Graph.nodePresets == null){
            Graph.loadPresets();
        }

        //init graph list
        Graph[] graphs = new Graph[numCopy];
        for (int i = 0; i < numCopy; i++) {
            graphs[i] = new Graph();
        }

        ArrayList<String> nodes = nodePresets.get(simulationType);
        for (int i = 0; i < nodes.size(); i++) {
            String[] info = nodes.get(i).split(" ");
            for (int j = 0; j < numCopy; j++) {
                graphs[j].addNode(new Node(Double.parseDouble(info[1]), Double.parseDouble(info[2])));
            }
        }


        ArrayList<String> edges = edgePresets.get(simulationType);
        for (int i = 0; i < edges.size(); i++) {
            String[] info = edges.get(i).split(" ");
            for (int j = 0; j < numCopy; j++) {
                int scale = 1;//for debug
                graphs[j].addBidirectionalArc(graphs[j].getNode(Integer.parseInt(info[1])), graphs[j].getNode(Integer.parseInt(info[2])), Double.parseDouble(info[3])*scale);
            }

        }
        setupPoints(graphs[0]);
        return graphs;
    }

    static void loadPresets(){
        Graph.nodePresets = new HashMap<>();
        Graph.edgePresets = new HashMap<>();

        Graph.nodePresets.put(SimulationType.PRESET1,FileUtils.loadPreset("preset/preset1/node.txt"));
        Graph.edgePresets.put(SimulationType.PRESET1,FileUtils.loadPreset("preset/preset1/edge.txt"));

        Graph.nodePresets.put(SimulationType.PRESET2,FileUtils.loadPreset("preset/preset2/node.txt"));
        Graph.edgePresets.put(SimulationType.PRESET2,FileUtils.loadPreset("preset/preset2/edge.txt"));

        Graph.nodePresets.put(SimulationType.PRESET3,FileUtils.loadPreset("preset/preset3/node.txt"));
        Graph.edgePresets.put(SimulationType.PRESET3,FileUtils.loadPreset("preset/preset3/edge.txt"));

        Graph.nodePresets.put(SimulationType.PRESET4,FileUtils.loadPreset("preset/preset4/node.txt"));
        Graph.edgePresets.put(SimulationType.PRESET4,FileUtils.loadPreset("preset/preset4/edge.txt"));

        Graph.nodePresets.put(SimulationType.PRESET5,FileUtils.loadPreset("preset/preset5/node.txt"));
        Graph.edgePresets.put(SimulationType.PRESET5,FileUtils.loadPreset("preset/preset5/edge.txt"));
    }
}
