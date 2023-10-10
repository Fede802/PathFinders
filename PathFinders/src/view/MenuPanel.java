package view;

import logic.Logic;
import utils.Config;
import utils.FontLoader;
import view.customComponents.customButtons.SimpleButton;
import view.tools.StringDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    private final Font TITLE_FONT = new Font(FontLoader.TITLE_FONT, Font.BOLD,50);

    private final String TITLE = "PathFinders";

    private final Color BG_COLOR= new Color(49, 51, 53,255);
    private final Color FILL_TITLE= new Color(195, 200, 217,255);

    private final SimpleButton APP_BUTTON,SIMULATION_BUTTON,EXIT_BUTTON;
    
    private final int BTN_WIDTH = 100, BTN_HEIGHT = 40;
    private final int TOP_PADDING = 75, BTN_SPACING = 20;


    public MenuPanel(){
        Dimension d = new Dimension(Config.SCREEN_WIDTH,Config.SCREEN_HEIGHT);
        setSize(d);
        setPreferredSize(d);
        setLayout(null);
        setBackground(BG_COLOR);

        int startXforCenteringBtn = (Config.SCREEN_WIDTH - BTN_WIDTH)/2;
        int startYforCenteringBtn = (Config.SCREEN_WIDTH - BTN_HEIGHT)/2;
        
        APP_BUTTON = new SimpleButton("App",BTN_WIDTH,BTN_HEIGHT);
        APP_BUTTON.setLocation(startXforCenteringBtn,startYforCenteringBtn-(BTN_HEIGHT+BTN_SPACING));
        add(APP_BUTTON);
        SIMULATION_BUTTON = new SimpleButton("Simulation",BTN_WIDTH,BTN_HEIGHT);
        SIMULATION_BUTTON.setLocation(startXforCenteringBtn,startYforCenteringBtn);
        add(SIMULATION_BUTTON);
        EXIT_BUTTON = new SimpleButton("Exit",BTN_WIDTH,BTN_HEIGHT);
        EXIT_BUTTON.setLocation(startXforCenteringBtn, startYforCenteringBtn+(BTN_HEIGHT+BTN_SPACING));
        add(EXIT_BUTTON);
        
        setupButtonListener();
        
        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        StringDrawer.drawOutlinedString(g2d, TITLE, TITLE_FONT, Color.BLACK, 4, FILL_TITLE, TOP_PADDING, 0, this, StringDrawer.CENTER);
    }

    private void setupButtonListener() {
        APP_BUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                APP_BUTTON.onNormalSetup();//reset hover
                Logic.getInstance().openApp();
            }

        });
        SIMULATION_BUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SIMULATION_BUTTON.onNormalSetup();//reset hover
                Logic.getInstance().openSimulation();
            }

        });
        EXIT_BUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}
