package maze;

/**
 *
 * @author Marcus
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*; 
public class Main {
    
     public static void main(String[] args){
        
        Maze m = new Maze();
     }
     
     /**
      * This class will be used to made locations that will be added and dropped
      * from a linked list so that the maze can be traversed.
      */
       static public class Location {
        private int row;
        private int col;
        /**
         * Default constructor
         */
        public Location()
        {
            row = 0;
            col = 0;
        }
        /**
         * Alternate constructor
         * @param i row
         * @param j column
         */
        public Location(int i, int j)
        {
            row = i;
            col = j;
        }
        /**
         * Returns row
         * @return row
         */
        int getRow()
        {
            return row;
        }
        /**
         * Returns column
         * @return col
         */
        int getCol()
        {
            return col;
        }
        /**
         * Sets the row.
         * @param row1 row
         */
        void setRow(int row1)
        {
            row = row1;
        }
        /*Sets the column
         * 
         * @param col1 col
         */
        void setCol(int col1)
        {
            col = col1;
        } 
}

        
    static class Maze{

        private int row;
        private int col;
        private final char [][] mazeLoc = new char [30][20];
        private int [][] coordinates = new int [31][21];
        LinkedList path = new LinkedList();
        static private int count;

        /**
         * Starts maze
         */
        public Maze() 
        {
            count = 0;
            intializeMaze();
            doMaze();
        }
        /**
         * Initializes the maze by reading the characters from a file and them 
         * printing them out 30 X 20. Then the user is asked if they want to start
         * the maze. The switch case serves as input validation. If the user 
         * selects no, the program will end; if not the user will be asked to 
         * enter a location.
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

        for (int row = 0; row <= 30; row++){ //prints two dimensinal array
            for (int col = 0; col <= 20; col++){

                if (col == 0){
                    System.out.println();
                }

                if ((row == 0 && col >= 0) || (col == 0 && row >= 1))
                {
                    System.out.format("%4d", coordinates[row][col]);
                }
                else if (row > 0 && col > 0)
                System.out.format("%4c", mazeLoc[row-1][col-1]);    
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
                    intializeMaze();
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
                    intializeMaze();
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
         * This creates the coordinates that border the maze.
         * @param r row
         * @param c column
         * @return The coordinates that border the maze
         */
        public int[][] makeCoordinates(int r, int c)
        {
        int [][] yes = new int[r][c];
        for (int rows = 0; rows < yes.length ; rows++)
        {
            for (int columns = 0; columns < yes[rows].length; columns++)
            {
                if(columns > 0)
                {
                    yes[rows][columns] = (rows+1)*(columns);
                }
                else{
                    yes[rows][columns] = (rows)*(columns+1);
                }
            }

        }
        return yes;
        }

          /**
           *This method allows the user to pick a starting point. Method getInt() 
           * serves as input validation. If the maze is solved successfully startTheMaze()
           * will be returned as true; if not it'll return false.
           */
        boolean startTheMaze()
        {
            count = 0;
            Scanner console = new Scanner (System.in);
            System.out.println("Use the coordinates along the maze to choose where you want to start.");
            System.out.print("Row: ");
            row = getInt()-1;
            System.out.print("Col: ");
            col = getInt()-1;
            boolean startCheck = valid(row,col,count);
            while (startCheck==false)//Checks to see if starting point is valid
            {
                System.out.println("Sorry, that point is not open. Chose the point you want to start");
                row = getInt()-1;
                col = getInt()-1;
                startCheck = valid(row,col,count);
            }
            
            boolean finishedSolving = solveMaze(row,col)==true;
            return finishedSolving;
        }
        /**
         * Attempts to solve the maze from valid starting point. 
         * Uses recursion to use find valid moves to traverse the maze. 
         * Stops when all moves are invalid
         * or the position makes it to E.
         * @param row row
         * @param col column
         * @return True if maze was solved; False if maze wasn't solved
         */
        boolean solveMaze(int row, int col)
        {
           count++; // 
           boolean fin = false;
            if(valid(row,col,count)){
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
         * Checks to see if location is valid and then creates a location. Turns 
         * available spaces into plus signs.
         * @param row row
         * @param col column
         * @param beganMaze Used to see if first point is available or not
         * @return found
         */
        boolean valid(int row, int col,int beganMaze)
        {
            boolean found = false;

            if (row >= 0 && row < 30 && // checks bounds
              col >= 0 && col < 20)
            {
                if(mazeLoc[row][col]=='0'&& beganMaze == 0) // checks to see if conditions are appropriate to make a start position; pushed to stack 
                {
                    Location m = new Location (row,col);
                    path.push(m);
                    mazeLoc[row][col]='S';
                    found = true;

                }
                else if (mazeLoc[row][col]=='S') //makes sure starting position doesn't turn into a +
                {
                     found = true;
                }
                else if (mazeLoc[row][col]=='0' && beganMaze > 0) //subsequent positions are added to the stack
                {
                Location newPos = new Location (row,col);
                path.push(newPos);
                mazeLoc[row][col]='+';
                found = true;
                }
                else if (mazeLoc[row][col]=='+') // if current location goes back; pop linked list; turn location in maze to minus
                {
                Location badPos = (Location) path.peek();
                mazeLoc[badPos.getRow()][badPos.getCol()]='-';
                path.pop();
                mazeLoc[row][col]='-'; 

                }
                else if (mazeLoc[row][col]=='E') // if position makes it to E; add E
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
            int getInt() {
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
            * If the maze is solved, then this is called so that all minuses 
            * revert back to zeros. After that the maze is printed. 
            * The negative signs represent a path already treaded.
            */
           void printSolvedMaze(){
               for (int row = 0; row < 30; row++){
            for (int col = 0; col < 20; col++){
                if (mazeLoc[row][col]=='-')
                    mazeLoc[row][col]='0';
            }
        }
               printMaze();

           }
           /**
            * If maze cannot be solved, all minus signs are made into plus signs again. 
            * After that the maze is printed. The negative signs represent a
            * path  that's already treaded.
            */
           void printNegMaze(){
            for (int row = 0; row < 30; row++){
                for (int col = 0; col < 20; col++){
                    if (mazeLoc[row][col]=='-')
                        mazeLoc[row][col]='+';
                }
        }

               printMaze();
        }

           /**
            * Prints maze
            */
        public void printMaze()
        {
        for (int row = 0; row <= 30; row++){ 
            for (int col = 0; col <= 20; col++){

                if (col == 0){
                    System.out.println();
                }

                if ((row == 0 && col >= 0) || (col == 0 && row >= 1))
                {
                    System.out.format("%4d", coordinates[row][col]);
                }
                else if (row > 0 && col > 0)
                System.out.format("%4c", mazeLoc[row-1][col-1]);
            }
        }
        System.out.println();//Space between maze and restart question
        }
    }
}









