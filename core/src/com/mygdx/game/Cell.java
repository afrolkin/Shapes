package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Frolkin on 31/10/2014.
 * the class representing a cell in the grid
 */
public class Cell {
    // Cell coordinates
    private int x;
    private int y;
    // The type of cell
    private CellType cellType;

    private CellState cellState;

    private List<Cell> neighbours;

    public static enum CellType {
        BLUE,
        RED,
        GREEN,
        YELLOW,
        EMPTY,
    }

    public static enum CellState {
        IDLE,
        CLEARING,
        FLASHING
    }

    public Cell(int x, int y, CellType type) {
        this.x = x;
        this.y = y;
        this.cellType = type;
        cellState = CellState.IDLE;
        neighbours = new ArrayList<Cell>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellType getType() {
        return cellType;
    }

    public CellState getState() { return cellState; }

    public boolean isEmpty() {
        return cellType == CellType.EMPTY;
    }

    public void addNeighbour(Cell cell) {
        neighbours.add(cell);
    }

    public void notifyNeighbours(CellState state) {

        for (Cell c : neighbours) {
            c.setState(state);

            // change this
            c.setColor(cellType);
        }
    }

    public void clearNeighbours() {
        neighbours.clear();
    }

    // "clears" this cell and it's neighbours
    public void clear() {
        setState(CellState.CLEARING);
        for (Cell c : neighbours) {
            if (c.getType().equals(this.getType()) && !c.getState().equals(CellState.CLEARING)) {
                c.clear();
            }
        }
        setColor(CellType.EMPTY);
    }

    public void debugPrint() {
        String printText = "";

        switch (cellType) {
            case BLUE:
                printText = "B";
                break;
            case RED:
                printText = "R";
                break;
            case YELLOW:
                printText = "Y";
                break;
            case GREEN:
                printText = "G";
                break;
            case EMPTY:
                printText = "E";
                break;
        }

        System.out.print(printText);
    }

    public void setState(CellState state) {
        cellState = state;
    }

    public void setColor(CellType type) {
        cellType = type;
    }
}
