import java.util.Vector;
public class CheckersPiece extends Piece
{
    private boolean red;
    private boolean king;
    CheckersPiece()
    {
        super();
        this.red = true;
        this.king = false;
    }
    CheckersPiece(int x, int y, boolean red)
    {
        super(x,y);
        this.red = red;
        this.king = false;
    }
    CheckersPiece(Position p, boolean red)
    {
        super(p);
        this.red = red;
        this.king = false;
    }

    public boolean red() { return red; }
    public void red(boolean isRed) { this.red = isRed; }

    public boolean king() { return king; }
    public void king(boolean isKing) { this.king = isKing; }

    public String pieceString()
    {
        String ret;
        if (!isPiece)
        {
            ret = ".";
        }
        else
        {
            if (red && king)
                ret = "R";
            else if (red && !king)
                ret = "r";
            else if (!red && king)
                ret = "B";
            else
                ret = "b";
        }
        return ret;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof CheckersPiece)
        {
            return super.equals(obj) 
                && this.red == ((CheckersPiece)obj).red 
                && this.king == ((CheckersPiece)obj).king;
        }
        return false;
    }

}
