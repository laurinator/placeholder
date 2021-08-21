package com.duodev.placeholder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.EnumMap;

public class CharacterAnimationRenderer {

    private final SpriteBatch batch;
    private final Placeholder game;
    private final EnumMap<PlayerAnimationType, Animation<TextureRegion>> animationMap;

    float stateTime;

    public CharacterAnimationRenderer(Placeholder game, SpriteBatch batch){

        this.batch = batch;
        this.game = game;

        stateTime = 0f;

        animationMap = new EnumMap<>(PlayerAnimationType.class);

    }

    public void addAnimation(PlayerAnimationType type, Animation<TextureRegion> animation){

        animationMap.put(type, animation);

    }

    public void resetTimer(){

        stateTime = 0f;

    }

    public void render(PlayerAnimationType type, float playerX, float playerY){

        stateTime += Gdx.graphics.getDeltaTime();

        batch.draw(animationMap.get(type).getKeyFrame(stateTime, true), playerX, playerY);

    }

}