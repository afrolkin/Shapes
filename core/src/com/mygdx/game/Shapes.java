package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Color;

// TODO: resolution independence
public class Shapes implements ApplicationListener {
	SpriteBatch batch;
    public static final int VIRTUAL_WIDTH = 1080;
    public static final int VIRTUAL_HEIGHT = 1920;
    private static final float ASPECT_RATIO = 0.5626f;

    // Assets
    private AssetManager assetManager = null;

    // TODO: create states for menu and tutorial, settings, etc
    // States
    private StateGame game;

    // Camera
    private OrthographicCamera camera;
    private Viewport viewPort;
    private Stage stage;

    // shape renderer
    private ShapeRenderer renderer;
	
	@Override
	public void create () {
        // View debug messages
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        batch = new SpriteBatch();
        renderer = new ShapeRenderer();

        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        assetManager = new AssetManager();

        //game.create();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(ShapesColor.BACKGROUND.r, ShapesColor.BACKGROUND.g, ShapesColor.BACKGROUND.b, ShapesColor.BACKGROUND.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        camera.update();

        Gdx.gl.glViewport((int) viewPort.getScreenX(), (int) viewPort.getScreenY(), (int) viewPort.getScreenWidth(), (int) viewPort.getScreenHeight());
        renderer.setProjectionMatrix(camera.combined);
        game.render();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.end();
	}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void resize (int arg0, int arg1) {
        float aspectRatio = (float)arg0/(float)arg1;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f);

        if(aspectRatio > ASPECT_RATIO) {
            scale = (float)arg1 / (float)VIRTUAL_HEIGHT;
            crop.x = (arg0 - VIRTUAL_WIDTH * scale) / 2.0f;
        } else if(aspectRatio < ASPECT_RATIO) {
            scale = (float)arg0 / (float)VIRTUAL_WIDTH;
            crop.y = (arg1 - VIRTUAL_HEIGHT * scale) / 2.0f;
        } else {
            scale = (float)arg0/(float)VIRTUAL_WIDTH;
        }

        float w = (float)VIRTUAL_WIDTH * scale;
        float h = (float)VIRTUAL_HEIGHT * scale;
        //viewPort = new Rectangle(crop.x, crop.y, w, h);
        viewPort = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        //viewPort.setCamera(camera);
        viewPort.setScreenBounds((int) crop.x, (int) crop.y, (int) w, (int) h);
        stage = new Stage(viewPort);

        game = new StateGame(this);
        Gdx.input.setInputProcessor(stage);

    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    public ShapeRenderer getShapeRenderer() {
        return renderer;
    }

    public Stage getStage() { return stage; }
}

