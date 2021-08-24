package com.duodev.placeholder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class LevelScreen implements Screen {

    final Placeholder game;
    final OrthographicCamera camera;
    final CameraController cameraController;
    final InputHandler inputHandler;

    public State state = State.RUNNING;

    private int fps = 1000;
    private long timeMillis = TimeUtils.millis();


    Player player;

    public LevelScreen(final Placeholder game){

        this.game = game;

        inputHandler = new InputHandler(this);

        camera = CameraFactory.createStandardCamera();

        player = new Player(this.game, 32, 32, 100, 100);

        cameraController = new CameraController(camera, player);

        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        Gdx.graphics.setContinuousRendering(false);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        inputHandler.handleInput();

        if(TimeUtils.millis() - timeMillis > 300){
            timeMillis = TimeUtils.millis();
            fps = MathUtils.floor(1 / delta);
        }

        ScreenUtils.clear(0,0,0,1);


        cameraController.update(delta, false);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, fps + "", 10, 200);
        player.draw(delta);
        game.batch.end();

        player.updateMovement(delta);

        if(state == State.RUNNING){
            Gdx.graphics.requestRendering();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
