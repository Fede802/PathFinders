package view.customComponents.customButtons;

import javax.swing.*;
import java.awt.*;

public class ImageButton extends ButtonTemplate{

    private Image img, imgHover, currImg;

    public ImageButton(String urlImg, String urlImgHover, int size) {
        super(size, size);
        img = new ImageIcon(urlImg).getImage();
        imgHover = new ImageIcon(urlImgHover).getImage();
        currImg = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(currImg,0,0,getWidth(),getHeight(),null);
    }

    @Override
    protected void onNormalSetup() {
        currImg = img;
    }

    @Override
    protected void onHoverSetup() {
        currImg = imgHover;
    }
}
