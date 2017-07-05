package be.mneli.rushhour.model;

import java.util.List;

/**
 * Class Board. An instance of Board is represented by a grid and an exit
 * position inside the grid.
 * Created by mneli on 05/07/17.
 */

public class Board {

    private Car[][] grid;
    private Position exit;

    /**
     * Construct an instance of Board.
     *
     * @param height the number of rows of the board
     * @param width  the number of columns of the board
     * @param exit   the position of the exit. The exit position must be on a
     *               border and can't be on any corner
     * @throws IllegalArgumentException if <ul><li>height smaller than 1</li>
     *                                  <li>width smaller than 1</li>
     *                                  <li>exit position is any any corner</li></ul>
     */
    public Board(int height, int width, Position exit) {
        if (height <= 0) {
            throw new IllegalArgumentException("Invalid height");
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Invalid width");
        }

        if (exit.getRow() != 0 && exit.getRow() != height - 1
                && exit.getColumn() != 0 && exit.getColumn() != width - 1) {
            throw new IllegalArgumentException("Invalid exit position");
        }

        if (exit.getRow() == 0 && (exit.getColumn() <= 0
                || exit.getColumn() >= width - 1)) {
            throw new IllegalArgumentException("Invalid exit position");
        }
        if (exit.getRow() == height - 1 && (exit.getColumn() <= 0
                || exit.getColumn() >= width - 1)) {
            throw new IllegalArgumentException("Invalid exit position");
        }
        if (exit.getColumn() == 0 && (exit.getRow() <= 0
                || exit.getRow() >= height - 1)) {
            throw new IllegalArgumentException("Invalid exit position");
        }
        if (exit.getColumn() == width - 1 && (exit.getRow() <= 0
                || exit.getRow() >= height - 1)) {
            throw new IllegalArgumentException("Invalid exit position");
        }

        this.grid = new Car[height][width];
        this.exit = exit;
    }

    /**
     * Construct an instance of Board with default values. The default values
     * are :
     * <ul><li>height : 6</li>
     * <li>width : 6</li>
     * <li>exit at Position (2,5)</li></ul>
     */
    public Board() {
        this(6, 6, new Position(2, 5));
    }

    /**
     * Get the exit of an instance of Board.
     *
     * @return the position of the exit
     */
    public Position getExit() {
        return this.exit;
    }

    /**
     * Get the height(number of rows) of a board.
     *
     * @return the height
     */
    public int getHeight() {
        return this.grid.length;
    }

    /**
     * Get the width(number of columns) of a board.
     *
     * @return the width
     */
    public int getWidth() {
        return this.grid[0].length;
    }

    /**
     * Get the car at the position received in parameter.
     *
     * @param pos the position to check
     * @return a car or null if the position received in parameter doesn't
     * contains a car
     */
    public Car getCarAt(Position pos) {
        return this.grid[pos.getRow()][pos.getColumn()];
    }

    /**
     * Verify if a car can be placed on board.
     * <ul><li>Check if every position that the car received in parameter want
     * to occupy on board doesn't contain already an another car</li>
     * <li>Check if every position that the car received in parameter want to
     * occupy on board are on board and not outside</li></ul>
     *
     * @param car the instance of Car
     * @return true if the car can be placed
     */
    public boolean canPut(Car car) {
        List<Position> listPos = car.getPositions();
        int index = 0;
        while (index < listPos.size()
                && (listPos.get(index).getRow() >= 0
                && listPos.get(index).getRow() < getHeight())
                && (listPos.get(index).getColumn() >= 0
                && listPos.get(index).getColumn() < getWidth())
                && getCarAt(listPos.get(index)) == null) {
            index++;
        }
        return index == listPos.size();
    }

    /**
     * Place a car on board.
     *
     * @param car the instance of Car to place on board
     */
    public void put(Car car) {

        List<Position> listPos = car.getPositions();
        for (Position pos : listPos) {
            this.grid[pos.getRow()][pos.getColumn()] = car;
        }
    }

    /**
     * Remove a car from the board. Initialize the cells occupied by the car to
     * null
     *
     * @param car the car to remove
     */
    public void remove(Car car) {
        List<Position> listPos = car.getPositions();
        for (Position pos : listPos) {
            this.grid[pos.getRow()][pos.getColumn()] = null;
        }
    }

    /**
     * Get the car which has the same id as the parameter.
     *
     * @param id the car id
     * @return null if no car on board has the same id as the parameter, else
     * return the car
     */
    public Car getCar(char id) {
        int row = 0, column;
        Car car = null;
        boolean contains = false;
        while (row < getHeight() && !contains) {
            column = 0;
            while (column < getWidth() && !contains) {
                car = getCarAt(new Position(row, column));
                if (car != null)
                    contains = (id == this.grid[row][column].getId());
                column++;
            }
            row++;
        }
        if (contains)
            return car;
        return null;
    }

    /**
     * Verify if the car received in parameter can move to the direction
     * received in parameter.
     *
     * @param car       the car to move
     * @param direction the direction to move
     * @return true if the movement is permitted
     */
    public boolean canMove(Car car, Direction direction) {
        boolean canMove;
        List<Position> listPos = car.getPositions();
        Car newCar = new Car(car.getId(), listPos.size(),
                car.getOrientation(), car.getCurrentPosition());
        newCar.move(direction);
        remove(car);
        canMove = canPut(newCar);
        put(car);
        return canMove;
    }
}
