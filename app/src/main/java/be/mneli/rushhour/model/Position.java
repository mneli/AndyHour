package be.mneli.rushhour.model;

/**
 * Class Position. A position is identified by a row number and a column
 * number.
 * Created by mneli on 05/07/17.
 */

public class Position {

    private final int row;
    private final int column;

    /**
     * Construct an instance of Position.
     *
     * @param row    the row number of a position
     * @param column the column number of a position
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Get the row of an instance of Position.
     *
     * @return the row of the position
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Get the column of an instance of Position.
     *
     * @return the column of the position
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Determine the state of an instance of Position.
     *
     * @return the coordinates of the position
     */
    @Override
    public String toString() {
        return "(" + this.row + "," + this.column + ")";
    }

    /**
     * Get the next position in the direction next to the current instance of
     * Position. <br>For example : If the current position is (2,3) and the
     * direction is UP, the translated position would be (1,3)
     *
     * @param direction the direction to move
     * @return the new position
     */
    public Position getPosition(Direction direction) {
        Position newPosition = null;
        switch (direction) {
            case UP:
                newPosition = new Position(this.row - 1, this.column);
                break;
            case DOWN:
                newPosition = new Position(this.row + 1, this.column);
                break;
            case LEFT:
                newPosition = new Position(this.row, this.column - 1);
                break;
            case RIGHT:
                newPosition = new Position(this.row, this.column + 1);
        }
        return newPosition;
    }

    /**
     * Test if two instance of Position are structurally equal.
     *
     * @param o the other instance of Position
     * @return true is two positions are structurally equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Position))
            return false;

        Position p = (Position) o;
        return (this.row == p.row && this.column == p.column);
    }

    /**
     * Get the hashCode of an instance
     *
     * @return the hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.row;
        hash = 79 * hash + this.column;
        return hash;
    }

}
