package view;

import commons.RenderType;
import commons.ShowState;
import logic.Logic;
import utils.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class AppPanel extends JLayeredPane implements ActionListener {

    private final Color BG_COLOR= new Color(49, 51, 53,255);

    private final SetupPanel SETUP_PANEL = new SetupPanel();
    private final Toolbar TOOLBAR = new Toolbar();
    private final Map<RenderType, AppScreen> screenMap = new HashMap<>();
    private RenderType currRenderType;

    //sim handling
    private boolean simulate;

    public AppPanel(){
        Dimension d = new Dimension(Config.SCREEN_WIDTH,Config.SCREEN_HEIGHT);
        setSize(d);
        setPreferredSize(d);
        setLayout(null);

        AppScreen temp = new AppScreen.GraphScreen();
        screenMap.put(RenderType.GRAPH,temp);
        add(temp);
        setLayer(temp,DEFAULT_LAYER);
        temp = new AppScreen.LabScreen();
        screenMap.put(RenderType.LAB,temp);
        add(temp);
        setLayer(temp,DEFAULT_LAYER);
        temp = new AppScreen.MapScreen();
        screenMap.put(RenderType.MAP,temp);
        add(temp);
        setLayer(temp,DEFAULT_LAYER);

        TOOLBAR.setLocation(0,Config.SCREEN_HEIGHT-Toolbar.BAR_HEIGHT);
        add(TOOLBAR);
        setLayer(TOOLBAR,PALETTE_LAYER);

        add(SETUP_PANEL);
        setLayer(SETUP_PANEL,MODAL_LAYER);

        setFocusable(true);

    }

    public void setUpScreen(RenderType currRenderType, ShowState showState) {

        if (this.currRenderType != null) {
            screenMap.get(this.currRenderType).clearInfo();
            screenMap.get(this.currRenderType).setVisible(false);
        }
        this.currRenderType = currRenderType;
        if (currRenderType != null){
            screenMap.get(currRenderType).setupScreen(showState);
            screenMap.get(currRenderType).setName();
        }else{
            if(showState == null)
                SETUP_PANEL.setup();
        }
    }

    public void setSimulation(boolean simulate) {
        this.simulate = simulate;
        if(currRenderType != null)
            screenMap.get(currRenderType).clearInfo();
        if(!simulate)
            TOOLBAR.stopSetup();
    }

    public void simulationEnded() {
        screenMap.get(currRenderType).setInfo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SETUP_PANEL.updateUI();
        if(simulate) {
            if (currRenderType != null)
                Logic.getInstance().nextSimulationStep();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());

    }

}
