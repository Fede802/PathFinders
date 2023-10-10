import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra extends PathFinder {
    private PriorityQueue<Node> nodesHeap;

    public Dijkstra(Graph g) {
        super(g);
        name = "Dijkstra";
    }
    public Dijkstra(){
        name = "Dijkstra";
    }

    @Override
    boolean simulation() {
        while (!nodesHeap.isEmpty()){
                Node currNode = nodesHeap.poll();
                if(currNode == endNode)
                    return true;
                for (Edge e :
                        currNode.getEdges()) {
                    Node destination = e.getDestination();
                    double currentNodeToDestinationCost = currNode.getCost()+e.getEdgeInfo().getWeight();
                    numStep++;
                    if (destination.getCost() > currentNodeToDestinationCost) {
                        destination.setCost(currentNodeToDestinationCost);
                        destination.setParent(currNode);
                        //only with add (doesn't work)
                        nodesHeap.remove(destination);
                        nodesHeap.offer(destination);
                    }
                }
            }
        return false;
    }

    @Override
    public void setupSimulation() {
        super.setupSimulation();
        startNode.setCost(0);

        nodesHeap = new PriorityQueue<>(g.getNumNode(),new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Double.compare(o1.getCost(), o2.getCost());
            }
        });

        for (int i = 0; i < g.getNumNode(); i++) {
            nodesHeap.add(g.getNode(i));
        }


    }
}
