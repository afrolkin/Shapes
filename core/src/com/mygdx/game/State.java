package com.mygdx.game;

/**
 * Created by Andrew Frolkin on 31/10/2014.
 */
import com.badlogic.gdx.InputProcessor;

public class State implements InputProcessor {
    protected Shapes parent = null;

    public State(Shapes game) {
        parent = game;
    }

    // GAME LOOP

    public void update(double deltaT) {

    }

    public void render() {

    }

    // LIFE - CYCLE
    public void pause() {

    }

    public void resume() {

    }

    // EVENTS

    @Override
    public boolean keyDown(int arg0) {
        return false;
    }

    @Override
    public boolean keyTyped(char arg0) {
        return false;
    }

    @Override
    public boolean keyUp(int arg0) {
        return false;
    }

    @Override
    public boolean scrolled(int arg0) {
        return false;
    }

    @Override
    public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
        return false;
    }

    @Override
    public boolean touchDragged(int arg0, int arg1, int arg2) {
        return false;
    }

    @Override
    public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    // MEMORY MANAGEMENT

    public void load() {

    }

    public void unload() {

    }

    public void assignResources() {
        parent.getAssetManager().finishLoading();
    }
}