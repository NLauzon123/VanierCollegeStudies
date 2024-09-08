import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
/**********************************************************************************/
// Maze class:
// This class incorporates the methods required to build:
// 1. A maze matrix from input read from a file.
// 2. A maze graph by characterizing the vertices and edges present in the maze,
//    allowing the definition of the path network in the maze.
// 3. A maze path list by characterizing all the paths leading to the end vertex,
//    coming back to the start vertex, or ending at a dead-end vertex.
//
// Methods for visualizing the maze and its paths are also incorporated in that
// class.
//
// The methods in that class are supported by the following attributes:
// 1. viable: A true value indicates a sealed maze with a start and an end,
//    assuring a finite number of paths.
// 2. grid: The matrix representation of the maze, each element containing an
//    attribute defining either a wall, path, start or end.
// 3. height and width: The height and width of the grid, as a convenience for
//    operating some of the methods in that class.
// 4. vertices: The list of vertices (nodes) in the maze under its graph
//    representation.
// 5. edges: The list of edges (lists of nodes) in the maze under its graph
//    representation.
// 6. parallelEdges: The groups of parallel edges (edges with the same vertices).
// 7. paths: The list of paths (lists of nodes) within the maze, from the
//    start vertex to either the end vertex, back to the start vertex, or
//    a dead end vertex.
// 8. totPath, destPath, minPath and maxPath: placeholders for the total
//    number of paths in the maze, the total number of paths leading to the end
//    vertex, the shortest path to the end vertex, and the longest path to the
//    end vertex.
/**********************************************************************************/
public class Maze {
    private boolean viable = false;
    private byte[][] grid;
    private int height, width;
    private ArrayList<Node> vertices = new ArrayList<Node>();
    private List<List<Node>> edges = new ArrayList<>();
    private List<List<Integer>> parallelEdges = new ArrayList<>();
    private List<List<Node>> paths;
    private int totPath, destPath, minPath, maxPath;

/**********************************************************************************/
// Use case: Matrix building
// Constructors to build the maze as a matrix and quality checks to ensure the
// resulting matrix is viable for the establishment of vertices, edges and paths.
/**********************************************************************************/
    public Maze() {}
    public Maze(String[] grid) {
        height = grid.length;
        width = grid[0].length();
        this.grid = new byte[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch(grid[i].charAt(j)) {
                    case '|': this.grid[i][j] = 0; break;
                    case ' ': this.grid[i][j] = 1; break;
                    case 'S': this.grid[i][j] = 2; break;
                    case 'E': this.grid[i][j] = 3; break;
                    default: this.grid[i][j] = 127;
                }
            }
        }
        viable = goodOuterBorders() && foundStartAndEnd();
        if(viable) buildGraph();
    }
    public boolean getViable() {return viable;}
    public int getTotalPath() {return totPath;}
    public int getDestPath() {return destPath;}
    public int getMinPath() {return minPath;}
    public int getMaxPath() {return maxPath;}
    private void setGrid(byte[][] grid) {
        height = grid.length;
        width = grid[0].length;
        this.grid = grid;
    }
    private boolean goodOuterBorders() {
        for (int i = 0; i < height; i++) {
            if (!(grid[i][0] == 0 || grid[i][0] == 2 || grid[i][0] == 3 || grid[i][width - 1] == 0 ||
                    grid[i][width - 1] == 2 || grid[i][width - 1] == 3)) return false;
        }
        for (int i = 1; i < width - 1; i++) {
            if (!(grid[0][i] == 0 || grid[0][i] == 2 || grid[0][i] == 3 || grid[height - 1][i] == 0 ||
                    grid[height - 1][i] == 2 || grid[height - 1][i] == 3)) return false;
        }
        return true;
    }
    private boolean foundStartAndEnd() {
        int countE = 0;
        int countS = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 2) countS++;
                if (grid[i][j] == 3) countE++;
            }
        }
        return ((countE == 1) && (countS == 1));
    }

