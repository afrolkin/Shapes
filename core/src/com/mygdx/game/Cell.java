package com.mygdx.game;

/**
 * Created by Andrew Frolkin on 31/10/2014.
 * the class representing a cell in the grid
 */
public class Cell {
    // Cell coordinates
    private int x;
    private int y;
    // The type of cell
    public CellType cellType;

    public static enum CellType {
        BLUE,
        RED,
        GREEN,
        YELLOW,
        EMPTY,
    }

    public Cell(int x, int y, CellType type) {
        this.x = x;
        this.y = y;
        this.cellType = type;
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

    public boolean isEmpty() {
        return cellType == CellType.EMPTY;
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
}
