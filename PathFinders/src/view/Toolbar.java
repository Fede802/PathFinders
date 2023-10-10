package view;

import logic.Logic;
import utils.Config;
import view.customComponents.customButtons.ImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel {

    public static final int BAR_HEIGHT = 50,BUTTON_SIZE = 40, BUTTON_PADDING = 20;

    private ImageButton playBtn, pauseBtn, stepBtn, resetBtn, skipBtn, mainMenuBtn;;
    private final Color BG_COLOR= new Color(29, 29, 29, 255);

    public Toolbar() {
        setSize(new Dimension(Config.SCREEN_WIDTH, BAR_HEIGHT));
        setBackground(BG_COLOR);

        playBtn = new ImageButton("res/play2.png", "res/playHover2.png", BUTTON_SIZE);
        pauseBtn = new ImageButton("res/pause2.png", "res/pauseHover2.png", BUTTON_SIZE);
        stepBtn = new ImageButton("res/step2.png", "res/stepHover2.png", BUTTON_SIZE);
        resetBtn = new ImageButton("res/reset2.png", "res/resetHover2.png", BUTTON_SIZE);
        skipBtn = new ImageButton("res/skip2.png", "res/skipHover2.png", BUTTON_SIZE);
        mainMenuBtn = new ImageButton("res/mainMenu2.png", "res/mainMenuHover2.png", BUTTON_SIZE);

        setLayout(null);

        add(playBtn);
        playBtn.setLocation((getWidth() - BUTTON_SIZE) / 2, (getHeight() - BUTTON_SIZE) / 2);
        playBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseBtn.setVisible(true);
                playBtn.setVisible(false);

                Logic.getInstance().startSimulation();
            }
        });

        pauseBtn.setVisible(false);
        add(pauseBtn);
        pauseBtn.setLocation((getWidth() - BUTTON_SIZE) / 2, (getHeight() - BUTTON_SIZE) / 2);
        pauseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSetup();
                Logic.getInstance().pauseSimulation();
            }
        });


        add(stepBtn);
        stepBtn.setLocation((getWidth() - BUTTON_SIZE) / 2 + BUTTON_SIZE + BUTTON_PADDING, (getHeight() - BUTTON_SIZE) / 2);
        stepBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logic.getInstance().doSimulationStep();

            }
        });

        add(resetBtn);
        resetBtn.setLocation((getWidth() - BUTTON_SIZE) / 2 - BUTTON_SIZE - BUTTON_PADDING, (getHeight() - BUTTON_SIZE) / 2);
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logic.getInstance().resetSimulation();
            }
        });

        add(skipBtn);
        skipBtn.setLocation(getWidth() - BUTTON_SIZE - 2 * BUTTON_PADDING, (getHeight() - BUTTON_SIZE) / 2);
        skipBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logic.getInstance().generateData();
                if(Config.autoStart) {
                    pauseBtn.setVisible(true);
                    playBtn.setVisible(false);
                    Logic.getInstance().startSimulation();
                }

            }
        });


        add(mainMenuBtn);
        mainMenuBtn.setLocation(2*BUTTON_PADDING, (getHeight() - BUTTON_SIZE) / 2);
        mainMenuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logic.getInstance().openMenu();
            }
        });

    }

    public void stopSetup() {
        pauseBtn.setVisible(false);
        playBtn.setVisible(true);
    }
}
