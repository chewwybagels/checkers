public class Piece
{
    protected Position pos;
    protected boolean isPiece;
    Piece()
    {
        this.isPiece = false;
        this.pos = new Position();
    }
    Piece(int x, int y)
    {
        this.isPiece = true;
        this.pos = new Position(x,y);
    }
    Piece(Position p)
    {
        this.isPiece = true;
        this.pos = new Position(p);
    }
    //Getters/Setters
    public Position pos() { return pos; }
    public void pos(Position pos) { this.pos = pos; } 

    public int x() { return pos.x; }
    public void x(int x) { pos.x = x; }

    public int y() { return pos.y; }
    public void y(int y) { pos.y = y; }

    public boolean isPiece() { return isPiece; }
    public void isPiece(boolean isPiece) { this.isPiece = isPiece; }

    public String prettyPos()
    {
        int convert = pos.x + 65;
        char letter = (char)convert;
        return letter + String.valueOf(pos.y+1);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj.getClass() == getClass())
        {
            return pos.equals(((Piece)obj).pos) 
                && isPiece == ((Piece)obj).isPiece;
        }
        return false;
    }

}
