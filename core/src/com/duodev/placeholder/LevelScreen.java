package com.duodev.placeholder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelScreen implements Screen {

    final Placeholder game;
    final OrthographicCamera camera;
    final CameraController cameraController;

    Player player;

    public LevelScreen(final Placeholder game){

        this.game = game;

        camera = CameraFactory.createStandardCamera();

        player = new Player(this.game, 32, 32, 100, 100);

        cameraController = new CameraController(camera, player);

        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,1);

        cameraController.update(delta);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, MathUtils.floor(1 / delta) + "", 10, 200);
        player.draw(delta);
        game.batch.end();

        player.updateMovement(delta);

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
