package commons;

public class NodeInfo {

    private final double x,y;

    private boolean visited, selected, candidate, path;

    private int numEdge;

    public NodeInfo(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void reset() {
        visited = false;
        path = false;
        candidate = false;
        selected = false;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isCandidate() {
        return candidate;
    }

    public void setCandidate(boolean candidate) {
        this.candidate = candidate;
    }

    public boolean isPath() {
        return path;
    }

    public void setPath(boolean path) {
        this.path = path;
    }

    public int getNumEdge() {
        return numEdge;
    }

    public void setNumEdge(int numEdge) {
        this.numEdge = numEdge;
    }

}
