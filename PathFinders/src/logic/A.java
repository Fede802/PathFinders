package logic;

import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class A extends PathFinder{

    private PriorityQueue<Node> nodesHeap; //openList
    private Node current;

    public A(Graph g) {
        super(g);
    }
    public A(){}

    @Override
    boolean simulation() {
        while(!nodesHeap.isEmpty()){
            Node currNode = nodesHeap.poll(); //same as remove

            if(currNode == endNode) {
                return true;
            }

            //explore connections
            for (Edge e :
                    currNode.getEdges()) {
                Node destination = e.getDestination();
                double currentNodeToDestinationCost = currNode.getCost() + e.getEdgeInfo().getWeight();
                numStep++;
                if (currentNodeToDestinationCost < destination.getCost()) {
                    destination.setCost(currentNodeToDestinationCost);
                    destination.setParent(currNode);
                    setHeuristicCost(destination);
                    //only with add (doesn't work)
                    nodesHeap.remove(destination);
                    nodesHeap.add(destination);
                }
            }
        }
        return false;
    }

    @Override
    void desSimulation() {
        if(!nodesHeap.isEmpty()){
            if(current != null){
                current.getNodeInfo().setSelected(false);
            }
            current = nodesHeap.poll();//same as remove

            current.getNodeInfo().setVisited(true);
            current.getNodeInfo().setSelected(true);
            current.getNodeInfo().setCandidate(false);

            if(current == endNode){
                simulate = false;
                endMessage();
                setPath();
            }

            //explore connection
            for (Edge e :
                    current.getEdges()) {
                e.getEdgeInfo().setVisited(true);
                Node destination = e.getDestination();
                double currentNodeToDestinationCost = current.getCost() + e.getEdgeInfo().getWeight();
                if (currentNodeToDestinationCost < destination.getCost()) {
                    destination.setCost(currentNodeToDestinationCost);
                    destination.setParent(current);
                    setHeuristicCost(destination);
                    destination.getNodeInfo().setCandidate(true);
                    nodesHeap.remove(destination);
                    nodesHeap.add(destination);
                }
            }
        }

    }

    @Override
    public void setupSimulation() {
        super.setupSimulation();
        startNode.setCost(0);//set g_cost
        setHeuristicCost(startNode);//set h_cost

        nodesHeap = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Double.compare(o1.getCompleteCost(), o2.getCompleteCost());
            }
        });
        nodesHeap.add(startNode);

        //setup for rendering
        current = null;

    }

    abstract void setHeuristicCost(Node current);
    abstract void endMessage();

}
