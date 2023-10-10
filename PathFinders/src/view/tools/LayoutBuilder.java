package view.tools;

import javax.swing.*;
import java.awt.*;

public class LayoutBuilder {

    private static final int SIDE_SPACING = 0;
    private static final int VERTICAL_SPACING = 5;

    public static GridBagConstraints buildGridBagLayout(Component[] c, int numRows, JPanel container, boolean hFill){
        return buildGridBagLayout(c,numRows,container,hFill,0);
    }
    public static GridBagConstraints buildGridBagLayout(Component[] c, int numRows, JPanel container, boolean hFill, int sideSpacingAdjustment){
        //todo work with only 2 rows
        int numRowElement = (int) Math.ceil(c.length/(double)numRows);

        container.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(VERTICAL_SPACING,0,VERTICAL_SPACING,0);
        int currRow = 0;
        Row temp = null;
        for (int i = 0; i < c.length; i++) {


            if(i%(numRowElement) == 0){
                temp = new Row(hFill,sideSpacingAdjustment);
                gbc.gridy = currRow;
                currRow++;
                container.add(temp,gbc);
            }
            temp.add(c[i]);
        }
        container.setOpaque(false);
        return gbc;
    }

    private static class Row extends JPanel{

        GridBagConstraints gbc = new GridBagConstraints();
        public Row(boolean hFill, int sideSpacingAdjustment){
            setLayout(new GridBagLayout());
            gbc.weightx = 0.5;
            gbc.insets = new Insets(0,SIDE_SPACING+sideSpacingAdjustment,0,SIDE_SPACING+sideSpacingAdjustment);
            if(hFill)
                gbc.fill = GridBagConstraints.HORIZONTAL;
            setOpaque(false);
        }

        @Override
        public Component add(Component comp) {
            super.add(comp, gbc);
            return comp;
        }
    }
}
