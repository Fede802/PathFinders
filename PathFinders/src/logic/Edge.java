package logic;

import commons.EdgeInfo;

public class Edge {

    //source is used to make easier Lab creation
    private final Node source,destination;

    //edgeInfo is used to share same info between (u,v) and (v,u) edges
    private final EdgeInfo edgeInfo;

    public Edge(Node source, Node destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.edgeInfo = new EdgeInfo(
                source.getNodeInfo().getX(),
                source.getNodeInfo().getY(),
                destination.getNodeInfo().getX(),
                destination.getNodeInfo().getY(),
                weight
        );
    }

    public Edge(Node source, Node destination, EdgeInfo ei) {
        this.source = source;
        this.destination = destination;
        this.edgeInfo = ei;
    }
    public void reset() {
        edgeInfo.reset();
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public EdgeInfo getEdgeInfo() {
        return edgeInfo;
    }

    //debug purpose
    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", destination=" + destination +
                '}';
    }
}
