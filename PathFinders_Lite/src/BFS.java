import java.util.ArrayDeque;
import java.util.Queue;

public class BFS extends PathFinder {
    private Queue<Node> bfsQueue;


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
                if (!temp.isVisited()) {
                    temp.setVisited(true);
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
    public void setupSimulation() {
        super.setupSimulation();
        startNode.setVisited(true);
        bfsQueue = new ArrayDeque<>();
        bfsQueue.add(startNode);

    }
}
