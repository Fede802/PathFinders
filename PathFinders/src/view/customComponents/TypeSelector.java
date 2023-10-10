package view.customComponents;

import view.customComponents.customButtons.PushButton;
import view.customComponents.customButtons.PushListener;
import view.tools.LayoutBuilder;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

public class TypeSelector extends JPanel implements ItemListener {

    private Map<PushButton,Enum<?>> enumMap = new HashMap<>();
    private Enum<?> currentType;

    private PushListener pushListener;
    public TypeSelector(Enum<?>[] e, int numRows){
        ButtonGroup group = new ButtonGroup();
        PushButton[] btnList = new PushButton[e.length];

        for (int i = 0; i < e.length; i++) {
            String enumName = e[i].name();
            String btnName = enumName.substring(0, 1).toUpperCase() + enumName.substring(1).toLowerCase();
            PushButton temp = new PushButton(btnName);
            temp.addItemListener(this);
            group.add(temp);
            enumMap.put(temp,e[i]);
            btnList[i] = temp;

        }
        btnList[0].setSelected(true);
        currentType = e[0];
        LayoutBuilder.buildGridBagLayout(btnList, numRows,this,false);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        PushButton pb = (PushButton) e.getSource();
        if(pb.isSelected()) {
            currentType = enumMap.get(pb);
            if (pushListener != null)
                pushListener.onPushStateChange(currentType);
        }
    }

    public Enum<?> getCurrentType(){
        return currentType;
    }

    public void setItemStateChangeListener(PushListener pushListener) {
        this.pushListener = pushListener;
    }
}
