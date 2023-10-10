package view;

import commons.NodeInfo;
import logic.Logic;
import view.tools.StringDrawer;

import java.awt.*;

public class MapPanel extends AppSection{
    private final Font FONT = new Font("arial", Font.PLAIN,12);

    private static int NUM_PANEL, PADDING = 50;
    private int id;
    private String name= "";

    public MapPanel() {
        super();

        id = NUM_PANEL;
        NUM_PANEL++;
    }


    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        Color prevColor = g2d.getColor();

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.WHITE);

        g2d.drawRect(0, 0, getWidth(), getHeight());
        if(id < Logic.getInstance().getNumGraph()) {
            int numCells = Logic.getInstance().getNumNode();

            int numRows = Logic.getInstance().getNumRows(); //y
            int numColumns = Logic.getInstance().getNumColumns(); //x

            int cellWidth = (getWidth() - 2 * PADDING) / numColumns;
            int cellHeight = (getHeight() - 2 * PADDING) / numRows;
            int cellSize = Math.min(cellWidth, cellHeight);
            int topShift = Math.min(numRows, numColumns) == numRows ? (numColumns - numRows) * cellSize / 2 : 0;
            int leftShift = Math.min(numRows, numColumns) == numColumns ? (numRows - numColumns) * cellSize / 2 : 0;
            g2d.setColor(Color.BLACK);
            g2d.fillRect(PADDING + leftShift, PADDING + topShift, cellSize * numColumns, cellSize * numRows);

            NodeInfo startCell = Logic.getInstance().getStartNode(id);
            NodeInfo endCell = Logic.getInstance().getEndNode(id);
            for (int i = 0; i < numCells; i++) {

                g2d.setColor(Color.WHITE);
                NodeInfo info = Logic.getInstance().getNodeInfo(id, i);
                if (info == startCell)
                    g2d.setColor(Color.RED);
                else if (info.isVisited())
                    g2d.setColor(Color.ORANGE);
                if (info.isCandidate())
                    g2d.setColor(Color.BLUE);
                if (info.isPath())
                    g2d.setColor(Color.CYAN);
                if (info == endCell)
                    g2d.setColor(Color.YELLOW);
                if (info.isSelected()) {

                    g2d.setColor(Color.GREEN);
                }

                g2d.fillRect((int) (PADDING + leftShift + info.getY() * cellSize), (int) (PADDING + topShift + info.getX() * cellSize), cellSize, cellSize);

                g2d.setColor(Color.BLACK);

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
