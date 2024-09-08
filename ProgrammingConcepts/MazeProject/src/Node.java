/**********************************************************************************/
// Node class:
// A node represents the base unit for the graph representation of the maze, where:
// 1. A single node represents a vertex.
// 2. A list of nodes represents an edge or a path.
//
// The equals method is implemented to compare nodes against each other.
// The toString method is implemented to provide a coherent display of a node.
//
// The attributes of that class are:
// 1. row and col: the row and column index on the matrix representation of the
//    maze.
// 2. att: a value that define either a wall, an empty grid cell, the start
//    vertex, the end vertex, a transit vertex, or the element of an edge.
/**********************************************************************************/
public class Node {
    private int row, col;
    private byte att;
    public Node(int row, int col, byte att) {this.row = row; this.col = col; this.att = att;}
    public int getRow() {return row;}
    public int getCol() {return col;}
    public int getAtt() {return att;}
    public boolean equals(Node loc) {return this == loc;}
    public String toString() {return "[" + row + "," + col + "," + att + "]";}
}
