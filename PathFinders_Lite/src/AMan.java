public class AMan extends A{
    public AMan(Graph g) {
        super(g);
        name = "A*Man";
    }
    public AMan(){name = "A*Man";}
    @Override
    void setHeuristicCost(Node current) {
        double manhattanDistance = Math.abs(endNode.getX()-current.getX())+
                Math.abs(endNode.getY()-current.getY());
        current.setHeuristicCost(manhattanDistance);
    }
    @Override
    void endMessage() {
        System.out.println("AMAN end because found");
    }
}
