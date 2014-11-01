package com.mygdx.game;

import java.util.Random;

/**
 * Class representing a grid of cells
 */
public class Grid {
    private Cell[][] cells;

    // TODO: Singleton pattern?
    public Grid() {
        cells = new Cell[8][8];
    }

    public void generate() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
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
    }

    public void debugDraw() {
        if (cells[1][1] != null) {
            System.out.println("DEBUG GRID");
            for (int x = 0; x < 8; x++) {
                System.out.print(x + " ");
                for (int y = 0; y < 8; y++) {
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
