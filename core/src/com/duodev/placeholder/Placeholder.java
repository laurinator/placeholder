package com.duodev.placeholder;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Placeholder extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        LevelScreen levelScreen = new LevelScreen(this);
        MainMenuScreen mainMenuScreen = new MainMenuScreen(this);
        this.setScreen(levelScreen);
    }

    public void render(){
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
