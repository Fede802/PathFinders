package logic;

import commons.EdgeInfo;
import commons.NodeInfo;

import java.util.ArrayList;

public class Node {

    private NodeInfo nodeinfo;
    private Node parent;
    private final ArrayList<Edge> edges = new ArrayList<>();
    private double cost = Double.MAX_VALUE, heuristicCost = Double.MAX_VALUE;

    public Node(double x, double y){
        nodeinfo = new NodeInfo(x,y);
    }

    public Node(double x, double y, NodeInfo info){
        nodeinfo = info;
    }

    public EdgeInfo addEdge(Node n, double weight){
        Edge temp = new Edge(this,n,weight);
        edges.add(temp);
        nodeinfo.setNumEdge(nodeinfo.getNumEdge()+1);
        return temp.getEdgeInfo();
    }

    //default weight is 1 to count path length in unweighted graphs
    public EdgeInfo addEdge(Node n){
        return addEdge(n,1);
    }

    public void addEdge(Node n, EdgeInfo ei){
        edges.add(new Edge(this,n, ei));
        nodeinfo.setNumEdge(nodeinfo.getNumEdge()+1);
    }

    public void reset(){
        parent = null;
        cost = Double.MAX_VALUE;
        heuristicCost = Double.MAX_VALUE;
        nodeinfo.reset();
        for (Edge e:
                edges) {
            e.reset();
        }
    }

    public NodeInfo getNodeInfo() {
        return nodeinfo;
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

    //debug purpose
    @Override
    public String toString() {

        return "Node{" +
                "nodeX=" + nodeinfo.getX() +
                ", nodeY=" + nodeinfo.getY() +
                ", gcost=" + getCost() +
                '}';
    }

}
