package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * main game class
 */
public class StateGame extends State{

    public enum State {
        LOADING,
        IDLE,
        WAIT,
        SELECTED_CELL
    };

    // current game stste
    private State state;

    private Grid grid;

    public StateGame(Shapes game) {
        super(game);

        state = State.LOADING;

        grid = new Grid();
        grid.generate();

        init();
    }

    private void init() {

    }

    @Override
    public void unload() {

    }

    @Override
    public void load() {

    }

    @Override
    public void update(double deltaT) {

    }

    @Override
    public void render() {
        SpriteBatch batch = parent.getSpriteBatch();
        ShapeRenderer renderer = parent.getShapeRenderer();

        // TODO: make this dynamic or something
        int tempSpacing = 20;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (grid.getCell(x,y) != null) {
                    // draw the cells
                    Color c = Color.ORANGE;
                    renderer.begin(ShapeRenderer.ShapeType.Filled);
                    int radius = 30;
                    if (grid.getCell(x,y).getType() == Cell.CellType.BLUE) {
                        c = Color.BLUE;
                    }
                    else if (grid.getCell(x,y).getType() == Cell.CellType.RED) {
                        c = Color.RED;
                    }
                    else if (grid.getCell(x,y).getType() == Cell.CellType.YELLOW) {
                        c = Color.YELLOW;
                    }
                    else if (grid.getCell(x,y).getType() == Cell.CellType.GREEN) {
                        c = Color.GREEN;
                    }
                    renderer.setColor(c);
                    // fix this
                    renderer.circle(x * 70 + tempSpacing - 240, y * 80 + tempSpacing - 200, radius);
                    renderer.end();

                }
            }
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void assignResources() {
        super.assignResources();

        Gdx.input.setInputProcessor(this);
    }



}
