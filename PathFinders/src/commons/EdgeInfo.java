package commons;

public class EdgeInfo {

    private final double n1x, n1y, n2x, n2y,weight;

    private boolean visited, selected, path;

    public EdgeInfo(double n1x, double n1y, double n2x, double n2y, double weight){
        this.n1x = n1x;
        this.n1y = n1y;
        this.n2x = n2x;
        this.n2y = n2y;
        this.weight = weight;
    }

    public void reset() {
        visited = false;
        path = false;
        selected = false;
    }

    public double getN1x() {
        return n1x;
    }

    public double getN1y() {
        return n1y;
    }

    public double getN2x() {
        return n2x;
    }

    public double getN2y() {
        return n2y;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isPath() {
        return path;
    }

    public void setPath(boolean path) {
        this.path = path;
    }

}
