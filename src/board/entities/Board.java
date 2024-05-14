package board.entities;

public class Board {
    private Integer rows;
    private Integer columns;
    private Piece[][] pieces;

    public Board() {
    }

    public Board(Integer rows, Integer columns) {
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
    }

    public Piece piece(Integer row, Integer column) {
        return this.pieces[row][column];
    }

    public Piece piece(Position position) {
        return this.pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {
        piece.position = position;
        this.pieces[position.getRow()][position.getColumn()] = piece;
    }
    public Integer getRows() {
        return this.rows;
    }

    public Integer getColumns() {
        return this.columns;
    }
}

