package view.customComponents.customButtons;

import commons.AlgoType;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AlgoButton extends JRadioButton {

    public static final Map<AlgoType,String> algoList = Map.of(
            AlgoType.AE,"A*E",
            AlgoType.AMAN,"A*MAN",
            AlgoType.DFS,"DFS",
            AlgoType.DIJKSTRA,"Dijkstra",
            AlgoType.BFS,"BFS"
    );
    private final AlgoType algoType;

    public AlgoButton(AlgoType algoType){
        super(algoList.get(algoType));
        this.algoType = algoType;
        this.setOpaque(false);
        this.setForeground(Color.BLACK);
    }

    public AlgoType getAlgoType() {
        return algoType;
    }
}
