package board.entities;

import board.exceptions.BoardException;

public class Board {
    private final Integer rows;
    private final Integer columns;
    private final Piece[][] pieces;

    public Board(Integer rows, Integer columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("[Board generation error]: There must be at least 1 row and 1 column to generate a board.");
        }

        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
    }

    public Piece piece(Integer row, Integer column) {
        if (!this.positionExists(row, column)) {
            throw new BoardException("[Position error]: Position (" + row + ", " + column + ") does not exists.");
        }

        return this.pieces[row][column];
    }

    public Piece piece(Position position) {
        if (!this.positionExists(position)) {
            throw new BoardException("[Position error]: Position (" + position + ") does not exists.");
        }

        return this.pieces[position.getRow()][position.getColumn()];
    }

    public Piece removePiece(Position position) {
        if (!this.thereIsAPiece(position)) {
            return null;
        }

        Piece auxiliaryPiece = this.piece(position);
        auxiliaryPiece.position = null;

        this.pieces[position.getRow()][position.getColumn()] = null;

        return auxiliaryPiece;
    }

    public Boolean positionExists(Integer row, Integer column) {
        return (0 <= row && row < this.rows)
                &&
                (0 <= column && column < this.columns);
    }

    public Boolean positionExists(Position position) {
        return this.positionExists(position.getRow(), position.getColumn());
    }

    public Boolean thereIsAPiece(Position position) {
        if (!this.positionExists(position)) {
            throw new BoardException("[Position error]: Position (" + position + ") does not exists.");
        }

        return this.piece(position.getRow(), position.getColumn()) != null;
    }

    public void placePiece(Piece piece, Position position) {
        if (this.thereIsAPiece(position)) {
            throw new BoardException("[Position error]: Position (" + position + ") already filled.");
        }

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

