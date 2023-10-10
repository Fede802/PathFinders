package view.customComponents;

import commons.TimeType;
import view.tools.LayoutBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

public class TimeOptions extends JPanel implements ItemListener {

    private Map<JRadioButton,TimeType> enumMap = new HashMap<>();

    private TimeType timeType;

    public TimeOptions(){
        TimeType[] timeTypes = TimeType.values();
        JRadioButton[] timeButtons = new JRadioButton[timeTypes.length];
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < timeTypes.length; i++) {
            String enumName = timeTypes[i].name();
            String btnName = enumName.substring(0, 1).toUpperCase() + enumName.substring(1).toLowerCase();
            JRadioButton temp = new JRadioButton(btnName);
            temp.setOpaque(false);
            temp.setForeground(Color.BLACK);
            temp.addItemListener(this);
            enumMap.put(temp,timeTypes[i]);
            group.add(temp);
            timeButtons[i] = temp;
        }
        timeButtons[1].setSelected(true);
        timeType =timeTypes[1];
        LayoutBuilder.buildGridBagLayout(timeButtons,1,this,true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JRadioButton rb = (JRadioButton) e.getSource();
        if(rb.isSelected()) {
            timeType = enumMap.get(rb);
        }
    }

    public TimeType getCurremtTimeType(){
        return timeType;
    }

}
