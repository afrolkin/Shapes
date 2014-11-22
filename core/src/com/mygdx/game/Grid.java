package com.mygdx.game;

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
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
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
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
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

    private void initNeighbours() {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
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

    public void debugDraw() {
        if (cells[1][1] != null) {
            System.out.println("DEBUG GRID");
            for (int x = 0; x < height; x++) {
                System.out.print(x + " ");
                for (int y = 0; y < width; y++) {
                    cells[x][y].debugPrint();
                    System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println("  0 1 2 3 4 5 6 7");

        } else {
            System.out.println("debugDraw called before generating grid");
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

}
