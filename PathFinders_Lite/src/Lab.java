import java.util.ArrayList;

public class Lab extends Graph{
    public Lab(int rows, int columns){
        ArrayList<ArrayList<Cell>> tempCells = new ArrayList<>();

        //add cell to grid
        for (int i = 0; i < rows; i++) {
            tempCells.add(i,new ArrayList<>());
            for (int j = 0; j < columns; j++) {
                tempCells.get(i).add(new Cell(i,j));
            }
        }

        //connect every cell with his neighbours
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell currentCell = tempCells.get(i).get(j);
                if(i<rows-1) {
                    Cell bottomCell = tempCells.get(i + 1).get(j);
                    bottomCell.addEdge(currentCell,currentCell.addEdge(bottomCell));
                }
                if(j<columns-1){
                    Cell rightCell = tempCells.get(i).get(j+1);
                    rightCell.addEdge(currentCell,currentCell.addEdge(rightCell));
                }
            }
        }

        //populate real Graph
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                nodes.add(tempCells.get(i).get(j));
            }
        }

    }

    public static Lab[] generateRandomLab(){
        return generateRandomLab(numRows, numCols, numCopy);
    }
    public static Lab[] generateRandomLab(int rows, int columns, int numCopy){
        Graph.numRows = rows;
        Graph.numCols = columns;
        Graph.numCopy = numCopy;

        Lab[] labs = new Lab[numCopy];

        //init labs with all cells connected
        for (int i = 0; i < numCopy; i++) {
            labs[i] = new Lab(rows, columns);
        }
        //dig lab (find MST with randomized selection)

        //init: select start cell and create neighbours list
        int startCell = r.nextInt(rows*columns);
        Node[] current = new Cell[numCopy];
        ArrayList<ArrayList<Edge>> neighbours = new ArrayList<>();

        //setup for every copy
        for (int i = 0; i < numCopy; i++) {
            current[i] = labs[i].getNode(startCell);
            current[i].setVisited(true);
            ArrayList<Edge> currentEdges = new ArrayList<>();
            for (int j = 0; j < current[i].getNumEdges(); j++) {
                currentEdges.add(current[i].getEdges().get(j));
            }
            neighbours.add(i,currentEdges);
        }

        //remove walls for each copy (with same rand choice)
        while (!neighbours.get(0).isEmpty()){
            int randEdge = r.nextInt(neighbours.get(0).size());
            for (int i = 0; i < numCopy; i++) {
                ArrayList<Edge> currentNeighbours = neighbours.get(i);
                Edge currentEdge = currentNeighbours.get(randEdge);
                Node destination = currentEdge.getDestination();
                if(!destination.isVisited()){

                    currentEdge.getEdgeInfo().setVisited(true);
                    destination.setVisited(true);

                    for (int j = 0; j < destination.getNumEdges(); j++) {
                        currentNeighbours.add(destination.getEdges().get(j));
                    }

                }
                currentNeighbours.remove(currentEdge);
            }
        }

        //clear for each copy connection(support) edges a leave only the MST ones
        for (int i = 0; i < labs[0].getNumNode(); i++) {
            for (int j = 0; j < numCopy; j++) {
                ((Cell)labs[j].getNode(i)).clearUnvisitedEdges();
            }
        }

        //reset each lab because visit fields were used
        for (int i = 0; i < numCopy; i++) {
            labs[i].reset();
        }
        setupPoints(labs[0]);
        return labs;
    }




}
