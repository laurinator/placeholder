package com.duodev.placeholder;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class CameraController {

    private final OrthographicCamera camera;
    private final Player player;
    public float cameraSpeed = 1f;
    public float speedAdaptability = 50f;
    public float cameraDistance = 100f;

    public CameraController(OrthographicCamera camera, Player player){
        this.camera = camera;
        this.camera.position.set(player.getMidpoint().x, player.getMidpoint().y, 0);
        this.player = player;
    }

    public void update(float delta, boolean mode){

        float diffX;
        float diffY;

        if(mode) {
            diffX = player.getMidpoint().x - camera.position.x;
            diffY = player.getMidpoint().y - camera.position.y;

        } else {

            diffX = player.getMidpoint().x + player.lookingDirection.x * cameraDistance - camera.position.x;
            diffY = player.getMidpoint().y + player.lookingDirection.y * cameraDistance - camera.position.y;

        }

        float newX = camera.position.x + (diffX * delta * cameraSpeed * (player.speed / speedAdaptability));
        float newY = camera.position.y + (diffY * delta * cameraSpeed * (player.speed / speedAdaptability));
        camera.position.set(newX, newY, 0);

    }

}