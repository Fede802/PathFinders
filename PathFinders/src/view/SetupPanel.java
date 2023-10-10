package view;

import logic.Logic;
import utils.Config;
import view.customComponents.SelectionBottomMenu;
import view.customComponents.SlidablePanel;
import view.tools.ImageAnimator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class SetupPanel extends SlidablePanel implements MouseListener {

    private final Color BG_COLOR= new Color(89, 91, 93,255);
    private final int ROTATION_STEP = 12;
    private final float OPACITY_STEP = 0.06f;
    private float opacity;

    private final int iconMargin = 10, iconSize = 30;
    private final Rectangle2D.Double iconBoundingBox;
    private ImageAnimator
            menu = new ImageAnimator("res/menu.png",180,ROTATION_STEP),
            back = new ImageAnimator("res/back.png",180,ROTATION_STEP);


    private final SetupContent setupContent;

    private final SelectionBottomMenu selectionButtons;


    public SetupPanel(){
        setSize(new Dimension(Config.SCREEN_WIDTH/3, Config.SCREEN_HEIGHT));
        leftExitLimit = 2*iconMargin+iconSize;
        iconBoundingBox = new Rectangle2D.Double(Config.SCREEN_WIDTH/3-iconSize-iconMargin, iconMargin, iconSize, iconSize);
        onScreen = true;
        opacity = 1f;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        gbc.gridx = 0;
        gbc.gridy = 0;
        setupContent = new SetupContent(2,false);
        add(setupContent,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        selectionButtons = new SelectionBottomMenu();
        selectionButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(setupContent.checkText()) {
                    boolean dataAccepted = Logic.getInstance().generateData(
                            setupContent.getCurrentRenderType(),
                            setupContent.getOptions(),
                            setupContent.getSelectedAlgo()
                    );

                    Logic.getInstance().setUpScreen();

                    if (dataAccepted) {
                        slideLeft = true;
                    } else {
                        setupContent.clearText();
                    }

                }
            }
        });

        add(selectionButtons,gbc);

        addMouseListener(this);
        setOpaque(false);
        setFocusable(true);
        requestFocus();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color prevColor = g2d.getColor();
        //the order is important

//        offScreenDraw
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f-opacity));
        g2d.drawImage(menu.getImage(), getWidth()-iconSize-iconMargin, iconMargin, iconSize, iconSize, null);

//        onScreenDraw
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.drawImage(back.getImage(), getWidth()-iconSize-iconMargin, iconMargin, iconSize, iconSize, null);

        g2d.setColor(prevColor);
    }

    @Override
    protected void onLeftSlide() {
        if(onScreen){
            menu.CWRotation();
            back.CWRotation();
            opacity-=OPACITY_STEP;
        }else{
            menu.CCWRotation();
            back.CCWRotation();
            opacity+=OPACITY_STEP;
        }
    }

    @Override
    protected void onLeftSlideEnd() {
        if(onScreen) {
            opacity = 1f;
        }else{
            opacity = 0f;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(iconBoundingBox.contains(e.getX(),e.getY())){
            slideLeft = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //nothing to do
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //nothing to do
    }

    public void setup(){
        if(!onScreen){
            onScreen = true;
            setLocation(0,0);
            opacity = 1f;
            menu.setRotation(180);
            back.setRotation(180);
        }
    }

}
