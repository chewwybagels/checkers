public class Move
{
    public CheckersPiece movingPiece;
    public CheckersPiece moveTo;
    public boolean capture;

    Move()
    {
        this.movingPiece = new CheckersPiece();
        this.moveTo = new CheckersPiece();
        this.capture = false;
    }

    Move(CheckersPiece piece, CheckersPiece moveTo, boolean capture)
    {
        this.movingPiece = piece;
        this.moveTo = moveTo;
        this.capture = capture;
    }
}
