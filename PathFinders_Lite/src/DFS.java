import java.util.ArrayList;

public class DFS extends PathFinder {

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
        currNode.setVisited(true);
        if(currNode == endNode) {
            found = true;
        }else {
            ArrayList<Edge> currEdges = currNode.getEdges();
            for (int i = 0; i < currEdges.size() && !found; i++) {
                numStep++;
                Node destination = currEdges.get(i).getDestination();
                if (!destination.isVisited()) {
                    destination.setParent(currNode);
                    found = dfsRic(destination);
                }
            }
        }
        return found;
    }

    @Override
    public void setupSimulation() {
        super.setupSimulation();
    }
}
