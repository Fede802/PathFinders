package view;

import javax.swing.*;

public abstract class AppSection extends JPanel {

    public AppSection(){
        setFocusable(true);
        requestFocusInWindow();
    }

    protected String cost = "",step="",time="",name="";

    public void clearInfo(){
        cost ="";
        step="";
        time="";
        name="";
    }

    public abstract void setInfo();

    public abstract void setName();
}
