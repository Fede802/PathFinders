package view.customComponents;

import view.tools.LayoutBuilder;

import javax.swing.*;
import java.awt.*;

public class Option extends JPanel{

    private final JLabel label;
    private final HintTextField field;

    public Option(String name, String hint){
        label = new JLabel(name);
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(JLabel.RIGHT);
        field = new HintTextField(hint);
        JComponent[] comp = new JComponent[]{label,field};
        LayoutBuilder.buildGridBagLayout(comp,1,this,true);


    }

    public String getText(){
        return field.getText();
    }
    public void clear(){
        field.clear();
    }
}
