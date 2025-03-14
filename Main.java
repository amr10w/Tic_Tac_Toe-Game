import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
    
    public static void main(String[] args) {
        System.out.println("This Tic Tac Toe Game:");
        
        Scanner scanner = new Scanner(System.in);
        String[][] gameBoard = {
            {"", "", ""},
            {"", "", ""},
            {"", "", ""}
        };
        
        System.out.println("Tic Tac Toe - You are X, AI is O");
        System.out.println("Enter moves as 'row col' (0-2 space 0-2)");
        
        while (!Terminal(gameBoard)) {
            // Human turn (X)
            printBoard(gameBoard);
            System.out.print("Your move (row col): ");
            String[] input = scanner.nextLine().split(" ");
            int row = Integer.parseInt(input[0]);
            int col = Integer.parseInt(input[1]);
            
            if (gameBoard[row][col].equals("")) {
                gameBoard[row][col] = "x";
                
                if (Terminal(gameBoard)) {
                    printBoard(gameBoard);
                    break;
                }
                
                // AI turn (O)
                System.out.println("AI thinking...");
                gameBoard = MiniMax(gameBoard);
            } else {
                System.out.println("Invalid move! Spot taken.");
            }
        }
        
        printBoard(gameBoard);
        String result = Value(gameBoard);
        if (result.equals("+1")) System.out.println("You win!");
        else if (result.equals("-1")) System.out.println("AI wins!");
        else System.out.println("Draw!");
        
        scanner.close();
    }
    static void printBoard(String[][] gb) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(gb[i][j].equals("") ? "." : gb[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    

    static String[][] MiniMax(String [][] gb)
    {  
        
        if(Terminal(gb))
        {
            return gb; 
        }
        int bestValue = 0;
        String[][] bestMove = gb;
        if(Player(gb)==1)
        {
            bestValue = Integer.MIN_VALUE;
        for (String[][] nextState : Actions(gb)) {
            int eval = Integer.parseInt(Value(MiniMax(nextState))); // Recursive call
            if (eval > bestValue) {
                bestValue = eval;
                bestMove = nextState;
            }
            }


        }
        if(Player(gb)==-1)
        {
            bestValue = Integer.MAX_VALUE;
            for (String[][] nextState : Actions(gb)) {
                int eval = Integer.parseInt(Value(MiniMax(nextState))); // Recursive call
                if (eval < bestValue) {
                    bestValue = eval;
                    bestMove = nextState;
                }
            }
            
        }

        return bestMove; 
        
    }
    
    
    static boolean Terminal(String [][] gb)
    {  
        if (!Value(gb).equals("0")) {
            return true; // Someone won
        }
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(gb[i][j].equals(""))
                {
                    return false;
                }
            }
        }
        return true;
        
    }
    static String Value(String[][] gb) {
        int[] boards = encodeBoard(gb);
        int xBoard = boards[0], oBoard = boards[1];
        
        for (int mask : winningMasks) {
            if ((xBoard & mask) == mask) return "+1"; // X wins
            if ((oBoard & mask) == mask) return "-1"; // O wins
        }
                return "0";
    }
    static int Player(String [][] gb)
    {  
        int countX=0;
        int countO=0;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(gb[i][j].equals("x")){ countX++;}

                if(gb[i][j].equals("o")) {countO++;}

                
            }
        }
      
        //return (countX>countY) ? 1:(countY==countX) ? 0:-1;
        return (countX > countO) ? -1 : 1;
                
    }
    static  List<String[][]> Actions(String [][] gb)
    {
        
        List<String[][]> possibleMoves = new ArrayList<>();
        String player= (Player(gb)==1)?"x" :"o";

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(gb[i][j].equals(""))
                {
                    String[][] newBoard=copyOf(gb);
                    newBoard[i][j]=player;
                    possibleMoves.add(newBoard);
                    
                }
            }
        }
        return possibleMoves;

    }
    static String[][] copyOf(String [][] gb)
    {   
        String[][] copyString=new String[3][3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
               copyString[i][j]=gb[i][j];
            }
        }
        return copyString;


    }
    static int[] encodeBoard(String[][] gb) {
        int xBoard = 0, oBoard = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int pos = i * 3 + j; // Convert (i, j) to bit position
                    if ("x".equals(gb[i][j])) xBoard |= (1 << pos);
                    if ("o".equals(gb[i][j])) oBoard |= (1 << pos);
                }
            }
            return new int[]{xBoard, oBoard};
        }
        
        
        
        
        static int[] winningMasks = {
            0b111000000, // Row 1
            0b000111000, // Row 2
            0b000000111, // Row 3
            0b100100100, // Col 1
            0b010010010, // Col 2
            0b001001001, // Col 3
            0b100010001, // Diagonal 1
            0b001010100  // Diagonal 2
        };
}