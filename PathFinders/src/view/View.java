package view;

import commons.RenderType;
import commons.ShowState;

public class View implements IView{

    private final Frame container;
    private final AppPanel app;
    private final SimulationPanel simulation;

    //    --------------------------------------------------------
    //                      WINDOW HANDLING
    //    --------------------------------------------------------

    @Override
    public void openMenu() {
        container.openMenu();
    }

    @Override
    public void openApp() {
        container.openApp();
    }

    @Override
    public void openSimulation() {
        container.openSimulation();
    }

    @Override
    public void setUpScreen(RenderType rType, ShowState sState) {
        app.setUpScreen(rType, sState);
    }



    //    --------------------------------------------------------
    //                      SIMULATION HANDLING
    //    --------------------------------------------------------

    @Override
    public void setSimulation(boolean simulate) {
        app.setSimulation(simulate);
    }

    @Override
    public void simulationEnded() {
        app.simulationEnded();
    }




    //    --------------------------------------------------------
    //                      SINGLETON PATTERN
    //    --------------------------------------------------------

    private static IView instance = null;

    public static IView getInstance() {
        if (instance == null) {
            instance = new View();
        }
        return instance;
    }

    private View() {
        container = new Frame();
        app = container.getAppContainer();
        simulation = container.getSimulationContainer();
    }

}
