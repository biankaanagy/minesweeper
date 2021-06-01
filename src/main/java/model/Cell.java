package model;
/**
 * {@code Cell} representing squares on the board.
 */
public class Cell {
    /**
     * Square's value.
     */
    int value;
    /**
     * Square's opened or closed.
     */
    boolean opened;
    /**
     * Square's flagged or not.
     */
    boolean flag;

    /**
     * {@code Cell} constructor.
     */
    public Cell(int value, boolean opened, boolean flag) {
        this.value = value;
        this.opened = opened;
        this.flag = flag;
    }
}
