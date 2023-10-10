package view.customComponents.customButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class ButtonTemplate extends JPanel implements MouseListener {

    private ActionListener onClickListener;

    public ButtonTemplate(int width, int height){
        Dimension d = new Dimension(width,height);
        setSize(d);
        setPreferredSize(d);
        addMouseListener(this);
        setOpaque(false); //to render properly
    }

    public void addActionListener(ActionListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isEnabled())
            if(onClickListener != null) {
                onClickListener.actionPerformed(null);
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        onNormalSetup();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        onHoverSetup();
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        onHoverSetup();
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        onNormalSetup();
        repaint();
    }

    protected abstract void onNormalSetup();
    protected abstract void onHoverSetup();

}
