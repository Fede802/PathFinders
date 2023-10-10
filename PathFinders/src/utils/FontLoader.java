package utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    private static final String FONT_PATH = "res/Heavitas.ttf";
    public static final String TITLE_FONT = "Heavitas";

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    private FontLoader(){}

    //    --------------------------------------------------------
    //                      STATIC METHOD
    //    --------------------------------------------------------

    public static void loadFont(){
        GraphicsEnvironment ge;
        try{
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH)));
        }catch (FontFormatException | IOException e) {
            System.out.println("Failed loading font");
        }
    }

}