/**********************************************************************************/
// Use case: Graph building
// Graph building is managed by the buildGraph methods, which print the
// resulting vertices, edges and group of parallel edges on the console
// to allow verification of the result.
//
// Establishment of the vertices (setVertices) and edges (setEdges), with
// identification of parallel edges (setParallelEdges) if present.
//
// Support methods (setDirections and setPaths) assist in finding available
// paths around a given location for exploration.
/**********************************************************************************/
    private void buildGraph() {
        System.out.println("Building the graph representation of the maze in progress...");
        setVertices();
        setEdges();
        setParallelEdges();
        System.out.println("Vertices found: " + vertices.size());
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println("Vertex " + i + ": " + vertices.get(i));}
        System.out.println("Edges found: " + edges.size());
        for (int i = 0; i < edges.size(); i++) {
            StringBuilder txt = new StringBuilder("Edge " + i + ": ");
            for (int j = 0; j < edges.get(i).size(); j++)
                txt.append(edges.get(i).get(j)).append(" ");
            System.out.println(txt);
        }
        if (parallelEdges.isEmpty()) System.out.println("No parallel edges found.");
        else {
            for (int i = 0; i < parallelEdges.size(); i++) {
                StringBuilder txt = new StringBuilder("Parallel edge group " + i + ": ");
                for (int j = 0; j < parallelEdges.get(i).size(); j++)
                    txt.append(parallelEdges.get(i).get(j)).append(" ");
                System.out.println(txt);
            }
        }
        System.out.println("Graph building completed.");
    }
    private int[][] setDirections(int row, int col) {
        return new int[][]{{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}};
    }
    private ArrayList<int[]> setPaths(int row, int col) {
        ArrayList<int[]> paths = new ArrayList<int[]>();
        int[][] dirs = setDirections(row, col);
        for (int[] dir : dirs) {
            if (!(dir[0] < 0 || dir[0] >= height || dir[1] < 0 || dir[1] >= width))
                if (grid[dir[0]][dir[1]] >= 1 && grid[dir[0]][dir[1]] <= 4)
                    paths.add(new int[]{dir[0], dir[1]});
        }
        return paths;
    }
    private void setVertices() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 1) {
                    int[][] dirs = setDirections(i, j);
                    int count = 0;
                    for (int[] dir : dirs)
                        if (grid[dir[0]][dir[1]] >= 1 && grid[dir[0]][dir[1]] <= 3) count++;
                    if ( count != 2) vertices.add(new Node(i, j, (byte)4));
                }
                if (grid[i][j] == 2) vertices.add(new Node(i, j, (byte)2));
                if (grid[i][j] == 3) vertices.add(new Node(i, j, (byte)3));
            }
        }
    }
    private void setEdges() {
        for(Node vertex1 : vertices) {
            ArrayList<int[]> paths = setPaths(vertex1.getRow(), vertex1.getCol());
            if (!paths.isEmpty()) {
                for (int[] path : paths) {
                    int row1 = vertex1.getRow(); int col1 = vertex1.getCol();
                    int row2 = path[0]; int col2 = path[1];
                    boolean existingEdgeFound = false;
                    if (!edges.isEmpty()) {
                        for (List<Node> edge : edges) {
                            int last = edge.size() - 1;
                            if (edge.get(last).getRow() == row1
                                    && edge.get(last).getCol() == col1
                                    && edge.get(last - 1).getRow() == row2
                                    && edge.get(last - 1).getCol() == col2) {
                                existingEdgeFound = true; break;
                            }
                        }
                    }
                    if (existingEdgeFound) continue;
                    edges.add(new ArrayList<>());
                    edges.get(edges.size() - 1).add(vertex1);
                    boolean vertex2Found = false;
                    do {
                        Node n = null;
                        for (Node vertex2 : vertices) {
                            if (vertex2.getRow() == row2 && vertex2.getCol() == col2) {
                                n = vertex2;
                                vertex2Found = true;
                            }
                        }
                        if (n == null) n = new Node(row2, col2, (byte)5);
                        edges.get(edges.size() - 1).add(n);
                        if (!vertex2Found) {
                            ArrayList<int[]> newPaths = setPaths(row2, col2);
                            for (int[] newPath : newPaths) {
                                if (row1 != newPath[0] || col1 != newPath[1]) {
                                    row1 = row2; col1 = col2;
                                    row2 = newPath[0]; col2 = newPath[1];
                                    break;
                                }
                            }
                        }
                    } while (!vertex2Found);
                }
            }
        }
    }
    private void setParallelEdges() {
        boolean[] check = new boolean[edges.size()];
        for (int i = 0; i < edges.size(); i++) {check[i] = false;}
        for (int i = 0; i < edges.size(); i++) {
            if (!check[i]) {
                check[i] = true;
                ArrayList<Integer> parallel = new ArrayList<Integer>();
                parallel.add(i);
                for (int j = i+1; j < edges.size(); j++) {
                    int first = 0; int lastI = edges.get(i).size() -1 ; int lastJ = edges.get(j).size() - 1;
                    if ((edges.get(i).get(first).equals(edges.get(j).get(first)) &&
                            edges.get(i).get(lastI).equals(edges.get(j).get(lastJ))) ||
                            (edges.get(i).get(first).equals(edges.get(j).get(lastJ)) &&
                            edges.get(i).get(lastI).equals(edges.get(j).get(first)))) {
                        check[j] = true;
                        parallel.add(j);
                    }
                    if (parallel.size() > 1) parallelEdges.add(parallel);
                }
            }
        }
    }

