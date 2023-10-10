package view;

import commons.ShowState;
import utils.Config;

import javax.swing.*;
import java.awt.*;

public class AppScreen extends JPanel{

    private final Color BG_COLOR= new Color(49, 51, 53,255);

    private static final Dimension
            FULL =  new Dimension(Config.SCREEN_WIDTH, (Config.SCREEN_HEIGHT-Toolbar.BAR_HEIGHT)),
            QUARTER = new Dimension(Config.SCREEN_WIDTH/2, (Config.SCREEN_HEIGHT-Toolbar.BAR_HEIGHT)/2);
    private final AppSection[] screenSection;

    public AppScreen(AppSection section1, AppSection section2, AppSection section3, AppSection section4){
        this.setSize(FULL);
        setLayout(null);
        this.screenSection = new AppSection[]{section1, section2, section3, section4};

        section2.setLocation(Config.SCREEN_WIDTH/2,0);
        section3.setLocation(0,(Config.SCREEN_HEIGHT-Toolbar.BAR_HEIGHT)/2);
        section4.setLocation(Config.SCREEN_WIDTH/2,(Config.SCREEN_HEIGHT-Toolbar.BAR_HEIGHT)/2);

        for (int i = 0; i < screenSection.length; i++) {
            screenSection[i].setSize(QUARTER);
            add(screenSection[i]);
        }
        setVisible(false);
        setFocusable(true);
        requestFocusInWindow();
    }

    public void setupScreen(ShowState state){
        switch (state){
            case ALL -> showAll();
            case SINGLE -> showFirst();
        }
        setVisible(true);
    }

    private void showFirst() {
        screenSection[0].setSize(FULL);
        screenSection[0].setVisible(true);
        for (int i = 1; i < screenSection.length; i++) {
            screenSection[i].setVisible(false);
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());

    }
    private void showAll() {
        screenSection[0].setSize(QUARTER);
        for (int i = 0; i < screenSection.length; i++) {
            screenSection[i].setVisible(true);
        }
    }

    public void clearInfo() {
        for (AppSection as:
             screenSection) {
            as.clearInfo();
        }
    }

    public void setInfo() {
        for (AppSection as:
                screenSection) {
            if(as.isVisible()) {
                as.setInfo();
            }
        }
    }

    public void setName() {
        for (AppSection as:
                screenSection) {
            if(as.isVisible())
                as.setName();
        }
    }

    public static class GraphScreen extends AppScreen{
        public GraphScreen() {
            super(new GraphPanel(),new GraphPanel(),new GraphPanel(),new GraphPanel());
        }

    }

    public static class LabScreen extends AppScreen{
        public LabScreen() {
            super(new LabPanel(),new LabPanel(),new LabPanel(),new LabPanel());
        }

    }

    public static class MapScreen extends AppScreen{
        public MapScreen() {
            super(new MapPanel(),new MapPanel(),new MapPanel(),new MapPanel());
        }

    }
}
