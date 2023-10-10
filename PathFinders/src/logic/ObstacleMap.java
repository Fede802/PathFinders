package logic;


import java.util.ArrayList;

public class ObstacleMap extends Graph{

    private static int spawn, expansion;//map setup data

    private final static PathFinder pathChecker = new BFS();
    private enum Direction{
        UP,DOWN,LEFT,RIGHT
    }
    private static final java.util.Map<Integer,Direction> dirMap = java.util.Map.of(
            0, Direction.UP,
            1, Direction.DOWN,
            2, Direction.LEFT,
            3, Direction.RIGHT
    );
    public ObstacleMap(int rows, int columns, boolean[][] walls){
        ArrayList<ArrayList<Node>> tempNodes = new ArrayList<>();

        //add Nodes
        for (int i = 0; i < rows; i++) {
            tempNodes.add(i,new ArrayList<>());
            for (int j = 0; j < columns; j++) {
                if(!walls[i][j])
                    tempNodes.get(i).add(j,new Node(i,j));
                else
                    tempNodes.get(i).add(j,null);
            }
        }

        //connectNodes
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(!walls[i][j]) {
                    if (i < rows - 1) {
                        if(!walls[i+1][j]) {
                            Node currentNode = tempNodes.get(i).get(j);

                            Node bottomNode = tempNodes.get(i + 1).get(j);

                            bottomNode.addEdge(currentNode, currentNode.addEdge(bottomNode));
                        }
                    }
                    if (j < columns - 1) {
                        if (!walls[i][j+1]) {
                            Node currentNode = tempNodes.get(i).get(j);


                            Node rigthNode = tempNodes.get(i).get(j + 1);


                            rigthNode.addEdge(currentNode, currentNode.addEdge(rigthNode));
                        }
                    }

                }

            }
        }
        //populate real Graph

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(tempNodes.get(i).get(j) != null)
                    nodes.add(tempNodes.get(i).get(j));
            }
        }
    }

    public static ObstacleMap[] generateRandomMap(){
        return generateRandomMap(numRows, numCols,spawn,expansion,numCopy);
    }
    public static ObstacleMap[] generateRandomMap(int rows, int columns, int spawn, int expansion, int numCopy) {

        Graph.numRows = rows;
        Graph.numCols = columns;
        ObstacleMap.spawn = spawn;
        ObstacleMap.expansion = expansion;
        Graph.numCopy = numCopy;

        ObstacleMap[] maps = new ObstacleMap[numCopy];

        do {
            boolean[][] walls = positionRandomWalls();
            //init maps
            for (int i = 0; i < numCopy; i++) {
                maps[i] = new ObstacleMap(rows, columns, walls);
            }
            setupPoints(maps[0]);
            pathChecker.setup(maps[0]);
        }while (!pathChecker.simulate());

        for (int i = 0; i < numCopy; i++) {
            maps[i].reset();
        }
        return maps;
    }

    private static boolean[][] positionRandomWalls(){
        boolean[][] walls = new boolean[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if(r.nextInt(100) < spawn){
                    walls[i][j] = true;
                    tryToExpand(walls,i,j);
                }
            }
        }
        return walls;
    }

    private static void tryToExpand(boolean[][] walls, int currRow, int currCol) {
        boolean emptyCell = true;
        boolean valid = true;

        while(r.nextInt(100) < expansion && emptyCell && valid){
            Direction dir = dirMap.get(r.nextInt(4));
            valid = true;
            switch (dir) {
                case DOWN -> {
                    if (currRow +1> numRows)
                        valid = false;
                }
                case UP -> {
                    if (currRow -1< 0)
                        valid = false;
                }
                case LEFT -> {
                    if (currCol -1< 0)
                        valid = false;
                }
                case RIGHT -> {
                    if (currCol +1> numCols)
                        valid = false;
                }
            }
            if(valid){
                if(!walls[currRow][currCol]){
                    walls[currRow][currCol] = true;
                }else{
                    emptyCell = false;
                }
            }
        }
    }


}
