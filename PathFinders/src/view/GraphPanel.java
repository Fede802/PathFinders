package view;

import commons.EdgeInfo;
import commons.NodeInfo;
import logic.Logic;
import utils.Config;
import view.tools.StringDrawer;

import java.awt.*;

public class GraphPanel extends AppSection {

    private final Font FONT = new Font("arial", Font.PLAIN,12);
    private static int NUM_PANEL, PADDING = 50;
    private int id;
    private String name = "";

    private final Stroke[] strokes = new Stroke[Config.WEIGHT_BOUND+1];

    public GraphPanel(){
        super();
        id = NUM_PANEL;
        NUM_PANEL++;

        for (int i = 0; i < strokes.length; i++) {
            strokes[i] = new BasicStroke(i+1);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Color prevColor = g2d.getColor();

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.WHITE);
        g2d.drawRect(0,0,getWidth(),getHeight());

        if(id < Logic.getInstance().getNumGraph()) {

            int numNode = Logic.getInstance().getNumNode();

            int numRows = Logic.getInstance().getNumRows(); //y
            int numColumns = Logic.getInstance().getNumColumns(); //x

            int nodeWidth = (getWidth() - 2 * PADDING) / numColumns;
            int nodeHeight = (getHeight() - 2 * PADDING) / numRows;
            int nodeSize = Math.min(nodeWidth, nodeHeight);
            int topShift = Math.min(numRows, numColumns) == numRows ? (numColumns - numRows) * nodeSize / 2 : 0;
            int leftShift = Math.min(numRows, numColumns) == numColumns ? (numRows - numColumns) * nodeSize / 2 : 0;
            int padding = nodeSize/8 ;
            NodeInfo startCell = Logic.getInstance().getStartNode(id);
            NodeInfo endCell = Logic.getInstance().getEndNode(id);

            for (int i = 0; i < numNode; i++) {

                NodeInfo info = Logic.getInstance().getNodeInfo(id, i);
                int numEdge = info.getNumEdge();
                Stroke prevStroke = g2d.getStroke();
                for (int j = 0; j < numEdge; j++) {
                    EdgeInfo ei = Logic.getInstance().getEdgeInfo(id, i, j);
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(strokes[(int) ei.getWeight()]);
                    if (ei.isVisited()) {
                        g2d.setColor(Color.ORANGE);
                    }
                    if (ei.isPath())
                        g2d.setColor(Color.CYAN);
                    if (ei.isSelected()) {
                        g2d.setColor(Color.GREEN);
                    }
                    g2d.drawLine((int) (PADDING + padding + leftShift + ei.getN1y() * nodeSize + nodeSize / 2), (int) (PADDING + padding + topShift + ei.getN1x() * nodeSize +nodeSize / 2),(int) (PADDING + padding + leftShift + ei.getN2y() * nodeSize + nodeSize / 2), (int) (PADDING + padding + topShift + ei.getN2x() * nodeSize +nodeSize / 2));
                }
                g2d.setStroke(prevStroke);


            }
            for (int i = 0; i < numNode; i++) {
                NodeInfo info = Logic.getInstance().getNodeInfo(id, i);
                g2d.setColor(Color.WHITE);

                if (info == startCell)
                    g2d.setColor(Color.RED);
                else if (info.isVisited())
                    g2d.setColor(Color.ORANGE);
                if (info.isCandidate())
                    g2d.setColor(Color.BLUE);
                if (info.isPath()) {
                    g2d.setColor(Color.CYAN);
                }
                if (info == endCell)
                    g2d.setColor(Color.YELLOW);
                if (info.isSelected()) {
                    g2d.setColor(Color.GREEN);
                }
                g2d.fillOval((int) (PADDING + padding + leftShift + info.getY() * nodeSize ), (int) (PADDING + padding + topShift + info.getX() * nodeSize), nodeSize, nodeSize);


            }

            if (!name.equals(""))
                StringDrawer.drawString(g2d, name, FONT, Color.WHITE, 20, getWidth() - StringDrawer.getStringWidth(g2d, name, FONT) - 20);
            if (!step.equals(""))
                StringDrawer.drawString(g2d, "step: " + step, FONT, Color.WHITE, getHeight() - StringDrawer.getStringHeight(g2d, FONT) - 20, 20);
            if (!cost.equals(""))
                StringDrawer.drawCenteredString(g2d, "cost: " + cost, FONT, Color.WHITE, getHeight() - StringDrawer.getStringHeight(g2d, FONT) - 20, this);
            if (!time.equals(""))
                StringDrawer.drawString(g2d, "time: " + time, FONT, Color.WHITE, getHeight() - StringDrawer.getStringHeight(g2d, FONT) - 20, getWidth() - StringDrawer.getStringWidth(g2d, "time: " + time, FONT) - 20);
        }
        g2d.setColor(prevColor);


    }

    @Override
    public void setInfo() {
        if(id < Logic.getInstance().getNumGraph()) {
            cost = String.valueOf(Logic.getInstance().getPathCost(id));
            step = String.valueOf(Logic.getInstance().getNumStep(id));
            time = String.valueOf(Logic.getInstance().getTime(id));
        }
    }

    @Override
    public void setName() {
        if(id < Logic.getInstance().getNumGraph()) {
            name = Logic.getInstance().getName(id);
        }
    }
}

