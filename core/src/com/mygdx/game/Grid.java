package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing a grid of cells
 */
public class Grid {
    private Cell[][] cells;

    // TODO: Singleton pattern?
    // TODO: instatiate height/width in constructor/ custom height/width
    private int height = 8;
    private int width = 8;
    public Grid() {
        cells = new Cell[height][width];
    }

    public void generate() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // TODO: fix randomization
                Random rn = new Random();
                Cell.CellType type = Cell.CellType.RED;
                int n = rn.nextInt(4000);
                if (n < 1000){
                    type = Cell.CellType.BLUE;
                }
                else if (n <  2000) {
                    type = Cell.CellType.GREEN;
                }
                else if (n < 3000) {
                    type = Cell.CellType.RED;
                }
                else if (n < 4000) {
                    type = Cell.CellType.YELLOW;
                }

                cells[x][y] = new Cell(x, y, type);
            }
        }
        initNeighbours();
    }

    public void regenerate() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // TODO: fix randomization
                Random rn = new Random();
                Cell.CellType type = Cell.CellType.RED;
                int n = rn.nextInt(4000);
                if (n < 1000){
                    type = Cell.CellType.BLUE;
                }
                else if (n <  2000) {
                    type = Cell.CellType.GREEN;
                }
                else if (n < 3000) {
                    type = Cell.CellType.RED;
                }
                else if (n < 4000) {
                    type = Cell.CellType.YELLOW;
                }

                cells[x][y].setColor(type);
                cells[x][y].setState(Cell.CellState.IDLE);
                cells[x][y].clearNeighbours();
            }
        }
        initNeighbours();
    }

    // New algortihm: any cell that has an item below it is fixed
    // scan through the enitre grid (bottom left to top right) and find non-fixed cells, create a list of these cells
    // pass this list into a method called shift columns
    //  starting with the first element of the list, create another linked list, "column" by passing in the element into "create column"
    //      create a list starting with the element passed in, and check if the cell above it is not empty and not a "cieling", if it is, pass it into the list - and do the same check with this cell, otherwise stop and return the list
    //  calculate the vertical displacement for this column by looking at the fixed cell
    //  pass this column list into a method "shift column" with the displacement
    //      for each cell in this list, translate the cells down using the displacement
    //  do this for the rest of the list
    // fill in the blank spots with random blocks
    // TODO: generate one extra row at he top that isn't visible to the user to take care of cases where there are no "non-fixed" cells but you still have to fill in with random cells
    public void shiftCellsDown() {
        List<Cell> nonFixedCells = new ArrayList<Cell>();
        // traverse grid from left to right
        for (int x = 0; x < width; x++) {
            // ignore bottom most row
            for (int y = height - 2; y >= 0; y--) {
                Cell c = getCell(x, y);
                Cell belowC = getCell(x, y + 1);
                if (!c.getState().equals(Cell.CellState.CLEARING) && belowC.getState().equals(Cell.CellState.CLEARING)) {
                    nonFixedCells.add(c);
                }
            }
        }

        shiftColumns(nonFixedCells);

        clearNeighbours();
        initNeighbours();
    }

    private boolean coloumnIsEmpty(int column) {
        for (int i = 0; i < height; i++) {
            if (!getCell(column, i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // "cells" contains only the "nonfixed" cells of columns
    private void shiftColumns(List<Cell> cells) {
        int count = 1;
        for (Cell c: cells) {
            List<Cell> column = new ArrayList<Cell>();
            column.add(c);
            int y = c.getY();
            int x = c.getX();
            int i = 1;
            boolean generate = false;

            // need to generate extra blocks since the top most block needs to be shifted
            if (y == 0) {
                generate = true;
            }
            // form the "column" to shift
            // dont need to check if c is in the top row
            // continue generating row until y reaches the top row or the cell above is empty
            while (y != 0 && y - i >= 0 && !getCell(x, y - i).getState().equals(Cell.CellState.CLEARING)) {
                // need to generate extra blocks since the top most block needs to be shifted
                if (y - i == 0) {
                    generate = true;
                }
                column.add(getCell(x, y - i));
                i++;
            }
            // the amount to move this column down
            int displacement = emptyCellsBelow(c);
            shiftColumn(column, displacement, generate);

            // debug
            /*
            for (Cell d: column) {
                System.out.println("Column " + String.valueOf(count) + ": displacement: " + String.valueOf(displacement) + " " + String.valueOf(d.getX()) + " " + String.valueOf(d.getY()));
            }
            */
            count++;
        }
    }

    // return number of empty cells below c
    private int emptyCellsBelow(Cell c) {
        int emptyRows = 0;

        int y = c.getY();
        int x = c.getX();

        while (y != height && y + emptyRows+1 < height && getCell(x, y+emptyRows+1).getState().equals(Cell.CellState.CLEARING)) {
            emptyRows++;
        }
        return emptyRows;
    }

    // cells contains the cells of the column to shift down
    private void shiftColumn(List<Cell> cells, int displacement, boolean generate) {
        for (Cell i : cells) {
            getCell(i.getX(), i.getY() + displacement).setColor(i.getType());
            getCell(i.getX(), i.getY() + displacement).setState(i.getState());
            // might need to change the following two lines
            i.setColor(Cell.CellType.EMPTY);
            i.setState(Cell.CellState.CLEARING);
        }

        // need to generate random blocks to fill empty spaces
        // might need to add this to "cells"
        if (generate) {
            int numBlocksToCreate = displacement;
            for (int j = 1; j <= numBlocksToCreate; j++) {
                Random rn = new Random();
                // TODO: fix randomization
                Cell.CellType type = Cell.CellType.RED;
                int n = rn.nextInt(4000);
                if (n < 1000){
                    type = Cell.CellType.BLUE;
                }
                else if (n <  2000) {
                    type = Cell.CellType.GREEN;
                }
                else if (n < 3000) {
                    type = Cell.CellType.RED;
                }
                else if (n < 4000) {
                    type = Cell.CellType.YELLOW;
                }
                getCell(cells.get(0).getX(), cells.get(cells.size() - 1).getY() + displacement - j).setColor(type);
                getCell(cells.get(0).getX(), cells.get(cells.size() - 1).getY() + displacement - j).setState(Cell.CellState.IDLE);
            }
        }
    }

    private void initNeighbours() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell c = getCell(x, y);
                // also check if -1
                if (x+1 <= width -1 && y <= height - 1 && x+1 >= 0 && y >= 0) {
                    c.addNeighbour(getCell(x+1, y));
                }
                if (x <= width -1&& y - 1 <= height - 1 && x >= 0 && y-1 >= 0) {
                    c.addNeighbour(getCell(x, y-1));
                }
                if (x-1 <= width -1&& y <= height -1&& x-1 >= 0 && y >= 0) {
                    c.addNeighbour(getCell(x-1, y));
                }
                if (x <= width -1&& y + 1 <= height -1&& x >= 0 && y+1 >= 0) {
                    c.addNeighbour(getCell(x, y+1));
                }
            }
        }
    }

    private void clearNeighbours() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell c = getCell(x, y);
                c.clearNeighbours();
            }
        }
    }

    public void debugDraw() {
        if (cells[1][1] != null) {
            System.out.println("DEBUG GRID");
            for (int y = 0 ; y < height; y++) {
                System.out.print(y + " ");
                for (int x = 0; x < width; x++) {
                    cells[x][y].debugPrint();
                    System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println("* 0 1 2 3 4 5 6 7");

        } else {
            System.out.println("debugDraw called before generating grid");
        }
    }

    // TODO: throw exception if out of bounds
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }
}
