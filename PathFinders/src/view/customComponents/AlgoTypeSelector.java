package view.customComponents;

import commons.AlgoType;
import utils.Config;
import view.customComponents.customButtons.AlgoButton;
import view.tools.LayoutBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AlgoTypeSelector extends JPanel{


    private final ArrayList<AlgoButton> algoButtons = new ArrayList<>();
    private final JCheckBox displayAll = new JCheckBox("All");

    private AlgoTypeSelector(AlgoType[] algoTypes, boolean grouped){
        for (int i = 0; i < algoTypes.length; i++) {
            algoButtons.add(new AlgoButton(algoTypes[i]));
        }
        if(grouped) {
            ButtonGroup group = new ButtonGroup();
            for (AlgoButton ab :
                    algoButtons) {
                group.add(ab);
            }
        }
        algoButtons.get(0).setSelected(true);
        displayAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean enable = !displayAll.isSelected();
                for (AlgoButton ab:
                        algoButtons) {
                    ab.setEnabled(enable);
                }
            }
        });
        algoButtons.get(0).setHorizontalAlignment(JLabel.RIGHT);
        algoButtons.get(2).setHorizontalAlignment(JLabel.RIGHT);
        if(algoButtons.size()==3){
            algoButtons.get(2).setHorizontalAlignment(JLabel.CENTER);
        }
        GridBagConstraints gbc = LayoutBuilder.buildGridBagLayout((algoButtons.toArray(new AlgoButton[]{})),2,this,true);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;

        displayAll.setForeground(Color.BLACK);
        displayAll.setOpaque(false);
        displayAll.setHorizontalAlignment(JCheckBox.CENTER);
        add(displayAll,gbc);

        setVisible(false);
    }

    public boolean hasSelection(){
        boolean hasSelection = false;
        if(displayAll.isSelected())
            return true;
        else{
            for (AlgoButton ab:
                    algoButtons) {
                if(ab.isSelected())
                    hasSelection = true;
            }
        }
        return hasSelection;
    }
    public AlgoType[] getSelectedAlgo(){
        ArrayList<AlgoType> algoTypes = new ArrayList<>();
        if(displayAll.isSelected()) {

            for (AlgoButton ab :
                    algoButtons) {
                algoTypes.add(ab.getAlgoType());
            }

        }else{
            for (AlgoButton ab:
                    algoButtons) {
                if(ab.isSelected())
                    algoTypes.add(ab.getAlgoType());
            }
        }
        return algoTypes.toArray(new AlgoType[]{});
    }
    public static class GraphAlgoType extends AlgoTypeSelector{
        public GraphAlgoType(boolean grouped){
            super(new AlgoType[]{AlgoType.AE,AlgoType.AMAN,AlgoType.DFS,AlgoType.DIJKSTRA},grouped);
        }
    }

    public static class LabAlgoType extends AlgoTypeSelector{
        public LabAlgoType(boolean grouped){
            super(new AlgoType[]{AlgoType.AE,AlgoType.AMAN,AlgoType.DFS,Config.useBFS?AlgoType.BFS:AlgoType.DIJKSTRA},grouped);
        }
    }

    public static class MapAlgoType extends AlgoTypeSelector{
        public MapAlgoType(boolean grouped){
            super(new AlgoType[]{AlgoType.AE,AlgoType.AMAN,AlgoType.DFS, Config.useBFS?AlgoType.BFS:AlgoType.DIJKSTRA},grouped);
        }
    }
    public static class PreSetAlgoType extends AlgoTypeSelector{
        public PreSetAlgoType(){
            super(new AlgoType[]{AlgoType.AE,AlgoType.AMAN,AlgoType.DIJKSTRA}, false);
        }
    }


}
