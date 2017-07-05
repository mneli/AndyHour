package be.mneli.rushhour.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class Car. An instance of Car is identified by an id, a size, an orientation
 * and a position.
 * Created by mneli on 05/07/17.
 */
public class Car {

    private final char id;
    private final int size;
    private final Orientation orientation;
    private Position currentPosition;

    /**
     * Construct an instance of Car.
     *
     * @param id              the identity of a car, the car is displayed with this parameter
     * @param size            the size of the car
     * @param orientation     the orientation of the car (horizontal or vertical) on
     *                        the board
     * @param currentPosition the current position of the car on the board.<br>
     *                        <ul><li>If the orientation of the car is horizontal, the current position
     *                        indicates the row number(which doesn't change) and the smallest column
     *                        number that the car occupies.</li>
     *                        <li>If the orientation of the car is vertical, the current position
     *                        indicates the smallest row number and the column number(which doesn't
     *                        change) that the car occupies.</li></ul>
     *                        For example : <br>
     *                        <ul><li>If the instance orientation is horizonal, the current position of
     *                        a car of size 2 that occupies the positions (1,2),(1,3) would be
     *                        (1,2)</li>
     *                        <li>If the instance orientation is vertical, the current position of a
     *                        car of size 2 that occupies the positions (1,2),(2,2) would be
     *                        (1,2)</li></ul>
     * @throws IllegalArgumentException if the size is smaller than 1
     */
    public Car(char id, int size, Orientation orientation,
               Position currentPosition) {
        if (size <= 0)
            throw new IllegalArgumentException("The size must be positive");

        this.id = id;
        this.size = size;
        this.orientation = orientation;
        this.currentPosition = currentPosition;
    }

    /**
     * Get the id of an instance of Car.
     *
     * @return the identity of the car
     */
    public char getId() {
        return this.id;
    }

    /**
     * Get the orientation of an instance of Car.
     *
     * @return the orientation of the car
     */
    public Orientation getOrientation() {
        return this.orientation;
    }

    /**
     * Get the currentPosition of an instance of Car.
     * <ul><li>If the orientation of the car is horizontal, the current position
     * indicates the row number(which doesn't change) and the smallest column
     * number that the car occupies.</li>
     * <li>If the orientation of the car is vertical, the current position
     * indicates the smallest row number and the column number(which doesn't
     * change) that the car occupies.</li></ul>
     * For example : <br>
     * <ul><li>If the instance orientation is horizonal, the current position of
     * a car of size 2 that occupies the positions (1,2),(1,3) would be
     * (1,2)</li>
     * <li>If the instance orientation is vertical, the current position of a
     * car of size 2 that occupies the positions (1,2),(2,2) would be
     * (1,2)</li></ul>
     *
     * @return the current position of the car
     */
    public Position getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Determine the state of an instance of Car
     *
     * @return the details of the car
     */
    @Override
    public String toString() {
        return "Car " + this.id + ". Size : " + this.size
                + ".\nOrientation :" + this.orientation
                + ". Current position : " + this.currentPosition;
    }

    /**
     * Move the current instance of car one step to the direction received in
     * parameter.
     *
     * @param direction the direction that the car should be moved
     * @throws IllegalArgumentException if the orientation of the current
     *                                  instance of Car is incompatible with the direction received in parameter.
     *                                  <br>For example : If the car is oriented horizontally, the direction
     *                                  can't be UP or DOWN.
     */
    public void move(Direction direction) {
        if (this.orientation == Orientation.HORIZONTAL
                && (direction == Direction.UP
                || direction == Direction.DOWN)) {
            throw new IllegalArgumentException(
                    "Car oriented horizontally, invalid direction.");
        } else if (this.orientation == Orientation.VERTICAL
                && (direction == Direction.LEFT
                || direction == Direction.RIGHT)) {
            throw new IllegalArgumentException(
                    "Car oriented vertically, invalid direction.");
        }

        this.currentPosition = this.currentPosition.getPosition(direction);
    }

    /**
     * Get the list of positions a car occupies. Example :
     * <ul><li>If a car of size 2 is oriented horizontally, and the current
     * position is (1,2), the returned list will contain the positions (1,2) and
     * (1,3)</li>
     * <li>If a car of size 3 is oriented vertically, and the current position
     * is (1,2), the returned list will contain the positions (1,2), (2,2) and
     * (3,3)</li></ul>
     *
     * @return a list of positions this instance of Car occupies without any
     * particular order in the list
     */
    public List<Position> getPositions() {
        List<Position> listPositions = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            switch (this.orientation) {
                case HORIZONTAL:
                    listPositions.add(new Position(
                            this.currentPosition.getRow(),
                            this.currentPosition.getColumn() + i));
                    break;
                case VERTICAL:
                    listPositions.add(new Position(
                            this.currentPosition.getRow() + i,
                            this.currentPosition.getColumn()));
            }
        }

        return listPositions;
    }

    /**
     * Test if two instance of Car are structurally equal.
     *
     * @param o the other instance of Car
     * @return true is two cars are structurally equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Car))
            return false;

        Car c = (Car) o;
        return (this.id == c.id
                && this.size == c.size
                && this.orientation == c.orientation
                && this.currentPosition.equals(c.currentPosition));
    }

    /**
     * Get the hashCode of an instance
     *
     * @return the hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id;
        hash = 43 * hash + this.size;
        hash = 43 * hash + Objects.hashCode(this.orientation);
        hash = 43 * hash + Objects.hashCode(this.currentPosition);
        return hash;
    }

}

