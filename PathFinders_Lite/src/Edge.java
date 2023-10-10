public class Edge {

    //source and edge info are used to make easier Lab creation
    private final Node destination;
    private final EdgeInfo edgeInfo;
    public Edge(Node destination, double weight) {
        this.destination = destination;
        this.edgeInfo = new EdgeInfo(
                weight
        );
    }
    public Edge(Node destination, EdgeInfo ei) {
        this.destination = destination;
        this.edgeInfo = ei;
    }
    public void reset() {
        edgeInfo.reset();
    }

    public Node getDestination() {
        return destination;
    }

    public EdgeInfo getEdgeInfo() {
        return edgeInfo;
    }

}
