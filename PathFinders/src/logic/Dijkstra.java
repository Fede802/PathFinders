package logic;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra extends PathFinder{

    private PriorityQueue<Node> nodesHeap;
    private Node current;

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
//                        only with add (doesn't work)
                        nodesHeap.remove(destination);
                        nodesHeap.offer(destination);
                    }
                }
            }
        return false;
    }

    @Override
    void desSimulation() {
        if(!nodesHeap.isEmpty()){
            if(current != null) {
                current.getNodeInfo().setSelected(false);
            }
            current = nodesHeap.poll();//same as remove

            current.getNodeInfo().setVisited(true);
            current.getNodeInfo().setSelected(true);
            current.getNodeInfo().setCandidate(false);

            if(current == endNode){
                simulate = false;
                System.out.println("Dijkstra end because found");
                setPath();

            }


            //explore connection
            for (Edge e :
                    current.getEdges()) {
                e.getEdgeInfo().setVisited(true);
                Node destination = e.getDestination();

                double currentNodeToDestinationCost = current.getCost()+e.getEdgeInfo().getWeight();
                if (destination.getCost() > currentNodeToDestinationCost) {
                    destination.setCost(currentNodeToDestinationCost);
                    destination.setParent(current);
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
        nodesHeap.add(startNode);

        //setup for rendering
        current = null;
    }
}
