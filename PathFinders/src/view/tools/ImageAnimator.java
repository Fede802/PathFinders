package view.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageAnimator {

    private BufferedImage image, resultImage;
    private Graphics2D supportCanvas;
    private int rotation;
    private final int ROTATION_STEP;
    public ImageAnimator(String path, int rotation, int rotationStep){
        try {
            image =  ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setup();
        this.rotation = rotation;
        this.ROTATION_STEP = rotationStep;
        rotate();
    }

    private void setup(){
        resultImage = new BufferedImage(
                image.getWidth(), image.getHeight(), image.getType());
        supportCanvas = resultImage.createGraphics();
    }
    public void CWRotation(){
        rotation+=ROTATION_STEP;
        rotate();
    }
    public void CCWRotation(){
        rotation-=ROTATION_STEP;
        rotate();
    }

    public Image getImage(){
        return resultImage;
    }
    private void rotate(){
        setup();
        supportCanvas.rotate(Math.toRadians(rotation), image.getWidth() / 2,
                image.getHeight() / 2);
        supportCanvas.drawImage(image, null, 0, 0);
    }


    public void setRotation(int rotation) {
        this.rotation = rotation;
        rotate();
    }
}
