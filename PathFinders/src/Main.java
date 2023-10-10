import utils.FontLoader;
import view.View;

public class Main {
    public static void main(String[] args) {
        //load title font
        FontLoader.loadFont();
        //build and show view
        View.getInstance();
    }


}