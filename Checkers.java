import java.util.Vector;
import java.util.Scanner;
public class Checkers
{
    //For Board games, indexing for board will be LL = origin
//    private Vector<CheckersPiece> redPieces;
//    private Vector<CheckersPiece> blackPieces;
//    private CheckersPiece[][] board;
    public Vector<CheckersPiece> redPieces;
    public Vector<CheckersPiece> blackPieces;
    public CheckersPiece[][] board;
    private boolean gameOver;
    public boolean redTurn;
    private CheckersPiece currentPiece;

    Checkers()
    {
        gameOver = false;
        redTurn = true;
        currentPiece = new CheckersPiece();
        setUpBoard();
    }
    
    private void setUpBoard()
    {
        redPieces = new Vector<CheckersPiece>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 8; j++)
                if ((i%2 == 0 && j%2 == 0) || (i%2 == 1 && j%2 == 1))
                    redPieces.add(new CheckersPiece(j,i,true));
                    
        blackPieces = new Vector<CheckersPiece>();
        for (int i = 5; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if ((i%2 == 0 && j%2 == 0) || (i%2 == 1 && j%2 == 1))
                    blackPieces.add(new CheckersPiece(j,i,false));
        board = new CheckersPiece[8][8];
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                board[i][j] = new CheckersPiece();
                board[i][j].pos(new Position(j,i));
            }
        }
        for (CheckersPiece redPiece : redPieces)
            board[redPiece.y()][redPiece.x()] = redPiece;
        for (CheckersPiece blackPiece : blackPieces)
            board[blackPiece.y()][blackPiece.x()] = blackPiece;
    }

    public boolean gameOver() { return gameOver; }


    public Vector<Move> getPossibleMoves(boolean red)
    {
        Vector<Move> moves = new Vector<Move>();
        CheckersPiece invalid = new CheckersPiece();
        if (currentPiece.equals(invalid))
        {
            if (red)
                for (CheckersPiece redPiece : redPieces)
                    moves.addAll(possibleMoves(redPiece));
            else
                for (CheckersPiece blackPiece : blackPieces)
                    moves.addAll(possibleMoves(blackPiece));
        }
        else
        {
            System.out.println("red: " + invalid.red() + "," + currentPiece.red());
            System.out.println("king: " + invalid.king() + "," + currentPiece.king());
            System.out.println("isPiece: " + invalid.isPiece() + "," + currentPiece.isPiece());
            System.out.println("pos: " + invalid.prettyPos() + "," + currentPiece.prettyPos());
            moves.addAll(possibleMoves(currentPiece));
        }
        //Need to filter out moves in situation where there is a capture
        boolean hasCapture = false;
        for (Move move : moves)
        {
            if (move.capture == true)
                hasCapture = true;
            break;
        }
        if (hasCapture)
        {
            for (int i = moves.size()-1; i >= 0; i--)
            {
                if (!(moves.get(i).capture)) 
                {
                    System.out.println(moves.get(i).moveTo.prettyPos());
                    moves.remove(i);
                }
            }
        }
        return moves;
    }

    public Move checkDirection(CheckersPiece piece, boolean xOffsetPositive, boolean yOffsetPositive)
    {
        if (!piece.isPiece())
            return new Move();

        int xOffset = (xOffsetPositive) ? 1 : -1;
        int yOffset = (yOffsetPositive) ? 1 : -1;

        //check if invalid position
        if (piece.x() + xOffset > 7 || piece.x() + xOffset < 0 || piece.y() + yOffset > 7 || piece.y() + yOffset < 0)
            return new Move();

        CheckersPiece checkDir = board[piece.y() + yOffset][piece.x() + xOffset];
        //there is a valid diagonal position
        if (!checkDir.isPiece())
            return new Move(piece, checkDir, false);
        //there is a pice of the same colour blocking the diagonal direction
        else if(checkDir.red() == piece.red())
            return new Move();
        //there is a piece of the opposite colour blocking the diagonal direction
        else
        {
            xOffset *= 2;
            yOffset *= 2;
            //the jump goes to an invalid position
            if (piece.x() + xOffset > 7 || piece.x() + xOffset < 0 || piece.y() + yOffset > 7 || piece.y() + yOffset < 0)
                return new Move();
            CheckersPiece checkJumpDir = board[piece.y() + yOffset][piece.x() + xOffset];
            //there is no piece in where the jump would land
            if (!checkJumpDir.isPiece())
                return new Move(piece, checkJumpDir, true);
            //there is a piece blocking the jump
            else
                return new Move();
        }
    }
    public Vector<Move> possibleMoves(CheckersPiece piece)
    {
        Vector<Move> ret= new Vector<Move>();
        CheckersPiece invalid = new CheckersPiece();
        if (!piece.isPiece())
            return ret;
        //just need to check two directions
        if (!piece.king())
        {
            Move left = checkDirection(piece, false, piece.red());
            Move right = checkDirection(piece, true, piece.red());
        
            if (left.capture == false && right.capture == false)
            {
                if (!left.moveTo.equals(invalid))
                    ret.add(left);
                if (!right.moveTo.equals(invalid))
                    ret.add(right);
            }
            else
            {
                if (left.capture)
                   ret.add(left); 
                if (right.capture)
                   ret.add(right); 
            }
        }
        else
        {
            Move leftDown = checkDirection(piece, false, false);
            Move leftUp = checkDirection(piece, false, true);
            Move rightDown = checkDirection(piece, true, false);
            Move rightUp = checkDirection(piece, true, true);
            //If there are no moves that require captures
            if (!leftDown.capture && !leftUp.capture && !rightDown.capture && !rightUp.capture)
            {
                if (!leftDown.moveTo.equals(invalid))
                    ret.add(leftDown);
                if (!leftUp.moveTo.equals(invalid))
                    ret.add(leftUp);
                if (!rightDown.moveTo.equals(invalid))
                    ret.add(rightDown);
                if (!rightUp.moveTo.equals(invalid))
                    ret.add(rightUp);
            }
            //Add all possible capture moves
            else
            {
                if (leftDown.capture)
                    ret.add(leftDown);
                if (leftUp.capture)
                    ret.add(leftUp);
                if (rightDown.capture)
                    ret.add(rightDown);
                if (rightUp.capture)
                    ret.add(rightUp);
            }
        }
        return ret;
    }

    public void printBoard()
    {
        for (int i = 7; i > -1; i--)
        {
            System.out.print("  " + (i+1) + ": ");
            for (int j = 0; j < 8; j++)
                System.out.print(board[i][j].pieceString() + " ");
            System.out.print("\n");
        }
        System.out.print("     ");
        for (int j = 0; j < 8; j++)
            System.out.print((char)(j+65) + " ");
        System.out.print("\n");
        System.out.print("\n");
    }

    public int printAndEvaluateMenu()
    {
        Scanner scanner = new Scanner(System.in);
        if (redTurn)
        {
            System.out.println("  Red's turn!");
            System.out.println("  1. Show Available Moves");
            System.out.println("  2. Resign");
        }
        else
        {
            System.out.println("  Black's turn!");
            System.out.println("  1. Show Available Moves");
            System.out.println("  2. Resign");
        }
        System.out.print("  Enter selection: ");
        int index = scanner.nextInt();
        if (index == 1)
        {
            return 0;
        }
        else if(index == 2)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public void resign()
    {
        if (redTurn)
        {
            System.out.println("  Red resigns! Black wins!");
        }
        else
        {
            System.out.println("  Black resigns! Red wins!");
        }
    }

    public void printPossibleMoves(boolean red)
    {
        Vector<Move> moves = getPossibleMoves(red);
        for (Move move : moves)
            System.out.println(move.movingPiece.prettyPos() + " -> " + move.moveTo.prettyPos());
    }

    public int printAndEvaluateTurn()
    {
        Vector<Move> moves = getPossibleMoves(redTurn);
        for (int i = 0; i < moves.size(); i++)
        {
            System.out.println("  " + (i+1) + ": " + moves.get(i).movingPiece.prettyPos() + " -> " + moves.get(i).moveTo.prettyPos());
        }
        System.out.print("  Enter selection: ");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        if (index-1 < 0 || index-1 > moves.size()-1)
        {
            System.out.println("Invalid input");
            return -1;
        }

        return -1;
    }

    public void play()
    {
    //    while (!gameOver)
    //    {
            int menuRet = -1;
            while (menuRet == -1)
            {
                printBoard();
                menuRet = printAndEvaluateMenu();
            }
            if (menuRet == 1)
            {
                //Do Resign and clean up;
                resign();
                return;
            }
            else
            {
                int turnRet = -1;
                while(turnRet == -1)
                {
                    turnRet = printAndEvaluateTurn();
                }
                //Do turn
            }
    //    }
    }

    public static void main(String [] args)
    {
        Checkers game = new Checkers();
        game.play();
//        game.printBoard();
//        game.printPossibleMoves(true);

    }
}
