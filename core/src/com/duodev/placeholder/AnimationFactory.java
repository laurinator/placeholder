package com.duodev.placeholder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationFactory {

    public static Animation<TextureRegion> createAnimation(Texture texture, int instanceWidth, int instanceHeight, float frameTime){

        TextureRegion[] frames = TextureRegion.split(texture,  instanceWidth, instanceHeight)[0];

        return new Animation<TextureRegion>(frameTime, frames);

    }

}
