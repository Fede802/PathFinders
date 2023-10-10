package view.customComponents;

import javax.swing.*;

public abstract class SlidablePanel extends JPanel {

    //could be generalized
    private final int SLIDING_STEP = 10;

    protected boolean slideLeft, onScreen;
    protected int leftExitLimit;

    @Override
    public void updateUI() {
        super.updateUI();
        if(slideLeft)
            slideLeft();
    }

    private void slideLeft(){

        if(onScreen) {
            setLocation(getX() - SLIDING_STEP, getY());
            onLeftSlide();
            if (getX() <= -getWidth()+leftExitLimit) {
                setLocation(-getWidth()+leftExitLimit, 0);
                slideLeft = false;
                onScreen = false;
                onLeftSlideEnd();
            }
        }else{
            setLocation(getX() + SLIDING_STEP, getY());
            onLeftSlide();
            if (getX() >= 0) {
                setLocation(0, 0);
                requestFocus();
                slideLeft = false;
                onScreen = true;
                onLeftSlideEnd();
            }
        }

    }

    protected abstract void onLeftSlide();
    protected abstract void onLeftSlideEnd();
}
