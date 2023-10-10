package commons;

public class CellInfo extends NodeInfo{

    public enum Connection{
        RIGHT,LEFT,TOP,BOTTOM
    }

    private boolean connectedRight = true, connectedLeft = true, connectedTop = true, connectedBottom = true;

    public CellInfo(double x, double y) {
        super(x, y);
    }

    public void disconnect(Connection dir){
        switch (dir){
            case RIGHT -> {connectedRight = false;}
            case LEFT -> {connectedLeft = false;}
            case TOP -> {connectedTop = false;}
            case BOTTOM -> {connectedBottom = false;}
        }

    }

    public boolean isConnectedRight() {
        return connectedRight;
    }

    public boolean isConnectedLeft() {
        return connectedLeft;
    }

    public boolean isConnectedTop() {
        return connectedTop;
    }

    public boolean isConnectedBottom() {
        return connectedBottom;
    }

}
