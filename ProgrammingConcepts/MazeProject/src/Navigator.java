import java.util.Scanner;
import java.io.*;
public class Navigator {
/**********************************************************************************/
// Navigator class:
// This class implements the terminal interface by which a user can navigate
// through the input compilation, input processing and output visualization use
// cases. All these use cases are incorporated in the applyMazeRunner method,
// which manages the flow of decisions from the users.
//
// Methods to read a maze from a file are incorporated in that class and serve
// as support to the input compilation use case (addTextLine and readMazeFile).
//
// No attributes are defined in that class, given that none are required.
/**********************************************************************************/
    public Navigator() {}
    private String[] addTextLine(String[] strArray, String str) {
        String[] newArray = new String[strArray.length + 1];
        for (int i = 0; i < strArray.length; i++) newArray[i] = strArray[i];
        newArray[strArray.length] = str;
        return newArray;
    }
    private String[] readMazeFile(File file) {
        String[] str = new String[0];
        try{
            Scanner sc = new Scanner(new FileReader(file));
            while (sc.hasNextLine()) str = addTextLine(str, sc.nextLine());
            sc.close();
            return str;
        }
        catch (IOException e) {System.out.println(e.getMessage() + " The file was not read."); return str;}
    }

/**********************************************************************************/
// Use case: Input compilation
// Incorporates functionalities in the applyMazeRunner that allows a user to
// select a maze for input processing and output visualization. The
// displayMainMenu list the choices of predefined mazes offered to the user.
/**********************************************************************************/
    private void displayMainMenu() {
        System.out.println("\nSolve the following maze:");
        System.out.println("1. Solve a maze with no path");
        System.out.println("2. Solve a maze with one path");
        System.out.println("3. Solve a maze with two paths");
        System.out.println("4. Solve a maze with four paths");
        System.out.println("5. Solve the challenging maze");
        System.out.println("6. Solve a maze from a chosen file");
        System.out.println("Choose a number or press q or Q to exit the program: ");
    }

/**********************************************************************************/
// Use case: Input processing
// Incorporates functionalities in the applyMazeRunner that allows a user to
// select a method to find paths in a maze prior to output visualization. The
// displaySolvingChoices list the available path finding methods (options 3 and 4),
// plus some output visualization methods as quality checks.
/**********************************************************************************/
    private void displaySolvingChoices() {
        System.out.println("\nChoose the following option:");
        System.out.println("1. Display the maze");
        System.out.println("2. Display the path network in the maze");
        System.out.println("3. Find all paths in the maze by the iterative method");
        System.out.println("4. Find all paths in the maze by the recursive method");
        System.out.println("Choose a number or press q or Q to return to the main menu. ");
    }

/**********************************************************************************/
// Use case: Output visualization
// Incorporates functionalities in the applyMazeRunner that allows a user to
// display the maze as is or with paths to destinations E or others.
/**********************************************************************************/
    private void displaySolutionChoices() {
        System.out.println("\nChoices of path display:");
        System.out.println("1. Display the maze with no path");
        System.out.println("2. Display paths to destination E");
        System.out.println("3. Display the shortest path to destination E");
        System.out.println("4. Display the Longest path to destination E");
        System.out.println("5. Display paths to other destinations");
        System.out.println("Choose a number or press q or Q to return to the main menu. ");
    }

/**********************************************************************************/
// Public access to the user to the input compilation, input processing and
// output visualization use cases, allowing implementation of the other use cases
// to process/solve mazes.
/**********************************************************************************/
    public void applyMazeRunner() {
        Maze maze = new Maze();
        boolean quitM = false;
        boolean quitS = false;
        String[] strMaze = new String[0];
        Scanner sc = new Scanner(System.in);
        while (!quitM) {
            // Implementation of the input compilation use case.
            // The maze runner application ends when the user quits input compilation.
            displayMainMenu();
            boolean validChoice = false;
            char choice = sc.next().charAt(0);
            if (choice == 'Q' || choice == 'q') {System.out.println("Goodbye"); quitM = true; continue;}
            if(!Character.isDigit(choice)) {System.out.println("Please enter a number"); continue;}
            switch (choice) {
                case '1': strMaze = readMazeFile(new File("NoPathMaze.txt")); break;
                case '2': strMaze = readMazeFile(new File("OnePathMaze.txt")); break;
                case '3': strMaze = readMazeFile(new File("TwoPathMaze.txt")); break;
                case '4': strMaze = readMazeFile(new File("FourPathMaze.txt")); break;
                case '5': strMaze = readMazeFile(new File("ChallengeMaze.txt")); break;
                case '6': System.out.println("\nPlease enter a file name: ");
                    String str = sc.next(); strMaze = readMazeFile(new File(str)); break;
                default: System.out.println("Please enter a valid number");
            }
            if (strMaze.length != 0) {
                maze = new Maze(strMaze);
                validChoice = maze.getViable();
                if (!validChoice) System.out.println("The maze is not viable and has been discarded.");
            }
            if (!validChoice) continue;
            // Implementation in sequence of the input processing and output visualization use cases.
            while (!quitS) {
                // Implementation of the input processing use case.
                // Selecting to find paths by the iterative or recursive method leads to output visualization.
                // Quitting input processing leads back to input compilation.
                displaySolvingChoices();
                validChoice = false;
                choice = sc.next().charAt(0);
                if (choice == 'Q' || choice == 'q') {
                    System.out.println("Back to main menu"); quitS = true; continue;}
                if(!Character.isDigit(choice)) {System.out.println("Please enter a number"); continue;}
                switch (choice) {
                    case '1': System.out.println(maze); validChoice = true; break;
                    case '2': System.out.println(maze.displayNodes()); validChoice = true; break;
                    case '3': maze.findPathsInitial(1); validChoice = true; quitS = true; break;
                    case '4': maze.findPathsInitial(2); validChoice = true; quitS = true; break;
                    default: System.out.println("Please enter a valid number");
                }
            }
            quitS = false;
            if (!validChoice) continue;
            while (!quitS) {
                // Implementation of the output visualization use case.
                // Quitting output visualization leads back to input compilation.
                displaySolutionChoices();
                choice = sc.next().charAt(0);
                if (choice == 'Q' || choice == 'q') {
                    System.out.println("Back to main menu"); quitS = true; continue;}
                if(!Character.isDigit(choice)) {System.out.println("Please enter a number"); continue;}
                switch (choice) {
                    case '1': System.out.println(maze); break;
                    case '2': if (maze.getDestPath() == 0) {
                        System.out.println("There is no path to destination");}
                    else {
                        for (int i = 0; i < maze.getDestPath(); i++) {
                            System.out.println("Path " + i + ":");
                            System.out.println(maze.displaySolution(i));
                            System.out.println();
                        }
                    }
                        break;
                    case '3': if (maze.getDestPath() == 0) {
                        System.out.println("There is no path to destination");}
                    else System.out.println(maze.displaySolution(maze.getMinPath()));
                        break;
                    case '4': if (maze.getDestPath() == 0) {
                        System.out.println("There is no path to destination");}
                    else System.out.println(maze.displaySolution(maze.getMaxPath()));
                        break;
                    case '5': if ((maze.getTotalPath() - maze.getDestPath()) == 0) {
                        System.out.println("There is no path to other destination");}
                    else {
                        for (int i = maze.getDestPath(); i < maze.getTotalPath(); i++) {
                            System.out.println("Path " + i + ":");
                            System.out.println(maze.displaySolution(i));
                            System.out.println();
                        }
                    }
                        break;
                    default: System.out.println("Please enter a valid number");
                }
            }
            quitS = false;
        }
    }
}
