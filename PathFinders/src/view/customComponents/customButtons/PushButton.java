package view.customComponents.customButtons;



import view.tools.StringDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PushButton extends JRadioButton implements MouseListener {

    private final Font FONT = new Font("arial", Font.BOLD,12);

    private static final Color hover = new Color(45, 170, 160, 150);
    private static final Color disabled = new Color(45, 170, 160,50);
    private static final Color enabled = new Color(45, 170, 160,255);
    private Color currentColor = disabled;
    private final int startOffset = 2, endOffset = startOffset+1, curvature = 40;
    private boolean push = false;

    private final int WIDTH = 90, HEIGHT = 40;

    public PushButton(String text){
        super();
        this.addMouseListener(this);
        Dimension d = new Dimension(WIDTH,HEIGHT);
        setSize(d);
        setPreferredSize(d);
        setText(text);
        setOpaque(false);

    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
    }

    @Override
    protected void fireItemStateChanged(ItemEvent event) {
        super.fireItemStateChanged(event);
        setSelected(!push);
    }

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        push = b;
        setColor();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color prevColor = g2d.getColor();

        g2d.setColor(currentColor);
        g2d.fillRoundRect(startOffset,startOffset,getWidth()-endOffset,getHeight()-endOffset,curvature,curvature);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(startOffset,startOffset,getWidth()-endOffset,getHeight()-endOffset,curvature,curvature);

        StringDrawer.drawCenteredString(g2d,getText(),FONT,Color.BLACK,(getHeight()-StringDrawer.getStringHeight(g2d,FONT))/2,this);
        g2d.setColor(prevColor);
    }
    private void setColor(){
        if(push) {
            currentColor = enabled;
        }else{
            currentColor = disabled;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        fireStateChanged();
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
        currentColor = hover;
        repaint();
    }


    @Override
    public void mouseExited(MouseEvent e) {
        setColor();
        repaint();
    }



}
