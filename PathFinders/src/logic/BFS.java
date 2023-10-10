package logic;

import java.util.ArrayDeque;
import java.util.Queue;

public class BFS extends PathFinder{

    private Queue<Node> bfsQueue;
    private Node current, prev;
    private Edge prevEdge;
    private int currentEdge, edgeNum;

    public BFS(Graph g) {
        super(g);
        name = "BFS";
    }

    public BFS() {
        name = "BFS";
    }

    @Override
    boolean simulation() {
        while (!bfsQueue.isEmpty()) {
            Node current = bfsQueue.poll();
            for (Edge e :
                    current.getEdges()) {
                Node temp = e.getDestination();
                numStep++;
                if (!temp.getNodeInfo().isVisited()) {
                    temp.getNodeInfo().setVisited(true);
                    temp.setParent(current);
                    bfsQueue.add(temp);
                }
                if (temp == endNode)
                    return true;
            }
        }
        return false;
    }

    @Override
    void desSimulation() {
        boolean foundValidDestination = false;
        while(!foundValidDestination && (currentEdge < edgeNum || !bfsQueue.isEmpty())){
            if(current == null) {
                current = bfsQueue.poll();
                current.getNodeInfo().setCandidate(false);
                edgeNum = current.getEdges().size();
                currentEdge = 0;
            }

            Edge e = current.getEdges().get(currentEdge);
            e.getEdgeInfo().setVisited(true);
            if(prevEdge!=null)
                prevEdge.getEdgeInfo().setSelected(false);
            e.getEdgeInfo().setSelected(true);
            prevEdge = e;
            Node temp = e.getDestination();

            if (!temp.getNodeInfo().isVisited()) {
                temp.getNodeInfo().setVisited(true);
                temp.getNodeInfo().setCandidate(true);
                if(prev!=null)
                    prev.getNodeInfo().setSelected(false);
                temp.getNodeInfo().setSelected(true);
                prev = temp;
                foundValidDestination = true;
                temp.setParent(current);
                bfsQueue.add(temp);
            }

            if(temp == endNode){
                System.out.println("BFS: end because found");
                simulate = false;
                setPath();
            }

            currentEdge++;
            if(currentEdge == edgeNum)
                current = null;
        }
        if(!foundValidDestination){
            System.out.println("BFS: end because no path");
            simulate = false;
        }
    }

    @Override
    public void setupSimulation() {
        super.setupSimulation();

        startNode.getNodeInfo().setVisited(true);

        bfsQueue = new ArrayDeque<>();
        bfsQueue.add(startNode);


        //setup for rendering
        current = null;
        prev = null;
        prevEdge = null;
        currentEdge = 0;
        edgeNum = 0;
    }
}
