package view;

import commons.SimulationType;
import logic.Logic;
import utils.Config;
import view.customComponents.*;
import view.customComponents.customButtons.PushListener;
import view.tools.StringDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationScreen extends JPanel implements ActionListener{

    private final Font FONT = new Font("arial", Font.BOLD,50);
    private final TypeSelector simulationTypeSelector;
    private final SetupContent setupContent;
    private final AlgoTypeSelector algoTypeSelector;
    private final TimeOptions timeOptions;

    private final Option numSimulation;

    private final SelectionBottomMenu selectionButtons;

    private SimulationType currentSimulationType;

    private boolean simulating;
    private String simulatingText = "";
    int currentStep = 3;


    public SimulationScreen(){
        setSize(new Dimension(Config.SCREEN_WIDTH/2, Config.SCREEN_HEIGHT));


        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        simulationTypeSelector = new TypeSelector(SimulationType.values(),2);
        currentSimulationType = (SimulationType) simulationTypeSelector.getCurrentType();
        simulationTypeSelector.setItemStateChangeListener(new PushListener() {
            @Override
            public void onPushStateChange(Enum e) {
                currentSimulationType = (SimulationType) e;
                if (currentSimulationType == SimulationType.MAKESET) {
                    setupContent.setVisible(true);
                    algoTypeSelector.setVisible(false);
                } else {
                    setupContent.setVisible(false);
                    algoTypeSelector.setVisible(true);
                }
            }

        });

        gbc.gridx = 0;
        gbc.gridy = 0;

        add(simulationTypeSelector,gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        setupContent = new SetupContent(1,false);
        if(currentSimulationType != SimulationType.MAKESET)
            setupContent.setVisible(false);
        add(setupContent,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        algoTypeSelector = new AlgoTypeSelector.PreSetAlgoType();
        if(currentSimulationType != SimulationType.MAKESET)
            algoTypeSelector.setVisible(true);
        add(algoTypeSelector,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        numSimulation = new Option("numSim","2-100");

        add(numSimulation,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        timeOptions = new TimeOptions();
        add(timeOptions,gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        selectionButtons = new SelectionBottomMenu();

        selectionButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    boolean dataAccepted = true;
                    if(currentSimulationType == SimulationType.MAKESET){
                        if(setupContent.checkText()) {
                            dataAccepted = Logic.getInstance().generateData(
                                    setupContent.getCurrentRenderType(),
                                    setupContent.getOptions(),
                                    setupContent.getSelectedAlgo()
                            );
                            if (!dataAccepted) {
                                setupContent.clearText();
                            }
                        }
                    }else{
                        Logic.getInstance().generatePreset(currentSimulationType,algoTypeSelector.getSelectedAlgo());
                    }
                    if(dataAccepted){
                        try {
                            int numSim = Integer.parseInt(numSimulation.getText());
                            simulating = true;
                            SwingWorker<Void, Void> WORKER = new SwingWorker<>() {
                                @Override
                                protected Void doInBackground() {
                                    Logic.getInstance().saveSimulation(currentSimulationType,numSim,timeOptions.getCurremtTimeType());
                                    return null;
                                }
                                @Override
                                public void done() {
                                    currentStep = 3;
                                    simulatingText = "";
                                    simulating = false;
                                    selectionButtons.setEnabled(true);
                                }

                            };
                            selectionButtons.setEnabled(false);
                            WORKER.execute();

                        }catch (NumberFormatException nfe){
                            numSimulation.clear();
                        }

                    }

                }

        });

        add(selectionButtons,gbc);

        setOpaque(false);
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(simulating) {
            if(currentStep < 3) {
                simulatingText += ".";
                currentStep++;
            }
            else {
                simulatingText = "Simulating";
                currentStep = 0;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if(!simulatingText.equals("")){
            StringDrawer.drawCenteredString(g2d,simulatingText,FONT,Color.BLACK,(getHeight()-StringDrawer.getStringHeight(g2d,FONT))/2,this);
        }

    }



}
