package view;

import commons.RenderType;
import commons.ShowState;

public interface IView {

    //    --------------------------------------------------------
    //                      WINDOW HANDLING
    //    --------------------------------------------------------
    void openMenu();

    void openApp();

    void openSimulation();

    void setUpScreen(RenderType rType, ShowState sState);

    //    --------------------------------------------------------
    //                      SIMULATION HANDLING
    //    --------------------------------------------------------


    void setSimulation(boolean simulate);

    void simulationEnded();

}
