package view;

import utils.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {

    private static final String FRAME_TITLE = "PathFinders";
    private ActionListener panelAction;

    private Timer t = new Timer(Config.TIMER_TICK, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(panelAction !=null)
                panelAction.actionPerformed(e);
            repaint();
        }
    });

    private final Container
            MENU = new MenuPanel(),
            APP = new AppPanel(),
            SIMULATION = new SimulationPanel();

    public Frame() {
        super(FRAME_TITLE);
        this.add(MENU);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        t.start();
    }

    public void openMenu(){
        getContentPane().remove(0);
        add(MENU);
        panelAction = null;
        this.revalidate();
        this.repaint();
        pack();
    }
    public void openApp(){
        getContentPane().remove(0);
        add(APP);
        APP.requestFocusInWindow();
        panelAction = (ActionListener) APP;
        this.revalidate();
        this.repaint();
        pack();
    }
    public void openSimulation(){
        getContentPane().remove(0);
        add(SIMULATION);
        panelAction = (ActionListener) SIMULATION;
        this.revalidate();
        this.repaint();
        pack();
    }
    public AppPanel getAppContainer(){
        return (AppPanel) APP;
    }

    public SimulationPanel getSimulationContainer(){
        return (SimulationPanel) SIMULATION;
    }
}
