package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * main game class
 */
public class StateGame extends State{
    private static final float mHeight = 50;
    private static final float mWidth = 50;

    public enum State {
        LOADING,
        IDLE,
        WAIT,
        SELECTED_CELL
    };

    public class RestartActor extends Actor {
        float x, y = 0;
        float width = mWidth;
        float height = mHeight;
        public boolean started = false;
        ShapeRenderer renderer = parent.getShapeRenderer();

        public RestartActor(float x, float y) {
            this.x = x;
            this.y = y;
            height = 30;
            width = 30;
            setBounds(x - width, y - width, 2 * width, 2 * height);
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    grid.regenerate();
                    grid.debugDraw();
                }
            });
        }

        @Override
        public void draw(Batch batch, float alpha) {
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            Color c = ShapesColor.PURPLE;
            int radius = (int)height;
            renderer.setColor(c);
            // fix this
            renderer.circle(x, y, radius);
            renderer.end();
        }
    }

    public class ShapeActor extends Actor {
        float x, y = 0;
        float width = mWidth;
        float height = mHeight;
        public Cell cell;
        Cell.CellType color;
        public boolean started = false;
        ShapeRenderer renderer = parent.getShapeRenderer();

        // TODO: fix touch bounds for each block
        public ShapeActor(float x, float y, Cell.CellType type, Cell cell) {
            this.x = x;
            this.y = y;
            height = 30;
            width = 30;
            color = type;
            this.cell = cell;
            setBounds(x - width, y - width, 2 * width, 2 * height);
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    ((ShapeActor)event.getTarget()).started = true;
                    ((ShapeActor)event.getTarget()).cell.notifyNeighbours(Cell.CellState.FLASHING);
                    return true;
                }
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    ((ShapeActor)event.getTarget()).started = false;
                    ((ShapeActor)event.getTarget()).cell.notifyNeighbours(Cell.CellState.IDLE);
                    ((ShapeActor)event.getTarget()).cell.clear();
                    grid.shiftCellsDown();
                }
            });
        }

        @Override
        public void draw(Batch batch, float alpha) {
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            Color c = Color.PURPLE;
            int radius = (int)height;
            color = cell.getType();
            if (color  == Cell.CellType.BLUE) {
                c = ShapesColor.BLUE;
            } else if (color == Cell.CellType.RED) {
                c = ShapesColor.RED;
            } else if (color == Cell.CellType.YELLOW) {
                c = ShapesColor.YELLOW;
            } else if (color == Cell.CellType.GREEN) {
                c = ShapesColor.GREEN;
            } else if (color == Cell.CellType.EMPTY) {
                c = ShapesColor.TEMP_CLEAR;
            }
            renderer.setColor(c);
            // fix this
            renderer.circle(x, y, radius);
            renderer.end();
        }

        @Override
        public void act(float delta) {
            if(started) {
                height = mHeight + 20;
            } else {
                height = mHeight;
            }
        }
    }

    // current game stste
    private State state;

    private Grid grid;

    public StateGame(Shapes game) {
        super(game);

        state = State.LOADING;

        grid = new Grid();
        grid.generate();
        grid.debugDraw();

        init();
    }

    private void init() {
        create();
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

    public void create() {
        SpriteBatch batch = parent.getSpriteBatch();
        ShapeRenderer renderer = parent.getShapeRenderer();

        RestartActor restart = new RestartActor(900, 1800);
        restart.setTouchable(Touchable.enabled);
        parent.getStage().addActor(restart);

        // TODO: make this dynamic or something
        int tempSpacing = 120;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (grid.getCell(x,y) != null) {
                    // draw the cells
                    /*
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
                    */
                    ShapeActor s = new ShapeActor(x * tempSpacing + 200, 1920 - 400 - y * tempSpacing, grid.getCell(x,y).getType(), grid.getCell(x,y));
                    s.setTouchable(Touchable.enabled);
                    parent.getStage().addActor(s);
                    //s.draw();
                }
            }
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void render() {
        parent.getStage().act(Gdx.graphics.getDeltaTime());
        parent.getStage().draw();
    }


    @Override
    public void assignResources() {
        super.assignResources();

        Gdx.input.setInputProcessor(this);
    }



}
