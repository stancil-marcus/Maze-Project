package maze;

/**
 *
 * @author Marcus
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Integer;
import java.util.*; 
public class Maze {
    private int row;
    private int col;
    private char [][] mazeLoc = new char [30][20];
    private int [][] coordinates = new int [31][21];
    LinkedList path = new LinkedList();
    
    /**
     * Starts maze
     */
    public Maze() 
    {
        intializeMaze();
        doMaze();
    }
    /**
     * Initializes the maze by reading the characters from a file and them printing them out 30 X 20. Then the user is asked if they want to start
     * the maze. The switch case serves as input validation. If the user selects no, the program will end; if not the user will be asked to enter a
     * location.
     */
    public void intializeMaze() 
    {
    coordinates = makeCoordinates(31,21);
    char choice;
    Scanner console = new Scanner(System.in);
    try{
    BufferedReader br = new BufferedReader(new FileReader("maze.txt"));
    for (int i = 0; i < 30; i++)
    {
        String line = br.readLine();    
        mazeLoc[i] = line.toCharArray();
    }
       
   }catch(IOException p){
       System.out.println("Error processing file: " + p);
   }
    
    for (int f = 0; f <= 30; f++){ //prints two dimensinal array
        for (int g = 0; g <= 20; g++){
            
            if (g == 0){
                System.out.println();
            }
            
            if ((f == 0 && g >= 0) || (g == 0 && f >= 1))
            {
                System.out.format("%4d", coordinates[f][g]);
            }
            else if (f > 0 && g > 0)
            System.out.format("%4c", mazeLoc[f-1][g-1]);
            
            
            
        }
    }
    
    System.out.println();//Space between maze and question
    }
    
    /**
     * This method inquires whether the user wants to start the maze. After they are done; it asks whether they'd
     * like to do the maze again.
     */
    public void doMaze(){
    char choice;
    do
    {
        Scanner console = new Scanner(System.in);
        System.out.println("Do you want to start the maze? (Y/N)");
        choice = console.next().charAt(0);
        switch(choice)
        {
            case 'n':
                System.out.println("Good bye!");
                break;
            case 'N':
                System.out.println("Good bye!");
                break;
            case 'y':
                restartMaze();
                if (startTheMaze()==true)
                {
                    printSolvedMaze();
                    System.out.println("I am free!");
                }
                else
                {
                    printNegMaze();
                    System.out.println("I am stuck!");
                }
                
                break;
            case 'Y':
                restartMaze();
                if (startTheMaze()==true)
                {
                    printSolvedMaze();
                    System.out.println("I am free!");
                    
                }
                else
                {
                    printNegMaze();
                    System.out.println("I am stuck!");
                }
                break;
            default:
                System.out.println("Invalid");
                break;
        }
    }while (choice !='N'&&choice != 'n');
    System.out.println();
    }
    
    /**
     * This creates the coordinates so that the user can pick the coordinate to start in the maze
     * @param r
     * @param c
     * @return 
     */
    public static int[][] makeCoordinates(int r, int c)
    {
    int rowCount;
    int [][] yes = new int[r][c];
    for (int row = 0; row < yes.length ; row++)
    {
        for (int column = 0; column < yes[row].length; column++)
        {
            if(column > 0)
            {
                yes[row][column] = (row+1)*(column);
            }
            else{
                yes[row][column] = (row)*(column+1);
            }
        }

    }
    return yes;
    }
    
      /**
       *This method allows the user to pick a starting point. Method getInt() serves as input validation. If the maze is solved successfully startTheMaze()
       * will be return as true; if not it'll return false.
       */
    boolean startTheMaze()
    {
        boolean f = false;
        int p = 0;
        Scanner console = new Scanner (System.in);
        System.out.println("Chose the point you want to start");
        row = getInt()-1;
        col = getInt()-1;
        boolean r = valid(row,col,p);
        while (r==false)//Checks to see if starting point is valid
        {
            System.out.println("Sorry, that point is not open. Chose the point you want to start");
            row = getInt()-1;
            col = getInt()-1;
            r = valid(row,col,p);
        }
        if (solveMaze(row,col)==true)
        {
            f = true;
        }
        else 
        {
            f = false;
        }
        return f;
    }
    /**
     * Attempts to solve the maze from valid starting point. Uses recursion to use find valid moves to traverse the maze. Stops when all moves are invalid
     * or the position makes it to E.
     * @param row row
     * @param col col
     * @return 
     */
    boolean solveMaze(int row, int col)
    {
       int i = 1;
       boolean fin = false;
        if(valid(row,col,i)){
            if (mazeLoc[row][col]=='E')
            {

                fin = true;
            }
            else
            {
            fin = solveMaze(row-1, col);  //up
            if (!fin)
            {
               fin = solveMaze (row, col+1); //right
            }
            if (!fin){
               fin = solveMaze (row+1, col); //down 
            }
            if (!fin)
            {
               fin = solveMaze (row, col-1); //left
            }
            if (fin == true && mazeLoc[row][col] != 'S')//Makes sure subsequent locations are turned into pluses
            {
                mazeLoc[row][col]='+';
            }
         }
        
        }
            
        return fin; 
}
    /**
     * Checks to see if location is valid and then creates a location. Turns into +'s  and old locations to -'s to make sure positions can't go back to old paths.
     * When the current location goes back over a plus, then the most recent location is popped from the linked list.
     * @param row row
     * @param col col
     * @param k sees if first point is available or not
     * @return found
     */
    boolean valid(int row, int col,int k)
    {
        boolean found = false;
       
        if (row >= 0 && row < 30 && // checks bounds
          col >= 0 && col < 20)
        {
            if(mazeLoc[row][col]=='0'&& k == 0) // checks to see if conditions are appropriate to make a start position; pushed to stack
            {
                Location m = new Location (row,col);
                path.push(m);
                mazeLoc[row][col]='S';
                found = true;
                
            }
            else if (mazeLoc[row][col]=='S') //makes sure starting position ins;t turn into a +
            {
                 Location m = new Location (row,col);
                 found = true;
            }
            else if (mazeLoc[row][col]=='0' && k > 0) //subsequent positions are added to the stack
            {
            Location m = new Location (row,col);
            path.push(m);
            mazeLoc[row][col]='+';
            found = true;
            }
            else if (mazeLoc[row][col]=='+') // if current location goes back; pop stack; turn to minus
            {
            Location n = (Location) path.peek();
            mazeLoc[n.getRow()][n.getCol()]='-';
            path.pop();
            mazeLoc[row][col]='-'; 
            
            }
            else if (mazeLoc[row][col]=='E') // if position makes it to e; add e
            {
                Location m = new Location (row,col);
                path.push(m);
                mazeLoc[row][col]='E';
                found = true;
            }
         
        }
        return found;
    }

       /**
        * Serves as input validation for integers
        * @return integer
        */
       private static int getInt() {
        Scanner keyInp = new Scanner(System.in);
        int integer = 0;
        boolean valid;
        do
        {
            if (keyInp.hasNextInt())
            {
                integer = keyInp.nextInt();
                valid = true;
            }
            else
            {
                valid = false;
                keyInp.next();
                System.out.print("Oops! That was not an integer, try again.");
            }
            }while (!valid);
        return integer;
       }
       
       /**
        * If the maze is solved, then this is called so that all minuses revert back to zeros. After that the maze is printed.
        */
       void printSolvedMaze(){
           for (int q = 0; q < 30; q++){
        for (int a = 0; a < 20; a++){
            if (mazeLoc[q][a]=='-')
                mazeLoc[q][a]='0';
        }
    }
           printMaze();
     
       }
       /**
        * If maze cannot be solved, all minuses are made into pluses again. After that the maze is printed.
        */
       void printNegMaze(){
           for (int q = 0; q < 30; q++){
        for (int a = 0; a < 20; a++){
            if (mazeLoc[q][a]=='-')
                mazeLoc[q][a]='+';
        }
    }
     
           printMaze();
    }
    
       /**
        * Prints maze
        */
    public void printMaze()
    {
        for (int f = 0; f <= 30; f++){ //prints two dimensinal array
        for (int g = 0; g <= 20; g++){
            
            if (g == 0){
                System.out.println();
            }
            
            if ((f == 0 && g >= 0) || (g == 0 && f >= 1))
            {
                System.out.format("%4d", coordinates[f][g]);
            }
            else if (f > 0 && g > 0)
            System.out.format("%4c", mazeLoc[f-1][g-1]);
        }
    }
    System.out.println();//Space between maze and question
    }
    
    /**
     * Restarts maze just incase user wants to solve it again
     */
    public void restartMaze() 
    {
    char choice;
    Scanner console = new Scanner(System.in);
    try{
    BufferedReader br = new BufferedReader(new FileReader("maze.txt"));
    for (int i = 0; i < 30; i++)
    {
        String line = br.readLine();    
        mazeLoc[i] = line.toCharArray();
    }
       
   }catch(IOException p){
       System.out.println("Error processing file: " + p);
   }
    }
}
    
    
    
    



