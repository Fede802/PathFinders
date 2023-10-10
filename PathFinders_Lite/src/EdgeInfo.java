public class EdgeInfo {

    private final double weight;
    private boolean visited;

    public EdgeInfo(double weight){
        this.weight = weight;
    }

    public void reset() {
        visited = false;
    }
    public double getWeight() {
        return weight;
    }
    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

}
