package view;

import utils.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationPanel extends JPanel implements ActionListener{

    private final Color BG_COLOR= new Color(49, 51, 53,255);
    private final SimulationScreen SIMULATION_SCREEN = new SimulationScreen();

    private int skipStep = 10, currentSkipStep;


    public SimulationPanel(){
        Dimension d = new Dimension(Config.SCREEN_WIDTH,Config.SCREEN_HEIGHT);
        setSize(d);
        setPreferredSize(d);
        setLayout(null);

        SIMULATION_SCREEN.setLocation(Config.SCREEN_WIDTH/4,0);
        add(SIMULATION_SCREEN);

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentSkipStep < skipStep) {
            currentSkipStep++;
        } else {
            currentSkipStep = 0;
            SIMULATION_SCREEN.actionPerformed(e);
        }

    }


}
