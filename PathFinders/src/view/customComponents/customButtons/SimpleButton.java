package view.customComponents.customButtons;



import view.tools.StringDrawer;

import java.awt.*;

public class SimpleButton extends ButtonTemplate{

    private final Font FONT = new Font("arial", Font.BOLD,12);

    private static final Color hover = new Color(45, 170, 160, 150);
    private static final Color enabled = new Color(45, 170, 160,255);
    private final Color disabledColor = new Color(75, 103, 99, 150);

    private Color currentColor = enabled;

    private final int startOffset = 2, endOffset = startOffset+1, curvature = 40;

    private final String text;

    public SimpleButton(String text, int width, int height) {
        super(width, height);
        this.text = text;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color prevColor = g2d.getColor();

        if(isEnabled()){
            g2d.setColor(currentColor);
        }else{
            g2d.setColor(disabledColor);
        }
        g2d.fillRoundRect(startOffset,startOffset,getWidth()-endOffset,getHeight()-endOffset,curvature,curvature);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(startOffset,startOffset,getWidth()-endOffset,getHeight()-endOffset,curvature,curvature);

        StringDrawer.drawCenteredString(g2d,text,FONT,Color.BLACK,(getHeight()-StringDrawer.getStringHeight(g2d,FONT))/2,this);
        g2d.setColor(prevColor);
    }

    @Override
    public void onNormalSetup() {
        currentColor = enabled;
    }

    @Override
    protected void onHoverSetup() {
        currentColor = hover;
    }


}
