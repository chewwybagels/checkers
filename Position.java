public class Position
{
    public int x;
    public int y;

    Position()
    {
        x = -1;
        y = -1;
    }

    Position(int x,int y)
    {
        this.x = x;
        this.y = y;
    }
    Position(Position pos)
    {
        this.x = pos.x;
        this.y = pos.y;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Position)
        {
            return x == ((Position)obj).x 
                && y == ((Position)obj).y;
        }
        return false;
    }
}

