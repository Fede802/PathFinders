package logic;

import commons.CellInfo;

import java.util.ArrayList;

public class Cell extends Node{

    public Cell(double x, double y) {
        super(x, y, new CellInfo(x,y));
    }

    @Override
    public CellInfo getNodeInfo() {
        return (CellInfo) super.getNodeInfo();
    }

    public void clearUnvisitedEdges() {
        ArrayList<Edge> edges = getEdges();
        for (int i = edges.size()-1; i >= 0 ; i--) {
            if(!edges.get(i).getEdgeInfo().isVisited())
                edges.remove(i);
        }
    }
}
