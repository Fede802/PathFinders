package view.customComponents;

import view.tools.LayoutBuilder;

import javax.swing.*;

public class AppOption extends JPanel {

    private Option[] options;
    private final int EXTRA_SIDE_SPACE = 10;

    public AppOption(String[] opNames, String[] opHints){
        options = new Option[opNames.length];
        for (int i = 0; i < opNames.length; i++) {
            options[i] = new Option(opNames[i],opHints[i]);
        }
        LayoutBuilder.buildGridBagLayout(options,options.length,this,true,EXTRA_SIDE_SPACE);
        setVisible(false);
    }

    public int[] getOptions(){
        int[] op = new int[options.length];
        for (int i = 0; i < options.length; i++) {
            op[i] = Integer.parseInt(options[i].getText());
        }
        return op;
    }

    public boolean checkText() {
        boolean validInput = true;
        try{
            for (int i = 0; i < options.length; i++) {
                Integer.parseInt(options[i].getText());
            }
        }catch (NumberFormatException nfe){
            clearText();
            validInput = false;
        }
        return validInput;
    }

    public void clearText() {
        for (int i = 0; i < options.length; i++) {
            options[i].clear();
        }
    }

    public static class GraphOption extends AppOption{
        public GraphOption() {
            super(new String[]{"numRows:","numCols:","%compl:","numNodes:"},new String[]{"2-50","2-50","2-100","2-40% rows*cols"});
        }

    }

    public static class LabOption extends AppOption{
        public LabOption() {
            super(new String[]{"numRows:","numCols:"},new String[]{"2-50","2-50"});
        }
    }

    public static class MapOption extends AppOption{
        public MapOption() {
            super(new String[]{"numRows:","numCols:","%spawn:","%extends"},new String[]{"5-50","5-50","2-50","2-50"});
        }
    }


}
