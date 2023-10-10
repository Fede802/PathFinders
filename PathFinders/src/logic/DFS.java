package logic;

import java.util.ArrayList;

public class DFS extends PathFinder{

    private Node current, prev;
    private int currentEdge, edgeNum;

    public DFS(Graph g) {
        super(g);
        name = "DFS";
    }
    public DFS(){
        name = "DFS";
    }

    @Override
    boolean simulation() {
        return dfsRic(startNode);
    }

    private boolean dfsRic(Node currNode) {
        boolean found = false;
        currNode.getNodeInfo().setVisited(true);
        if(currNode == endNode) {
            found = true;
        }else {
            ArrayList<Edge> currEdges = currNode.getEdges();
            for (int i = 0; i < currEdges.size() && !found; i++) {
                numStep++;
                Node destination = currEdges.get(i).getDestination();
                if (!destination.getNodeInfo().isVisited()) {
                    destination.setParent(currNode);
                    found = dfsRic(destination);
                }
            }
        }
        return found;
    }


    @Override
    void desSimulation() {

        boolean hasNext = false;
        while(!hasNext){
            current.getNodeInfo().setVisited(true);
            if(currentEdge < edgeNum) {
                Edge currEdge = current.getEdges().get(currentEdge);
                currEdge.getEdgeInfo().setVisited(true);
                Node destination = currEdge.getDestination();
                if (!destination.getNodeInfo().isVisited()) {
                    hasNext = true;
                    if(prev!=null)
                        prev.getNodeInfo().setSelected(false);
                    destination.getNodeInfo().setSelected(true);
                    prev = destination;
                    destination.getNodeInfo().setVisited(true);
                    destination.setParent(current);
                    if (destination == endNode) {
                        simulate = false;

                        System.out.println("DFS: end because found");
                        setPath();
                    }

                    current = destination;
                    currentEdge = 0;
                    edgeNum = destination.getEdges().size();
                }else{
                    currentEdge++;
                    if(currentEdge == edgeNum) {
                        currentEdge = 0;
                        current = current.getParent();
                        edgeNum = current.getEdges().size();
                    }
                }
            }
        }

    }



    @Override
    public void setupSimulation() {
        super.setupSimulation();

        //setup for rendering
        current = startNode;
        prev = null;
        currentEdge = 0;
        edgeNum = current.getEdges().size();
    }
}
