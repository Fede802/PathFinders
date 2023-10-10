package logic;

public class AE extends A{

    public AE(Graph g) {
        super(g);
        name = "A*E";
    }
    public AE(){name = "A*E";}

    @Override
    void setHeuristicCost(Node current) {
        double euclideanDistance = Math.sqrt(
                Math.pow(endNode.getNodeInfo().getX()-current.getNodeInfo().getX(),2)+
                        Math.pow(endNode.getNodeInfo().getY()-current.getNodeInfo().getY(),2));
        current.setHeuristicCost(euclideanDistance);
    }

    @Override
    void endMessage() {
        System.out.println("AE end because found");
    }
}
