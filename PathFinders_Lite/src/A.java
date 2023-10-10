import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class A extends PathFinder {

    private PriorityQueue<Node> nodesHeap; //openList

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

            //explore connection
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



    abstract void setHeuristicCost(Node current);
    abstract void endMessage();
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

    }
}
