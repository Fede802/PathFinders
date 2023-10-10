

import java.util.ArrayList;

public class Node {
    private boolean visited;
    private final double x,y;
    private Node parent;
    private final ArrayList<Edge> edges = new ArrayList<>();
    private double cost = Double.MAX_VALUE, heuristicCost = Double.MAX_VALUE;
    public Node(double x, double y){
        this.x = x;
        this.y = y;
    }

    public EdgeInfo addEdge(Node n, double weight){
        Edge temp = new Edge(n,weight);
        edges.add(temp);
        return temp.getEdgeInfo();
    }

    //default weight is 1 to count path length in unweighted graphs
    public EdgeInfo addEdge(Node n){
        return addEdge(n,1);
    }

    public void addEdge(Node n, EdgeInfo ei){
        edges.add(new Edge(n, ei));
    }
    public void reset(){
        parent = null;
        cost = Double.MAX_VALUE;
        heuristicCost = Double.MAX_VALUE;
        visited = false;
        for (Edge e:
                edges) {
            e.reset();
        }
    }

    public int getNumEdges() {
        return edges.size();
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(double heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public double getCompleteCost(){
        return cost+heuristicCost;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
