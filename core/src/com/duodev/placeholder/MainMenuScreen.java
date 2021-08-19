package com.duodev.placeholder;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MainMenuScreen implements Screen {

    private final Placeholder game;
    private final OrthographicCamera camera;
    private ArrayList<Button> buttons;
    private Button testButton;


    public MainMenuScreen(final Placeholder game){

        this.game = game;
        camera = CameraFactory.createStandardCamera();
        buttons = new ArrayList<Button>();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.end();

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