/**********************************************************************************/
// Use case: Path search
// The search for all paths in the maze is managed by the findPathsInitial method,
// which addresses the edge cases of simple mazes and prepare seed paths passed
// to either an iterative or recursive search method. The method print the
// found paths (vertex by vertex) to the console, allowing a verification of the
// results.
//
// The iterative search method sits within the findPathsInitial method (a simple
// while loop), while the recursive search method is supported by the
// findPathsRec method.
//
// The updatePaths method is the engine that powers both the iterative and
// recursive search methods. This engine manages the list of paths in the
// maze, putting completed paths at the beginning of the list and incomplete
// paths at the end of the list. A complete path either means reaching the end
// vertex, coming back to the start vertex, or reaching a dead-end vertex. The
// iterative and recursive methods picks from the end of the list, until all
// paths are complete (the termination condition).
//
// Supporting methods include findChildren and unvisited, to determine potential
// vertices to explore from a parent vertex.
/**********************************************************************************/
    private ArrayList<Node> findChildren(Node parent) {
        ArrayList<Node> children = new ArrayList<Node>();
        for (List<Node> edge : edges) {
            int first = 0, last = edge.size() - 1;
            if (edge.get(last).equals(parent)) children.add(edge.get(first));
            if (edge.get(first).equals(parent)) children.add(edge.get(last));
        }
        return children;
    }
    private boolean unvisited(ArrayList<Node> list, ArrayList<Node> nextNodes) {
        int start = nextNodes.size() - 1;
        for (int j = start; j >= 0; j--) {
            for (Node node : list) if (node.equals(nextNodes.get(j))) nextNodes.remove(j);
            if (nextNodes.isEmpty()) return true;
        }
        return !nextNodes.isEmpty();
    }
    private void printPaths() {
        for (int i = 0; i < paths.size(); i++) {
            StringBuilder txt = new StringBuilder("Path " + i + " : ");
            for (int j = 0; j < paths.get(i).size(); j++) {
                txt.append(paths.get(i).get(j)).append(" ");}
            System.out.println(txt);
        }
    }
    public void findPathsInitial(int method) {
        System.out.println("Path finding exploration in progress...");
        System.out.println("Initialization...");
        paths = new ArrayList<>();
        ArrayList<Boolean> complete = new ArrayList<Boolean>();
        Node origin = null;
        Node destination = null;
        for (Node vertex : vertices) {
            if (vertex.getAtt() == 2) origin = vertex;
            if (vertex.getAtt() == 3) destination = vertex;
        }
        ArrayList<Node> children = findChildren(origin);
        if (children.isEmpty()) {
            System.out.println("Enclosed start, with no available path."); return;
        }
        else if (children.size() == 1) {
            int first = 0;
            if (children.get(first).equals(destination)) {
                ArrayList<Node> list = new ArrayList<Node>();
                list.add(origin); list.add(children.get(first));
                paths.add(first,list); complete.add(first,true);
                System.out.println("Only one direct path from S to E, and no other.");
            }
            else {
                ArrayList<Node> list = new ArrayList<Node>();
                list.add(origin); list.add(children.get(first));
                paths.add(first,list); complete.add(first,true);
                System.out.println("Only one path available to S, and not leading to E.");
            }
        }
        else {
            for (Node child : children) {
                ArrayList<Node> list = new ArrayList<Node>();
                list.add(origin); list.add(child);
                if (child.equals(destination)) {
                    int first = 0; paths.add(first, list); complete.add(first, true);
                }
                else {
                    ArrayList<Node> nextNodes = findChildren(child);
                    if (!unvisited(list, nextNodes)) {
                        int first = 0; paths.add(first, list); complete.add(first, true);
                    }
                    else {paths.add(list); complete.add(false);}
                }
            }
        }
        printPaths();
        if (method == 1 && !complete.get(complete.size() - 1)) {
            System.out.println("Iteration in progress...");
            while (!complete.get(complete.size() - 1)) updatePaths(complete, origin, destination);
            printPaths();
        }
        if (method == 2 && !complete.get(complete.size() - 1)) {
            System.out.println("Recursion in progress...");
            findPathsRec(complete, origin, destination);
            printPaths();
        }
        System.out.println("Path finding exploration completed.");
        finalPaths();
    }
    private void findPathsRec(ArrayList<Boolean> complete, Node origin, Node destination) {
        if (complete.get(complete.size() - 1)) return;
        updatePaths(complete, origin, destination);
        findPathsRec(complete, origin, destination);
    }
    private void updatePaths(ArrayList<Boolean> complete, Node origin, Node destination) {
        ArrayList<Node> lastList = (ArrayList<Node>) paths.remove(paths.size() -1);
        complete.remove(complete.size() -1);
        ArrayList<Node> children = findChildren(lastList.get(lastList.size() - 1));
        for (int i = 0; i < children.size(); i++) {
            ArrayList<Node> newList = new ArrayList<Node>();
            for (Node node : lastList) newList.add(node);
            newList.add(children.get(i));
            if (newList.get(newList.size() - 1).equals(destination)) {
                paths.add(0,newList); complete.add(0,true);
            }
            else {
                boolean visited = false;
                for (int j = 0; j < newList.size() - 1; j++) {
                    if (newList.get(newList.size() - 1).equals(newList.get(j))) {visited = true; break;}
                }
                if (visited) {
                    if (newList.get(newList.size() - 1).equals(origin) && newList.size() > 3) {
                        paths.add(0,newList); complete.add(0,true);
                    }
                    else if (children.size() == 1 &&
                            newList.get(newList.size() - 1).equals(newList.get(newList.size() - 3))) {
                        newList.remove(newList.size() - 1);
                        paths.add(0,newList); complete.add(0,true);
                    }
                }
                else {paths.add(newList); complete.add(false);}
            }
        }
    }

