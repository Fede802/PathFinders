package view;

import commons.AlgoType;
import commons.RenderType;
import view.customComponents.AlgoTypeSelector;
import view.customComponents.AppOption;
import view.customComponents.TypeSelector;
import view.customComponents.customButtons.PushListener;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SetupContent extends JPanel {

    private final TypeSelector renderTypeSelector;
    private RenderType currentRenderType;
    private final Map<RenderType, AppOption> appOptions;
    private final Map<RenderType, AlgoTypeSelector> algoTypeSelector;

    public SetupContent(int numTypeSelectorRows, boolean algoGrouped){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        renderTypeSelector = new TypeSelector(RenderType.values(),numTypeSelectorRows);
        currentRenderType = (RenderType) renderTypeSelector.getCurrentType();
        renderTypeSelector.setItemStateChangeListener(new PushListener() {
            @Override
            public void onPushStateChange(Enum e) {
                appOptions.get(currentRenderType).setVisible(false);
                algoTypeSelector.get(currentRenderType).setVisible(false);
                currentRenderType = (RenderType) e;
                appOptions.get(currentRenderType).setVisible(true);
                algoTypeSelector.get(currentRenderType).setVisible(true);
            }



        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(renderTypeSelector,gbc);

        appOptions = new HashMap<>();
        gbc.gridx = 0;
        gbc.gridy = 1;
        AppOption tempOp = new AppOption.GraphOption();
        appOptions.put(RenderType.GRAPH,tempOp);
        add(tempOp,gbc);
        tempOp = new AppOption.LabOption();
        appOptions.put(RenderType.LAB,tempOp);
        add(tempOp,gbc);
        tempOp = new AppOption.MapOption();
        appOptions.put(RenderType.MAP,tempOp);
        add(tempOp,gbc);
        appOptions.get(currentRenderType).setVisible(true);

        algoTypeSelector = new HashMap<>();
        gbc.gridx = 0;
        gbc.gridy = 2;
        AlgoTypeSelector tempAlgo = new AlgoTypeSelector.GraphAlgoType(algoGrouped);
        algoTypeSelector.put(RenderType.GRAPH,tempAlgo);
        add(tempAlgo,gbc);
        tempAlgo = new AlgoTypeSelector.LabAlgoType(algoGrouped);
        algoTypeSelector.put(RenderType.LAB,tempAlgo);
        add(tempAlgo,gbc);
        tempAlgo = new AlgoTypeSelector.MapAlgoType(algoGrouped);
        algoTypeSelector.put(RenderType.MAP,tempAlgo);
        add(tempAlgo,gbc);
        algoTypeSelector.get((RenderType) renderTypeSelector.getCurrentType()).setVisible(true);

        setOpaque(false);
    }

    public RenderType getCurrentRenderType() {
        return currentRenderType;
    }

    public boolean checkText(){
        return appOptions.get(currentRenderType).checkText();
    }

    public void clearText(){
        appOptions.get(currentRenderType).clearText();
    }

    public int[] getOptions(){
        return appOptions.get(currentRenderType).getOptions();
    }

    public AlgoType[] getSelectedAlgo(){
        return algoTypeSelector.get(currentRenderType).getSelectedAlgo();
    }
}
