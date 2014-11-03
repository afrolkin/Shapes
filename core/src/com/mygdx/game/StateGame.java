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

    public enum State {
        LOADING,
        IDLE,
        WAIT,
        SELECTED_CELL
    };

    public class ShapeActor extends Actor {
        float x, y = 0;
        float width, height = 0;
        Cell.CellType color;
        public boolean started = false;
        ShapeRenderer renderer = parent.getShapeRenderer();

        // TODO: fix touch bounds for each block
        public ShapeActor(float x, float y, Cell.CellType type) {
            this.x = x;
            this.y = y;
            height = 30;
            width = 30;
            color = type;
            setBounds(x, y, width, height);
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    ((ShapeActor)event.getTarget()).started = true;
                    return true;
                }
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    ((ShapeActor)event.getTarget()).started = false;
                }
            });
        }

        @Override
        public void draw(Batch batch, float alpha) {
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            Color c = Color.PURPLE;
            int radius = (int)height;
            if (color  == Cell.CellType.BLUE) {
                c = Color.BLUE;
            }
            else if (color == Cell.CellType.RED) {
                c = Color.RED;
            }
            else if (color == Cell.CellType.YELLOW) {
                c = Color.YELLOW;
            }
            else if (color == Cell.CellType.GREEN) {
                c = Color.GREEN;
            }
            renderer.setColor(c);
            // fix this
            renderer.circle(x, y, radius);
            renderer.end();
       }

        @Override
        public void act(float delta) {
            if(started) {
                height = 50;
            } else {
                height = 30;
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

        // TODO: make this dynamic or something
        int tempSpacing = 20;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
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
                    ShapeActor s = new ShapeActor(x * 70 + 300, y * 70 + 1000, grid.getCell(x,y).getType());
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