/**********************************************************************************/
// Use case: Visualisation preparation
// At this stage, the paths only store the vertices leading for origin to
// destination. The purpose of the preparation is to include for any paths all
// the nodes (vertices and edges) that lead from origin to destination. These
// nodes in a paths are the element required to visualize a path in full on the
// console.
//
// This use case is managed solely by the finalPaths method, and entails for any
// available paths to compile all the locations on the maze matrix from origin
// to destination by the use of the vertices identified in the path during the
// path search and their connecting edges. Parallel edges are addressed in this
// method if required.
//
// The method print on the console 1) the total number of paths found, 2) the
// total number of paths leading to the end vertex, 3) the index of the shortest
// path to the end vertex, 4) the index of the longest path to the end vertex,
// and 5) all the paths, allowing verification of the results.
/**********************************************************************************/
    private void finalPaths() {
        totPath = destPath = minPath = maxPath = 0;
        if (!paths.isEmpty()) {
            List<List<Node>> sortedPaths = new ArrayList<>();;
            totPath = paths.size();
            int stack = paths.size() - 1;
            for (int i = stack; i >= 0; i--) {
                ArrayList<Node> list = (ArrayList<Node>) paths.remove(i);
                int first = 0; int last = list.size() - 1;
                if (list.get(first).getAtt() == 3 || list.get(last).getAtt() == 3) {
                    sortedPaths.add(0,list); destPath++;
                }
                else sortedPaths.add(list);
            }
            paths = sortedPaths;
        }
        int[] parallelPos = new int[0];
        if (!parallelEdges.isEmpty()) {
            parallelPos = new int[parallelEdges.size()];
            for (int i = 0; i < parallelPos.length; i++) parallelPos[i] = 0;
        }
        for (int i = 0; i < paths.size(); i++) {
            ArrayList<Node> oldList = (ArrayList<Node>) paths.remove(i);
            Set<Node> uniqueNodes = new HashSet<Node>();
            ArrayList<Node> newList = new ArrayList<Node>();
            for (int j = 0; j < oldList.size() - 1; j++) {
                Node n1 = oldList.get(j); Node n2 = oldList.get(j + 1);
                boolean found = false;
                for (int k = 0; k < edges.size(); k++) {
                    Node n3 = edges.get(k).get(0); Node n4 = edges.get(k).get(edges.get(k).size() - 1);
                    if ((n1.equals(n3) && n2.equals(n4)) || (n1.equals(n4) && n2.equals(n3))) {
                        found = true;
                        int e = k;
                        if (!parallelEdges.isEmpty()) {
                            for (int m = 0; m < parallelEdges.size(); m++) {
                                for (int n = 0; n < parallelEdges.get(m).size(); n++) {
                                    if (parallelEdges.get(m).get(n) == k) {
                                        e = parallelPos[m]; parallelPos[m] += 1;
                                        System.out.println(e + " " + parallelPos[m]);
                                    }
                                }
                            }
                        }
                        for (int m = 0; m < edges.get(e).size(); m++) {
                            uniqueNodes.add(edges.get(e).get(m));
                        }
                    }
                    if (found) break;
                }
            }
            Node[] trimmedNodes = uniqueNodes.toArray(new Node[0]);
            for (Node node : trimmedNodes) newList.add(node);
            paths.add(i,newList);
        }
        int minTemp = height * width; int maxTemp = 0;
        for (int i = 0; i < destPath; i++) {
            if (paths.get(i).size() < minTemp) {minTemp = paths.get(i).size(); minPath = i;}
            if (paths.get(i).size() > maxTemp) {maxTemp = paths.get(i).size(); maxPath = i;}
        }
        System.out.println("Final processing of the paths:");
        System.out.println("Total paths: " + totPath);
        System.out.println("Destination paths: " + destPath);
        System.out.println("Shortest path is Path: " + minPath);
        System.out.println("Longest path is Path: " + maxPath);
        printPaths();
    }

