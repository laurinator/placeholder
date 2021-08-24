package com.duodev.placeholder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {

    private LevelScreen screen;

    public InputHandler(LevelScreen screen){

        this.screen = screen;

    }

    public void handleInput(){

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_0)){

            screen.state = State.PAUSED;

        }

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_9)){

            screen.state = State.RUNNING;

        }

    }

}
