package view.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

public class StringDrawer {

    //    --------------------------------------------------------
    //                       STATIC FIELDS
    //    --------------------------------------------------------

    public static final int CENTER = 0;
    public static final int PADDING = 1;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private StringDrawer(){}

    //    --------------------------------------------------------
    //                      STATIC METHODS
    //    --------------------------------------------------------

    public static double getStringWidth(final Graphics2D g2d, final String text, final Font textFont) {
        return g2d.getFontMetrics(textFont).stringWidth(text);
    }

    public static double getStringHeight(final Graphics2D g2d, final Font textFont) {
        return g2d.getFontMetrics(textFont).getHeight();
    }
    public static void drawCenteredString(Graphics2D g2d, String text,  Font font, Color fillColor, double paddingTop, JComponent parent) {
        drawString(g2d, text, font, fillColor,paddingTop,parent.getWidth(),CENTER);
    }
    public static void drawString(Graphics2D g2d, String text, Font font, Color fillColor, double paddingTop, double paddingRight) {
        drawString(g2d, text, font, fillColor,paddingTop,paddingRight,PADDING);
    }
    private static void drawString(Graphics2D g2d, String text, Font font,  Color fillColor, double paddingTop, double paddingRight,int position) {
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        g2d.setFont(font);
        g2d.setColor(fillColor);

        int downShift = g2d.getFontMetrics().getAscent();
        if(position == CENTER){
            double length = getStringWidth(g2d,text,font);
            g2d.drawString(text, (int) (paddingRight-length)/2, (int) (paddingTop+downShift));
        }else{
            g2d.drawString(text, (int) paddingRight, (int) (paddingTop+downShift));
        }

        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    public static void drawOutlinedString(Graphics2D g2d, String text, Font font, Color outlineColor, int strokeWidth, Color fillColor, double paddingTop, double paddingRight, JPanel panel, int position) {
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();
        AffineTransform affineTransform = g2d.getTransform();
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics(font);
        int downShift = fm.getAscent();
        if(position == CENTER){
            affineTransform.translate(panel.getWidth()/2- fm.stringWidth(text)/2+paddingRight,downShift+paddingTop);
        }else{
            affineTransform.translate(paddingRight,downShift+paddingTop);
        }
        g2d.setTransform(affineTransform);
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl = new TextLayout(text, g2d.getFont(), frc);
        Shape shape = tl.getOutline(null);
        Stroke ds = g2d.getStroke();
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.setColor(outlineColor);
        g2d.draw(shape);
        g2d.setColor(fillColor);
        g2d.fill(shape);
        affineTransform.translate(-affineTransform.getTranslateX(),-affineTransform.getTranslateY());
        g2d.setTransform(affineTransform);
        g2d.setStroke(ds);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

}