/**********************************************************************************/
// Use case: Output visualization
// Within are three methods allowing visualization of the maze on the console:
// 1. toString: the basic method that can be used to display maze with no path;
// 2. displayNodes, with the support of toString: which displays the maze with
//    all the available paths.
// 3. displaySolution, with the support of toString: which displays the maze with
//    a path selected by the user.
/**********************************************************************************/
    public String displayNodes() {
        Maze m = new Maze();
        byte[][] newGrid = new byte[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) newGrid[i][j] = grid[i][j];
        m.setGrid(newGrid);
        for (Node vertex : vertices)
            m.grid[vertex.getRow()][vertex.getCol()] = (byte) vertex.getAtt();
        for (List<Node> edge : edges)
            for (Node node : edge)
                m.grid[node.getRow()][node.getCol()] = (byte) node.getAtt();
        return m.toString();
    }
    public String displaySolution(int choice) {
        Maze m = new Maze();
        byte[][] newGrid = new byte[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) newGrid[i][j] = grid[i][j];
        m.setGrid(newGrid);
        ArrayList<Node> nodeList = (ArrayList<Node>) paths.get(choice);
        for (Node node : nodeList) m.grid[node.getRow()][node.getCol()] = (byte) node.getAtt();
        return m.toString();
    }
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (grid[i][j]) {
                    case 0: result.append("|"); break;
                    case 1: result.append(" "); break;
                    case 2: result.append("S"); break;
                    case 3: result.append("E"); break;
                    case 4: result.append("V"); break;
                    case 5: result.append("●"); break;
                    default: result.append("?");
                }
            }
            if (i != height -1) result.append("\n");
        }
        return result.toString();
    }
}
