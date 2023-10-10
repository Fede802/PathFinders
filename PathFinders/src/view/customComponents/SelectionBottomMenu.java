package view.customComponents;

import logic.Logic;
import view.customComponents.customButtons.SimpleButton;
import view.tools.LayoutBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionBottomMenu extends JPanel {

    private final int BTN_WIDTH = 100, BTN_HEIGHT = 40;
    private SimpleButton menuButton, simulationButton;

    public SelectionBottomMenu(){
        menuButton = new SimpleButton("Menu",BTN_WIDTH,BTN_HEIGHT);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuButton.onNormalSetup();//reset hover
                Logic.getInstance().openMenu();
            }
        });
        simulationButton = new SimpleButton("Start",BTN_WIDTH,BTN_HEIGHT);
        JComponent[] comp = new JComponent[]{menuButton,simulationButton};
        LayoutBuilder.buildGridBagLayout(comp,1,this,true);
    }

    public void addActionListener(ActionListener listener){
        simulationButton.addActionListener(listener);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        menuButton.setEnabled(enabled);
        simulationButton.setEnabled(enabled);
    }
}
